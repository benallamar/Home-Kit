package Interfaces;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JButton;

public class IHMConnexion extends JPanel {
	private JTextField textField;

	/**
	 * Create the panel.
	 */
	public IHMConnexion() {
		setLayout(null);
		
		textField = new JTextField();
		textField.setEditable(false);
		textField.setBounds(10, 97, 430, 74);
		add(textField);
		textField.setColumns(10);
		//TODO : Ce JTextField doit contenir "Connexion en cours à 'equipementmachin'", créer méthode pour récupérer 
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.setBounds(351, 266, 89, 23);
		add(btnCancel);
		//TODO : ActionPerformed sur ce bouton doit annuler la connexion en cours et renvoyer sur IHMEquipement

	}

}
