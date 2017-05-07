
package reseau.usekryonet;

import java.io.IOException;
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

		server.bind(this.portTCP);

		customListener = new CustomListenerServer(this, JFrameHome.getInstance()); // TODO Ajouter jframe
		server.addListener(customListener);

		System.out.println("Server ready");
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
	}
