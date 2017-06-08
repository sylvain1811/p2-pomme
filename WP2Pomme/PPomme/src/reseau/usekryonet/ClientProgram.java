
package reseau.usekryonet;

import java.io.IOException;
import java.net.InetAddress;
import java.util.List;

import com.esotericsoftware.kryonet.Client;

import gui.jpanelingame.GameState;
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

	public ClientProgram(String pseudo, String addresseServer, int portTCP)
		{
		this.pseudo = pseudo;
		this.addresseServer = addresseServer;
		this.portTCP = portTCP;
		startKryoClient();
		}

	public ClientProgram(String pseudo)
		{
		this.pseudo = pseudo;
		startKryoClient();
		}

	public ClientProgram(String pseudo, String addresseServer)
		{
		this(pseudo, addresseServer, ClientProgram.PORT_TCP);
		}

	private void startKryoClient()
		{
		client = new Client();
		client.getKryo().register(PacketMessage.class);
		client.getKryo().register(cartes.Card[].class);
		client.getKryo().register(cartes.Card.class);
		client.getKryo().register(GameState.class);
		client.start();

		customListener = new CustomListenerClient(this);
		client.addListener(customListener);

		if (addresseServer == null)
			{
			this.listServerDispo = client.discoverHosts(ClientProgram.PORT_UDP, ClientProgram.MAX_WAITING_MS);
			//System.out.println(listServerDispo);
			}
		else
			{
			connectToServer(this.addresseServer);
			}
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	public void envoiPaquet(PacketMessage paquet)
		{
		client.sendTCP(paquet);
		}

	public List<InetAddress> getListServer()
		{
		return listServerDispo;
		}

	public void connectToServer(String addresse)
		{
		try
			{
			client.connect(ClientProgram.MAX_WAITING_MS, addresse, ClientProgram.PORT_TCP, ClientProgram.PORT_UDP);
			log("Connected to server at " + addresse);
			}
		catch (IOException e)
			{
			logErr("Unable to connect to " + addresse);
			e.printStackTrace();
			}
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

	public void log(String message)
		{
		System.out.println("[<client> " + this.pseudo + " say ]: " + message);
		}

	public void logErr(String message)
		{
		System.err.println("[<client> " + this.pseudo + " say ]: " + message);
		}

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	// Inputs
	private String addresseServer;

	public String getPseudo()
		{
		return this.pseudo;
		}

	private int portTCP;
	private String pseudo;

	// Tools
	private Client client;
	private CustomListener customListener;
	private List<InetAddress> listServerDispo;

	/*------------------------------*\
	|*			  Static			*|
	\*------------------------------*/

	private static final int PORT_TCP = 54778;
	private static final int PORT_UDP = 54777;
	private static final int MAX_WAITING_MS = 2500;

	}
