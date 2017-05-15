
package reseau.usekryonet;

import java.io.IOException;
import java.net.InetAddress;
import java.util.List;

import com.esotericsoftware.kryonet.Client;

import reseau.usekryonet.listener.CustomListener;
import reseau.usekryonet.listener.CustomListenerClient;

/**
 * Impl�mentation d'un client. Il se connecte � un serveur pour envoyer et recevoir des messages.
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

		customListener = new CustomListenerClient();
		client.addListener(customListener);

		if (addresseServer == null)
			{
			this.listServerDispo = client.discoverHosts(ClientProgram.PORT_UDP, ClientProgram.MAX_WAITING_MS);
			//System.out.println(listServerDispo);
			}
		else
			{
			try
				{
				// Choix du port
				//client.connect(ClientProgram.MAX_WAITING_MS, ClientProgram.this.addresseServer, ClientProgram.this.portTCP);

				// Port par d�faut
				client.connect(ClientProgram.MAX_WAITING_MS, ClientProgram.this.addresseServer, ClientProgram.PORT_TCP, ClientProgram.PORT_UDP);
				//System.out.println("Port par d�faut client. Addr srv : " + addresseServer);
				}
			catch (IOException e)
				{
				System.err.println("Impossible de d�marrer le client (client.connect())");
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

	public List<InetAddress> getListServer()
		{
		return listServerDispo;
		}

	public void connectToServer(String addresse)
		{
		try
			{
			client.connect(ClientProgram.MAX_WAITING_MS, addresse, ClientProgram.PORT_TCP, ClientProgram.PORT_UDP);
			}
		catch (IOException e)
			{
			System.err.println("Unable to connect to " + addresse);
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
	private static final int MAX_WAITING_MS = 2500;

	}
