package Interfaces.IHMHome;

import Component.Equipement;
import HomKit.Home;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

/**
 * Project Name : TL_crypto
 */
public class IHMConnectionPanel extends JPanel {
    /*
    This JPanel Class is the one who handle the display of all the equipement and draw the connection between them.
    So we have to give it all the informations about the equipements
     */
    HashSet<IHMHomeEquipement> equipHomePanels = new HashSet<IHMHomeEquipement>();

    public IHMConnectionPanel() throws InterruptedException {
        super();
        setLayout(null);
        setSize(new Dimension(1000, 600));
        final JLabel label2 = new JLabel();
        label2.setBackground(new Color(-983553));
        label2.setIcon(new ImageIcon(getClass().getResource("/data/icons/logo.png")));
        label2.setText("");
        label2.setBounds(0, 0, 150, 150);
        add(label2);
        initiate();
    }

    public void initiate() {
        int width = (int) ((getWidth() / 5));
        int height = (int) ((getHeight() / 5) * 0.6);
        int x = 160;
        int y = 160;
        if (Home.newCo) {
            equipHomePanels = new HashSet<IHMHomeEquipement>();
            for (Equipement equipement : Home.equipements) {
                if (!equipement.isAlive() || equipement.isDaemon()) {
                    equipement.start();
                }
                if (x + width > getWidth()) {
                    y += height * 2;
                    x = (x + width) % getWidth();
                }
                if (x + width == getWidth()) {
                    y += height * 2;
                    x = ((int) (3 * width * Math.random())) % getWidth();
                }
                IHMHomeEquipement equi = new IHMHomeEquipement(equipement, x, y, width, height);
                this.equipHomePanels.add(equi);
                add(equi);
                x = x + (int) (1.5 * width);
            }
            Home.newCo = false;
        } else {
            for (IHMHomeEquipement equipement : equipHomePanels) {
                add(equipement);
            }
        }
    }

    public void update() {
        removeAll();
        final JLabel label2 = new JLabel();
        label2.setBackground(new Color(-983553));
        label2.setIcon(new ImageIcon(getClass().getResource("/data/icons/logo-big.png")));
        label2.setText("");
        label2.setBounds(0, 0, 150, 150);
        add(label2);
        initiate();
        revalidate();
        repaint();
    }

    public void paintComponent(Graphics g) {
        for (IHMHomeEquipement interEqu : equipHomePanels) {
            Iterator it = interEqu.getEquipement().getCA().entrySet().iterator();
            while (it.hasNext()) {
                HashMap.Entry pair = (HashMap.Entry) it.next();
                if ((int) pair.getKey() > interEqu.getEquipement().getPort()) {

                    IHMHomeEquipement interEqu2 = getComponentInterface((int) pair.getKey());
                    //Set the Cartisien position for the lines..
                    int[] input;
                    int[] output;
                    if (interEqu.getY() < interEqu2.getY()) {
                        input = interEqu.getOutputPos();
                        output = interEqu2.getInputPos();
                    } else if (interEqu.getY() > interEqu2.getY()) {
                        input = interEqu.getInputPos();
                        output = interEqu2.getOutputPos();
                    } else {
                        input = interEqu.getInputPos();
                        output = interEqu2.getInputPos();
                    }
                    //Draw the lines
                    Graphics2D g2 = (Graphics2D) g;
                    g2.setStroke(new BasicStroke(2));
                    g2.draw(new Line2D.Float(input[0], input[1], output[0], output[1]));
                    g.setColor(new Color(26, 193, 18));
                    if (!interEqu.getEquipement().isOn() || !interEqu2.getEquipement().isOn())
                        g.setColor(new Color(142, 9, 9));
                }
            }
        }
    }

    public IHMHomeEquipement getComponentInterface(int port) {
        IHMHomeEquipement aux = null;
        for (IHMHomeEquipement interEqui : equipHomePanels) {
            if (interEqui.isEqual(port))
                aux = interEqui;
        }
        return aux;
    }
}
