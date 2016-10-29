package Interfaces;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JButton;

public class IHMConnexionReussie extends JPanel {
	private JTextField txtConnexionReussie;

	/**
	 * Create the panel.
	 */
	public IHMConnexionReussie() {
		setLayout(null);
		
		txtConnexionReussie = new JTextField();
		txtConnexionReussie.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtConnexionReussie.setHorizontalAlignment(SwingConstants.CENTER);
		txtConnexionReussie.setText("Connexion reussie");
		txtConnexionReussie.setBounds(10, 95, 430, 87);
		add(txtConnexionReussie);
		txtConnexionReussie.setColumns(10);
		
		JButton btnOk = new JButton("OK");
		btnOk.setBounds(334, 266, 89, 23);
		add(btnOk);
		//TODO : Ajouter l'actionListener pour rebasculer sur IHMEquipement

	}

}
