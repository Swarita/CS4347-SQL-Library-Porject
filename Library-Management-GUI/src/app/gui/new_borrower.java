package app.gui;

import javax.swing.*;
import javax.swing.JOptionPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Connection;

public class New_Borrower extends JFrame{
    private JPanel NewBorrowerPanel;
    private JPanel mainPanel;
    private JLabel LastName;
    private JLabel City;
    private JLabel Phone;
    private JLabel Email;
    private JLabel NewBorrower;
    private JLabel FirstName;
    private JLabel SSN;
    private JLabel Address;
    private JLabel State;
    private JTextField FirstNameField;
    private JTextField SSNField;
    private JTextField AddressField;
    private JTextField StateField;
    private JTextField LastNameField;
    private JTextField CityField;
    private JTextField PhoneField;
    private JTextField EmailField;
    private JButton AddBorrower;
    private JButton Back;

    New_Borrower () throws SQLException {
        createGui();
    }

    public static void main(String args[]){
        try{
            New_Borrower addBorrowerObj = new New_Borrower();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    void createGui() throws SQLException {
        setContentPane(NewBorrowerPanel);
        setSize(800,500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        //When the Add a New Borrower Button is pressed
        AddBorrower.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String firstName = FirstNameField.getText();
                    String lastName = LastNameField.getText();
                    String ssn = SSNField.getText();
                    String city = CityField.getText();
                    String address = AddressField.getText();
                    String phone = PhoneField.getText();
                    String state = StateField.getText();
                    String email = EmailField.getText();

                    if (firstName.equals("") || lastName.equals("") || ssn.equals("") || city.equals("") || address.equals("") || phone.equals("") || state.equals("") || email.equals("")) {
                        JOptionPane.showMessageDialog(null, "You must enter all attributes!");
                    }

                    if (ssn.length() < 9 || ssn.length() > 9) {
                        JOptionPane.showMessageDialog(null, "The SSN must be 9 digits long!");
                        SSNField.setText("");
                    }

                    if (phone.length() > 10 || phone.length() < 10 ) {
                        JOptionPane.showMessageDialog(null, "The Phone Number length must be 10 digits long!");
                        PhoneField.setText("");
                    }

                    //Connection Information
                    String url = "jdbc:mysql://database-lib.cliese5bfxyl.us-east-2.rds.amazonaws.com:3306/mysql";
                    String username = "admin";
                    String password = "lion1234";

                    try {
                        Connection connection = DriverManager.getConnection(url, username, password);
                        Statement statement = connection.createStatement();

                        //Checking to see if the following Borrower is in the system, based on the SSN
                        ResultSet resultSet = statement.executeQuery("SELECT * FROM LIBRARY.BORROWER WHERE Ssn='" + ssn + "';");


                        if (resultSet.next()) {
                            //If the SSN already exists in the Database
                            JOptionPane.showMessageDialog(null, "The following Borrower already exists ! You must add new Borrowers only !");
                            SSNField.setText("");
                        } else {
                            //If the SSN does not exist in the Database
                            Statement statementTwo = connection.createStatement();

                            //Checking to see what the max Card ID is
                            ResultSet resultSetTwo = statementTwo.executeQuery("SELECT max(Card_id) FROM LIBRARY.BORROWER;");

                            int cardId = 0;

                            //Depending on what the max ID is, this will ensure that the new Insert into database has a different Card_ID, which will be 1 up from the max
                            if (resultSetTwo.next()) {
                                cardId = resultSetTwo.getInt(1)+1;
                            }

                            //Concat to full name
                            String bName = firstName.concat(" " + lastName);

                            //Executing the insert into the Database
                            Statement statementThree = connection.createStatement();
                            statementThree.executeUpdate("INSERT INTO `LIBRARY`.`BORROWER` (`Card_id`, `Ssn`, `Bname`, `Address`, `Phone`) VALUES (" + cardId + "," + ssn + ", '" + bName + "', '" + address + "', " + phone + ")");

                            FirstNameField.setText("");
                            LastNameField.setText("");
                            SSNField.setText("");
                            CityField.setText("");
                            AddressField.setText("");
                            PhoneField.setText("");
                            StateField.setText("");
                            EmailField.setText("");

                            //The following is displaying the Card ID of the new Borrower
                            JOptionPane.showMessageDialog(null, "The Card Id for the new Borrower is : " + cardId);
                        }
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }

                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });

        //When the Back Button is Pressed
        Back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    setVisible(false);
                    new homepage();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });
    }





}
