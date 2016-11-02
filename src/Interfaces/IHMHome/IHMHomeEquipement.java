package Interfaces.IHMHome;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashSet;

import Component.Equipement;

/**
 * Project Name : TL_crypto
 */
public class IHMHomeEquipement extends JButton {
    Equipement equipement;

    public IHMHomeEquipement(Equipement equipement, int x, int y, int width, int height) {
        this.equipement = equipement;
        // Set the picture
        final JLabel label = new JLabel();
        label.setIcon(new ImageIcon(getClass().getResource("/data/icons/1477973680_computer.png")));
        setText(equipement.name());
        add(label);
        setBounds(x, y, width, height);
        if (equipement.isOn())
            setBackground(new Color(177, 218, 19));
        setBackground(new Color(-3826981));
        setContentAreaFilled(false);
        setForeground(new Color(-6112475));
        setText(equipement.name());
        setComponentPopupMenu(new IHMFirstMenu(equipement));
        //Set the action to open the "Information Equipement" JFrame
        addActionListener(event -> {
            equipement.display();
        });
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
