package ui.IHMHome;

import component.Equipement;
import java.util.Collection;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

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