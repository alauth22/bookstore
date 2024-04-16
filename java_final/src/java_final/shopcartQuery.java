package java_final;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;


public class shopcartQuery {
	
	//declare a private variable for a Connection object and make this only accessible for this class. 
	private Connection conn;

	//class constructor to connect the our database right away. 
	public shopcartQuery() {
		
		try {
			//create the connection to the database 
			conn = DriverManager.getConnection("jdbc:sqlite:bookstore.db");
		}
		catch (SQLException e) {
			
			e.printStackTrace();
			//if the database connection fails, send user appropriate message.
			JOptionPane.showMessageDialog(null, "Unable to obtain your account. Please try again or investigate.");
		}
	}
	
	
	
	
	/*
	 * This method will insert a new record into the shopping cart. 
	 * It receives two string values which are the book ID and the customer ID, 
	 * so that we know which customer is currently logged into the application. 
	 */
	public void populateShoppingCart(String bookID, String custID) {
		
		//first we need to get our queries set and we want the current book price. 
		String ShopCartQuery = "INSERT INTO shopping_cart (book_id, cust_id, book_price) VALUES (?,?,?)";
		String bookPrice = "SELECT book_price FROM book WHERE book_id = ?";
		
		try(
			
			//get my prepared statements done for each, getting the book price and for the insertion. 
			//we need the query to get the book price from the book table because that is field in the 
			//shopping cart table, so we need to populate it dynamically with the book ID. 
			PreparedStatement price = conn.prepareStatement(bookPrice);
			//query to insert into the shopping cart table. 
			PreparedStatement shopCart = conn.prepareStatement(ShopCartQuery)) {
			
			//set the book id value in the book price query. 
			price.setString(1, bookID);
			//execute the book price query and set the result equal to the result set. 
			ResultSet rs = price.executeQuery();
			
			//if query execution was successful.
			if (rs.next()) {
				
				//get the book price into the double variable. 
				double bookprice = rs.getDouble("book_price");
				
				//now set the shopping cart insertion query with the proper parameter values. 
				shopCart.setString(1, bookID);
				shopCart.setString(2, custID);
				//here's where we can put our book price value into the shopping cart insertion query. 
				shopCart.setDouble(3, bookprice);
				
				//now execute the shopping cart insertion query. 
				shopCart.executeUpdate();
				//send the proper user message saying the query was successful and their book is now in their cart. 
				JOptionPane.showMessageDialog(null, "Added to your cart!");
				
				//close these objects to avoid database lock down. 
				shopCart.close();
				rs.close();
				price.close();
				
			}
			
			//if the query failed to execute because the book was not present. 
			else {
				
				//send the proper user error message. 
				JOptionPane.showMessageDialog(null, "This book ID does not exist in our store. Please type a different book ID.");
				
			}
			
		}
		//catch rest of exceptions and errors. 
		catch (SQLException e3) {
			e3.printStackTrace();
		}

	}
	
	
	
	
	/*
	 * Method to get the count or total number of books that are currently in the customer's shopping cart. 
	 * Receives the one parameter integer customer ID so that we know which customer is currently logged into t
	 * the application. It will return the integer book count. 
	 */
	public int getCount(Integer custID) {
		
		//our base query set equal to our string value. 
		String getSum = "SELECT COUNT(book_id) FROM shopping_cart WHERE cust_id = ?";
		
		//declare our integer variable for our book count which we will return. 
		int count = 0;
		
		try {
			
			//start my prepared statement to run the query.
			PreparedStatement pstmt = conn.prepareStatement(getSum);
			
			//set the parameter value into the query. 
			pstmt.setInt(1, custID);
			
			//execute the query and set the result equal to the Result Set. 
			ResultSet rs = pstmt.executeQuery();
			
			//if the execution of the query was successful. 
			if (rs.next()) {
				//then get the count within the result set. 
				count = rs.getInt(1);
			}
			
			//close these objects to avoid database lock down. 
			rs.close();
			pstmt.close();
			
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		//return the result count. 
		return count;
		
	}
	
	
	
	
	/*
	 * Method to get the current price of the total books within the customer's shopping cart. 
	 * Receives the Integer CustID value which tells us which customer is currently logged into the application. 
	 * Returns a double value for the total price. 
	 */
	public double getPrice(Integer custID) {
		
		//base query to select the sum of the book price field in the shopping cart table where the customer id equals 
		//the customer id in our parameter. 
		String getPrice = "SELECT SUM(book_price) FROM shopping_cart WHERE cust_id = ?";
		
		//declare our double variable that will be returned. 
		double finalPrice = 0;
		
		try {
			
			//set our prepared statement for our base query. 
			PreparedStatement pstmt = conn.prepareStatement(getPrice);
			//place our parameter within our query. 
			pstmt.setInt(1, custID);
			//execute our query and set equal to our result set. 
			ResultSet rs = pstmt.executeQuery();
			
			//if the query was successfully executed. 
			if (rs.next()) {
				
				//get the result. 
				finalPrice = rs.getDouble(1);
			}
			
			//close these objects to avoid database lock down. 
			rs.close();
			pstmt.close();
			
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		//return the sum final price of the books. 
		return finalPrice;

	}	
	
	
	
	
	/*
	 * Method to check if customer is actually in the shopping cart table or not. 
	 * This will aid in checking if that customer's cart is empty already or not. 
	 * Receive the current customer's ID to know who is logged in the application at this moment. 
	 * Returns a boolean value to say if the customer is in the shopping cart or not (true or false). 
	 */
	public boolean custIDInCart(Integer custID) {
		
		//base query to select the sum of the book price field in the shopping cart table where the customer id equals 
		//the customer id in our parameter. 
		String checkCart = "SELECT COUNT(*) FROM shopping_cart WHERE cust_id = ?";
		//boolean value declared and initialized as false. This will be returned. 
		boolean isValid = false;
		
		try {
			
			//get this prepared statement done. 
			PreparedStatement pstmt = conn.prepareStatement(checkCart);
			//put in the parameter variable into the query. 
			pstmt.setInt(1, custID);
			//execute our query and set equal to our result set. 
			ResultSet rs = pstmt.executeQuery();
			
			//if the query was successfully executed. 
			if(rs.next()) {
				//we want to get the count value if there are indeed records with that person's id. 
				int count = rs.getInt(1);
				//if the record count is greater than 0, then we return true!
				isValid = count > 0;
			}
			
			//close these objects to avoid database lock down. 
			rs.close();
			pstmt.close();
			
			
		}
		catch (SQLException e) {
			e.printStackTrace();
			System.exit(0);
		}
		
		//return the boolean value. 
		return isValid;
		
	}
	
	
	
	
	/*
	 * Method to clear all the customer's books from their cart.
	 * Receives the current customer's id and will delete the shopping cart table records 
	 * wherever the customer's id matches. 
	 */
	public void clearShopCart(Integer custID) {
		
		//this will completely clear the shopping cart wherever the customer's id is located. 
		String clearCart = "DELETE FROM shopping_cart WHERE cust_id = ?";
		
		try {
			
			//get my prepared statement ready with the query. 
			PreparedStatement pstmt = conn.prepareStatement(clearCart);
			//set the parameter value inside the query. 
			pstmt.setInt(1, custID);
			//execute the update. 
			pstmt.executeUpdate();
			
			//close this object to avoid database lock down. 
			pstmt.close();
			
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	/*
	 * Method to confirm if the customer has the book id they typed to remove from their cart. 
	 * This will ensure that the customer cannot type just any book ID in the book_ID text field in the UI. 
	 * If that book ID is not in that customer's cart, the proper error message will show. If that book 
	 * is in their cart, then the book will be removed with another method. 
	 */
	public boolean checkBook(String bookID, Integer custID) {
		
		//base query to select the sum of the book price field in the shopping cart table where the customer id equals 
		//the customer id and book id in our parameters. 
		String checkBookInCart = "SELECT COUNT(*) FROM shopping_cart WHERE book_id = ? AND cust_id = ?";
		//boolean value declared and initialized as false. This will be returned. 
		boolean isValid = false;
		
		try {
			
			//get our prepared statement. 
			PreparedStatement pstmt = conn.prepareStatement(checkBookInCart);
			
			//put in the parameter variables into the query. 
			pstmt.setString(1, bookID);
			pstmt.setInt(2, custID);
			//execute our query and set equal to our result set. 
			ResultSet rs = pstmt.executeQuery();
		
			//if the query successfully executed. 
			if(rs.next()) {
				//we want to get the count value if there are indeed records with that person's id. 
				int count = rs.getInt(1);
				//if the record count is greater than 0, then we return true!
				isValid = count > 0;
			}
			
			//close these objects to avoid database lock down. 
			rs.close();
			pstmt.close();
			
		}
		catch (SQLException e) {
			e.printStackTrace();
			System.exit(0);
		}
		
		//return the boolean value. 
		return isValid;
		
	}
	
	
	
	
	/*
	 * Method to remove a chosen book from the shopping cart. 
	 * Receives the Customer Id and the book id. 
	 */
	public void removeBook(String bookID, int custID) {
		
		//query that will remove or delete from the shopping cart table. 
		String removeBook = "DELETE FROM shopping_cart WHERE book_id = ? AND cust_id = ?";
		
		try {
			//get our prepared statement. 
			PreparedStatement pstmt = conn.prepareStatement(removeBook);
			//put in the parameter variables into the query. 
			pstmt.setString(1, bookID);
			pstmt.setInt(2, custID);
			//execute the query. 
			pstmt.executeUpdate();
			
			//close the object to avoid database lock down. 
			pstmt.close();
		}
		catch (SQLException e) {
			e.printStackTrace();
	
		}
	}
	
	
	
	
	/*
	 * Method to get all the books that were put into the customer's shopping cart.
	 * Will receive the current customer's id and return a 2D array of type Object.
	 * This will hold all the books that customer has put into their shopping cart but has not 
	 * yet purchased. 
	 */
	public Object[][] displayCustBooks(int custID) throws SQLException {
		
		//declare our Object[][]. 
		Object[][] data = new Object[0][0];
		
		//get our Array List of type Object[].
		List<Object[]> dataList = new ArrayList<>();
		
		//create our base query that will select all the records where the customer ID matches. 
		String querySelectBks = "SELECT book.book_id, book.book_title, book.book_author, book.book_price " +
                "FROM shopping_cart INNER JOIN book " +
                "ON shopping_cart.book_id = book.book_id " +
                "WHERE shopping_cart.cust_id = ?";
		
		
		try {
			//get our prepared statement going. 
			PreparedStatement pstmt = conn.prepareStatement(querySelectBks);
			//put in the parameter variable into the query. 
			pstmt.setInt(1, custID);
			//execute our query and set equal to our result set. 
			ResultSet rs = pstmt.executeQuery();
			
			//while we are able to get our results in our result set variable. 
			while (rs.next()) {
				
				//populate each row of type Object[], where we tell what values go into each index of that array. 
				Object[] row = new Object[4];
				row[0] = rs.getInt("book_id");
				row[1] = rs.getString("book_title");
				row[2] = rs.getString("book_author");
				row[3] = rs.getDouble("book_price");
				
				//then we add each row to our ArrayList. 
				dataList.add(row);
				
			}
			
			//avoid database lock down 
			rs.close();
			pstmt.close();
			
			
		}
		catch (SQLException e2) {
			e2.printStackTrace();
			JOptionPane.showMessageDialog(null, "Could not get your selected books due to database error.");
			System.exit(0);
		}
		
		//finally we convert our ArrayList to our array which was declared as "data". 
		data = dataList.toArray(new Object[dataList.size()][]);
		//return that final array. 
		return data;

	}

}
//done 