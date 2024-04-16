package java_final;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class loginQuery {
	
	//declare a private variable for a Connection object and make this only accessible for this class. 
	private Connection conn;
	
	//class constructor to connect the our database right away. 
	public loginQuery() {
		
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
	 * Method for login.  
	 * Will receive the string parameters user name and password which will then be used to select 
	 * the customer in the customer table if they exist. 
	 * Will catch if the user already exists or not.
	 * If they do not exist, then a message will ask the customer to please register. 
	 * This method will return a boolean value saying if the customer is present or not (true or false). 
	 */
	public boolean ValidateLogin(String username, String password) {
		
		//Check first if the two text fields are empty or not. 
		if (username.isBlank() || password.isBlank()) {
			//if the user does not put any value into the user name and passwords fields, then the user will receive 
			//an error message saying invalid user name and password. 
			JOptionPane.showMessageDialog(null, "Invalid username and/or password. Please login again or register an account!");
			return false;
		}
		
		//now begin my base query to select from the customer. 
		String login = "SELECT * FROM customer";
		//declare my boolean value that will be returned. 
		boolean isValid = false;
		
		try {
			
			//Instantiate an object of Prepared Statement with our query. 
			PreparedStatement pstmt = conn.prepareStatement(login);
			
			//now my dynamic querying begins
			//if the user name and password are both provided. 
			if (!username.isBlank() && !password.isBlank()) {
				
				//have my base query have this String value added to it. 
				login += " WHERE cust_username = ? AND cust_password = ?";
				//now add this to my statement. 
				pstmt = conn.prepareStatement(login);
				//set parameter values in the query. 
				pstmt.setString(1, username);
				pstmt.setString(2, password);
			}
				
			//now execute the query and set equal to the result set. 
			ResultSet rs = pstmt.executeQuery();
			
			//if the query executed, we will have values in the result set. 
			if (rs.next()) {
				//set the boolean value to true. 
				isValid = true;
			}
			//if the customer is NOT in the customer table with that user name and password, then send appropriate error message. 
			else {
				JOptionPane.showMessageDialog(null, "Invalid username and/or password. Please login again or register an account!");
			}
			
			//close the objects to avoid database lock down. 
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
	
}
//done. 