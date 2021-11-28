package app.gui;

import javax.swing.*;
import javax.swing.plaf.nimbus.State;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.sql.DriverManager;
import java.sql.Driver;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class check_out {
    private JPanel panel1;
    private JPanel mainPanel;
    private JTextField ISBN_Text;
    private JLabel ISBN;
    private JTextField Card_no_Text;
    private JLabel Card_no;
    private JButton checkOutButton;
    private JButton backButton;

    public check_out() {
        GUI();
    }

    public void GUI() {
        JFrame checkout = new JFrame("Check Out");
        checkout.setContentPane(this.mainPanel);
        checkout.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        checkout.pack();
        checkout.setVisible(true);

        this.backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               checkout.setVisible(false);

               try {
                   new homepage();
               }catch(Exception e1) {
                   e1.printStackTrace();
               }
            }
        });

        this.checkOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (Card_no_Text.getText().equals("") || ISBN_Text.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Please fill in both fields");
                } else {
                    try {
                        String isbn = ISBN_Text.getText();
                        int cardNo = Integer.parseInt(Card_no_Text.getText());
                        String date = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
                        Calendar calendar = Calendar.getInstance();
                        calendar.add(Calendar.DATE, 14);
                        String dueDate = new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());

                        /*try {
                            Class.forName("com.mysql.cj.jdbc.Driver");
                        } catch (Exception e1) {
                            e1.printStackTrace();
                        }*/

                        //Driver d = new com.mysql.jdbc.Driver();

                        String url = "jdbc:mysql://database-lib.cliese5bfxyl.us-east-2.rds.amazonaws.com:3306/mysql";
                        String test = "jdbc:mysql://localhost:3306/test";
                        String user = "admin";
                        String pass = "lion1234";

                        Connection connection = DriverManager.getConnection(url,user,pass);
                        Statement s1 = connection.createStatement();
                        Statement s2 = connection.createStatement();
                        ResultSet r1 = s1.executeQuery("SELECT * from LIBRARY.BOOKS WHERE ISBN10 = '" + isbn + "';");
                        ResultSet r2 = s2.executeQuery("SELECT * from LIBRARY.BORROWERS WHERE Card_id = '" + cardNo + "';");

                        if (r1.next() && r2.next()) {
                            Statement s3 = connection.createStatement();
                            ResultSet r3 = s3.executeQuery("SELECT * from LIBRARY.BOOK_LOANS WHERE ISBN10 = '" + isbn + "' and Date_in is null;");
                            if (r3.next()) {
                                JOptionPane.showMessageDialog(null, "This is book is already checked out");
                                ISBN_Text.setText("");
                            } else {
                                Statement s4 = connection.createStatement();
                                ResultSet r4 = s4.executeQuery("SELECT * from LIBRARY.BOOK_LOANS WHERE Card_id = '" + cardNo + "' and Date_in is null and Due_date<CAST(CURRENT_TIMESTAMP AS DATE);");
                                if (r4.next()) {
                                    JOptionPane.showMessageDialog(null, "This borrower has an overdue book");
                                    ISBN_Text.setText("");
                                    Card_no_Text.setText("");
                                } else {
                                    Statement s5 = connection.createStatement();
                                    ResultSet r5 = s5.executeQuery("SELECT COUNT(*) from LIBRARY.BOOK_LOANS WHERE Card_id = '" + cardNo + "' and Date_in is null");
                                    r5.next();
                                    if (r5.getInt(1)>=3) {
                                        JOptionPane.showMessageDialog(null, "This borrower already has 3 active book loans");
                                        Card_no_Text.setText("");
                                        ISBN_Text.setText("");
                                    } else {
                                        Statement s6 = connection.createStatement();
                                        ResultSet r6 = s6.executeQuery("SELECT * from LIBRARY.BOOK_LOANS book, FINES fine WHERE Card_id = '" + cardNo + "' and book.Loan_id = fine.Loan_id and Paid = 0");
                                        if (r6.next()) {
                                            JOptionPane.showMessageDialog(null, "This borrower has an unpaid fine");
                                            Card_no_Text.setText("");
                                            ISBN_Text.setText("");
                                        } else {
                                            Statement s7 = connection.createStatement();
                                            ResultSet r7 = s7.executeQuery("SELECT MAX(Loan_id) from LIBRARY.BOOK_LOANS");
                                            int id = 0;
                                            if (r7.next()) {
                                                id = r7.getInt(1) + 1;
                                            }

                                            Statement s8 = connection.createStatement();
                                            s8.executeUpdate("INSERT INTO LIBRARY.BOOK_LOANS (Loan_id, ISBN10, Card_id, Date_out, Due_Date) VALUES ("+id+", '"+isbn+"', "+cardNo+", '"+date+"', '"+dueDate+"');");
                                            JOptionPane.showMessageDialog(null, "Checkout successful");
                                            Card_no_Text.setText("");
                                            ISBN_Text.setText("");
                                        }
                                    }
                                }
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "The ISBN or Card No is invalid");
                            Card_no_Text.setText("");
                            ISBN_Text.setText("");
                        }
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                }

            }
        });
    }
}
