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

//import net.proteanit.sql.DbUtils;

import java.sql.Connection;

public class PaidFines extends JFrame
{
    JFrame mainFrame;
    JPanel controlPanel;
    //static Connection conn=null;
    String url = "jdbc:mysql://database-lib.cliese5bfxyl.us-east-2.rds.amazonaws.com:3306/mysql";
    String username = "admin";
    String password = "lion1234";
    JTable table;

    PaidFines(String card_str) throws SQLException
    {
        prepareGUI(card_str);
    }

    void prepareGUI(String card_str) throws SQLException
    {

        String card=card_str;

        mainFrame=new JFrame("Paid Fines");
        mainFrame.setSize(500,500);
        mainFrame.setLocation(20, 50);
        mainFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);


        controlPanel=new JPanel();
        controlPanel.setBorder(new EmptyBorder(10,10,10,10));
        setContentPane(controlPanel);
        GridBagLayout gbl_controlPanel=new GridBagLayout();
        gbl_controlPanel.columnWidths=new int[]{0,0};
        gbl_controlPanel.rowHeights = new int[]{0, 0, 0};
        gbl_controlPanel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
        gbl_controlPanel.rowWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
        controlPanel.setLayout(gbl_controlPanel);

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
        gbc_layeredPane.gridy = 0;
        controlPanel.add(layeredPane, gbc_layeredPane);

        table=new JTable();
        JScrollPane scrollPane=new JScrollPane(table);
        scrollPane.setBounds(6,6,600,400);
        layeredPane.add(scrollPane);


        scrollPane.setViewportView(table);

        GridBagConstraints gbc_btnClose = new GridBagConstraints();
        gbc_btnClose.fill=GridBagConstraints.HORIZONTAL;
        gbc_btnClose.insets = new Insets(0, 0, 5, 0);
        gbc_btnClose.gridx = 0;
        gbc_btnClose.gridy = 2;
        gbc_btnClose.anchor=GridBagConstraints.PAGE_END;
        controlPanel.add(close, gbc_btnClose);
        try{
            //jdbc connection to database
            Connection conn=DriverManager.getConnection(url, username, password);
            Statement stmt=conn.createStatement();
            ResultSet rs= null;


            rs= stmt.executeQuery("Select f.Loan_id, b.Isbn10 from LIBRARY.FINES as f left outer join LIBRARY.BOOK_LOANS as b on b.Loan_id=f.Loan_id where b.Card_id='"+card+"' and Paid=1;");

            //table.setModel(DbUtils.resultSetToTableModel(rs));
            table.setEnabled(false);

            table.setModel(MakeTable.resultSetToTableModel(rs));


            table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }

        mainFrame.add(controlPanel);
        mainFrame.setVisible(true);

    }
}
