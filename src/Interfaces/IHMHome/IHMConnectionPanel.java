package Interfaces.IHMHome;

import Component.Equipement;
import HomKit.Home;

import javax.swing.*;
import javax.swing.text.html.HTMLDocument;
import java.awt.*;
import java.awt.geom.Line2D;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

/**
 * Project Name : TL_crypto
 */
public class IHMConnectionPanel extends JPanel {
    HashSet<IHMHomeEquipement> equipements = new HashSet<IHMHomeEquipement>();
    HashSet<Equipement> equis = new HashSet<Equipement>();

    public IHMConnectionPanel(HashSet<Equipement> equipements) throws InterruptedException {
        super();
        equis = equipements;
        setLayout(null);
        setSize(new Dimension(1000, 600));
        final JLabel label2 = new JLabel();
        label2.setBackground(new Color(-983553));
        label2.setIcon(new ImageIcon(getClass().getResource("/icons/logo.png")));
        label2.setText("");
        label2.setBounds(0, 0, 150, 150);
        add(label2);
        initiate();
    }

    public void initiate() {
        int width = (int) ((getWidth() / 5));
        int height = (int) ((getHeight() / 5) * 0.6);
        int x = 160;
        int y = 100;
        int i = 0;
        int size = equis.size();
        for (Equipement equipement : equis) {
            if (!equipement.isAlive() || equipement.isDaemon()) {
                equipement.start();
            }
            if (x + width > getWidth()) {
                y += height * (1 + Math.random());
                x = (x + width) % getWidth();
            }
            if (x + width == getWidth()) {
                y += height * (1 + Math.random());
                x = ((int) (3 * width * Math.random())) % getWidth();
            }
            IHMHomeEquipement equi = new IHMHomeEquipement(equipement, equis, x, y, width, height);
            this.equipements.add(equi);
            add(equi);
            x = x + (int) (1.5 * width);
            Home.loadPage.setValue(20 + i * 100 / size);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                System.out.print(e.fillInStackTrace());
            }
            i++;
        }
        Home.loadPage.setValue(100);
        revalidate();
        repaint();
    }

    public void update() {
        removeAll();
        final JLabel label2 = new JLabel();
        label2.setBackground(new Color(-983553));
        label2.setIcon(new ImageIcon(getClass().getResource("/icons/logo-big.png")));
        label2.setText("");
        label2.setBounds(0, 0, 150, 150);
        add(label2);
        int width = (int) ((getWidth() / 5));
        int height = (int) ((getHeight() / 5) * 0.6);
        int x = 160;
        int y = 100;
        int i = 0;
        int size = equis.size();
        if (Home.newCo) {
            equipements = new HashSet<IHMHomeEquipement>();
            for (Equipement equipement : equis) {
                if (!equipement.isAlive() || equipement.isDaemon()) {
                    equipement.start();
                }
                if (x + width > getWidth()) {
                    y += height * (1 + Math.random());
                    x = (x + width) % getWidth();
                }
                if (x + width == getWidth()) {
                    y += height * (1 + Math.random());
                    x = ((int) (3 * width * Math.random())) % getWidth();
                }
                IHMHomeEquipement equi = new IHMHomeEquipement(equipement, equis, x, y, width, height);
                this.equipements.add(equi);
                add(equi);
                x = x + (int) (1.5 * width);
                i++;
            }
            Home.newCo = false;
        } else {
            for (IHMHomeEquipement equipement : equipements) {
                add(equipement);
            }
        }
        revalidate();
        repaint();
    }

    public void paintComponent(Graphics g) {
        for (IHMHomeEquipement interEqu : equipements) {
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
        for (IHMHomeEquipement interEqui : equipements) {
            if (interEqui.isEqual(port))
                aux = interEqui;
        }
        return aux;
    }
}
