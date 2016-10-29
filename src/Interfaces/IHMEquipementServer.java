package Interfaces;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import Component.Equipement;

public class IHMEquipementServer extends JFrame {
	private Equipement equipement;

	private JPanel contentPane;
	private JTextField txtInformations;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					IHMEquipementServer frame = new IHMEquipementServer();
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
	public IHMEquipementServer() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
contentPane.setLayout(null);
		
		txtInformations = new JTextField();
		txtInformations.setEditable(false);
		txtInformations.setText("Informations ");
		txtInformations.setBounds(10, 11, 195, 20);
		contentPane.add(txtInformations);
		txtInformations.setColumns(10);
		
		JButton btnConnexionClient = new JButton("Connexion Serveur");
		btnConnexionClient.setBounds(250, 68, 150, 34);
		contentPane.add(btnConnexionClient);
		//TODO : Ajouter l'ActionListener qui fait la connexion.
		
		JTextArea textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setBounds(10, 33, 195, 217);
		contentPane.add(textArea);
	}
	

}
