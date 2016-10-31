package Interfaces.IHMHome;

import Component.Equipement;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Collection;
import java.util.HashSet;

/**
 * Project Name : TL_crypto
 */
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
    }
}