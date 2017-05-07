
package reseau.usekryonet;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Server;

import gui.JFrameHome;
import reseau.usekryonet.listener.CustomListener;
import reseau.usekryonet.listener.CustomListenerServer;

/**
 * Implémentation d'un serveur, qui attends un client, puis reçois et envoie des message.
 * @author sylvain.renaud
 */
public class ServerProgram
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	public ServerProgram(int portTCP) throws IOException
		{

		this.portTCP = portTCP;
		listClient = new ArrayList<>();
		server = new Server();

		server.getKryo().register(PacketMessage.class);

		server.start();

		// Choix du port
		//server.bind(this.portTCP);

		// Port par défaut
		server.bind(this.portTCP, ServerProgram.PORT_UDP);

		customListener = new CustomListenerServer(this, JFrameHome.getInstance());
		server.addListener(customListener);

		System.out.println("Server ready");
		}

	public ServerProgram() throws IOException
		{
		this(ServerProgram.PORT_TCP);
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	public boolean addClient(Connection connection)
		{
		if (listClient.size() > 0)
			{
			connection.sendTCP(new PacketMessage(PacketMessage.ERROR_SERVER_FULL));
			connection.close();
			return false;
			}
		else
			{
			this.listClient.add(connection);
			System.out.println("New client connected!");
			System.out.println(this.listClient);
			return true;
			}
		}

	public void removeClient(Connection connection)
		{
		this.listClient.remove(connection);
		System.out.println(listClient);
		}

	public void envoiPaquet(PacketMessage paquet)
		{
		this.listClient.get(0).sendTCP(paquet);
		}

	/*------------------------------*\
	|*			  Static			*|
	\*------------------------------*/

	public static String getIp()
		{
		try
			{
			InetAddress IP;
			IP = InetAddress.getLocalHost();
			return IP.getHostAddress();
			}
		catch (UnknownHostException e1)
			{
			e1.printStackTrace();
			}
		return "";
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
	private int portTCP;

	// Tools
	private Server server;
	private CustomListener customListener;
	private List<Connection> listClient;

	/*------------------------------*\
	|*			  Static			*|
	\*------------------------------*/

	public static final int PORT_TCP = 54778;
	public static final int PORT_UDP = 54777;
	private static final int MAX_WAITING_MS = 5000;

	}
