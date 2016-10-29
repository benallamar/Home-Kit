package Interfaces.IHMHome;

import Component.Equipement;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashSet;

/**
 * Project Name : TL_crypto
 */
class IHMFirstMenu extends JPopupMenu implements MouseListener {
    public IHMFirstMenu(HashSet<Equipement> equipements, int currentEquipement) {
        for (Equipement equi : equipements) {
            add(new JMenuItem(equi.name()));
        }
        addMouseListener(this);

    }

    public void mousePressed(MouseEvent e) {
        System.out.print("you have chosen this one");
    }

    public void mouseReleased(MouseEvent e) {

    }

    public void mouseEntered(MouseEvent e) {

    }

    public void mouseExited(MouseEvent e) {

    }

    public void mouseClicked(MouseEvent e) {

    }

    public void saySomething(String s) {

    }
}