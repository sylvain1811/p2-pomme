
package gui.jpanelingame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import cartes.Carte;
import gui.JFrameHome;
import reseau.usekryonet.ClientProgram;
import reseau.usekryonet.PacketMessage;

public class JPanelInGameClient extends JPanelInGame
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	public JPanelInGameClient(ClientProgram clientProgram)
		{
		super();
		this.clientProgram = clientProgram;
		controlBtnFinTour();
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	/*------------------------------*\
	|*				Set				*|
	\*------------------------------*/

	public void setCarteJoueurClient(Carte[] carteJoueurClient)
		{
		this.CarteJoueurClient = carteJoueurClient;
		changerAffichageBouton();
		}

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
				PacketMessage paquet = new PacketMessage(clientProgram.getPseudo(), PacketMessage.END_OF_TURN);
				clientProgram.envoiPaquet(paquet);
				}
			});
		}

	private void changerAffichageBouton()
		{
		// TODO Auto-generated method stub
		for(int i = 0; i < 9; i++)
			{
			jPanelMyCard = JFrameHome.getInstance().getjPanelInGame().getjPanelMyCard();
			jPanelMyCard.getTabMyCard()[i].setText(String.valueOf(CarteJoueurClient[i].getNumber()));
			}
		}
	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	// Tools
	private ClientProgram clientProgram;
	private Carte[] CarteJoueurClient;
	private JPanelMyCard jPanelMyCard;
	}
