package Interfaces;

import Component.Equipement;
import Connection.Client;
import Connection.SocketHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.Socket;
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

    public void authenticate(SocketHandler s, Client client) throws InterruptedException, IOException {
        panelContent.removeAll();
        JLabel login = new JLabel("Login");
        JTextField login1 = new JTextField();
        JLabel mdp = new JLabel("Mot de Passe");
        JPasswordField mdp1 = new JPasswordField();
        JButton valider = new JButton("Valider ");
        JButton annuler = new JButton(" Annuler");
        String[] auth = {"", ""};
        IHMConnexion localFrame = this;
        valider.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {


            }

            @Override
            public void mousePressed(MouseEvent e) {
                try {
                    auth[0] = String.valueOf(login1.getText());
                    auth[1] = String.valueOf(mdp1.getPassword());
                    client.sendCode(s, auth);
                } catch (ClassNotFoundException error) {

                } catch (IOException error) {

                }
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
        annuler.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                try {
                    localFrame.dispose();
                    client.close(s);
                } catch (IOException error) {

                }
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
        panelContent.setLayout(null);

        panelContent.add(login);
        login.setBounds(20, 20, 100, 20);

        panelContent.add(login1);
        login1.setBounds(150, 20, 150, 20);

        panelContent.add(mdp);
        mdp.setBounds(22, 55, 100, 20);

        panelContent.add(mdp1);
        mdp1.setBounds(150, 55, 150, 20);

        panelContent.add(valider);
        valider.setBounds(125, 100, 77, 20);

        panelContent.add(annuler);
        annuler.setBounds(225, 100, 82, 20);
        revalidate();
        repaint();
    }

    public boolean doYouAccept(String equiName) {
        int dialogButton = JOptionPane.YES_NO_OPTION;
        int dialogResult = JOptionPane.showConfirmDialog(null, "The device" + equiName + " is not connected \n do you want to accept the connection with it ?", "Title on Box", dialogButton);
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

}
