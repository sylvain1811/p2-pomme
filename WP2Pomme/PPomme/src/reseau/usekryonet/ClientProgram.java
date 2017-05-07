
package reseau.usekryonet;

import java.io.IOException;

import com.esotericsoftware.kryonet.Client;

import gui.JFrameHome;
import reseau.usekryonet.listener.CustomListener;
import reseau.usekryonet.listener.CustomListenerClient;

/**
 * Implémentation d'un client. Il se connecte à un serveur pour envoyer et recevoir des messages.
 * @author sylvain.renaud
 */
public class ClientProgram
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	public ClientProgram(String addresseServer, int portTCP)
		{
		this.addresseServer = addresseServer;
		this.portTCP = portTCP;

		startKryoClient();
		}

	private void startKryoClient()
		{
		client = new Client();
		client.getKryo().register(PacketMessage.class);

		client.start();

		customListener = new CustomListenerClient(JFrameHome.getInstance()); // TODO Ajouter jframe
		client.addListener(customListener);

		try
			{
			client.connect(4000, ClientProgram.this.addresseServer, ClientProgram.this.portTCP);
			}
		catch (IOException e)
			{
			System.err.println("Impossible de démarrer le client (client.connect())");
			e.printStackTrace();
			}

		}

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	public void envoiPaquet(PacketMessage paquet)
		{
		client.sendTCP(paquet);
		}

	/*------------------------------*\
	|*				Set				*|
	\*------------------------------*/

	/*------------------------------*\
	|*				Get				*|
	\*------------------------------*/

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	// Inputs
	private String addresseServer;
	private int portTCP;

	// Tools
	private Client client;
	private CustomListener customListener;

	}
