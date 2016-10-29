package Interfaces.IHMHome;

import Component.Equipement;

import java.awt.*;
import java.util.HashMap;
import java.util.HashSet;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class IHMHome extends JFrame {
    private HashMap<Integer, Equipement> equipements = new HashMap<Integer, Equipement>();
    private IHMConnectionPanel contentPane;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    IHMHome frame = new IHMHome();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public IHMHome() throws InterruptedException {
        setTitle("Home Kit SIS");
        equipements.put(2000, new Equipement("marouane", 2002));
        equipements.put(3000, new Equipement("marouane", 2003));
        equipements.put(4000, new Equipement("marouane", 2004));
        equipements.put(5000, new Equipement("marouane", 2005));
        equipements.put(6000, new Equipement("marouane", 2006));
        equipements.put(7000, new Equipement("marouane", 2007));
        equipements.put(8000, new Equipement("marouane", 2008));
        equipements.put(9000, new Equipement("marouane", 2009));
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

}

