package ui.IHMHome;

import component.Equipement;

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
        //Config to the name of the server
        super(serverEqui.name());
        client = clientEqui;
        server = serverEqui;
        addMouseListener(this);
    }

    public void mousePressed(MouseEvent e) {
        client.setClientMode();
        client.setOption(1);
        client.setServerPort(server.getPort());
        new Thread(client).start();
    }

    public void mouseReleased(MouseEvent e) {

    }

    public void mouseEntered(MouseEvent e) {

    }

    public void mouseExited(MouseEvent e) {

    }

    public void mouseClicked(MouseEvent e) {

    }

}