package Interfaces.IHMHome;

import Component.Equipement;

import java.awt.*;
import java.util.HashMap;
import java.util.HashSet;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class IHMHome extends JFrame {
    private static IHMConnectionPanel contentPane;

    /**
     * Create the frame.
     */

    public IHMHome(HashMap<Integer, Equipement> equipements) throws InterruptedException {
        setTitle("Home Kit SIS");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        contentPane = new IHMConnectionPanel(equipements);
        contentPane.setLayout(null);
        JLabel btnHome = new JLabel("Home");
        btnHome.setBounds(350, 25, 500, 25);
        contentPane.add(btnHome);
        setContentPane(contentPane);
        setSize(new Dimension(contentPane.getWidth(), contentPane.getHeight()));
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }
    /**
     * Update the display
     */
    public static  void update(){
        contentPane.repaint();
    }
}

