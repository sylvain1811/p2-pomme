
package reseau.usekryonet.listener;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import reseau.usekryonet.PacketMessage;

public abstract class CustomListener extends Listener
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	public CustomListener()
		{
		super();
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	@Override
	public void received(Connection connection, Object object)
		{
		if (object instanceof PacketMessage)
			{
			PacketMessage paquet = (PacketMessage)object;
			traiterPaquet(paquet);
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

	protected abstract void traiterPaquet(PacketMessage paquet);

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	}
