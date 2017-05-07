
package gui.jpanelingame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import reseau.usekryonet.PacketMessage;
import reseau.usekryonet.ServerProgram;

public class JPanelInGameServer extends JPanelInGame
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	public JPanelInGameServer(ServerProgram serverProgram)
		{
		super();
		this.serverProgram = serverProgram;
		controlBtnFinTour();
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	/*------------------------------*\
	|*				Set				*|
	\*------------------------------*/

	/*------------------------------*\
	|*				Get				*|
	\*------------------------------*/

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/
	private void controlBtnFinTour()
		{
		this.btnFinTour.addActionListener(new ActionListener()
			{

			@Override
			public void actionPerformed(ActionEvent e)
				{
				PacketMessage paquet = new PacketMessage(PacketMessage.END_OF_TURN);
				serverProgram.envoiPaquet(paquet);
				}
			});
		}

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	// Tools
	private ServerProgram serverProgram;

	}
