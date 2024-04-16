package java_final;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JOptionPane;


public class checkoutQuery {
	
	//declare a private variable for a Connection object and make this only accessible for this class. 
	private Connection conn;
	
	//class constructor to connect the our database right away. 
	public checkoutQuery() {
		//attempt to connect to the database. 
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
	 * Method to insert a payment record into the checkout table. 
	 * Accepts two parameters, one an Integer and other String. 
	 * We must know which customer for whom we need to insert a payment, which is the Integer parameter. 
	 * The String parameter is for the payment type the customer chooses from the combo box in the UI. 
	 */
	public void checkout(Integer custID, String payment) {
		
		//if the payment parameter is blank send a message saying you need payment. 
		if (payment.isBlank()) {
			//send the message to the user. 
			JOptionPane.showMessageDialog(null, "A payment option must be chosen to proceed.");
			return;
		}

		//the query to insert a new record into the checkout table. 
		String checkoutQuery = "INSERT INTO checkout (cust_id, check_paymentType, check_datetime) "
				+ "VALUES(?, ?, DATE())";
		
		try {
			//Because I am going to be putting in information from the user, I have to use PreparedStatement. 
			PreparedStatement pstmt = conn.prepareStatement(checkoutQuery);
			//Add each parameter value to the prepared statement. 
			pstmt.setString(1, Integer.toString(custID));
			pstmt.setString(2, payment);
			
			//once executed, find out how many rows have been affected. 
			int rows = pstmt.executeUpdate();
			//if successful there should be more than 0 rows affected. 
			
			if (rows > 0) {
				//send appropriate message to user saying their checkout was successful since the checkout table was populatd. 
				JOptionPane.showMessageDialog(null, "You have successfully checked out your selected book(s)!");
			}
			//if no rows were affected, no record was inserted, thus the checkout failed. 
			else {
				JOptionPane.showMessageDialog(null, "Unsuccessful checkout. Please try again.");
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