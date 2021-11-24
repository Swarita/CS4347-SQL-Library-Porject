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
import java.sql.SQLException;

public class check_in implements ActionListener {

    private JFrame frame;
	
	private JPanel checkInButtonPanel;
    private JPanel titlePanel;
    private JPanel panel;
    
    private JTextField bookIDField;
    private JTextField borrowerCardNoField;
    private JTextField borrowerNameField;
    
    private JLabel pageTitleLabel;
    private JLabel bookIDLabel;
    private JLabel borrowerCardNoLabel;
    private JLabel borrowerNameLabel;
    
    private JButton checkInButton;
    private JButton backButton;


	public check_in() {
		
	    borrowerCardNoLabel = new JLabel("Borrower Card Number", JLabel.RIGHT);
	    borrowerNameLabel = new JLabel("Borrower Name Button", JLabel.RIGHT);
	    bookIDLabel = new JLabel("Book ID", JLabel.RIGHT);
		pageTitleLabel = new JLabel("Check in a Book", JLabel.CENTER);
		checkInButton = new JButton("Check In");
		backButton = new JButton("Return to Previous Page");
		//to change font and size of text
		pageTitleLabel.setFont(pageTitleLabel.getFont().deriveFont(30.0f));
		
		frame = new JFrame();							//frames
		
		panel = new JPanel();							//panels
		titlePanel = new JPanel();
		checkInButtonPanel = new JPanel();
		
		bookIDField = new JTextField(30);			//fields
		borrowerCardNoField = new JTextField(30);
		borrowerNameField = new JTextField(30);
		
		panel.setSize(500, 500);
		
		//set horizontal and vertical spacing
		panel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 150));
		panel.setLayout(new GridLayout(5, 5, 20, 30));

		checkInButtonPanel.setBorder(BorderFactory.createEmptyBorder(50, 100, 50, 100));
		checkInButtonPanel.setLayout(new GridLayout(0, 1, 20, 30));

		
		//add buttons and labels
		titlePanel.add(pageTitleLabel);
		panel.add(borrowerCardNoLabel);
		panel.add(borrowerCardNoField);
		panel.add(borrowerNameLabel);
		panel.add(borrowerNameField);
		panel.add(bookIDLabel);
		panel.add(bookIDField);
		checkInButtonPanel.add(checkInButton);
		checkInButtonPanel.add(backButton);
		
		frame.add(panel, BorderLayout.CENTER);
		frame.add(titlePanel, BorderLayout.NORTH);
		frame.add(checkInButtonPanel, BorderLayout.SOUTH);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Check In");
		frame.pack();
		frame.setVisible(true);
		
		checkInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	//action
            }
        });
		
		backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	//action
            	try {
                    frame.setVisible(false);
                    //new homepage();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });
		
	}
	
	public static void main(String[] args) {
		new check_in();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}

