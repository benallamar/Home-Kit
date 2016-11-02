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
class IHMSecondMenu extends JMenuItem {
    Equipement client;
    Equipement server;

    public IHMSecondMenu(Equipement clientEqui, Equipement serverEqui) {
        //Config to the name of the server
        super(serverEqui.name());
        client = clientEqui;
        server = serverEqui;
        //Add an action listner
        addActionListener(event -> {
            client.setClientOption(1);
            client.setServerPort(server.getPort());
            new Thread(client).start();
        });
    }
}