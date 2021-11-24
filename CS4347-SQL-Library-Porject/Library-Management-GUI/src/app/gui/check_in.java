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

public class check_in implements ActionListener{

    private JFrame frame;

    private JPanel checkInPanel;
    private JPanel mainPanel;

    GridBagConstraints center = new GridBagConstraints();
		//center.gridwidth = GridBagConstraints.REMAINDER;
        //center.anchor = GridBagConstraints.NORTH;
		center.anchor = GridBagConstraints.CENTER;
		center.fill = GridBagConstraints.HORIZONTAL;
		
	    borrowerCardNoLabel = new JLabel("Borrower Card Number", JLabel.RIGHT);
	    borrowerNameLabel = new JLabel("Borrower Name Button",JLabel.RIGHT)

	bookIDLabel = new JLabel("Book ID", JLabel.RIGHT);
		checkInButton = new JButton("Check In");
		backButton = new JButton("Return to Previous Page");
		pageTitleLabel = new JLabel("Check in a Book", JLabel.CENTER);
		//to change font and size of text
		pageTitleLabel.setFont(pageTitleLabel.getFont().deriveFont(30.0f));
		
		JFrame frame = new JFrame();
		
		JPanel panel = new JPanel();
		JPanel titlePanel = new JPanel();
		JPanel borrowerCardNoPanel = new JPanel();
		JPanel checkInButtonPanel = new JPanel();
		
		JTextField bookIDField = new JTextField(30);
		JTextField borrowerCardNoField = new JTextField(30);
		JTextField borrowerNameField = new JTextField(30);
		
		panel.setSize(500, 500);
		//set horizontal and vertical spacing
		panel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 150));
		checkInButtonPanel.setBorder(BorderFactory.createEmptyBorder(50, 100, 50, 100));
		//set grid layout
		panel.setLayout(new GridLayout(5, 5, 20, 30));
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

    public static void main(String[] args) {
		new check_in();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}
