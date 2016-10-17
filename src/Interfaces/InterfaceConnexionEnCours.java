package Interfaces;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.SwingConstants;

public class InterfaceConnexionEnCours extends JFrame {

	private JPanel contentPane;
	private JTextField txtConnexionEnCours;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InterfaceConnexionEnCours frame = new InterfaceConnexionEnCours();
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
	public InterfaceConnexionEnCours() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		txtConnexionEnCours = new JTextField();
		txtConnexionEnCours.setHorizontalAlignment(SwingConstants.CENTER);
		txtConnexionEnCours.setEditable(false);
		txtConnexionEnCours.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 13));
		txtConnexionEnCours.setText("Connexion en cours");
		txtConnexionEnCours.setBounds(50, 54, 324, 111);
		contentPane.add(txtConnexionEnCours);
		txtConnexionEnCours.setColumns(10);
	}

}
