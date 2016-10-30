package Interfaces.IHMHome;

import Component.Equipement;

import java.awt.*;
<<<<<<< HEAD
=======
import java.util.HashMap;
>>>>>>> 7a6243510962f4572971285ac0623e07e60fb030
import java.util.HashSet;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class IHMHome extends JFrame {
<<<<<<< HEAD
    private HashSet<Equipement> equipements = new HashSet<Equipement>();
    private JPanel contentPane;
=======
    private HashMap<Integer, Equipement> equipements = new HashMap<Integer, Equipement>();
    private IHMConnectionPanel contentPane;
>>>>>>> 7a6243510962f4572971285ac0623e07e60fb030

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
<<<<<<< HEAD
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
=======
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

>>>>>>> 7a6243510962f4572971285ac0623e07e60fb030
}

