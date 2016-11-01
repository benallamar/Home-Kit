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
        JMenu connect = new JMenu("connect");
        for (Equipement equip : equipements) {
            if (!equip.equals(equipement) && !equip.connectedWith(equipement)) {
                connect.add(new IHMSecondMenu(equipement, equip));
            }
        }
        add(connect);
    }
}