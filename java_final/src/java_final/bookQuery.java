package java_final;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;


public class bookQuery {
	//declare a private variable for a Connection object. 
	private Connection conn;
	
	//I want this constructor to do one job of making a connection to the database. 
	//That way I avoid having perform this database connection every single time I develop a method within this class. 
	//And it will be connected when I instantiate this class. 
	public bookQuery() {
		//do a try catch statement to ensure that a mistake will be caught. 
		try {
			//create the connection to the database 
			conn = DriverManager.getConnection("jdbc:sqlite:bookstore.db");
		}
		catch (SQLException e) {
			//handle exceptions and errors. 
			e.printStackTrace();
			//send user appropriate error message if database connection failed.
			JOptionPane.showMessageDialog(null, "Could not connect to the bookstore database.");
		}
	}
	
	

	
	/*
	 * Method to display the books. 
	 * Takes three String parameters and displays the appropriate book to the User. 
	 * Returns a 2D array of Object Type. 
	 */
	public Object[][] displayBooks(String title, String author, String genre) throws SQLException {
		//declare my 2D empty array of object type. 
		Object[][] data = new Object[0][0];	
		
		//declare array list of 1D Object array type to store my data from the ResultSet. 
		List<Object[]> dataList = new ArrayList<>();
		
		//First part of my query written here. 
		String selectBook = "SELECT * FROM book";
		
		try {
			//instantiate my prepared statement here with the connection and my query. 
			PreparedStatement pstmt = conn.prepareStatement(selectBook);

			//Now we have dynamic SQL querying below. 
			
			//if the user provides title, author, and genre: 
			if (!title.isBlank() && !author.isBlank() && !genre.isBlank()) {
				//add this end portion of the query to the first query above. 
				selectBook += " WHERE book_title = ? AND book_author = ? AND book_genre = ?";
				//add to the prepared statement 
				pstmt = conn.prepareStatement(selectBook);
				//set the parameter values into the query. 
				pstmt.setString(1, title);
				pstmt.setString(2, author);
				pstmt.setString(3, genre);
			}
			
			//if user only provides the book title. 
			else if (!title.isBlank()) {
				selectBook += " WHERE book_title = ?";
				pstmt = conn.prepareStatement(selectBook);
				pstmt.setString(1, title);
				
			}
			
			//if user only provides the author. 
			else if (!author.isBlank()) {
				selectBook += " WHERE book_author = ?";
				pstmt = conn.prepareStatement(selectBook);
				pstmt.setString(1, author);
				
			}
			
			//if user only provides the book genre. 
			else if (!genre.isBlank()) {
				selectBook += " WHERE book_genre = ?";
				pstmt = conn.prepareStatement(selectBook);
				pstmt.setString(1, genre);
			}
			
			//if nothing is selected, send appropriate error message to user. 
			else {
				JOptionPane.showMessageDialog(null, "Please type either an author or a title or choose a genre to search for books!");
				//return the array
				return data;
			}
			
			//Use the result set to get the result after running the query. 
			ResultSet rs = pstmt.executeQuery();
			
			//now populate the 1D data array first. 
			while (rs.next()) {
				//set the size of each row. 
				Object[] row = new Object[4];
				
				//populate each row's index with the appropriate piece of information from the query result. 
				row[0] = rs.getString("book_id");
	            row[1] = rs.getString("book_title");
	            row[2] = rs.getString("book_author");
	            row[3] = rs.getString("book_price");
	            dataList.add(row);			
			}
			
			//close these objects to avoid database lock down. 
			rs.close();
			pstmt.close();			
			
			//Check if there is a non-existent book or author in the database. 
			//If so, then the dataList should remain empty because no book from the database table was placed into our result. 
			if (dataList.isEmpty()) {
				JOptionPane.showMessageDialog(null, "Sorry! This book title and/or author do not exist in our store. Please search again!");
				//return the empty array. 
				return data;
			}
			
		}
		catch  (SQLException e2) {
			e2.printStackTrace();
			//provide appropriate error message to user. 
			JOptionPane.showMessageDialog(null, "Could not search for your book due to database error.");
			System.exit(0);
		}
		
		//Convert the 1D arrayList to 2D arrayList.
		data = dataList.toArray(new Object[dataList.size()][]);
		
		//return data note: it has all four pieces of info for that book. 
		return data;
		
		}
	
}
//done