
package reseau.usekryonet;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Server;

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

	public ServerProgram(String pseudo, int portTCP) throws IOException
		{
		this.pseudo = pseudo;
		this.portTCP = portTCP;
		listClient = new ArrayList<>();
		server = new Server();

		server.getKryo().register(PacketMessage.class);

		server.start();

		// Choix du port
		//server.bind(this.portTCP);

		// Port par défaut
		server.bind(this.portTCP, ServerProgram.PORT_UDP);

		customListener = new CustomListenerServer(this);
		server.addListener(customListener);

		log("Server ready");
		}

	public ServerProgram(String pseudo) throws IOException
		{
		this(pseudo, ServerProgram.PORT_TCP);
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	public boolean addClient(Connection connection)
		{
		if (listClient.size() > 0)
			{
			connection.sendTCP(new PacketMessage(this.pseudo, PacketMessage.ERROR_SERVER_FULL));
			connection.close();
			return false;
			}
		else
			{
			this.listClient.add(connection);
			log("New client connected!");
			//System.out.println(this.listClient);
			return true;
			}
		}

	public void removeClient(Connection connection)
		{
		this.listClient.remove(connection);
		//System.out.println(listClient);
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

	public String getPseudo()
		{
		return this.pseudo;
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	public void log(String message)
		{
		System.out.println("[<server> " + this.pseudo + " say ]: " + message);
		}

	public void logErr(String message)
		{
		System.err.println("[<server> " + this.pseudo + " say ]: " + message);
		}

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	// Inputs
	private int portTCP;

	private String pseudo;

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
