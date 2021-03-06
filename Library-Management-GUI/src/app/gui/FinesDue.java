package app.gui;
import java.awt.*;
import java.awt.event.*;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

//import net.proteanit.sql.DbUtils;

import java.sql.Connection;

public class FinesDue {
    JFrame mainFrame;
    JPanel controlPanel;
    String url = "jdbc:mysql://database-lib.cliese5bfxyl.us-east-2.rds.amazonaws.com:3306/mysql";
    String username = "admin";
    String password = "lion1234";

    String loan_id ="";

    JTable table;

    FinesDue(String card_str) throws SQLException
    {
        prepareGUI(card_str);
    }

    void prepareGUI(String card_str) throws SQLException
    {

        String card=card_str;

        mainFrame=new JFrame("Fines Due");
        mainFrame.setSize(500,500);
        mainFrame.setLocation(20, 50);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        controlPanel=new JPanel();
        controlPanel.setBorder(new EmptyBorder(10,10,10,10));
        //setContentPane(controlPanel);
        GridBagLayout gbl_controlPanel=new GridBagLayout();
        gbl_controlPanel.columnWidths=new int[]{0,0};
        gbl_controlPanel.rowHeights = new int[]{0, 0, 0};
        gbl_controlPanel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
        gbl_controlPanel.rowWeights = new double[]{0.0,0.0,1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
        controlPanel.setLayout(gbl_controlPanel);


        //details
        JLabel details=new JLabel("Click on the desired row and then click Pay",JLabel.LEFT);
        details.setFont(new Font("Serif", Font.PLAIN,16));
        GridBagConstraints gbc_details=new GridBagConstraints();
        gbc_details.insets=new Insets(0,0,5,0);
        gbc_details.gridx=0;
        gbc_details.gridy=0;
        gbc_details.gridwidth=2;
        controlPanel.add(details, gbc_details);

        //space
        JLabel space1=new JLabel(" ",JLabel.CENTER);
        //space2.setFont(new Font("Times New Roman", Font.PLAIN,14));
        GridBagConstraints gbc_space1=new GridBagConstraints();
        gbc_space1.insets=new Insets(0,0,5,0);
        gbc_space1.gridx=0;
        gbc_space1.gridy=1;
        gbc_space1.gridwidth=2;
        controlPanel.add(space1, gbc_space1);


        //close button
        JButton close= new JButton("Close");
        close.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mainFrame.setVisible(false);
                mainFrame.dispose();
            }
        });



        JLayeredPane layeredPane=new JLayeredPane();
        GridBagConstraints gbc_layeredPane = new GridBagConstraints();
        gbc_layeredPane.insets = new Insets(0, 0, 5, 0);
        gbc_layeredPane.fill = GridBagConstraints.BOTH;
        gbc_layeredPane.gridx = 0;
        gbc_layeredPane.gridy = 2;
        controlPanel.add(layeredPane, gbc_layeredPane);

        String[] columns = {"Loan Id", "ISBN10", "Fine Amount"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        table=new JTable(model);
        JScrollPane scrollPane=new JScrollPane(table);
        scrollPane.setBounds(6,6,600,400);
        layeredPane.add(scrollPane);


        scrollPane.setViewportView(table);

        table.setColumnSelectionAllowed(false);
        table.setRowSelectionAllowed(true);

        table.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent evnt)
            {
                if(evnt.getClickCount() ==1 || evnt.getClickCount()==2)
                {
                    loan_id= (String)table.getModel().getValueAt(table.rowAtPoint(evnt.getPoint()), 0);

                }
            }
        });


        //Pay
        JButton pay=new JButton("Pay Fine");
        pay.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
               try {

                    Connection connection = DriverManager.getConnection(url, username, password);
                    Statement stmt1=connection.createStatement();
                    ResultSet rs=stmt1.executeQuery("Select * from LIBRARY.BOOK_LOANS where Loan_id='"+loan_id+"' and Date_in is null;");

                    if(!rs.next())
                    {
                        connection =DriverManager.getConnection(url, username, password);
                        Statement stmt2=connection.createStatement();
                        stmt2.executeUpdate("Update LIBRARY.FINES set Paid=1 where Loan_id='"+loan_id+"';");
                        JOptionPane.showMessageDialog(null, "Fines for Loan Id '"+loan_id+"' has been paid ");
                        mainFrame.setVisible(false);
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(null, "This book has not been returned");
                    }
                } catch (SQLException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }


            }
        });
        GridBagConstraints gbc_pay=new GridBagConstraints();
        gbc_pay.fill=GridBagConstraints.HORIZONTAL;
        gbc_pay.insets = new Insets(0, 0, 5, 0);
        gbc_pay.gridx = 0;
        gbc_pay.gridy = 3;
        gbc_pay.gridwidth=2;
        controlPanel.add(pay, gbc_pay);

        //space
        JLabel space2=new JLabel("  ",JLabel.CENTER);
        //searchLabel.setFont(new F);
        GridBagConstraints gbc_space2=new GridBagConstraints();
        gbc_space2.insets=new Insets(0,0,5,0);
        gbc_space2.gridx=0;
        gbc_space2.gridy=4;
        gbc_space2.gridwidth=2;
        controlPanel.add(space2, gbc_space2);


        GridBagConstraints gbc_btnClose = new GridBagConstraints();
        gbc_btnClose.fill=GridBagConstraints.HORIZONTAL;
        gbc_btnClose.insets = new Insets(0, 0, 5, 0);
        gbc_btnClose.gridx = 0;
        gbc_btnClose.gridy = 5;
        gbc_btnClose.anchor=GridBagConstraints.PAGE_END;
        controlPanel.add(close, gbc_btnClose);
        try{
            //jdbc connection to database
            Connection conn=DriverManager.getConnection(url, username, password);
            Statement stmt=conn.createStatement();
            ResultSet rs= null;

            rs= stmt.executeQuery("Select f.Loan_id, b.Isbn10, f.Fine_amt from LIBRARY.FINES as f left outer join LIBRARY.BOOK_LOANS as b on b.Loan_id=f.Loan_id where b.Card_id='"+card+"' and Paid=0;");

            table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            table.setEnabled(true);

            SwingUtilities.isEventDispatchThread();
//            //TableColumn c = new TableColumn(0);
//            //table.getColumnModel().addColumn(c);
//           table.getColumnModel().getColumn(0).setPreferredWidth(150);
//            table.getColumnModel().getColumn(1).setPreferredWidth(150);
//            table.getColumnModel().getColumn(2).setPreferredWidth(150);
//            //table.getColumnModel().getColumn(3).setPreferredWidth(100);
            table.setModel(MakeTable.resultSetToTableModel(rs));
            //TableColumn c = new TableColumn(0);
            /*int size =0;
            if (rs != null)
            {
                rs.last();    // moves cursor to the last row
                size = rs.getRow(); // get row id
            }*/
            //Object [] data = {"ID000001", "123456789", 3.75};
            //model.addRow(data);


        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }

        mainFrame.add(controlPanel);
        mainFrame.setVisible(true);

    }
}
