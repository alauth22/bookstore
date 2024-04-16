 package java_final;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;


public class customerQuery {
	
	//declare a private variable for a Connection object and make this only accessible for this class. 
	private Connection conn;
	
	//class constructor to connect the our database right away. 
	public customerQuery() {
		
		try {
			//create the connection to the database 
			conn = DriverManager.getConnection("jdbc:sqlite:bookstore.db");
		}
		catch (SQLException e) {
			e.printStackTrace();
			//if the database connection fails, send user appropriate message. 
			JOptionPane.showMessageDialog(null, "Unable to connect to the bookstore database.");
		}
	}
	
	
	
	
	/*
	 * Method to retrieve the current customer's ID. This result (CUSTID) will 
	 * be used within the GUI for all the frames as they take the CUSTID parameter. This 
	 * is my way of ensuring we are focused on the current customer who is logged into the application
	 * and we view only their information throughout the application. 
	 * 
	 * The method takes two String parameters which come from the login JFrame, and it
	 * returns an Integer which is the CUSTID. 
	 */
	public Integer getCustID(String username, String password) {
		
		//Declare my Integer variable to be returned. 
		Integer custID = null;
		//create our first half of our query. 
		String queryID = "SELECT cust_id FROM customer";
		
		try {
			//Instantiate an object of Prepared Statement with our query. 
			PreparedStatement pstmt = conn.prepareStatement(queryID);
			
			//now my dynamic querying begins
			
			//if the user name and password are both provided. 
			if (!username.isBlank() && !password.isBlank()) {
				//add this string portion to our query. 
				queryID += " WHERE cust_username = ? AND cust_password = ?";
				//now add this to the prepared statement. 
				pstmt = conn.prepareStatement(queryID);
				//add the parameters appropriately to the query. 
				pstmt.setString(1, username);
				pstmt.setString(2, password);
			}

			//if only the user name is provided. 
			else if (!username.isBlank()) {
				//add to the query queryID. 
				queryID += " WHERE cust_username = ?";
				//add to the prepared statement. 
				pstmt = conn.prepareStatement(queryID);
				//add the parameter appropriately to the query. 
				pstmt.setString(1, username);
			}
			
			//now if only the password is provided
			else if (!password.isBlank()) {
				//add to the query queryID. 
				queryID += " WHERE cust_password = ?";
				//add to the prepared statement.  
				pstmt = conn.prepareStatement(queryID);
				//add the parameter appropriately to the query. 
				pstmt.setString(1, password);
			}
			
			//get the result set. 
			ResultSet rs = pstmt.executeQuery();
			//get the custID from the result set. 
			custID = rs.getInt("cust_id");
			
			//close these objects to avoid database lock down. 
			rs.close();
			pstmt.close();
			
		}
		catch (SQLException e) {
			e.printStackTrace();
			//provide appropriate user error message if account is not obtainable. 
			JOptionPane.showMessageDialog(null, "Unable to obtain your account. Please try again or investigate.");
			System.exit(0);
		}
		
		//finally return the custID. 
		return custID;
		
	}
		
	
	
	
	/*
	 * Method to display the customer's information in the AccountFrame. 
	 * Accepts one Integer parameter which is the custID, this will help keep track 
	 * of which customer is the current customer logged into the application. 
	 * Will return a String Value which is the customer's first name. 
	 */
	public String getCustFName(Integer custID) {
		
		//declare my string variable for this method that will be returned. 
		String custFName = null;
		//begin my string query that will search for the customer's first name in the customer table. 
		String query = "SELECT cust_fname FROM customer WHERE cust_id = ?";
		
		try {
			//begin my prepared statement. 
			PreparedStatement pstmt = conn.prepareStatement(query);
			//set the ID to the statement but first convert the integer to a string. 
			pstmt.setString(1, Integer.toString(custID));
			
			//now execute the query and get the result.  
			ResultSet rs = pstmt.executeQuery();
			//grab  the customer's first name from the result. 
			custFName = rs.getString("cust_fname");
			
			//close these objects to avoid database lock down. 
			rs.close();
			pstmt.close();
			
		}
		catch (SQLException e) {
			e.printStackTrace();
			//provide error message in case account is not obtainable. 
			JOptionPane.showMessageDialog(null, "Unable to obtain your account. Please try again or investigate.");
			System.exit(0);
		}
		
		return custFName;
	}
	
	
	
	
	/*
	 * Method to get the customer's last name for the account frame. 
	 * Accepts one Integer parameter which is the customer's id, to keep 
	 * track of the current customer logged in the application. 
	 * Returns a String value for the last name. 
	 */
	public String getCusLFName(Integer custID) {
		
		//Declare my String variables to be returned. 
		String custLName = null;
		//set my query for selecting the customer's last name. 
		String query = "SELECT cust_lname FROM customer WHERE cust_id = ?";
		
		try {
			
			//my prepared statement. 
			PreparedStatement pstmt = conn.prepareStatement(query);
			//set the Integer parameter to the statement. 
			pstmt.setString(1, Integer.toString(custID));
			
			//now execute the query into the result. 
			ResultSet rs = pstmt.executeQuery();
			custLName = rs.getString("cust_lname");
			
			//close objects to avoid database lock down. 
			rs.close();
			pstmt.close();
			
		}
		catch (SQLException e) {
			e.printStackTrace();
			//user error message. 
			JOptionPane.showMessageDialog(null, "Unable to obtain your account. Please try again or investigate.");
			System.exit(0);
		}
		
		return custLName;
	}
	
	

	
	/*
	 * Method to get the customer's email for the account frame. 
	 * Receives Integer parameter which is the customer ID and returns
	 * a String value which is the customer's email. 
	 */
	public String getCustEmail(Integer custID) {
		
		//Declare my String value to be returned for the email. 
		String custEmail = null;
		//Create my String query to select the email from the customer table. 
		String query = "SELECT cust_email FROM customer WHERE cust_id = ?";
		
		try {
			
			//my prepared statement for the query above. 
			PreparedStatement pstmt = conn.prepareStatement(query);
			//put the parameter into the query, but first convert Integer to string. 
			pstmt.setString(1, Integer.toString(custID));
			
			//now execute the query and set equal to the result set. 
			ResultSet rs = pstmt.executeQuery();
			//obtain the email from the result and set equal to our returned variable. 
			custEmail = rs.getString("cust_email");
			
			//close these objects to avoid database lock down. 
			rs.close();
			pstmt.close();
			
		}
		catch (SQLException e) {
			e.printStackTrace();
			//user error message. 
			JOptionPane.showMessageDialog(null, "Unable to obtain your account. Please try again or investigate.");
			System.exit(0);
		}
		
		//return the customer's current email. 
		return custEmail;
	}
	
	
	//DONE
	/*
	 * Method to get the customer's phone for the account frame. 
	 */
	
	public String getCustPhone(Integer custID) {
		String custPhone = null;
		String query = "SELECT cust_phone FROM customer WHERE cust_id = ?";
		
		try {
			//connection to the database first off. 
			//Connection conn = DriverManager.getConnection("jdbc:sqlite:test.db");
			//prepared statement now
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setString(1, Integer.toString(custID));
			
			//now execute the query. 
			ResultSet rs = pstmt.executeQuery();
			custPhone = rs.getString("cust_phone");
			
			rs.close();
			pstmt.close();
			//conn.close();
		}
		catch (SQLException e) {
			e.printStackTrace();
			//System.out.println("could not get the customer's email for the account frame.");
			JOptionPane.showMessageDialog(null, "Unable to obtain your account. Please try again or investigate.");
			System.exit(0);
		}
		
		return custPhone;
	}
	
	
	

	/*
	 * Method to do an insertion into the customer table. 
	 * This happens when a new customer comes and wants to register an account. 
	 * It receives all the fields for the table, all of which are not null. 
	 */
	public void insertCust(String fname, String lname, String email, String phone, String username, String password) throws SQLException {
		
		//declare my string query to insert into the customer table a new customer record. 
		String insertCust = "INSERT INTO customer (cust_fname, cust_lname, cust_email, cust_phone, "
				+ "cust_username, cust_password)"
				+ "VALUES (?, ?, ?, ?, ?, ?)";
		
		{
			//my prepared statement 
			PreparedStatement pstmt = conn.prepareStatement(insertCust);
			//add the parameter values to the query in the correct order. 
			pstmt.setString(1, fname);
			pstmt.setString(2, lname);
			pstmt.setString(3, email);
			pstmt.setString(4, phone);
			pstmt.setString(5, username);
			pstmt.setString(6, password);
			
			//now execute the query. If it has executed the number of rows will be greater than 0. 
			int rows = pstmt.executeUpdate();
			if (rows > 0)
			{
				//display proper user message if the account was created. 
				JOptionPane.showMessageDialog(null, "Your account has successfully been made! Please login to start exploring our books.");
				
			}
			else {
				//Display proper user message if the account could not be created. 
				JOptionPane.showMessageDialog(null, "Sorry, your account could not be successfully made. Please try again.");
			}
			
			//close the object to avoid database lock down. 
			pstmt.close();
			
		}
		
	}
	

	
	
	/*
	 * Method for updating the customer's information. 
	 * This method will receive all the information as when creating the record and will update
	 * accordingly in the customer table. 
	 */
	public void updateCustInfo(Integer custID, String fname, String lname, String email, String phone, String passwrd) {
		
		//start with base query. 
		String updateUserInfo = "UPDATE customer SET ";
		
		//need an array list to add to the base query if the customer decides to update more than one field of their account. 
		ArrayList<String> updateCustFields = new ArrayList<>();
		
		//add to this array list depending on the dynamic SQL querying. We want if statements because we want the user 
		//to be able to update any if not all the fields at once. 
		if (fname != null && !fname.isBlank()) {
			updateCustFields.add("cust_fname = ?");
		}
		if (lname != null && !lname.isBlank()) {
			updateCustFields.add("cust_lname = ?");
		}
		if (email != null && !email.isBlank()) {
			updateCustFields.add("cust_email = ?");
		}
		if (phone != null && !phone.isBlank()) {
			updateCustFields.add("cust_phone = ?");
		}
		if (passwrd != null && !passwrd.isBlank()) {
			updateCustFields.add("cust_password = ?");
		}
		
		//we join our field values through the join method. 
		String updateFields = String.join(",", updateCustFields);
		
		//combine both String values for the final query 
		updateUserInfo += updateFields + " WHERE cust_id = ?";
		
		
		try {
			
			//get my parepared statement ready with the query. 
			PreparedStatement pstmt = conn.prepareStatement(updateUserInfo);
			
			//need this parameter since we do not know which field will be filled and therefore which 
			//number it needs to be added towards in the SQL query itself. 
			int parameterIndex = 1;
	        if (fname != null && !fname.isBlank()) {
	            pstmt.setString(parameterIndex++, fname);
	        }
	        if (lname != null && !lname.isBlank()) {
	            pstmt.setString(parameterIndex++, lname);
	        }
	        if (email != null && !email.isBlank()) {
	            pstmt.setString(parameterIndex++, email);
	        }
	        if (phone != null && !phone.isBlank()) {
	            pstmt.setString(parameterIndex++, phone);
	        }
	        if (passwrd != null && !passwrd.isBlank()) {
	        	pstmt.setString(parameterIndex++, passwrd);
	        }
	        
	        //finally set the parameter index variable which is automatically set to 1 for the CUSTID, because 
	        //that is the primary key and will always be 1. 
	        pstmt.setInt(parameterIndex, custID);
			
			//execute the query and set it equal to the number of rows. 
			int rows = pstmt.executeUpdate();
			
			//if the number of rows is greater than 0, then the query successfully executed. 
			if (rows > 0) {
				JOptionPane.showMessageDialog(null, "Your information was successfully updated!");
			}
			else {
				JOptionPane.showMessageDialog(null, "Your information could not be successfully updated. Please try again!");
			}
			
			//close this object to avoid database lock down. 
			pstmt.close();
			
		}
		catch (SQLException e3) {
			e3.printStackTrace();
		}
		
	}
	
}
//done