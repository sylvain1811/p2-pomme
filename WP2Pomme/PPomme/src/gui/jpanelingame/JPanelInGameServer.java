
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
		state = GameState.DEMMARAGE;
		game = new Game();
		game.distribuer(); //Distribution des cartes
		envoiDistribution();
		state = GameState.ECHANGE;
		changerAffichageBouton();
		controlBtnFinTour();
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	public void echangerTroisCartes(Carte[] indexCartesARemplacer)
		{
		Game.echangerTroisCartes(carteServeur, indexCartesARemplacer);
		changerAffichageBouton();
		jPanelMyCard.remiseAffichageApresEchangeTroisCartes();
		state = GameState.TOURSERVEUR;
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
	private void controlBtnFinTour()
		{
		this.btnFinTour.addActionListener(new ActionListener()
			{

			@Override
			public void actionPerformed(ActionEvent e)
				{
				PacketMessage paquet = new PacketMessage(serverProgram.getPseudo(), PacketMessage.END_OF_TURN);
				serverProgram.envoiPaquet(paquet);
				state = GameState.TOURCLIENT;
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
		carteServeur = game.getTabCarteJoueur1();
		for(int i = 0; i < 9; i++)
			{
			jPanelMyCard.getTabMyCard()[i].setCarte(carteServeur[i]);
			//jPanelMyCard.getTabMyCard()[0].setText(String.valueOf(carteServeur[0].getNumber()));
			//System.out.println(JFrameHome.getInstance().getjPanelInGame().getjPanelMyCard().getTabMyCard()[1].getText());
			jPanelMyCard.getTabMyCard()[i].setText(String.valueOf(carteServeur[i].getNumber()));
			}
		}

	public void setMAJCarteClient(Carte[] tabCarte)
		{
		game.setTabCarteJoueur2(tabCarte);

		}

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	// Tools
	private ServerProgram serverProgram;
	private Game game;
	private Carte[] carteServeur;
	}
