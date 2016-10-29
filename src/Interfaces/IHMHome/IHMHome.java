package Interfaces.IHMHome;

import Component.Equipement;

import java.awt.*;
import java.util.HashSet;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class IHMHome extends JFrame {
    private HashSet<Equipement> equipements = new HashSet<Equipement>();
    private JPanel contentPane;

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
    public IHMHome() {
        equipements.add(new Equipement("marouane", 100));
        equipements.add(new Equipement("marouane", 200));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1200, 750);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        JLabel btnHome = new JLabel("Home");
        btnHome.setBounds(350, 25, 100, 25);
        btnHome.setBackground(new Color(50, 50, 50));
        contentPane.add(btnHome);
        drawEquipement();
        setVisible(true);
    }

    public void drawEquipement() {
        int width = (int) ((getWidth() / 5) * 0.75);
        int height = (int) ((getHeight() / 5) * 0.30);
        int x = 100;
        int y = 50;
        int i = 0;
        for (Equipement equipement : equipements) {
            IHMEquipement aux = new IHMEquipement(equipement.name(), equipement.getPort(), equipements);
            aux.setText(equipement.name());
            aux.setBounds(x + width * i, y + i * height, width, height);
            aux.setBackground(Color.green);
            aux.setOpaque(true);
            aux.setFocusPainted(true);
            contentPane.add(aux);
            i++;
        }
        contentPane.repaint();
        this.getX();
    }
}

