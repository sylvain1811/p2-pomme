
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
		System.out.println("A client disconnected!");
		}

	@Override
	public void connected(Connection connection)
		{
		if (this.serverProgram.addClient(connection))
			{
			System.out.println("A client connected succefully!");
			PacketMessage message = new PacketMessage("Welcome");
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
		System.out.println("Message received from client: " + paquet.getMessage());
		}

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	private ServerProgram serverProgram;
	}
