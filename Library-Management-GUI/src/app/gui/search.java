package app.gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.sql.*;
import java.sql.DriverManager;

import java.lang.*;
import java.util.*;


public class search extends JFrame{
    //GUI aspects
    private JPanel panelMain;
    private JButton searchButton;
    private JButton backButton;
    private JTextField searchField;
    private JTable searchResults;

    //Database Information
    String url= "jdbc:mysql://database-lib.cliese5bfxyl.us-east.2.rds.amazonaws.com:3306/mysql";
    String user= "admin";
    String password= "lion1234";

    search () throws SQLException{
        searchGui();
    }

    public static void main(String[] args) {
        try{
            search searches= new search();
        }catch(Exception e1){
            e1.printStackTrace();
        }
    }

    void searchGui() throws SQLException {
        setContentPane(panelMain);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
        //When Search Button is clicked
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    //Gets the inputted text in the searchField text field
                    String searched = searchField.getText();
                    if(searched.isEmpty()){
                        //If the searchField is empty, then tell the user to enter something in the text field.
                        JOptionPane.showMessageDialog(null, "Please Enter an ISBN, Title, or Author.");
                    }else{
                        DefaultTableModel model= new DefaultTableModel(0,0);
                        //Table Headers
                        String header[] = new String [] {"ISBN10", "Title", "Author(s)", "Availability"};
                        model.setColumnIdentifiers(header);

                        ResultSet searchResultSet= null;
                        searchResults.setModel(model);

                        //Connecting to the Database
                        Connection con = DriverManager.getConnection(url, user, password);
                        Statement state= con.createStatement();

                        //Querying the database to display all instances of
                        searchResultSet= state.executeQuery("SELECT ISBN10, Title, Author, (CASE WHEN Isbn10 in(SELECT ISBN10 FROM LIBRARY.BOOK_LOANS WHERE Date_in IS NULL) then 'Checked-Out' else 'Available' end) AS Availability FROM LIBRARY.BOOKS WHERE (ISBN10 LIKE '%"+searched+"%' OR Title LIKE '%"+searched+"%' OR Author like '%"+searched+"%')");

                        //Putting the results into the table
                        searchResults.setModel(MakeTable.resultSetToTableModel(searchResultSet));
                        //Disable editing on the boxes
                        searchResults.setEnabled(false);
                    }
                }catch(Exception e1){
                    e1.printStackTrace();
                }
            }
        });
        //When Back Button is clicked
        backButton.addActionListener(new ActionListener() {
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

//Takes ResultSet given through the Query and formats it in TableModel form
class MakeTable {
    public static TableModel resultSetToTableModel(ResultSet rs) {
        try {
            ResultSetMetaData metaData = rs.getMetaData();
            int numberOfColumns = metaData.getColumnCount();
            Vector columnNames = new Vector();

            // Get the column names
            for (int column = 0; column < numberOfColumns; column++) {
                columnNames.addElement(metaData.getColumnLabel(column + 1));
            }

            // Get all rows.
            Vector rows = new Vector();

            while (rs.next()) {
                Vector newRow = new Vector();

                for (int i = 1; i <= numberOfColumns; i++) {
                    newRow.addElement(rs.getObject(i));
                }

                rows.addElement(newRow);
            }

            return new DefaultTableModel(rows, columnNames);
        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }
    }
}
