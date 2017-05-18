
package reseau.usekryonet.listener;

import com.esotericsoftware.kryonet.Connection;

import gui.JFrameHome;
import reseau.usekryonet.PacketMessage;
import reseau.usekryonet.ServerProgram;

public class CustomListenerServer extends CustomListener
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	public CustomListenerServer(ServerProgram serverProgram)
		{
		super();
		this.serverProgram = serverProgram;
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	@Override
	public void disconnected(Connection connection)
		{
		this.serverProgram.removeClient(connection);
		serverProgram.log("A client disconnected!");
		// TODO: gérer l'arrêt de la partie, retour au menu
		}

	@Override
	public void connected(Connection connection)
		{
		if (this.serverProgram.addClient(connection))
			{
			serverProgram.log("Client connected successfully");
			PacketMessage message = new PacketMessage(serverProgram.getPseudo(), "Welcome");
			connection.sendTCP(message);
			JFrameHome.getInstance().commencerPartie();
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

	@Override
	protected void traiterPaquet(PacketMessage paquet)
		{
		serverProgram.log("Message received from " + paquet.getPseudoFrom() + " : " + paquet.getMessage());
		}

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	private ServerProgram serverProgram;
	}
