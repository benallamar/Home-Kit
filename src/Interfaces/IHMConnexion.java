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

public class IHMConnexion extends JFrame {
    private JPanel panelContent = new JPanel();
    private String host, client;

    /**
     * Create the panel.
     */
    public IHMConnexion(String host, String client) {
        this.host = host;
        this.client = client;
        setBounds(50, 50, 200, 200);
        setContentPane(panelContent);
        setTitle(host);
        setSize(new Dimension(400, 200));
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);

    }

    public boolean authenticate(SocketHandler s, Client client) throws InterruptedException, IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        String message = IOOperation.genKeyMessage(s.getPubKey(), (String) s.getKey("token"), (String) s.getKey("code"));
        int dialogButton = JOptionPane.YES_NO_OPTION;
        int dialogResult = JOptionPane.showConfirmDialog(this, "Do you see this: \n " + message + "\n on your system panel ?", " Avoid Man In The Middle Attack", dialogButton);
        return dialogResult == 0;
    }

    public boolean doYouAccept(String equiName) {
        int dialogButton = JOptionPane.YES_NO_OPTION;
        int dialogResult = JOptionPane.showConfirmDialog(this, "The device" + equiName + " is not connected \n do you want to accept the connection with it ?", "Title on Box", dialogButton);
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
        System.out.print(this.getContentPane().getComponent(1).toString());
        displayMessage("Connection Accepted");

    }

    public void refused() {
        displayMessage("Connection Refused");
    }

    public void codeAccepted() {
        displayMessage("Code Accepted");

    }

    public void securityMessage(String message) {
        displayMessage("Please make sure that your equipement \n display the following message: \n " + message);
    }

}
