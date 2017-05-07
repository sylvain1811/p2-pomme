
package reseau.usekryonet.listener;

import javax.swing.JOptionPane;

import cartes.Carte;
import gui.JFrameHome;
import reseau.usekryonet.PacketMessage;

public class CustomListenerClient extends CustomListener
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	public CustomListenerClient(JFrameHome frame)
		{
		super(frame);
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

	@Override
	protected void traiterPaquet(PacketMessage paquet)
		{
		if (paquet.getCode() < PacketMessage.ERROR_SERVER_FULL)
			{
			System.out.println("Message received from server: " + paquet.getMessage());
			switch(paquet.getCode())
				{
				case PacketMessage.START_GAME_DISTRIBUTION:
					traiterCasDistribution(paquet.getTabCarte());
					break;

				default:
					break;
				}
			}
		else
			{
			switch(paquet.getCode())
				{
				case PacketMessage.ERROR_SERVER_FULL:
					System.out.println("Serveur plein, impossible de se connecter");
					JOptionPane.showMessageDialog(null, "Serveur plein, impossible de se connecter.", "Serveur plein", JOptionPane.ERROR_MESSAGE);
					break;

				default:
					break;
				}
			}
		}

	private void traiterCasDistribution(Carte[] tabCartes)
		{
		// TODO
		}

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/
	}
