package app.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.*;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class check_in implements ActionListener {

	/*Using your GUI, be able to check in a book. For examPle, be able to locate
	BOOK_LOANS tuples by searching on any of BOOKS.book_id, BORROWER.card_no,
	and/or any substring of BORROWER name. Once located, provide a way of selecting
	one of potentially multiple results and a button (or menu item) to check in (i.e. today as
	the date_in in corresponding BOOK_LOANS tuple).
	search for bookID, cardNo, AND/OR any substring of borrower name
	*/

    	private JFrame frame;
	
	private JPanel searchButtonPanel;
    	private JPanel titlePanel;
    	private JPanel panel;
	private JPanel tablePanel;

	private JTextField bookIDField;
	private JTextField borrowerCardNoField;
	private JTextField borrowerNameField;

	private JLabel pageTitleLabel;
	private JLabel bookIDLabel;
	private JLabel borrowerCardNoLabel;
	private JLabel borrowerNameLabel;
	private JLabel tableName;

	private JButton searchButton;
	private JButton backButton;
	private JButton checkInButton;
	private JPanel panel1;
	private JPanel mainPanel;

	private JTable loansTable;

	public boolean isQueried = false;
	public boolean isCheckedIn = false;

	//Connection Information
	String url = "jdbc:mysql://database-lib.cliese5bfxyl.us-east-2.rds.amazonaws.com:3306/mysql";
	String username = "admin";
	String password = "lion1234";

	public check_in() {
	    createGui();
	}

	public void createGui(){
		borrowerCardNoLabel = new JLabel("Borrower Card Number", JLabel.RIGHT);
		borrowerNameLabel = new JLabel("Borrower First Name", JLabel.RIGHT);
		bookIDLabel = new JLabel("Book ID(ISBN10)", JLabel.RIGHT);
		pageTitleLabel = new JLabel("Check in a Book", JLabel.CENTER);
		searchButton = new JButton("Search");
		backButton = new JButton("Return to Previous Page");
		checkInButton = new JButton("Check In");
		//to change font and size of text
		pageTitleLabel.setFont(pageTitleLabel.getFont().deriveFont(30.0f));

		frame = new JFrame();					//frames
		frame.setSize(700, 500);

		panel = new JPanel();					//panels
		titlePanel = new JPanel();
		searchButtonPanel = new JPanel();

		bookIDField = new JTextField(30);			//fields
		borrowerCardNoField = new JTextField(30);
		borrowerNameField = new JTextField(30);

		//set horizontal and vertical spacing
		panel.setSize(300, 300);
		panel.setBorder(BorderFactory.createEmptyBorder(50, 10, 10, 200));
		panel.setLayout(new GridLayout(5, 5, 20, 20));

		searchButtonPanel.setSize(500, 100);
		searchButtonPanel.setBorder(BorderFactory.createEmptyBorder(0, 100, 10, 100));
		searchButtonPanel.setLayout(new GridLayout(0, 1, 20, 10));

		//add buttons and labels
		titlePanel.add(pageTitleLabel);
		panel.add(borrowerCardNoLabel);
		panel.add(borrowerCardNoField);
		panel.add(borrowerNameLabel);
		panel.add(borrowerNameField);
		panel.add(bookIDLabel);
		panel.add(bookIDField);

		searchButtonPanel.add(searchButton);
		searchButtonPanel.add(checkInButton);
		searchButtonPanel.add(backButton);

		frame.add(panel, BorderLayout.CENTER);		//changed from CENTER to WEST to add table to EAST
		frame.add(titlePanel, BorderLayout.NORTH);
		frame.add(searchButtonPanel, BorderLayout.SOUTH);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Check In");
		frame.pack();
		frame.setVisible(true);

		//need to display from BOOK_LOANS: Loan_id, Card_id, Date_out, Date_in, Isbn10
		String[] colNames = {"Loan_id", "Card_id", "Date_out", "Due_date", "Date_in", "Isbn10"};
		String from;

		DefaultTableModel model = new DefaultTableModel();
		model.setColumnIdentifiers(colNames);

		loansTable = new JTable();
		loansTable.setModel(model);
		loansTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		loansTable.setFillsViewportHeight(true);
		tableName = new JLabel("Our database found:", JLabel.RIGHT);

		JScrollPane scroll = new JScrollPane(loansTable);
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

		//when search button is pressed
		searchButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//get text from user
				String bookID = bookIDField.getText();
				String cardNo = borrowerCardNoField.getText();
				String name = borrowerNameField.getText();
				model.setRowCount(0);
				try {
					Connection connection = DriverManager.getConnection(url, username, password);
					Statement statement = connection.createStatement();

					//double check parameters for inputs
					if(bookID.length() == 0 && cardNo.length() == 0 && name.length() ==0) {
						JOptionPane.showMessageDialog(null, "You must enter at least one of the boxes to check in a book");
					} else if (bookID.length() != 10 && bookID.length() != 0){
						//length is not valid
						JOptionPane.showMessageDialog(null, "The Isbn must be either 10 digits long");				//error
						bookIDField.setText("");
					} else if(bookID.length() == 10) {
						ResultSet nameResult = statement.executeQuery("SELECT * FROM LIBRARY.BOOK_LOANS WHERE Isbn10='" + bookID + "' AND  Date_in is null;");
						isQueried = queryTable(statement, nameResult, model, isQueried);
					} else if(cardNo.length() != 8 && cardNo.length() != 0){
						JOptionPane.showMessageDialog(null, "The borrower's ID must be in the form of ID######");			//error
						bookIDField.setText("");
					} else if (cardNo.length() == 8) {
						ResultSet nameResult = statement.executeQuery("SELECT * FROM LIBRARY.BOOK_LOANS WHERE Card_id='" + cardNo + "' AND  Date_in is null;");
						isQueried = queryTable(statement, nameResult, model, isQueried);
					} else if (name.length() > 0) {
						ResultSet nameResult = statement.executeQuery("SELECT * FROM LIBRARY.BOOK_LOANS WHERE Card_id in (SELECT Card_id FROM LIBRARY.BORROWERS WHERE Bname LIKE '%" + name + "%') AND  Date_in is null;");
						isQueried = queryTable(statement, nameResult, model, isQueried);
					} else if (bookID.length() == 10 && cardNo.length() == 8 && name.length() > 0) {
						ResultSet nameResult = statement.executeQuery("SELECT * FROM LIBRARY.BORROWERS WHERE Isbn10="+ bookID +" and Card_id="+ cardNo + " and Date_in is null;");
						isQueried = queryTable(statement, nameResult, model, isQueried);
					}

					if(isQueried == true) {
						loansTable.setRowSelectionAllowed(true);
						loansTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
						//select row, get
						//System.out.println("selected: " + model.getDataVector().get(selectedRowIndex));

						loansTable.addMouseListener(new MouseAdapter(){
							public void mouseClicked(MouseEvent event)
							{
								String loan_id=(String)loansTable.getModel().getValueAt(loansTable.rowAtPoint(event.getPoint()), 0);
								System.out.println("loan id selected: " + loan_id);
								String currentDate = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());

								try {
									//check if it is overdue or not
									Statement st=connection.createStatement();
									st.executeUpdate("UPDATE LIBRARY.BOOK_LOANS SET Date_in='"+ currentDate + "' WHERE Loan_id='" + loan_id + "';");
									//ResultSet nameResult = st.executeQuery();
									Statement st2=connection.createStatement();
									ResultSet result = st2.executeQuery("SELECT * FROM LIBRARY.BOOK_LOANS WHERE Loan_id='" + loan_id + "';");
									if(result.next())
											isCheckedIn = true;
								} catch (Exception e1) {
									e1.printStackTrace();
								}
							}
						});


					}
				} catch (SQLException throwables) {
					throwables.printStackTrace();
				}
				panel.add(tableName);
				panel.add(scroll);
				panel.setVisible(true);
			}
		});

		//check in already searched items - after validation
		checkInButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(isQueried == false) {
					JOptionPane.showMessageDialog(null, "You have not searched for any books yet.");
				}
				else if (isQueried == true && isCheckedIn){
					//model.getDataVector().elementAt(loansTable.getSelectedRow());
					System.out.println("worked");
					JOptionPane.showMessageDialog(null, "You have checked in the selected books!");
					frame.dispose();
					new check_in();

				} else if(isQueried == true && loansTable.getSelectionModel().isSelectionEmpty() == true) {
					JOptionPane.showMessageDialog(null, "You have not selected any books. You can click and Ctrl+Click "
					+ "to select multiple books if you would like to check in multiple books");
				}
			}
		});

		//go back to homepage
		backButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//action
				try {
					frame.setVisible(false);
					new homepage();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
	}

	public static boolean queryTable(Statement statement, ResultSet nameResult, DefaultTableModel model, boolean isQueried) throws SQLException {
		int i = 0;
		while (nameResult.next()) {

			String loanID = nameResult.getString("Loan_id");
			String cardID = nameResult.getString("Card_id");
			String dateOut = nameResult.getString("Date_out");
			String dueDate = nameResult.getString("Due_date");
			String dateIN = nameResult.getString("Date_in");
			String isbn10 = nameResult.getString("Isbn10");
			//String isbn13 = nameResult.getString("Isbn13");

			model.addRow(new Object[]{loanID, cardID, dateOut,dueDate, dateIN, isbn10});
			i++;
		}
		if(i < 1){
			JOptionPane.showMessageDialog(null, "No Record Found", "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		else {
			System.out.println("/nFound: " + i);
			System.out.println("/Query in function: " + isQueried);
			isQueried = true;
			JOptionPane.showMessageDialog(null, "Please resize the window to be able to see the table of book loans queried");
			return true;
		}

	}

	public static void main(String[] args) {
		new check_in();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}

}
