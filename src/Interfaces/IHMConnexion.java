package Interfaces;

import Component.Equipement;
import Connection.Client;
import Connection.IOOperation;
import Connection.SocketHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.Socket;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.concurrent.TimeUnit;

/*

This Class is to display all the message of the current happening actions.
    - 1 - Do you accept Connection.
    - 2 - Do you see the same message.
    - 3 - Connection accepted.
    - 3 - Waiting for connection.
    - 4 - Connection Refused
 */
public class IHMConnexion extends JFrame {
    private JPanel panelContent = new JPanel();
    private String host, client;

    /**
     * Create the panel.
     */
    public IHMConnexion(String host, String client, boolean isServer) {
        this.host = host;
        this.client = client;
        setBounds(150 + (isServer ? 430 : 0), 200, 200, 200);
        setContentPane(panelContent);
        setTitle(host);
        setUndecorated(true);
        getRootPane().setWindowDecorationStyle(JRootPane.NONE);
        setSize(new Dimension(400, 200));
        setResizable(false);
        setVisible(true);

    }

    public boolean authenticate(SocketHandler s, Client client) throws InterruptedException, IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        ImageIcon icon = new ImageIcon("src/data/icons/logo.png");
        String message = IOOperation.genKeyMessage(s.getPubKey(), (String) s.getKey("token"), (String) s.getKey("code"));
        int dialogButton = JOptionPane.YES_NO_OPTION;
        int dialogResult = JOptionPane.showConfirmDialog(this, "Do you see this: \n " + message + "\n on your system panel ?", " Avoid Man In The Middle Attack", dialogButton, JOptionPane.INFORMATION_MESSAGE, icon);
        return dialogResult == 0;
    }

    public boolean doYouAccept(String equiName) {
        ImageIcon icon = new ImageIcon("src/data/icons/logo.png");
        int dialogButton = JOptionPane.YES_NO_OPTION;
        int dialogResult = JOptionPane.showConfirmDialog(this, "The device : " + equiName.toUpperCase() + " is not connected \n Do you want to accept the connection ?", "Connection Request", dialogButton, JOptionPane.INFORMATION_MESSAGE, icon);
        return dialogResult == 0;
    }

    public void displayMessage(String string) {
        panelContent.removeAll();
        JLabel label = new JLabel(string);
        label.setBounds(351, 266, 89, 23);
        panelContent.add(label);
        panelContent.repaint();
        panelContent.revalidate();
        revalidate();
        repaint();
    }

    public void newConnection() {
        displayMessage("New Connection from Device:" + client);

    }

    public void checkTrustedEquipement() {
        displayMessage("Check the identity of the client");
    }

    public void waitForConnection() {
        displayMessage("Connectio en cours");
    }

    public void codeDisplay(String code, String token) {
        panelContent.removeAll();
        JLabel codeLabel = new JLabel(code);
        codeLabel.setBounds(351, 266, 89, 23);
        panelContent.add(codeLabel);
        JLabel tokenLabel = new JLabel(token);
        tokenLabel.setBounds(351, 266, 89, 23);
        panelContent.add(tokenLabel);
        panelContent.revalidate();
        panelContent.repaint();
        revalidate();
        repaint();
    }

    public void accepted() {
        displayMessage("Connection Accepted");

    }

    public void refused() {
        displayMessage("Connection Refused");
    }

    public void codeAccepted() {
        displayMessage("Code Accepted");

    }

    public void securityMessage(String message) {
        panelContent.removeAll();
        JLabel codeLabel = new JLabel("Please make sure you see the code below on your equipement");
        codeLabel.setBounds(351, 266, 89, 23);
        panelContent.add(codeLabel);
        JLabel tokenLabel = new JLabel(message);
        tokenLabel.setBounds(351, 300, 89, 23);
        panelContent.add(tokenLabel);
        panelContent.revalidate();
        panelContent.repaint();
        revalidate();
        repaint();
    }

}
