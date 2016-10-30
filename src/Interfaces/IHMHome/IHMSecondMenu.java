package Interfaces.IHMHome;

import Component.Equipement;
<<<<<<< HEAD

import javax.swing.*;
import java.util.HashSet;
=======
import Connection.Client;
import Interfaces.IHMConnexion;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
>>>>>>> 7a6243510962f4572971285ac0623e07e60fb030

/**
 * Project Name : TL_crypto
 */
<<<<<<< HEAD
class IHMSecondMenu extends JPopupMenu {
    JMenuItem anItem;

    public IHMSecondMenu(HashSet<Equipement> equipements, int currentEquipement) {
        System.out.print(currentEquipement);
        for (Equipement equi : equipements) {

            anItem = new JMenuItem(equi.name());
            add(anItem);

        }


=======
class IHMSecondMenu extends JMenuItem implements MouseListener {
    Equipement client;
    Equipement server;

    public IHMSecondMenu(Equipement clientEqui, Equipement serverEqui) {
        //Open config
        super(clientEqui.name());
        client = clientEqui;
        server = serverEqui;
        addMouseListener(this);
    }

    public void mousePressed(MouseEvent e) {
        //try {
        client.switchMode();
        System.out.println(client.getPort());
        System.out.println(server.getPort());
        client.setServerPort(server.getPort());
        new Thread(client).start();
        //c.start();
        //} catch (InterruptedException err) {

        //}

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
        System.out.print(s);
>>>>>>> 7a6243510962f4572971285ac0623e07e60fb030
    }
}