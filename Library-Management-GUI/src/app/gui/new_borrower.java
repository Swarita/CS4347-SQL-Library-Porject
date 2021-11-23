package app.gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class New_Borrower extends JFrame{
    private JPanel NewBorrowerPanel;
    private JPanel mainPanel;
    private JLabel LastNameField;
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
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
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
        //SearchPanel.setSize(500,500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        String firstName = FirstNameField.getText();
        String lastName = LastNameField.getText();
        String ssn = SSNField.getText();
        String city = City.getText();
        String address = AddressField.getText();
        String phone = Phone.getText();
        String state = StateField.getText();
        String email = EmailField.getText();

        boolean isValid = true;



        //When the Add a New Borrower Button is pressed
        AddBorrower.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {

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
