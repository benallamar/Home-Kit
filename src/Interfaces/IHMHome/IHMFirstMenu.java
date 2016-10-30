package Interfaces.IHMHome;

import Component.Equipement;

import javax.swing.*;
<<<<<<< HEAD
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
=======
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Collection;
>>>>>>> 7a6243510962f4572971285ac0623e07e60fb030
import java.util.HashSet;

/**
 * Project Name : TL_crypto
 */
<<<<<<< HEAD
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

=======
class IHMFirstMenu extends JPopupMenu {
    JMenuItem anItem;

    public IHMFirstMenu(Collection<Equipement> equipements, Equipement equipement) {
        //Open config
        JMenuItem open = new JMenuItem("open");
        open.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                equipement.display();
            }

            @Override
            public void mousePressed(MouseEvent e) {
                equipement.display();
            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
        JMenu connect = new JMenu("connect");
        for (Equipement equip : equipements) {
            if (!equip.equals(equipement)) {
                connect.add(new IHMSecondMenu(equipement, equip));
            }
        }
        add(open);
        add(connect);
>>>>>>> 7a6243510962f4572971285ac0623e07e60fb030
    }
}