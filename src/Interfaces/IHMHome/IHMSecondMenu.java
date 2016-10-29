package Interfaces.IHMHome;

import Component.Equipement;
import Connection.Client;
import Interfaces.IHMConnexion;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Project Name : TL_crypto
 */
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
    }
}