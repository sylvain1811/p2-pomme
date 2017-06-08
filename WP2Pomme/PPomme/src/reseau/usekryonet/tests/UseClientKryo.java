
package reseau.usekryonet.tests;

import reseau.usekryonet.ClientProgram;
import reseau.usekryonet.PacketMessage;

public class UseClientKryo
	{

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	public static void main(String[] args)
		{
		main();
		}

	public static void main()
		{
		ClientProgram client = new ClientProgram("user", "localhost", 54444);

		// PacketMessage paquet = new PacketMessage();
		PacketMessage paquet = new PacketMessage("pseudo", "Hello");
		client.sendPackage(paquet);
		paquet.setMessage("Comment va ?");
		client.sendPackage(paquet);

		new JFrameKryo(client);
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	}
