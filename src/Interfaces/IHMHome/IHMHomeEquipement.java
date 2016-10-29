package Interfaces.IHMHome;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.HashSet;

import Component.Equipement;

/**
 * Project Name : TL_crypto
 */
public class IHMHomeEquipement extends JButton {
    Equipement equipement;
    HashMap<Integer, Equipement> equipements = new HashMap<Integer, Equipement>();

    public IHMHomeEquipement(Equipement equipement, HashMap<Integer, Equipement> equipements, int x, int y, int width, int height) {
        this.equipement = equipement;
        this.equipements = equipements;
        setText(equipement.name());
        setBounds(x, y, width, height);
        if (equipement.isOn()) {
            setBackground(new Color(177, 218, 19));
        } else {
            setBackground(new Color(254, 79, 18));
        }
        setOpaque(true);
        setFocusPainted(true);
        setComponentPopupMenu(new IHMFirstMenu(equipements.values(), equipement));
    }

    public Equipement getEquipement() {
        return equipement;
    }

    public int[] getInputPos() {
        return new int[]{getX() + getWidth() / 2, getY()};
    }

    public int[] getOutputPos() {
        return new int[]{getX() + getWidth() / 2, getY() + getHeight()};
    }

    public boolean isEqual(int port) {
        return equipement.getPort() == port;
    }
}
