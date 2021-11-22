package app.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class homepage extends JFrame{
    private JPanel mainPanel;
    private JLabel Title;
    private JButton Search;
    private JButton Check_in;
    private JButton Check_out;
    private JButton Create_new_borrower;
    private JButton Check_fines;

    public homepage() {
        GUI();
    }

    public static void main(String[] args){
        try {
            homepage homepage = new homepage();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void GUI() {
        JFrame mainPage = new JFrame("Homepage");
        mainPage.setContentPane(this.mainPanel);
        mainPage.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainPage.pack();
        mainPage.setVisible(true);

        this.Search.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainPage.setVisible(false);
                try {
                    new search();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }

            }
        });

        this.Check_in.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainPage.setVisible(false);
                try {
                    new check_in();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });

        this.Check_out.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainPage.setVisible(false);
                try {
                    new check_out();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });

        this.Create_new_borrower.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainPage.setVisible(false);
                try {
                    new new_borrower();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });

        this.Check_fines.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainPage.setVisible(false);
                try {
                    new fines();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });
    }
}
