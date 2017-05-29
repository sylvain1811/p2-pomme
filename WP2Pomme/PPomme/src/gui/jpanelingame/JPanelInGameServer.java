
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
		sendStateClient();
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
		sendStateClient();
		tourServeurOuTourJoueur();
		}

	public void tourServeurOuTourJoueur()
		{
		if (state == GameState.TOURCLIENT)
			{
			for(int i = 0; i < jPanelMyCard.getTabMyCard().length; i++)
				{
				jPanelMyCard.getTabMyCard()[i].setEnabled(false);
				btnFinTour.setEnabled(false);
				}
			}
		else
			{
			for(int i = 0; i < jPanelMyCard.getTabMyCard().length; i++)
				{
				jPanelMyCard.getTabMyCard()[i].setEnabled(true);
				btnFinTour.setEnabled(true);
				}
			}
		}

	public void sendStateClient()
		{
		PacketMessage paquet = new PacketMessage(serverProgram.getPseudo(), PacketMessage.SEND_STATE_SERVER_TO_CLIENT, state);
		serverProgram.envoiPaquet(paquet);
		}

	public void sendStateClientChangementTour()
		{
		PacketMessage paquet = new PacketMessage(serverProgram.getPseudo(), PacketMessage.SEND_STATE_SERVER_TO_CLIENT, state);
		serverProgram.envoiPaquet(paquet);
		tourServeurOuTourJoueur();
		}

	/*------------------------------*\
	|*				Set				*|
	\*------------------------------*/

	public void setMAJCarteClient(Carte[] tabCarte)
		{
		game.setTabCarteJoueur2(tabCarte);
		}

	public void setStateClientUpdate(GameState gameState)
		{
		stateClient = gameState;
		tourServeurOuTourJoueur();
		}

	public void changerAffichageBouton()
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

	public void setChangementTour(GameState stateRecup)
		{
		stateClient = stateRecup;
		ajusterStateClientVersServeur();
		tourServeurOuTourJoueur();
		}

	public void setCarteAdverse(Carte carte)
		{
		jPanelMyCard.setTabCarteSurPlateau(carte, 1);
		}

	public void setCartePoseParClient(Carte carteClient)
	{
		this.carteClient = carteClient;
		System.out.println("Carte reçu par le Client : " + this.carteClient.getNumber());
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
				PacketMessage paquet = new PacketMessage(serverProgram.getPseudo(), PacketMessage.END_OF_TURN);
				serverProgram.envoiPaquet(paquet);
				state = GameState.TOURCLIENT;
				stateClient = GameState.TOURCLIENT; // on force pour l'affichage
				sendStateClientChangementTour();	//Envoie state du serveur au client et change l'affichage du serveur
				sendCarteToClient();
				}
			});
		}

	private void sendCarteToClient()
	{
	PacketMessage paquet = new PacketMessage(serverProgram.getPseudo(), PacketMessage.SEND_CARD_SERVER_TO_CLIENT,jPanelMyCard.getCartePose());
	System.out.println("Carte joué par le Serveur  : " + jPanelMyCard.getCartePose().getNumber());
	buttonDisparition(jPanelMyCard.getCartePose());
	serverProgram.envoiPaquet(paquet);
	}

	private void buttonDisparition(Carte carte)
	{
		for(int i = 0; i<jPanelMyCard.getTabMyCard().length;i++)
			{
			if(jPanelMyCard.getTabMyCard()[i].getCarte() == carte)
				{
				jPanelMyCard.getTabMyCard()[i].setEnabled(false);
				jPanelMyCard.getTabMyCard()[i].setVisible(false);

				}
			}
	}

	private void envoiDistribution()
		{
		// TODO Auto-generated method stub
		PacketMessage paquet = new PacketMessage(serverProgram.getPseudo(), PacketMessage.START_GAME_DISTRIBUTION, game.getTabCarteJoueur2());
		serverProgram.envoiPaquet(paquet);
		}

	private void ajusterStateClientVersServeur()
		{
		state = stateClient;
		tourServeurOuTourJoueur();
		}

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	// Tools
	private ServerProgram serverProgram;
	private Game game;
	private Carte carteClient;
	private GameState stateClient;
	private Carte[] carteServeur;
	}
