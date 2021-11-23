package app.gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class Search extends JFrame{
    private JPanel SearchPanel;
    private JTextField SearchField;
    private JButton SearchButton;
    private JButton BackButton;

    Search() throws SQLException{
        createGui();
    }

    public static void main (String args[]) {
        try{
            Search searchObj = new Search();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    void createGui() throws SQLException {
        setContentPane(SearchPanel);
        setSize(500,500);
        //SearchPanel.setSize(500,500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        //When the Search Button is Pressed
        SearchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });

        //When the Back Button is Pressed
        BackButton.addActionListener(new ActionListener() {
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
