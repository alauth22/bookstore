package java_final;
import java.io.File;
import java.io.IOException;

public class testRun {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		
		/*
		 * NOTE TO TONY:
		 * To explore this bookstore application, please first register your own account. 
		 * IF you want to explore another person's account, you can register another one or explore mine. 
		 * My user name is alauth and my password is Sparky22?!. 
		 * 
		 * Thanks for a great java semester! I had a lot of fun developing this application. Still lots of 
		 * improvement to be made here, but I'm happy with how it turned out in 8 weeks. 
		 * 
		 * -Amelia 
		 */
		
		
		
		//Check if database file already exists.
		File fileDB = new File("bookstore.db");
		//If the database does not exist create it. 
		if (!fileDB.exists()) {
			//create a new database. 
			new bookstoreDB("bookstore.db");
		}
		
		//Fire up my GUI for the user to have fun!
		new GUI();
		
		
	}

}
//done