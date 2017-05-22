
package gui.jpanelingame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import cartes.Carte;
import cartes.Game;
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
		game = new Game();
		game.distribuer(); //Distribution des cartes
		envoiDistribution();
		changerAffichageBouton();
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
				PacketMessage paquet = new PacketMessage(serverProgram.getPseudo(), PacketMessage.END_OF_TURN);
				serverProgram.envoiPaquet(paquet);
				}
			});
		}

	private void envoiDistribution()
		{
		// TODO Auto-generated method stub
		PacketMessage paquet = new PacketMessage(serverProgram.getPseudo(), PacketMessage.START_GAME_DISTRIBUTION, game.getTabCarteJoueur2());
		serverProgram.envoiPaquet(paquet);
		}

	private void changerAffichageBouton()
		{
		// TODO Auto-generated method stub
		for(int i = 0; i < 9; i++)
			{
			carteServeur = game.getTabCarteJoueur1();
			//jPanelMyCard.getTabMyCard()[0].setText(String.valueOf(carteServeur[0].getNumber()));
			//jPanelMyCard = JFrameHome.getInstance().getjPanelInGame().getjPanelMyCard();
			//Erreur ici !!
			//jPanelMyCard.getTabMyCard()[i].setText(String.valueOf(game.getTabCarteJoueur1()[i].getNumber()));
			}
		}

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	// Tools
	private ServerProgram serverProgram;
	private Game game;
	private Carte[] carteServeur;
	private JPanelMyCard jPanelMyCard;
	}
