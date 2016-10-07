package Component;

import Security.Certificat;
import Security.PaireClesRSA;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.PublicKey;

import org.omg.CORBA_2_3.portable.InputStream;
import org.omg.CORBA_2_3.portable.OutputStream;

/**
 * Project Name : TL_crypto
 */
public class Equipement {
	private PaireClesRSA maCle;
	private Certificat monCert;
	private String monNom;
	private int monPort;

	Equipement(String name, int port) {
		// Define the component
		monNom = name;
		monPort = port;
	}

	public void affichage_da() {

	}

	public void affichage_ca() {

	}

	public void affichage() {

	}

	public String monNom() {
		return monNom;
	}

	public PublicKey maClePub() {
		// Return the publicKe
		return null;
	}

	public Certificat maCertif() {
		return monCert;
	}

	public void socketServeur(){
		ServerSocket serverSocket = null;
		Socket NewServerSocket = null;
		InputStream NativeIn = null;
		ObjectInputStream ois = null;
		OutputStream NativeOut = null;
		ObjectOutputStream oos = null;
		//Creation de socket (TCP);
		try{
			serverSocket = new ServerSocket(this.monPort);
		} catch(Exception e) {
			//Gestion des exceptions
		}
		//Creation des flux natifs et evolues
		try{
			NativeIn = NewServerSocket.getInputStream();
			ois = new ObjectInputStream(NativeIn);
			NativeOut = NewServerSocket.getOutputStream();
			oos = new ObjectOutputStream(NativeOut);
		} catch(IOException e){
			//Gestion des exceptions
		}
		//Reception d'un string
		try{
			String res = (String) ois.readObject();
			System.out.println(res);
		}catch(Exception e){
			//Gestion des exceptions
		}
		//Emission d'un String
		try{
			oos.writeObject("Au revoir");
			oos.flush();
		}catch(Exception e){
			//Gestion des exceptions
		}
		//Fermeture des flux evolues et natifs
		try{
			ois.close();
			oos.close();
			NativeIn.close();
			NativeOut.close();
		} catch(IOException e){
			//Gestion des exceptions
		}
		//Fermeture de la connexion
		try{
			NewServerSocket.close();
		}catch(IOException e){
			//Gestion des exceptions
		}
		//Arret du serveur
		try{
			serverSocket.close();
		}catch(IOException e){
			//Gestion des exceptions
		}
	}

	public void socketClient(){
		int ServerPort;
		String ServerName;
		Socket clientSocket = null;
		InputStream NativeIn = null;
		ObjectInputStream ois = null;
		OutputStream NativeOut = null;
		ObjectOutputStream oos = null;

		//Creation de socket(TCP)
		try{
			clientSocket = new Socket(ServerName,ServerPort);
		}catch(Exception e){
			//Gestion des exceptions
		}
		//Creation des flux natifs et evolues
		try{
			NativeOut = clientSocket.getOutputStream();
			oos = new ObjectOutputStream(NativeOut);
			NativeIn = clientSocket.getInputStream();
			ois = new ObjectInputStream(NativeIn);
		}catch(Exception e){
			//Gestion des exceptions
		}
		//Emission d'un string
		try{
			oos.writeObject("Bonjour");
			oos.flush();
		}catch(Exception e){
			//Gestion des exceptions
		}
		//Reception d'un String
		try{
			String res = (String) ois.readObject();
			System.out.println(res);
		} catch(Exception e){
			//Gestion des exceptions
		}
		//Fermeture des flux evolues et natifs
		try{
			ois.close();
			oos.close();
			NativeIn.close();
			NativeOut.close();
		}catch(IOException e){
			//Gestion des exceptions
		}
		//Fermeture de la connexion
		try{
			clientSocket.close();
		}catch(IOException e){
			//Gestion des exceptions
		}
	}
}
