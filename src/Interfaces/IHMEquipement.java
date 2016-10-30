package Interfaces;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Component.Equipement;

import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTextArea;

public class IHMEquipement extends JFrame {
    private Equipement equipement;

    private JPanel contentPane;
    private JTextField txtInformations;
    private JTextField txtPort;
    private JTextField textField;
    private JTextField txtReseau;
    private JTextField textField_2;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    IHMEquipement frame = new IHMEquipement();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public IHMEquipement() {
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        txtInformations = new JTextField();
        txtInformations.setEditable(false);
        txtInformations.setText("Informations ");
        txtInformations.setBounds(10, 11, 195, 20);
        contentPane.add(txtInformations);
        txtInformations.setColumns(10);

        JButton btnConnexionClient = new JButton("Connexion client");
        btnConnexionClient.setBounds(250, 68, 150, 34);
        contentPane.add(btnConnexionClient);

        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setBounds(10, 33, 195, 217);
        contentPane.add(textArea);

        txtPort = new JTextField();
        txtPort.setEditable(false);
        txtPort.setText("Port");
        txtPort.setBounds(338, 152, 86, 20);
        contentPane.add(txtPort);
        txtPort.setColumns(10);

        textField = new JTextField();
        textField.setBounds(338, 179, 86, 20);
        contentPane.add(textField);
        textField.setColumns(10);

        txtReseau = new JTextField();
        txtReseau.setEditable(false);
        txtReseau.setText("Cible");
        txtReseau.setBounds(231, 152, 86, 20);
        contentPane.add(txtReseau);
        txtReseau.setColumns(10);

        textField_2 = new JTextField();
        textField_2.setBounds(231, 179, 86, 20);
        contentPane.add(textField_2);
        textField_2.setColumns(10);
    }


    //Cette m�thode permet de passer d'une interface � une autre via les ActionListener des diff�rentes IHM
    public void switchScreen(int id) {

        switch (id) {
            case 0:
                this.setContentPane(this.contentPane);
                this.setVisible(true);
                this.requestFocus();
                break;
            case 1:
                //this.setContentPane(vueConnexion);
                this.setVisible(true);
                this.requestFocus();
                break;
            case 2:
               // this.setContentPane(vueConnexionReussie);
                this.setVisible(true);
                this.requestFocus();
                break;


            default:

                break;
        }

    }


}
