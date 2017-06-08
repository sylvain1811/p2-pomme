
package gui.jpanelingame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import cartes.Card;
import cartes.Game;
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
		state = GameState.DEMMARAGE;
		adjusteStateServerToClient();
		controlBtnEndTour();
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	public void tourServerOrTourPlayer()
		{
		if (state == GameState.ECHANGE)
			{
			}
		else
			{
			if (stateServeur == GameState.TOURSERVEUR)
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
					}
				}
			}
		}

	public void sendStateClient()
		{
		PacketMessage paquet = new PacketMessage(clientProgram.getPseudo(), PacketMessage.SEND_STATE_CLIENT_TO_SERVER, state);
		clientProgram.envoiPaquet(paquet);
		}

	public void changerAffichageBouton()
		{
		// TODO Auto-generated method stub
		for(int i = 0; i < 9; i++)
			{
			jPanelMyCard.getTabMyCard()[i].setCarte(cardClient[i]);
			}
		}

	/*------------------------------*\
	|*				Set				*|
	\*------------------------------*/

	public void setCarteJoueurClient(Card[] carteJoueurClient)
		{
		this.cardClient = carteJoueurClient;
		changerAffichageBouton();
		}

	public static void setFinJeu()
		{
		//	ACTION DE LA FIN DU JEU
		//Afficher fenetre avec score (les deux variable sont rempli, manque juste affichage

		}

	public void echangerTroisCartes(Card[] indexCartesARemplacer)
		{
		Game.echangerTroisCartes(cardClient, indexCartesARemplacer);
		changerAffichageBouton();
		jPanelMyCard.remiseAffichageApresEchangeTroisCartes();
		sendNewCardServeur();//Envoie des carte au serveur
		state = GameState.TOURSERVEUR;
		sendStateClient();
		tourServerOrTourPlayer();
		}

	public void setStateUpdate(GameState stateRecup)
		{
		stateServeur = stateRecup;
		tourServerOrTourPlayer();
		}

	public static void setUpdateScoreClient(int score)
		{
		scoreClient = score;
		}

	public static void setCarteAtout(Card carteAtoutRec)
		{
		carteAtout = carteAtoutRec;
		//Afficher l'atout
		}

	public static void setUpdateScoreServeur(int score)
		{
		scoreServer = score;
		}

	public void setChangementTour(GameState stateRecup)
		{
		stateServeur = stateRecup;
		adjusteStateServerToClient();
		tourServerOrTourPlayer();
		}

	public static void setCartePoseParServeur(Card carte) //static OBLIGATOIRE !!
		{
		cardPoseToServer = carte;
		//Afficher la carte reçu par le serveur

		}
	/*------------------------------*\
	|*				Get				*|
	\*------------------------------*/

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	private void buttonDisparition(Card carte)
		{
		for(int i = 0; i < jPanelMyCard.getTabMyCard().length; i++)
			{
			if (jPanelMyCard.getTabMyCard()[i].getCarte() == carte)
				{
				jPanelMyCard.getTabMyCard()[i].setEnabled(false);
				jPanelMyCard.getTabMyCard()[i].setVisible(false);
				}
			}
		}

	private void adjusteStateServerToClient()
		{
		state = stateServeur;
		tourServerOrTourPlayer();
		}

	private void sendNewCardServeur()
		{
		PacketMessage paquet = new PacketMessage(clientProgram.getPseudo(), PacketMessage.SEND_PAQUET_CARD_CLIENT_TO_SERVER, cardClient);
		clientProgram.envoiPaquet(paquet);
		}

	private void controlBtnEndTour()
		{
		this.btnFinTour.addActionListener(new ActionListener()
			{

			@Override
			public void actionPerformed(ActionEvent e)
				{
				//Afficher carte du client
				PacketMessage paquet = new PacketMessage(clientProgram.getPseudo(), PacketMessage.END_OF_TURN); //envoi paquet fin de tour
				clientProgram.envoiPaquet(paquet);
				state = GameState.TOURSERVEUR; //change le tour
				stateServeur = GameState.TOURSERVEUR; // on force pour l'affichage
				sendStateServerChangementTour();
				sendCarteToServer();
				}
			});
		}

	private void sendStateServerChangementTour()
		{
		PacketMessage paquet = new PacketMessage(clientProgram.getPseudo(), PacketMessage.CARD_PLAYED, jPanelMyCard.getTabCarteSurPlateau()[1]);
		clientProgram.envoiPaquet(paquet);
		PacketMessage paquet2 = new PacketMessage(clientProgram.getPseudo(), PacketMessage.SEND_STATE_CLIENT_TO_SERVER, state);
		clientProgram.envoiPaquet(paquet2);
		tourServerOrTourPlayer();
		}

	private void sendCarteToServer()
		{
		PacketMessage paquet = new PacketMessage(clientProgram.getPseudo(), PacketMessage.SEND_CARD_CLIENT_TO_SERVER, jPanelMyCard.getCartePose());
		buttonDisparition(jPanelMyCard.getCartePose());
		clientProgram.envoiPaquet(paquet);
		}

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	// Tools
	private ClientProgram clientProgram;
	private Card[] cardClient;
	private static Card cardPoseToServer;
	private GameState stateServeur = GameState.ECHANGE;
	private static int scoreClient;
	private static int scoreServer;
	private static Card carteAtout;
	}
