package ui.IHMHome;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashSet;

import component.Equipement;

/**
 * Project Name : TL_crypto
 */
public class IHMHomeEquipement extends JButton {
    Equipement equipement;
    HashSet<Equipement> equipements = new HashSet<Equipement>();

    public IHMHomeEquipement(Equipement equipement, HashSet<Equipement> equipements, int x, int y, int width, int height) {
        this.equipement = equipement;
        this.equipements = equipements;
        JLabel label1 = new JLabel();
        label1.setIcon(new ImageIcon(getClass().getResource("/icons/1477973680_computer.png")));
        setText(" " + equipement.name());
        add(label1);
        setBounds(x, y, width, height);
        if (equipement.isOn()) {
            setBackground(new Color(177, 218, 19));
        } else {
            setBackground(new Color(254, 79, 18));
        }
        setBackground(new Color(-3826981));
        setContentAreaFilled(false);
        setForeground(new Color(-6112475));
        setText(equipement.name());
        setComponentPopupMenu(new IHMFirstMenu(equipements, equipement));
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                equipement.display();
            }
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
