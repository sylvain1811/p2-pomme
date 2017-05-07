
package reseau.usekryonet;

import java.io.IOException;
import java.net.InetAddress;
import java.util.List;

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

	public ClientProgram()
		{
		startKryoClient();
		}

	public ClientProgram(String addresseServer)
		{
		this(addresseServer, ClientProgram.PORT_TCP);
		}

	private void startKryoClient()
		{
		client = new Client();
		client.getKryo().register(PacketMessage.class);

		client.start();

		customListener = new CustomListenerClient(JFrameHome.getInstance());
		client.addListener(customListener);

		//DEBUG
		/*
			{
			try
				{
				InetAddress IP;
				IP = InetAddress.getLocalHost();
				System.out.println("IP of my system is := " + IP.getHostAddress());
				}
			catch (UnknownHostException e1)
				{
				e1.printStackTrace();
				}
			}*/

		if (addresseServer == null)
			{
			this.listServerDispo = client.discoverHosts(ClientProgram.PORT_UDP, ClientProgram.MAX_WAITING_MS);
			System.out.println(listServerDispo);

			}
		else
			{
			try
				{
				// Choix du port
				//client.connect(ClientProgram.MAX_WAITING_MS, ClientProgram.this.addresseServer, ClientProgram.this.portTCP);

				// Port par défaut
				client.connect(ClientProgram.MAX_WAITING_MS, ClientProgram.this.addresseServer, ClientProgram.PORT_TCP, ClientProgram.PORT_UDP);
				//System.out.println("Port par défaut client. Addr srv : " + addresseServer);
				}
			catch (IOException e)
				{
				System.err.println("Impossible de démarrer le client (client.connect())");
				e.printStackTrace();
				}
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
	private List<InetAddress> listServerDispo;

	/*------------------------------*\
	|*			  Static			*|
	\*------------------------------*/

	private static final int PORT_TCP = 54778;
	private static final int PORT_UDP = 54777;
	private static final int MAX_WAITING_MS = 5000;
	}
