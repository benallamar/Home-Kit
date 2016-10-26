package Interfaces;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JList;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;

public class InterfaceListe extends JFrame{
	
	private String[] ListEquipements;
	private InterfaceConnexion interfaceConnexion;
	private InterfacePassword interfacePassword;
	private InterfaceConnexionEnCours interfaceCeC;
	private JFrame frame;
	private JTextField txtMaison;

	/**
	 * Launch the application.
	 */
	

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InterfaceListe window = new InterfaceListe();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	


	/**
	 * Create the application.
	 */
	public InterfaceListe() {
		this.ListEquipements = new String[50];
		this.interfaceConnexion = new InterfaceConnexion();
		this.interfacePassword = new InterfacePassword();
		this.interfaceCeC = new InterfaceConnexionEnCours();
		initialize();
		this.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setToolTipText("Maison");
		panel.setBounds(120, 11, 200, 43);
		frame.getContentPane().add(panel);
		
		txtMaison = new JTextField();
		txtMaison.setText("Maison");
		txtMaison.setEditable(false);
		panel.add(txtMaison);
		txtMaison.setColumns(10);
		
		JList list = new JList(ListEquipements);
		list.setBounds(30, 80, 273, 138);
		frame.getContentPane().add(list);
		
		JButton btnConnexion = new JButton("Connexion");
		btnConnexion.setBounds(120, 227, 89, 23);
		btnConnexion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				InterfaceConnexion interfCo = new InterfaceConnexion();
				interfCo.setVisible(true);
			}
		});
		frame.getContentPane().add(btnConnexion);
		
		JButton btnInformations = new JButton("Informations");
		btnInformations.setBounds(313, 123, 101, 23);
		frame.getContentPane().add(btnInformations);
	}
}
