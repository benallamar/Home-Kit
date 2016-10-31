package Interfaces.IHMHome;

import Component.Equipement;

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

    public IHMConnectionPanel(HashMap<Integer, Equipement> equipements) throws InterruptedException {
        super();
        setSize(new Dimension(1000, 600));
        int width = (int) ((getWidth() / 5) * 0.75);
        int height = (int) ((getHeight() / 5) * 0.30);
        int x = 100;
        int y = 100;
        for (Equipement equipement : equipements.values()) {
            equipement.start();
            IHMHomeEquipement equi = new IHMHomeEquipement(equipement, equipements, x, y, width, height);
            this.equipements.add(equi);
            add(equi);
            x = x + 2 * width;
            if (x > getWidth()) {
                y += 4 * height;
                x = x % getWidth();
            }
            if (x == getWidth()) {
                y += 4 * height;
                x = (3 * width) % getWidth();
            }
        }
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
