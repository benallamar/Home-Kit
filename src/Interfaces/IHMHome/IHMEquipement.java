package Interfaces.IHMHome;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashSet;

import Component.Equipement;

/**
 * Project Name : TL_crypto
 */
public class IHMEquipement extends JButton implements MouseListener {
    private String name;
    private int port;
    IHMFirstMenu menu;
    HashSet<Equipement> equipements = new HashSet<Equipement>();

    public IHMEquipement(String name, int port, HashSet<Equipement> equipements) {
        this.name = name;
        this.port = port;
        this.equipements = equipements;
        addMouseListener(this);
        //menu = new IHMFirstMenu(equipements, port);
    }

    public void mousePressed(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {

    }

    public void mouseEntered(MouseEvent e) {

    }

    public void mouseExited(MouseEvent e) {

    }

    public void mouseClicked(MouseEvent e) {
        menu.show(e.getComponent(), e.getX(), e.getY());

    }
}
