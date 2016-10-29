package Interfaces;

import Component.Equipement;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.util.Iterator;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;

public class IHMHome extends JFrame {
	private Equipement[] equipements;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					IHMHome frame = new IHMHome();
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
	public IHMHome() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1200, 750);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JButton btnHome = new JButton("Home");
		btnHome.setBounds(350, 25, 100, 25);
		contentPane.add(btnHome);

	}

	public void placeButtons(JPanel newContentPane, Equipement equipement,int xinit,int yinit){
		int x = 100;
		int y = 50;
		int n =1;
		while (n!=0){
			n=equipement.getChilds().size();
			Iterator<Equipement> iter = equipement.getChilds().iterator();
			Equipement[] equip = new Equipement[n];
			for (int i=0;i<n-1;i++){		//On r�cup�re la liste des enfants contenus dans le HashSet childs, qui lui n'�tait pas ordonn�.
				equip[i] = iter.next();
			}
			for (int i=0;i<n-1;i++){
				int xres; int yres;
				yres = yinit + y;
				xres = xinit -x +(i*200*n);
				JButton bouton = new JButton(equip[i].getName());
				bouton.setBounds(xres,yres,100,25);
				newContentPane.add(bouton);
				placeButtons(newContentPane, equip[i], xres, yres); //On applique r�cursivement (?) aux enfants
			}
		}
	}

	public void construct(Equipement equipement){
		JPanel newContentPane = new JPanel();
		int xhome = 350;
		int yhome = 25;
		placeButtons(newContentPane,equipement,xhome,yhome); //On initialise la r�cursion � l'�quipement "central"
		this.setContentPane(newContentPane);
		this.setVisible(true);
		this.requestFocus();
	}
}

