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
        System.out.println(getWidth());
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
            System.out.println(x);
        }
        repaint();

    }


    public void paintComponent(Graphics g) {
        for (IHMHomeEquipement interEqu : equipements) {

            Iterator it = interEqu.getEquipement().getCA().entrySet().iterator();
            //while (it.hasNext()) {
            for (IHMHomeEquipement interEqu2 : equipements) {
                //HashMap.Entry pair = (HashMap.Entry) it.next();
                //if ((int) pair.getKey() > interEqu.getEquipement().getPort()) {
                //if (interEqu2.getEquipement().getPort() != interEqu.getEquipement().getPort()) {
                //We draw the line
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
                Graphics2D g2 = (Graphics2D) g;
                g2.setStroke(new BasicStroke(2));
                g2.draw(new Line2D.Float(input[0], input[1], output[0], output[1]));
                g.setColor(new Color(26, 193, 18));
                if (!interEqu.getEquipement().isOn() || !interEqu.getEquipement().isOn())
                    g.setColor(new Color(142, 9, 9));

                //}
            }
        }
    }

    public IHMHomeEquipement getComponentInterface(IHMHomeEquipement interEqu) {
        for (IHMHomeEquipement interEqui : equipements) {
            if (interEqui.isEqual(interEqu.getEquipement().getPort()))
                return interEqui;
        }
        return interEqu;
    }
}
