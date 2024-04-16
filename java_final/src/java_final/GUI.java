package java_final;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.SQLException;
import org.sqlite.SQLiteDataSource;


/*
 * After my research, I have learned that I need to use both JFrame and JPanel. 
 * JFrame = a framed window 
 * JPanel = is an area where controls (buttons, check boxes, and text fields can be placed) 
 * want this GUI class to have the JFrame, found this way of doing it besides JFrame myFrame = new JFrame()
 */

//want this class to extend or inherit from the JFrame class. 
public class GUI extends JFrame { 
	
	
	//for serialized objects or objects that are converted into a series of bytes. 
	private static final long serialVersionUID = 1L;
	//my integer variable that will keep track of the customer ID, or the current customer that is 
	//logged into the application. This CUSTID will be used by all the JFrames and almost all the methods. 
	protected static final int CUSTID = 0;


	/*
	 * Constructor for GUI. I want to immediately connect to the database and 
	 * immediately show the login JFrame upon running this class. 
	 * That way we are at the beginning of the application for the user. 
	 */
	public GUI () {
		
		//create the data source to connect to my database. 
		SQLiteDataSource ds = null;
		
		//now begin my try and catch. 
		try {
			
			//set my data source equal to a new object of that class. 
			ds = new SQLiteDataSource();
			//I just typed the entire database out here. 
			ds.setUrl("jdbc:sqlite:bookstore.db");
		}
		catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
		
	
		
		//HERE IS THE BEGINNING OF MY GUI
		//set up my login JFrame.  
		setTitle("Literary Torch Bookstore");
		setSize(500, 450);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
		//now we want the JPanel to put the controls on. 
		JPanel homePanel = new JPanel();
		//make layout of the GUI null so that I can move the buttons around. 
		homePanel.setLayout(null);
		
		//make the labels here. 
		JLabel welcomeLabel = new JLabel("Welcome to Literary Torch Bookstore");
		//set the bounds for welcombLabel. 
		welcomeLabel.setBounds(80, 50, 400, 70);
		//change the font for welcome label. 
		welcomeLabel.setFont(new Font ("Arial", Font.BOLD, 18));
		
		JLabel homeLabel2 = new JLabel("Please login in to start exploring our books!");
		homeLabel2.setBounds(90, 100, 400, 70);
		homeLabel2.setFont(new Font("Arial", Font.BOLD, 14));
		
		
		//CUSTOMER LOGIN LABELS. 
		JLabel userCust = new JLabel("Username:");
		userCust.setBounds(100, 180, 100, 30);
		userCust.setFont(new Font("Arial", Font.PLAIN, 14));
		
		//CUSTOMER login for text field. 
		JTextField usernameCust = new JTextField();
		usernameCust.setBounds(100, 210, 100, 30);
			
		//password label. 
		JLabel passCust = new JLabel("Password:");
		passCust.setBounds(270, 180, 100, 30);
		passCust.setFont(new Font("Arial", Font.PLAIN, 14));
		
		//text box for password. 
		JPasswordField passwrdCust = new JPasswordField();
		passwrdCust.setBounds(270, 210, 100, 30);
		
		//login button and its entire method. 
		JButton loginBtn = new JButton("Login");
		//set the bounds for the customer button.
		loginBtn.setBounds(195, 270, 80, 30);
		//ActionListener -> give these button another task once it is clicked. 
		loginBtn.addActionListener(new ActionListener() {
			@Override 
			public void actionPerformed(ActionEvent e) {
	
				//get the string user name and password values. 
				String username = usernameCust.getText();
				@SuppressWarnings("deprecation")
				String password = passwrdCust.getText();
				
				//make an object of our loginQuery class so that we can call the 
				//validate login method. 
				loginQuery loginquery = new loginQuery();
				
				//if the result of that query is true. 
				if (loginquery.ValidateLogin(username, password) == true) {
					
					//instantiate a new object of this class to get our customer's id through the proper method. 
					customerQuery custquery = new customerQuery();
					//get the customer's id and put result into proper variable. 
					int CUSTID = custquery.getCustID(username, password);
					
					//set both text fields to blank right away. 
					usernameCust.setText("");
					passwrdCust.setText("");
					
					//Make my current JFrame invisible. 
					setVisible(false);
					//open the customer frame and make this only visible. 
					CustFrame(CUSTID);
					
				}
				//if the login validation fails.
				else {
					//the method will send the proper user error message. 
					//clear our the textFields and reset the focus on the first text field. 
					usernameCust.setText("");
					usernameCust.requestFocus();
					passwrdCust.setText("");
				}
				
			}	
			
		});
		
		
		//register button. 
		JButton registCust = new JButton("Register An Account");
		registCust.setBounds(155, 330, 160, 30);
		//allow register button to open the register frame. 
		registCust.addActionListener(new ActionListener() {
			@Override 
			public void actionPerformed(ActionEvent e) {
				//make the login page invisible. 
				setVisible(false);
				//only show the registration frame. 
				RegistFrame();
			}	
			
		});
		
		
		//ADD ALL COMPONENETS TO THE JPANEL AND THEN TO THE JFRAME. 
		homePanel.add(loginBtn);
		homePanel.add(welcomeLabel); 
		homePanel.add(homeLabel2);
		homePanel.add(passwrdCust);
		homePanel.add(usernameCust);
		homePanel.add(userCust);
		homePanel.add(passCust);
		homePanel.add(registCust);
		//add panel to frame. 
		add(homePanel);
		setVisible(true);
	}
	
	
	
	
	/*
	 * Method for the construction of the Registration JFrame. 
	 */
	private void RegistFrame() {
		
		//declare my JFrame for registration. 
		JFrame registFrame = new JFrame("Registration");
        registFrame.setSize(500, 550);
        registFrame.setResizable(false);
        registFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
        
        //declare my JPanel for this frame. 
        JPanel acctPanel = new JPanel();
        //learned the hard way that you need this panel to be null for you 
        //to be able to add components to it. 
		acctPanel.setLayout(null);
		
		//START ADDING ALL MY JLABELS BELOW. 
		JLabel instrut = new JLabel("Please fill in all the information below to register.");
		instrut.setBounds(30, 30, 400, 30);;
		
		JLabel fname = new JLabel("First Name:");
		fname.setBounds(30, 70, 200, 30);
		
		JTextField fnameTxt = new JTextField();
		fnameTxt.setBounds(30, 100, 200, 30);
		
		JLabel lname = new JLabel("Last Name:");
		lname.setBounds(30, 130, 200, 30);
		
		JTextField lnameTxt = new JTextField();
		lnameTxt.setBounds(30, 160, 200, 30);
		
		JLabel email = new JLabel("Email:");
		email.setBounds(30, 190, 200, 30);
		
		JTextField emailTxt = new JTextField();
		emailTxt.setBounds(30, 220, 200, 30);
		
		JLabel phone = new JLabel("Phone:");
		phone.setBounds(30, 250, 200, 30);
		
		JTextField phoneTxt = new JTextField();
		phoneTxt.setBounds(30, 280, 200, 30);
		
		JLabel username = new JLabel("Username:");
		username.setBounds(30, 310, 200, 30);
		
		JTextField usernameTxt = new JTextField();
		usernameTxt.setBounds(30, 340, 200, 30);
			
		JLabel password = new JLabel("Password:");
		password.setBounds(30, 370, 200, 30);
		
		JTextField passwordTxt = new JTextField();
		passwordTxt.setBounds(30, 400, 200, 30);

		//this button will allow us to submit the information. 
		JButton submitBtn = new JButton("Submit");
		submitBtn.setBounds(30, 450, 80, 30);
		
		/*
		 * Here I want to allow this button to insert all the information 
		 * the user provides into the Customer table. 
		 */
		submitBtn.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				
				//get the respective values from the text fields in the UI. 
				String fname = fnameTxt.getText();
				String lname = lnameTxt.getText();
				String email = emailTxt.getText();
				String phone = phoneTxt.getText();
				String username = usernameTxt.getText();
				String password = passwordTxt.getText();
				
				//instantiate an object of this class to get the respective method. 
				customerQuery custquery = new customerQuery();
				
				//ensure that the text fields are neither blank or null. 
				if (!fname.isBlank() && fname != null && 
						!lname.isBlank() && lname != null &&
						!email.isBlank() && email != null &&
						!phone.isBlank() && phone != null &&
						!username.isBlank() && username != null &&
						!password.isBlank() && password != null)
					
				//if so then...
				{
					try {
						
						//call my insertCust method here from the class. 
						custquery.insertCust(fname, lname, email, phone, username, password);
						//set the current frame invisible. 
						registFrame.setVisible(false);
						//set the login Frame visible and have customer login themselves in.  
						setVisible(true);
						
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
				}
				//if the user tries to only fill a portion of the information text fields, send an error message 
				//saying they must fill ALL the information to register. 
				else {
					JOptionPane.showMessageDialog(null, "You must type all the required information to register.");
				}	
				
			}
		});
		
		//button closes the form. 
		JButton closeBtn = new JButton("Close");
		closeBtn.setBounds(130, 450, 80, 30);
		
		//method tied to the close button. 
		closeBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//need to tell the program which JFrame it needs to close. 
				JFrame registFrame = (JFrame) SwingUtilities.getRoot((Component) e.getSource());
				//closes the JFrame. 
				registFrame.dispose();
				//sets the Login Frame visible. 
				setVisible(true);
			}
		});
		
		//ADD ALL COMPONENETS TO THE JPANEL AND THEN TO THE JFRAME. 
		acctPanel.add(instrut);
		acctPanel.add(fname);
		acctPanel.add(fnameTxt);
		acctPanel.add(lname);
		acctPanel.add(lnameTxt);
		acctPanel.add(email);
		acctPanel.add(emailTxt);
		acctPanel.add(phone);
		acctPanel.add(phoneTxt);
		acctPanel.add(username);
		acctPanel.add(usernameTxt);
		acctPanel.add(password);
		acctPanel.add(passwordTxt);
		acctPanel.add(submitBtn);
		acctPanel.add(closeBtn);
		//add JPanel to JFrame. 
		registFrame.add(acctPanel);
		registFrame.setVisible(true);
	}
	


	
	/*
	 * Method for the construction of the customer frame for browsing once customers are logged in. 
	 * This will show up once the login button is clicked. 
	 */
	private void CustFrame(int CUSTID) {
		
		//name of the JFrame. 
		JFrame custFrame = new JFrame("Customer Home Page");
	    custFrame.setSize(700, 750);
	    custFrame.setResizable(false);
	    custFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
	    
	    //instantiate the panel for this frame. 
	    JPanel custPanel = new JPanel();
	    //to allow for buttons to be placed wherever. 
	    custPanel.setLayout(null);
	    
	    //Begin adding the JLabels and other components here. 
	    JLabel search = new JLabel("Happy Book Searching!");
	    search.setBounds(30, 10, 200, 30); 
	    search.setFont(new Font("Arial", Font.BOLD, 14));
	   
	    JLabel cust_fname = new JLabel();
	    //get the customer's first name through their ID, and then 
	    //display it in the label. 
	    //instantiate an object of the customerQuery class to get the first name method. 
	    customerQuery name = new customerQuery();
	    String fname = name.getCustFName(CUSTID);
	    
	    //set that JLabel to show the current customer's first name within the label. 
	    cust_fname.setText(fname + "'s Account");
	    cust_fname.setBounds(230, 10, 150, 30);
	    cust_fname.setFont(new Font("Arial", Font.BOLD, 14));
	    
	    //this button will check the user account information. 
	    JButton userInfo = new JButton("User Account");
	    userInfo.setBounds(380, 10, 130, 30);
	    
	    //logout button. 
	    JButton logout = new JButton("Logout");
	    logout.setBounds(530, 10, 130, 30);
	    
	    //instruction label for the customer. 
	    JLabel instruc = new JLabel("Please browse our books by genre, title, and/or author.");
	    instruc.setBounds(30, 70, 400, 30);
	    instruc.setFont(new Font("Arial", Font.BOLD + Font.ITALIC, 14));
	    
	   
	    //BOOK LABELS FOR THIS JFRAME. 
	    JLabel bookID = new JLabel("Book ID");
	    bookID.setBounds(50, 100, 100, 30);
	    
	    JLabel booktitle = new JLabel("Book Title");
	    booktitle.setBounds(150, 100, 100, 30);
	    
	    JLabel bookauthor = new JLabel("Author");
	    bookauthor.setBounds(280, 100, 100, 30);
	    
	    JLabel bookprice = new JLabel("Price");
	    bookprice.setBounds(400, 100, 100, 30);
	    
	    JLabel genre = new JLabel("Genre");
	    genre.setBounds(500, 130, 100, 30);
	    
	    
	 	//Drop down menu here for payment selections. 
  		String[] genreChoice = {"Fantasy", "Historical Fiction", "Science Fiction", "Non-Fiction", "Romance", "Thriller"};
  		final JComboBox<String> combo = new JComboBox<String>(genreChoice);
  		//set the combo bounds. 
  		combo.setBounds(500, 160, 100, 30);
	    
	    
	    //Add all the browsing components for the JFrame. 
  		//Create a DefaultTableModel to display the books in a JTable. 
	    DefaultTableModel model = new DefaultTableModel();
	    //add the columns 
	    model.addColumn("ID");
	    model.addColumn("Title");
	    model.addColumn("Author");
	    model.addColumn("Price ($)");
	    
	    JTable bookTable = new JTable(model);
	    bookTable.setBounds(10, 130, 470, 450);
  		
  		
	    JLabel title = new JLabel("Title");
	    title.setBounds(500, 190, 100, 30);
	    
	    JTextField titleTxt = new JTextField();
	    titleTxt.setBounds(500, 220, 100, 30);
	    
	    JLabel author = new JLabel("Author");
	    author.setBounds(500, 250, 100, 30);
	    
	    JTextField authorTxt = new JTextField();
	    authorTxt.setBounds(500, 280, 100, 30);
	  
	    //this button will call upon the query.
	    JButton browse = new JButton("Browse");
	    browse.setBounds(500, 330, 100, 30);
	    
	    //browse button action method.  
	    browse.addActionListener(new ActionListener() {
	    	@Override
	    	public void actionPerformed(ActionEvent e) {
	    		//grab the user defined variables. 
	    		String title = titleTxt.getText();
	    		String author = authorTxt.getText();
	    		//String genre = genreTxt.getText();
	    		String selectGenre = combo.getSelectedItem().toString();
	    		
	    		//make an instance of the bookQuery class. 
	    		bookQuery bookquery = new bookQuery();
	    		
	    		try {
	    			//grab the returning 2D array from our method in the queries class. 
	    			Object[][] dataList = bookquery.displayBooks(title, author, selectGenre);
	    			
	    			//want to clear the existing table data. 
		    		DefaultTableModel model = (DefaultTableModel) bookTable.getModel();
		    		
		    		//clear existing rows too. 
		    		model.setRowCount(0);
	    			
	    			//now populate our model with this dataList. 
	    			//do a simple for each loop
	    			for (Object[] row : dataList) {
	    				//add it to the model. 
	    				model.addRow(row);
	    			}
	
	    			//add the book table here to our JPanel right away. 
	    			custPanel.add(bookTable);
	    			//help ensure the panel is refreshed or cleared. 
	    			custPanel.revalidate();
	    			custPanel.repaint();
	    			model.fireTableDataChanged();
	    			
	    			//finally clear out the text fields. 
	    			titleTxt.setText("");
	    			titleTxt.requestFocus();
	    			authorTxt.setText("");

	    		}
	    		catch (SQLException e2) {
					e2.printStackTrace();
					System.exit(0);
				}
	    	}
	    	
	    });
	    
	    
	    //action for the logout button to simply send you back to the login JFrame. 
	    logout.addActionListener(new ActionListener() {
	    	@Override
	    	public void actionPerformed(ActionEvent e) {
	    		//need to make this current frame disappear. 
	    		custFrame.setVisible(false);
	    		//now make the login frame visible! 
	    		setVisible(true);
	    	}
	    	
	    });
	    
	    //More labels for the instructions on how to add a book to our shopping cart. 
	    JLabel cartInstruct = new JLabel("Please type one book ID for purchase and click 'Add to Cart'! Repeat for other books.");
	    cartInstruct.setBounds(20, 610, 630, 30);
	    cartInstruct.setFont(new Font("Arial", Font.BOLD, 14));
	    //I chose to make this red so that people can more easily see it and know what to do. 
	    cartInstruct.setForeground(Color.RED);
	   
	    //this text field is to allow the user to type in the book ID they want to add to their cart. 
	    JTextField addcartTxt = new JTextField();
	    addcartTxt.setBounds(20, 650, 150, 30);
	    
	    JButton addCart = new JButton("Add to Cart");
	    addCart.setBounds(200, 650, 150, 30);

	    //action for the addCart button. 
	    addCart.addActionListener(new ActionListener() {
	    	@Override
	    	public void actionPerformed(ActionEvent e) {
	    		
	    		//first get the book ID from the text field from the user. 
	    		String bookID = addcartTxt.getText();
	    		
	    		try {
	    			
	    			//parse that String book id to Integer to pass it to the method's parameter. 
	    			Integer.parseInt(bookID);
	    			//instantiate the shopcartQuery class. 
	    			shopcartQuery shopcart = new shopcartQuery();
		    		
	    			//run the method from that class and pass the bookID and the Customer's ID. 
		    		shopcart.populateShoppingCart(bookID, Integer.toString(CUSTID));
		    		//ensure the add cart Text field is reset to blank for the next usage. 
		    		addcartTxt.setText("");
	    		}
	    		//unable to run the query, send the proper error message. 
	    		catch (NumberFormatException e1) {
	    			JOptionPane.showMessageDialog(null, "Invalid value. Please type only an integer value.");
	    			//reset the text field and put the focus back on it. 
	    			addcartTxt.setText("");
	    			addcartTxt.requestFocus();
	    		}
	    	}
	    	
	    });
	   
	   
	    //This button will display user's information from the AccountFrame. 
	    userInfo.addActionListener(new ActionListener() {
	    	@Override 
	    	public void actionPerformed(ActionEvent e) {
	    		
	    		//set that customer JFrame invisible 
	    		custFrame.setVisible(false);
	    		//bring up only the account JFrame. 
	    		AccountFrame(CUSTID);
	    	}
	    	
	    });
	    
	    //this button will simply open the shopping cart frame. 
	    JButton shopCart = new JButton("Shopping Cart");
	    shopCart.setBounds(380, 650, 150, 30);
	        
	    //from this button, have the checkout frame appear. 
	    shopCart.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				//set the current customer frame invisible. 
				custFrame.setVisible(false);
				//bring up only the shopping cart frame. 
				ShopCartFrame(CUSTID);
			}
		});
	    
	    
	    //ADD ALL COMPONENETS TO THE JPANEL AND THEN TO THE JFRAME. 
	    custPanel.add(search);
	    custPanel.add(author);
	    custPanel.add(title);
	    custPanel.add(bookID);
	    custPanel.add(booktitle);
	    custPanel.add(bookauthor);
	    custPanel.add(bookprice);
	    custPanel.add(authorTxt);
	    custPanel.add(titleTxt); 
	    custPanel.add(genre);
	    custPanel.add(combo);
	    custPanel.add(browse);
	    custPanel.add(addCart); 
	    custPanel.add(addcartTxt);
	    custPanel.add(userInfo);
	    custPanel.add(shopCart);
	    custPanel.add(cartInstruct);
	    custPanel.add(logout);
	    custPanel.add(instruc);
	    custPanel.add(cust_fname);
	    custPanel.setVisible(true);
	    //add panel to the frame. 
	    custFrame.add(custPanel);
	    custFrame.setVisible(true);
	    custFrame.revalidate();
	    custFrame.repaint();
	    
	}
	
	
	
	
	/*
	 * Method to create and show the AccountFrame. 
	 */
	private void AccountFrame(int CUSTID) {
		
		//create the JFrame. 
		JFrame accountFrame = new JFrame("User Account");
		accountFrame.setSize(300, 250);
		accountFrame.setResizable(false);
		accountFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		//Create the JPanel for the frame. 
		JPanel acctPanel = new JPanel();
		acctPanel.setLayout(null);
		
		//Instantiate an object from the customerQuery class right away to call methods later on. 
		customerQuery custInfo = new customerQuery();
		
		//first get the name of the customer. 
		//call the proper method with the Customer ID. 
		String fName = custInfo.getCustFName(CUSTID);
		
		//now create the components for the frame using that String variable fName. 
		JLabel fname = new JLabel("First Name: " + fName);
		fname.setBounds(20, 20, 200, 30);
		
		//do the same thing but for the last name. 
		String lName = custInfo.getCusLFName(CUSTID);
		JLabel lname = new JLabel("Last Name: " + lName);
		lname.setBounds(20, 50, 200, 30);
		
		//now get the email!
		String Email = custInfo.getCustEmail(CUSTID);
		JLabel email = new JLabel("Email: " + Email);
		email.setBounds(20, 80, 200, 30);
		
		//finally get the phone for that customer. 
		String Phone = custInfo.getCustPhone(CUSTID);
		JLabel phone = new JLabel("Phone: " + Phone);
		phone.setBounds(20, 110, 200, 30);
	
		//create the JButton to update this information. 
		JButton update = new JButton("Update");
		update.setBounds(20, 150, 80, 30);
		
		//this button will open the update information frame. 
        update.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//want to make this current frame invisible 
				accountFrame.setVisible(false);
				//only see this frame at this moment. 
				UpdateInfo(CUSTID);
			}
		});
        
        
        //Closes the current JFrame.
		JButton closeBtn = new JButton("Close");
		closeBtn.setBounds(130, 150, 80, 30);
		
		//action for the close button. 
		closeBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//tell which frame it needs to close. 
				JFrame accountFrame = (JFrame) SwingUtilities.getRoot((Component) e.getSource());
				//tell the frame it actually needs to close. 
				accountFrame.dispose();
				//go back to the customer JFrame. 
				CustFrame(CUSTID);
			}
		});
		
		//ADD ALL COMPONENETS TO THE JPANEL AND THEN TO THE JFRAME. 
		acctPanel.add(fname);
		acctPanel.add(lname);
		acctPanel.add(phone);
		acctPanel.add(email);
		acctPanel.add(update);
		acctPanel.add(closeBtn);
		//add the panel to the frame. 
		accountFrame.add(acctPanel);
		accountFrame.setVisible(true);
		
	}
	
	
	
	
	/*
	 * Method to create and display the update 
	 * customer information frame. 
	 */
	private void UpdateInfo(int CUSTID) {
		
		//frame creation. 
		JFrame accountupdateFrame = new JFrame("Update Account");
		accountupdateFrame.setSize(400, 450);
		accountupdateFrame.setResizable(false);
		accountupdateFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		//panel creation. 
		JPanel updatePanel = new JPanel();
		updatePanel.setLayout(null);
		
		//start creating our JLabels and other components. 
		JLabel instruct = new JLabel("Please update the appropriate information.");
		instruct.setBounds(20, 20, 250, 30);
		
		JLabel fname = new JLabel("First Name");
		fname.setBounds(20, 60, 200, 30);
		
		JTextField fnameTxt = new JTextField();
		fnameTxt.setBounds(20, 90, 200, 30);
	
		JLabel lname = new JLabel("Last Name");
		lname.setBounds(20, 120, 200, 30);
		
		JTextField lnameTxt = new JTextField();
		lnameTxt.setBounds(20, 150, 200, 30);;

		JLabel email = new JLabel("Email");
		email.setBounds(20, 180, 200, 30);
		
		JTextField emailTxt = new JTextField();
		emailTxt.setBounds(20, 210, 200, 30);
	
		JLabel phone = new JLabel("Phone");
		phone.setBounds(20, 240, 200, 30);
		
		JTextField phoneTxt = new JTextField();
		phoneTxt.setBounds(20, 270, 200, 30);
		
		JLabel passwrd = new JLabel("Password");
		passwrd.setBounds(20, 300, 200, 30);
		
		JTextField passwrdTxt = new JTextField();
		passwrdTxt.setBounds(20, 330, 200, 30);

		
		//button for actually updating the person's information. 
		JButton submit = new JButton("Update");
		submit.setBounds(20, 370, 80, 30);
		
		//now put action behind that submit button. 
        submit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//need to grab each person's text information from the text fields in the UI. 
				String fname = fnameTxt.getText();
				String lname = lnameTxt.getText();
				String email = emailTxt.getText();
				String phone = phoneTxt.getText();
				String passwrd = passwrdTxt.getText();
				
				//instantiate an object from the customerQuery class to get the needed method. 
				customerQuery custquery = new customerQuery();	
				
				//call this method to update the customer's information in the database. 
				//give it the proper arguments in the parameters. This update will be reflected when the other queries 
				//select this information in the other JFrames from the table. 
				custquery.updateCustInfo(CUSTID, fname, lname, email, phone, passwrd);
				
				//set the current JFrame invisible. 
				accountupdateFrame.setVisible(false);
				
				//finally have the accountFrame pop up. 
				AccountFrame(CUSTID);
			}
		});

        
        //button will close the current frame. 
		JButton closeBtn = new JButton("Close");
		closeBtn.setBounds(130, 370, 80, 30);
		
		//action for this button. 
		closeBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//get the current frame to close. 
				JFrame accountupdateFrame = (JFrame) SwingUtilities.getRoot((Component) e.getSource());
				//have this JFrame become invisible 
				accountupdateFrame.setVisible(false);
				//make the account JFrame appear. 
				AccountFrame(CUSTID);
				
			}
		});
		
		
		//ADD ALL COMPONENETS TO THE JPANEL AND THEN TO THE JFRAME. 
		updatePanel.add(instruct);
		updatePanel.add(fname);
		updatePanel.add(fnameTxt);
		updatePanel.add(lname);
		updatePanel.add(lnameTxt);
		updatePanel.add(phone);
		updatePanel.add(phoneTxt);
		updatePanel.add(email);
		updatePanel.add(emailTxt);
		updatePanel.add(passwrd);
		updatePanel.add(passwrdTxt);
		updatePanel.add(submit);
		updatePanel.add(closeBtn);
		accountupdateFrame.add(updatePanel);
		accountupdateFrame.setVisible(true);
		
	}
	
	
	
	
	/*
	 * Method to create and open the ShoppingCart Frame. 
	 */
	private void ShopCartFrame(int CUSTID) {
		
		//create a new JFrame 
		JFrame shopcartFrame = new JFrame("Your Shopping Cart");
		shopcartFrame.setSize(570, 490);
		shopcartFrame.setResizable(false);
		shopcartFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		//new JPanel instance. 
		JPanel shopcartPanel = new JPanel();
		//get the checkout Panel layout to equal null. 
		shopcartPanel.setLayout(null);
		
		//add the JLabels and other components. 
		JLabel cust_fname = new JLabel();
		
	    //get the customer's first name through their ID, and then 
	    //display it in the label. 
		
		//Need to first instantiate an object of the customerQuery class to get the proper method. 
	    customerQuery name = new customerQuery();
	    
	    //get the proper method here. 
	    String fname = name.getCustFName(CUSTID);
	    //put variable into the JLabel. 
	    cust_fname.setText(fname + "'s Cart");
	    cust_fname.setBounds(20, 10, 150, 30);
	    cust_fname.setFont(new Font("Arial", Font.BOLD, 14));
	    
	    //instantiate an object from the shopcartQuery class.  
	    shopcartQuery shopcart = new shopcartQuery();
	    
	    JLabel totalCount = new JLabel();
	    //call the proper method to get the count of the number of books currently in the cart. 
	    int count = shopcart.getCount(CUSTID);
	    //display that number within the JLabel for the user in the UI. 
	    totalCount.setText("Number of Books in Cart: " + Integer.toString(count));
	    totalCount.setBounds(130, 10, 200, 30);
	    totalCount.setFont(new Font("Arial", Font.BOLD, 14));
	    
	    
	    JLabel totalPrice = new JLabel();
	    //same thing her but with the total price in the current cart. 
	    double price = shopcart.getPrice(CUSTID);
	    //now display that total price within the JLabel for the user to see in the UI. 
	    totalPrice.setText("Current Total Price: $" + Double.toString(price));
	    totalPrice.setBounds(350, 10, 200, 30);
	    totalPrice.setFont(new Font("Arial", Font.BOLD, 14));
	    
	    
	    //book labels for the JTable so that the user knows what each column represents in the table. 
	    JLabel bookID = new JLabel("Book ID");
	    bookID.setBounds(20, 70, 100, 30);
	    
	    JLabel booktitle = new JLabel("Book Title");
	    booktitle.setBounds(90, 70, 100, 30);
	    
	    JLabel bookauthor = new JLabel("Author");
	    bookauthor.setBounds(170, 70, 100, 30);
	    
	    JLabel bookprice = new JLabel("Price ($)");
	    bookprice.setBounds(240, 70, 100, 30);
	    
	    
	    //Create a DefaultTableModel to display the books in the customer's cart. 
	    DefaultTableModel model = new DefaultTableModel();
	    //add the columns to the model. 
	    model.addColumn("ID");
	    model.addColumn("Title");
	    model.addColumn("Author");
	    model.addColumn("Price ($)");
	    
	    //create the JTable. 
	    JTable bookTable = new JTable(model);
	    bookTable.setBounds(10, 100, 300, 250);
	    
	    JButton viewCart = new JButton("View Your Cart");
	    viewCart.setBounds(330, 100, 150, 30);
	    
	    //give the look 
	    viewCart.addActionListener(new ActionListener() {
	    	@Override
	    	public void actionPerformed(ActionEvent e) {
	    		shopcartQuery cartQuery = new shopcartQuery();
	    		
	    		try {
	    			//get the result from the displayCustBooks method. 
	    			Object[][] dataList = cartQuery.displayCustBooks(CUSTID);
	    			DefaultTableModel model = (DefaultTableModel) bookTable.getModel();
	    			model.setRowCount(0);
	    			
	    			//have a for each loop to loop through the data list array and add each row to the 
	    			//model. 
	    			for (Object[] row: dataList) {
	    				model.addRow(row);
	    			}
	    			
	    			//right away add the table to the panel (the table already contains the model). 
	    			shopcartPanel.add(bookTable);
	    			//make sure we can refresh the panel with the table. 
	    			shopcartPanel.revalidate();
	    			shopcartPanel.repaint();
	    			model.fireTableDataChanged();
	    			
	    			//now update the price and the number of books in the checkout frame in case the person adds or deletes 
	    			//books from their cart. 
	    			
	    			//here we call the proper methods and put the results within the JLabels for the user to see in the UI. 
	    			double price = shopcart.getPrice(CUSTID);
	    		    totalPrice.setText("Current total Price: $" + Double.toString(price));
	    		    
	    		    int count = shopcart.getCount(CUSTID);
	    		    totalCount.setText("Number of books in cart: " + Integer.toString(count));
	    			
	    		}
	    		catch (SQLException e2) {
					e2.printStackTrace();
					System.exit(0);
				}
	    		
	    	}
	    });
	    
	    
        //button which will take you back to the original frame. 
        JButton backBtn = new JButton("Continue Shopping");
		backBtn.setBounds(330, 150, 150, 30);
		
		//add action to this back button. 
		backBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//you need this because you have to reference the current JFrame you are on, especially if you have 
				//multiple open simultaneously. 
				JFrame shopcartFrame = (JFrame) SwingUtilities.getRoot((Component) e.getSource());
				//dispose the shopping cart frame. 
				shopcartFrame.dispose();
				//show the main customer frame. 
				CustFrame(CUSTID);
			}
		});
		
        
		//checkout button to take you to the checkout frame. 
        JButton checkoutBtn = new JButton("Checkout");
		checkoutBtn.setBounds(330, 200, 150, 30);
        
		//from this button, maybe have the checkout frame pop up. 
        checkoutBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//I want to ensure that the customer actually has books in their cart
				//before they can even go to the checkout JFrame. 
				//to do so, we need to instantiate an object from the shopcartQuery class. 
				shopcartQuery cartQ = new shopcartQuery();
				
				//declare a boolean variable to check if the cart has books in it or not. 
				boolean cartFull = false;
				
				//run the query and grab boolean value.
				cartFull = cartQ.custIDInCart(CUSTID);
				
				if (cartFull == true) {
					//make current frame invisible. 
					shopcartFrame.setVisible(false);
					//make this one visible only. 
					CheckoutFrame(CUSTID);
					
				}
				//if cart does not have at least one book, then the customer cannot checkout. 
				//Send proper user error message. 
				else {
					JOptionPane.showMessageDialog(null, "You cart is currently empty. Please select books and then checkout!");
					//take user directly back to the customer frame for them to shop or logout. 
					shopcartFrame.setVisible(false);
					CustFrame(CUSTID);
				}
				
			}
		});
        
        
        //instructions for how to use the remove book button for the user. 
        //this will be in a JLabel. 
        JLabel instruct = new JLabel("Please type the book ID and click 'Remove Book', one book at a time.");
        instruct.setBounds(10, 370, 450, 30);
        //because these instructions are towards the bottom of the JFrame, I want these to be red so that they are easily spotted. 
        instruct.setForeground(Color.RED);
        
        
        //text field to receive the book id that the customer wants to get rid of. 
        JTextField bookid = new JTextField();
        bookid.setBounds(10, 400, 130, 30);
        
        //the button to delete it. 
        JButton deleteBk = new JButton("Remove Book");
        deleteBk.setBounds(160, 400, 130, 30);
        shopcartPanel.add(deleteBk);
        
        //now give that delete book button functionality. 
        deleteBk.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent e) {
        		
        		//we first need to check if the book ID that the customer wants to remove is actually present in their cart. 
        		//If the book is not present in the cart, then we need to send a proper error message. 
        		
        		//Instantiate an object of the shopcartQuery class to get the proper method. 
        		shopcartQuery bookCart = new shopcartQuery();
        		//get the book id value from the user. 
        		String bookID = bookid.getText();
        		//set these boolean value equal to false first. 
        		boolean bookInCart = false;
        		boolean rightBook = false;
        		
        		//need to first check if the customer actually has books in their cart. 
        		bookInCart = bookCart.custIDInCart(CUSTID);
        		
        		//if the cart contains at least one book. 
        		if (bookInCart == true) {
        			
        			//go ahead and try to grab the book id. 
    				try {
            			//ensure that your value is an integer and NOT a space or a string. 
            			Integer.parseInt(bookID);
            			
            			//if there is a book id. 
            			if (!bookID.isBlank()) {
            				
            				//now we want to check if the book id is actually in the customer's cart or not. 
            				rightBook = bookCart.checkBook(bookID, CUSTID);
            				
            				//now if the right book is chosen, go ahead and remove it. 
            				if (rightBook == true) {
            					
            					//call this method to remove the book id for that customer. 
            					bookCart.removeBook(bookID, CUSTID);
            					//provide proper message to user. 
                        		JOptionPane.showMessageDialog(null, "You successfully deleted your selected book. Please click"
                        				+ "on the 'View Your Cart' button to refresh your cart.");
                        		//set the book id text field back to blank. 
                        		bookid.setText("");
            					
            				}
            				//if the book selected to be removed does not exist in the shopping cart for that user. 
            				else {
            					
            					//provide proper error message for that user. 
            					JOptionPane.showMessageDialog(null, "This book is not in your current cart. Please select another book to remove.");
            					//reset the text field to blank.
            					bookid.setText("");
            					//reset the focus back to that text field. 
            	    			bookid.requestFocus();
            				}
            			
                		
            			}
            			//if the user tries to type anything other than an integer value, catch it and send proper error message. 
            			else {
            				JOptionPane.showMessageDialog(null, "Invalid value. Please type only an integer value for the book ID. Try again.");
            				bookid.setText("");
        	    			bookid.requestFocus();
            			}
            			
            		}
            		
    				//catch other errors and exceptions and send proper error message. 
            		catch (NumberFormatException e1) {
    	    			JOptionPane.showMessageDialog(null, "Invalid value. Please type only an integer value for the book ID.");
    	    			bookid.setText("");
    	    			bookid.requestFocus();
    	    		}
        				
        			        			
        		}
        		//if the cart is already empty, send proper error message. 
        		else {
        			JOptionPane.showMessageDialog(null, "Your cart is currently empty. Please continue to shop for books!");
        			bookid.setText("");
	    			bookid.requestFocus();
	    			
	    			//set the shopping cart invisible and show the customer frame again for the user to continue shopping. 
	    			shopcartFrame.setVisible(false);
					CustFrame(CUSTID);
	    			
        		}
        		
        	}
        });
        
        
        //the final button here to clear the cart. 
        JButton clearCart = new JButton("Clear Cart");
        clearCart.setBounds(310, 400, 130, 30);
        shopcartPanel.add(bookid);
        shopcartPanel.add(clearCart);
        
        //functionality for the clear button. 
        clearCart.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//instantiate object of my class to get my appropriate method. 
				shopcartQuery clearCart = new shopcartQuery();
				
				//declare my boolean value to make sure my customer is actually in the shopping cart table. 
				boolean custShop = false;
				
				//run the query to check if the customer is actually in the shopping cart table or not?
				custShop = clearCart.custIDInCart(CUSTID);
				
				//now run this test to see if we get true or false
				if (custShop == true) {
					
					//run the query to clear the shopping cart table wherever customer id equals the customer ID. 
					clearCart.clearShopCart(CUSTID);
					//show proper error message. 
					JOptionPane.showMessageDialog(null, "You cart has been cleared!");
					
				}
				//if the customer is not in the shopping cart table, send proper error message. 
				else {
					JOptionPane.showMessageDialog(null, "Your cart is currently empty. Please continue to shop for books!");
				}
				//show the customer the customer frame to continue shopping or log out. 
				shopcartFrame.setVisible(false);
				CustFrame(CUSTID);
				
				

			}
		});
        
        
        //ADD ALL COMPONENETS TO THE JPANEL AND THEN TO THE JFRAME. 
		shopcartPanel.add(instruct);
		shopcartPanel.add(totalPrice);
		shopcartPanel.add(totalCount);
		shopcartPanel.add(backBtn);
		shopcartPanel.add(checkoutBtn);
		shopcartPanel.add(bookID);
		shopcartPanel.add(bookauthor);
		shopcartPanel.add(booktitle);
		shopcartPanel.add(bookprice);
		shopcartPanel.add(viewCart);
		shopcartPanel.add(cust_fname);
		shopcartFrame.add(shopcartPanel);
		shopcartFrame.setVisible(true);

	}
	
	
	
	
	/*
	 * Method for the creation and show of the Checkout Frame. 
	 */
	private void CheckoutFrame(int CUSTID) {
		//create a new JFrame 
		JFrame checkoutFrame = new JFrame("Literary Torch Checkout");
		checkoutFrame.setSize(350, 300);
		checkoutFrame.setResizable(false);
		checkoutFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		JPanel checkoutPanel = new JPanel();
		checkoutPanel.setLayout(null);

		//JLabels and other components of the JFrame. 
		JLabel instruct = new JLabel("Please select a payment option and checkout!");
		instruct.setBounds(20, 20, 300, 30);
		
		//try the drop down menu here for payment selections. 
		String[] payChoice = {"Credit Card", "Debit Card", "PayPal", "Digital Wallet"};
		final JComboBox<String> combo = new JComboBox<String>(payChoice);
		//set the combo bounds. 
		combo.setBounds(20, 60, 200, 30);
		
		JButton submitBtn = new JButton("Submit");
		submitBtn.setBounds(20, 200, 80, 30);
		
		//functionality for the submit button. 
		submitBtn.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {

				//get the user's selected payment option from the combo box. 
				String selectPay = combo.getSelectedItem().toString();
				
				//instantiate an object of the checkoutQuery class to get the method we need. 
				checkoutQuery checkoutquery = new checkoutQuery();
				checkoutquery.checkout(CUSTID, selectPay);
				
				//again get the method we need from this class to clear the shopping cart after payment. 
				shopcartQuery clearCart = new shopcartQuery();
				clearCart.clearShopCart(CUSTID);
				
				//set the checkout frame invisible and show the shopping cart frame. 
				checkoutFrame.setVisible(false);
				ShopCartFrame(CUSTID);
				
			}
		});
	
		
		//cancel button. 
		JButton cancelBtn = new JButton("Cancel");
		cancelBtn.setBounds(130, 200, 80, 30);
		
		//cancel button functionality. 
		cancelBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//make checkout fame invisible and show the shopping cart frame. 
				checkoutFrame.setVisible(false);
				ShopCartFrame(CUSTID);
				
			}
		});
		
		//ADD ALL COMPONENETS TO THE JPANEL AND THEN TO THE JFRAME. 
		checkoutPanel.add(instruct);
		checkoutPanel.add(combo);
		checkoutPanel.add(cancelBtn);
		checkoutPanel.add(submitBtn);
		checkoutFrame.add(checkoutPanel);
		checkoutFrame.setVisible(true);
		
	}
	
}

//done 