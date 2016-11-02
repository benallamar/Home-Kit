package Interfaces.IHMHome;

import Component.Equipement;
import HomKit.Home;

import java.awt.*;
import java.util.HashSet;

import javax.swing.*;

public class IHMHome extends JFrame {
    private IHMConnectionPanel contentPane;
    HashSet<Equipement> equipements = new HashSet<Equipement>();

    /**
     * Create the frame.
     */

    public IHMHome(HashSet<Equipement> equipements) {
        setTitle("Home Kit SIS");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.equipements = equipements;
        try {
            contentPane = new IHMConnectionPanel(equipements);

        } catch (InterruptedException e) {

        }
        setContentPane(contentPane);
        setSize(new Dimension(contentPane.getWidth(), contentPane.getHeight()));
        setLocationRelativeTo(null);
        Home.loadPage.dispose();
        requestFocus();
        setResizable(false);
        setVisible(true);

    }

    /**
     * Update the display
     */
    public void update() {
        contentPane.update();
    }
}

