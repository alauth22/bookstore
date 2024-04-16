package java_final;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.SQLException;
import org.sqlite.SQLiteDataSource;


public class bookstoreDB {
	
	/*
	 * Constructor for bookstoreDB class. Requires a string parameter which will be the name of the database. 
	 * The string name will be automatically filled in upon run time within the system, so no user input.
	 * Here I put all the tables inside because I want every table to be created once the class runs. 
	 */
	public bookstoreDB(String dbName) {
		
		//Data source is the path to a database file. This will allow us to create our actual database.  
		//set this instance of the object to null.
		SQLiteDataSource datasource = null;
		
		//need our string path for our database, the dbName will be the string value of the programmer wants
		//when an instance of this class is instantiated. Did some research and learned that "jdbc:sqlite:" + your 
		//database name will put your local database in the current directory. 
		String pathnameDB = "jdbc:sqlite:" + dbName;
		
		try {
			//get new instance of the data source. 
			datasource = new SQLiteDataSource();
			
			//now tell the source where the database is through the setURL method. 
			//this tells the driver we're going to connect to a database that is called test.db
			datasource.setUrl(pathnameDB);
			
		}
		//try to catch all the exceptions 
		catch (Exception e)
		{
			e.printStackTrace();
			System.exit(0);			
		}	
	
		
		//CODE FOR THE FOUR TABLES ARE BELOW: 
		//Purposefully put these table creations inside the constructor since I want these tables made immediately. 
		
		
		/*
		 * CUSTOMER TABLE
		 * CUST_ID (PK)
		 * CUST_FNAME
		 * CUST_LNAME
		 * CUST_EMAIL
		 * CUST_PHONE
		 * CUST_USERNAME
		 * CUST_PASSWORD
		 */
		try {
			
			//Connection object needed to connect to the database. 
			Connection conn = DriverManager.getConnection(pathnameDB);
			
			//create statement to perform our query on the database. 
			Statement stmt = conn.createStatement();
			//full query for customer table. 
			String createCust = "CREATE TABLE IF NOT EXISTS customer (\n"
					+ "	cust_id integer PRIMARY KEY AUTOINCREMENT, \n"
					+ "cust_fname text NOT NULL, \n"
					+ "cust_lname text NOT NULL, \n"
					+ "cust_email varchar(250) NOT NULL, \n"
					+ "cust_phone varchar(250) NULL, \n"
					+ "cust_username text NOT NULL, \n"
					+ "cust_password text NOT NULL);";
			
			//execute query. 
			stmt.executeUpdate(createCust);
			
		}
		//catch exceptions and errors. 
		catch (SQLException e) {
			e.printStackTrace();
			System.exit(0);	
		}
		
		
		
		
		/*
		 * BOOK TABLE
		 * BOOK_ID PK
		 * BOOK_TITLE
		 * BOOK_AUTHOR
		 * BOOK_PRICE
		 */
		try 
		{
			//same connection here and statement.  
			Connection conn = DriverManager.getConnection(pathnameDB);
			Statement stmt = conn.createStatement();
			
			//Query for the book table creation. 
			String createBook = "CREATE TABLE IF NOT EXISTS book (\n"
					+ "book_id integer PRIMARY KEY AUTOINCREMENT, \n"
					+ "book_title text NOT NULL, \n"
					+ "book_author text NOT NULL, \n"
					+ "book_price real NOT NULL, \n"
					+ "book_genre text NOT NULL);";
			//execute the query. 	
			stmt.executeUpdate(createBook);
			//System.out.println("Book table created.");
		}
		catch (SQLException e) {
			e.printStackTrace();
			System.exit(0);
		}
		
		
		
		
		/*
		 * CREATE CHECKOUT TABLE. 
		 * CHECK_ID (PK)
		 * CUST_ID (FK)
		 * CHECK_PAYMENT_TYPE
		 * CHECK_DATETIME
		 */
		try {
			//Connection to the database and statement.  
			Connection conn = DriverManager.getConnection(pathnameDB);
			Statement stmt = conn.createStatement();
			
			//actual query for the checkout table creation. 
			String createCheckout = "CREATE TABLE IF NOT EXISTS checkout (\n"
					+ "check_id integer PRIMARY KEY AUTOINCREMENT, \n"
					+ "cust_ID integer NOT NULL, \n"
					+ "check_paymentType varchar(250) NOT NULL, \n"
					+ "check_datetime datetime NOT NULL, \n"
					+ "FOREIGN KEY(cust_ID) REFERENCES customer(cust_id));";
			
			//query executed. 
			stmt.executeUpdate(createCheckout);
		}
		catch (SQLException e) {
			e.printStackTrace();
			System.exit(0);
			}
		
		
		
		
		/*
		 * SHOPPING_CART TABLE
		 * I think this will be my bridge table
		 * BOOK_ID (PK, FK)
		 * CUST_ID (PK, FK)
		 * 
		 */
		try 
		{
			//connection to the database and statement. 
			Connection conn = DriverManager.getConnection(pathnameDB);
			Statement stmt = conn.createStatement();
			
			//query for the shopping cart table creation. 
			String createShopCart = "CREATE TABLE IF NOT EXISTS shopping_cart (\n"
					+ "book_id integer NOT NULL, \n"
					+ "cust_ID integer NOT NULL, \n"
					+ "book_price real NOT NULL, \n"
					+ "FOREIGN KEY (book_id) references book (book_id), \n" 
					+ "FOREIGN KEY (cust_ID) references customer (cust_id)); \n";
			//query executed. 
			stmt.executeUpdate(createShopCart);
		}
		catch (SQLException e) {
			e.printStackTrace();
			System.exit(0);
		}
		
		
		
		
		//Declare an array list to hold the books that I want to insert into my book table. 
		ArrayList<String> availBooks = new ArrayList<>();
		
		//all my queries to insert each book into my arrayList to eventually display 
		//in my model table in the GUI. 
		availBooks.add("INSERT INTO book (book_title, book_author, book_price, book_genre) VALUES ('Love, Theoretically', 'Ali Hazelwood', 19.99, 'Romance')");
		availBooks.add("INSERT INTO book (book_title, book_author, book_price, book_genre) VALUES ('The Love Hypothesis', 'Ali Hazelwood', 17.99, 'Romance')");
		availBooks.add("INSERT INTO book (book_title, book_author, book_price, book_genre) VALUES ('Check & Mate', 'Ali Hazelwood', 17.99, 'Romance')");
		availBooks.add("INSERT INTO book (book_title, book_author, book_price, book_genre) VALUES ('The Uncharted Flight of Olivia West', 'Sara Ackerman', 20.99, 'Historical Fiction')");
		availBooks.add("INSERT INTO book (book_title, book_author, book_price, book_genre) VALUES ('The Codebreaker''s Secret', 'Sara Ackerman', 20.99, 'Historical Fiction')");
		availBooks.add("INSERT INTO book (book_title, book_author, book_price, book_genre) VALUES ('Radar Girls', 'Sara Ackerman', 18.99, 'Historical Fiction')");
		availBooks.add("INSERT INTO book (book_title, book_author, book_price, book_genre) VALUES ('Book Lovers', 'Emily Henry', 18.99, 'Romance')");
		availBooks.add("INSERT INTO book (book_title, book_author, book_price, book_genre) VALUES ('The Hating Game', 'Sally Thorne', 17.99, 'Romance')");
		availBooks.add("INSERT INTO book (book_title, book_author, book_price, book_genre) VALUES ('A Court of Thornes and Roses', 'Sarah J. Maas', 20.99, 'Fantasy')");
		availBooks.add("INSERT INTO book (book_title, book_author, book_price, book_genre) VALUES ('A Court of Mist and Fury', 'Sarah J. Maas', 20.99, 'Fantasy')");
		availBooks.add("INSERT INTO book (book_title, book_author, book_price, book_genre) VALUES ('A Court of Wings and Ruin', 'Sarah J. Maas', 20.99, 'Fantasy')");
		availBooks.add("INSERT INTO book (book_title, book_author, book_price, book_genre) VALUES ('A Court of Frost and Starlight', 'Sarah J. Maas', 20.99, 'Fantasy')");
		availBooks.add("INSERT INTO book (book_title, book_author, book_price, book_genre) VALUES ('A Court of Silver Flames', 'Sarah J. Maas', 20.99, 'Fantasy')");
		availBooks.add("INSERT INTO book (book_title, book_author, book_price, book_genre) VALUES ('Her Hidden Genius', 'Marie Benedict', 14.99, 'Historical Fiction')");
		availBooks.add("INSERT INTO book (book_title, book_author, book_price, book_genre) VALUES ('The Mystery of Mrs. Christie', 'Marie Benedict', 14.99, 'Historical Fiction')");
		availBooks.add("INSERT INTO book (book_title, book_author, book_price, book_genre) VALUES ('The Personal Librarian', 'Marie Benedict', 16.99, 'Historical Fiction')");
		availBooks.add("INSERT INTO book (book_title, book_author, book_price, book_genre) VALUES ('Carnegie''s Maid', 'Marie Benedict', 17.99, 'Historical Fiction')");
		availBooks.add("INSERT INTO book (book_title, book_author, book_price, book_genre) VALUES ('The Other Einstein', 'Marie Benedict', 17.99, 'Historical Fiction')");
		availBooks.add("INSERT INTO book (book_title, book_author, book_price, book_genre) VALUES ('Half Life', 'Jillian Cantor', 20.99, 'Historical Fiction')");
		availBooks.add("INSERT INTO book (book_title, book_author, book_price, book_genre) VALUES ('The Lost Letter', 'Jillian Cantor', 20.99, 'Historical Fiction')");
		availBooks.add("INSERT INTO book (book_title, book_author, book_price, book_genre) VALUES ('The Help', 'Kathryn Stockett', 20.99, 'Historical Fiction')");
		availBooks.add("INSERT INTO book (book_title, book_author, book_price, book_genre) VALUES ('All the Light We Cannot See', 'Anthony Doerr', 20.99, 'Historical Fiction')");
		availBooks.add("INSERT INTO book (book_title, book_author, book_price, book_genre) VALUES ('The Four Loves', 'C.S. Lewis', 17.99, 'Non-Fiction')");
		availBooks.add("INSERT INTO book (book_title, book_author, book_price, book_genre) VALUES ('Wuthering Heights', 'Emily Bronte', 21.99, 'Fiction')");
		availBooks.add("INSERT INTO book (book_title, book_author, book_price, book_genre) VALUES ('Pride and Prejudice', 'Jane Austen', 18.99, 'Romance')");
		availBooks.add("INSERT INTO book (book_title, book_author, book_price, book_genre) VALUES ('Emma', 'Jane Austen', 18.99, 'Romance')");
		availBooks.add("INSERT INTO book (book_title, book_author, book_price, book_genre) VALUES ('Persuasion', 'Jane Austen', 18.99, 'Romance')");
		availBooks.add("INSERT INTO book (book_title, book_author, book_price, book_genre) VALUES ('Sense and Sensibility', 'Jane Austen', 18.99, 'Romance')");
		availBooks.add("INSERT INTO book (book_title, book_author, book_price, book_genre) VALUES ('Flyboys: A True Story of Courage', 'James D. Bradley', 21.99, 'Non-Fiction')");
		availBooks.add("INSERT INTO book (book_title, book_author, book_price, book_genre) VALUES ('Flags of Our Fathers', 'James D. Bradley', 21.99, 'Non-Fiction')");
		availBooks.add("INSERT INTO book (book_title, book_author, book_price, book_genre) VALUES ('The Immortal Life of Henrietta Lacks', 'Rebecca Skloot', 20.99, 'Non-Fiction')");
		availBooks.add("INSERT INTO book (book_title, book_author, book_price, book_genre) VALUES ('The Last Rose of Shanghai', 'Weina Dai Randel', 18.99, 'Historical Fiction')");
		availBooks.add("INSERT INTO book (book_title, book_author, book_price, book_genre) VALUES ('Killing the Witches: The Horror of Salem, Massachusetts', 'Bill O''Reilly', 18.99, 'Non-Fiction')");
		availBooks.add("INSERT INTO book (book_title, book_author, book_price, book_genre) VALUES ('Boy''s Life', 'Robert McCammon', 18.99, 'Thriller')");
		availBooks.add("INSERT INTO book (book_title, book_author, book_price, book_genre) VALUES ('The Abominable', 'Dan Simmons', 19.99, 'Thriller')");
		availBooks.add("INSERT INTO book (book_title, book_author, book_price, book_genre) VALUES ('A Deadly Influence', 'Mike Omer', 18.99, 'Thriller')");
		availBooks.add("INSERT INTO book (book_title, book_author, book_price, book_genre) VALUES ('Presumed Innocent', 'Scott Turow', 17.99, 'Thriller')");
		availBooks.add("INSERT INTO book (book_title, book_author, book_price, book_genre) VALUES ('The Burden of Proof', 'Scott Turow', 17.99, 'Thriller')");
		availBooks.add("INSERT INTO book (book_title, book_author, book_price, book_genre) VALUES ('Divine Rivals', 'Rebecca Ross', 18.99, 'Fantasy')");
		availBooks.add("INSERT INTO book (book_title, book_author, book_price, book_genre) VALUES ('Ruthless Vows', 'Rebecca Ross', 18.99, 'Fantasy')");
		availBooks.add("INSERT INTO book (book_title, book_author, book_price, book_genre) VALUES ('One Dark Window', 'Rachel Gillig', 19.99, 'Fantasy')");
		availBooks.add("INSERT INTO book (book_title, book_author, book_price, book_genre) VALUES ('Two Twisted Crowns', 'Rachel Gillig', 19.99, 'Fantasy')");
		availBooks.add("INSERT INTO book (book_title, book_author, book_price, book_genre) VALUES ('The Martian', 'Andy Weir', 18.99, 'Science Fiction')");
		availBooks.add("INSERT INTO book (book_title, book_author, book_price, book_genre) VALUES ('Fahrenheit 451', 'Ray Bradbury', 18.99, 'Science Fiction')");
		availBooks.add("INSERT INTO book (book_title, book_author, book_price, book_genre) VALUES ('The Time Machine', 'H.G. Wells', 16.99, 'Science Fiction')");
		availBooks.add("INSERT INTO book (book_title, book_author, book_price, book_genre) VALUES ('All Systems Red', 'Martha Wells', 18.99, 'Science Fiction')");
		availBooks.add("INSERT INTO book (book_title, book_author, book_price, book_genre) VALUES ('2001: A Space Odyssey', 'Arthur C. Clarke', 18.99, 'Science Fiction')");
		availBooks.add("INSERT INTO book (book_title, book_author, book_price, book_genre) VALUES ('Dark Matter', 'Blake Crouch', 18.99, 'Science Fiction')");
		availBooks.add("INSERT INTO book (book_title, book_author, book_price, book_genre) VALUES ('The Left Hand of Darkness', 'Ursula K. Le Guin', 18.99, 'Science Fiction')");
		
		//now we have to execute all these insert queries that are in the array List. 
		try 
		{
			//ensure I have the connection to the database. 
			Connection conn = DriverManager.getConnection(pathnameDB);
			Statement stmt = conn.createStatement();
			
			//now I want to loop through each input in the array and execute it. 
			for (int i = 0; i < availBooks.size(); i++) {
				stmt.executeUpdate(availBooks.get(i));	
			}
			
		}
		catch (SQLException e) {
			e.printStackTrace(); 
	
		}
		
	}
	
}
//done