package Interfaces.IHMHome;

import Component.Equipement;

import javax.swing.*;
import java.util.HashSet;

/**
 * Project Name : TL_crypto
 */
class IHMSecondMenu extends JPopupMenu {
    JMenuItem anItem;

    public IHMSecondMenu(HashSet<Equipement> equipements, int currentEquipement) {
        System.out.print(currentEquipement);
        for (Equipement equi : equipements) {

            anItem = new JMenuItem(equi.name());
            add(anItem);

        }


    }
}