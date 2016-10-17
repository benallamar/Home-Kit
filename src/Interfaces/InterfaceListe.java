package Interfaces;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JList;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JLabel;

public class InterfaceListe {

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
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JList listeEquipement = new JList();
		listeEquipement.setBounds(10, 65, 414, 163);
		frame.getContentPane().add(listeEquipement);
		
		JPanel panel = new JPanel();
		panel.setToolTipText("Maison");
		panel.setBounds(120, 11, 200, 43);
		frame.getContentPane().add(panel);
		
		txtMaison = new JTextField();
		txtMaison.setText("Maison");
		panel.add(txtMaison);
		txtMaison.setColumns(10);
	}
}
