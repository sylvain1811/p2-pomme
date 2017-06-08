
package gui.jpanelingame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import cartes.Card;
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
		scoreServer = 0;
		scoreClient = 0;
		listCardServerWin = new ArrayList<>();
		listCardClientWin = new ArrayList<>();
		endGame = false;
		game = new Game();
		game.distribute(); //Distribution des cartes
		sendDitribute();
		state = GameState.ECHANGE;
		sendStateClient();
		changeDisplayButton();
		controlBtnEndTour();
		sendCardAtout(game.getTabCard()[game.getNumeroAtout()]);
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	public void exchangeThreeCards(Card[] indexCardsToReplace)
		{
		Game.exchangeThreeCards(cardServer, indexCardsToReplace);
		changeDisplayButton();
		jPanelMyCard.resetDisplayAfterExchangeThreeCards();
		state = GameState.TOURSERVER;
		sendStateClient();
		tourServerOrTourClient();
		}

	public void tourServerOrTourClient()
		{
		if (state == GameState.TOURCLIENT)
			{
			for(int i = 0; i < jPanelMyCard.getTabMyCard().length; i++)
				{
				jPanelMyCard.getTabMyCard()[i].setEnabled(false);
				btnEndTour.setEnabled(false);
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

	public void sendStateClient()
		{
		PacketMessage paquet = new PacketMessage(serverProgram.getPseudo(), PacketMessage.SEND_STATE_SERVER_TO_CLIENT, state);
		serverProgram.sendPackage(paquet);
		}

	public void sendStateClientChangeTour()
		{
		PacketMessage paquet = new PacketMessage(serverProgram.getPseudo(), PacketMessage.SEND_STATE_SERVER_TO_CLIENT, state);
		serverProgram.sendPackage(paquet);
		tourServerOrTourClient();
		}

	/*------------------------------*\
	|*				Set				*|
	\*------------------------------*/

	public void setUpdateCardClient(Card[] tabCarte)
		{
		game.setTabCardPlayer2(tabCarte);
		}

	public void setStateClientUpdate(GameState gameState)
		{
		stateClient = gameState;
		tourServerOrTourClient();
		}

	public void changeDisplayButton()
		{
		// TODO Auto-generated method stub
		cardServer = game.getTabCardPlayer1();
		for(int i = 0; i < 9; i++)
			{
			jPanelMyCard.getTabMyCard()[i].setCard(cardServer[i]);
			jPanelMyCard.getTabMyCard()[i].setText(String.valueOf(cardServer[i].getNumber()));
			}
		}

	public void setChangeTour(GameState stateRecup)
		{
		stateClient = stateRecup;
		adjusteStateClientToServer();
		tourServerOrTourClient();
		}

	public void setCardAdverse(Card card)
		{
		jPanelMyCard.setTabCardOnBoard(card, 1);
		}

	public void setCardPosedToClient(Card cardClient)
		{
		this.cardClient = cardClient;
		jPanelMyCard.setTabCardOnBoard(this.cardClient, 1);
		//Afficher carte client sur le serveur
		//TODO
		if (jPanelMyCard.getTwoPlayerPlayed() == true)
			{
			calculateWinner();
			}
		else
			{
			jPanelMyCard.setTwoPlayerPlayed(true);
			}
		}
	/*------------------------------*\
	|*				Get				*|
	\*------------------------------*/

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	private void controlBtnEndTour()
		{
		this.btnEndTour.addActionListener(new ActionListener()
			{

			@Override
			public void actionPerformed(ActionEvent e)
				{
				PacketMessage paquet = new PacketMessage(serverProgram.getPseudo(), PacketMessage.END_OF_TURN);
				serverProgram.sendPackage(paquet);
				//Si on le serveur est le deuxieme a jouer
				//Afficher carte du serveur
				if (jPanelMyCard.getTwoPlayerPlayed() == true)
					{
					sendStateClientChangeTour(); //Envoie state du serveur au client et change l'affichage du serveur
					sendCardToClient();
					calculateWinner();
					}
				else
					{
					state = GameState.TOURCLIENT;
					stateClient = GameState.TOURCLIENT; // on force pour l'affichage
					sendStateClientChangeTour(); //Envoie state du serveur au client et change l'affichage du serveur
					sendCardToClient();
					jPanelMyCard.setTwoPlayerPlayed(true);
					}
				}
			});
		}

	private void endOfGame()
		{
		endGame = true;
		for(int i = 0; i < 9; i++)
			{
			if (jPanelMyCard.getTabMyCard()[i].isVisible() == true)
				{
				endGame = false;
				}
			}
		if (endGame == true)
			{
			//Le jeu est fini
			//Création des paquet de carte a envoyé a la méthode
			if (listCardClientWin.size() != 0)
				{
				Card[] carteGagneClient = new Card[listCardClientWin.size()];
				for(int i = 0; i < listCardClientWin.size(); i++)
					{
					carteGagneClient[i] = listCardClientWin.get(i);
					}
				scoreClient = game.comptagePointsFinal(carteGagneClient);
				}
			if (listCardServerWin.size() != 0)
				{
				Card[] carteGagneServeur = new Card[listCardServerWin.size()];
				for(int i = 0; i < listCardServerWin.size(); i++)
					{
					carteGagneServeur[i] = listCardServerWin.get(i);
					}
				scoreServer = game.comptagePointsFinal(carteGagneServeur);
				}
			sendScoreToClient();
			sendFinDeJeu();
			finDeJeu();
			}
		}

	private void finDeJeu()
		{
		//Partie fin du jeu
		//Afficher fenetre avec score (les deux variable sont rempli, manque juste affichage
		}

	private void calculateWinner()
		{
		game.setTabCartePose(jPanelMyCard.getTabCardOnBoard());
		winner = game.calculateWinnerTour();

		if (winner == 0)
			{
			System.out.println("Server gagnant");
			listCardServerWin.add(jPanelMyCard.getTabCardOnBoard()[0]);
			listCardServerWin.add(jPanelMyCard.getTabCardOnBoard()[1]);
			//TODO Faire disparaitre les cartes après un petit moment
			jPanelMyCard.setTwoPlayerPlayed(false);
			state = GameState.TOURSERVER;
			stateClient = GameState.TOURSERVER; // on force pour l'affichage
			sendStateClientChangeTour(); //Envoie state du serveur au client et change l'affichage du serveur
			}
		else
			{
			System.out.println("Client gagnant");
			listCardClientWin.add(jPanelMyCard.getTabCardOnBoard()[0]);
			listCardClientWin.add(jPanelMyCard.getTabCardOnBoard()[1]);
			jPanelMyCard.setTabCardOnBoard(null, 0); //Suppression des carte sur le plateau
			jPanelMyCard.setTabCardOnBoard(null, 1);
			jPanelMyCard.setTwoPlayerPlayed(false);
			state = GameState.TOURCLIENT;
			stateClient = GameState.TOURCLIENT; // on force pour l'affichage
			sendStateClientChangeTour(); //Envoie state du serveur au client et change l'affichage du serveur
			}
		endOfGame();
		}

	private void sendCardToClient()
		{
		PacketMessage paquet = new PacketMessage(serverProgram.getPseudo(), PacketMessage.SEND_CARD_SERVER_TO_CLIENT, jPanelMyCard.getCardPosed());
		addCardToControl(jPanelMyCard.getCardPosed());
		buttonDisplayInvisible(jPanelMyCard.getCardPosed());
		serverProgram.sendPackage(paquet);
		}

	private void sendScoreToClient()
		{
		PacketMessage paquet = new PacketMessage(serverProgram.getPseudo(), PacketMessage.SEND_SCORE_SERVER_TO_CLIENT_CLIENT, scoreClient);
		serverProgram.sendPackage(paquet);
		PacketMessage paquet2 = new PacketMessage(serverProgram.getPseudo(), PacketMessage.SEND_SCORE_SERVER_TO_CLIENT_SERVER, scoreServer);
		serverProgram.sendPackage(paquet2);
		}

	private void sendFinDeJeu()
		{
		PacketMessage paquet = new PacketMessage(serverProgram.getPseudo(), PacketMessage.END_GAME);
		serverProgram.sendPackage(paquet);
		}

	private void sendCardAtout(Card carteAtout)
		{
		PacketMessage paquet = new PacketMessage(serverProgram.getPseudo(), PacketMessage.SEND_CARD_ATOUT_SERVER_TO_CLIENT, carteAtout);
		serverProgram.sendPackage(paquet);
		}

	private void addCardToControl(Card carteAControler)
		{
		jPanelMyCard.setTabCardOnBoard(carteAControler, 0);
		}

	private void buttonDisplayInvisible(Card carte)
		{
		for(int i = 0; i < jPanelMyCard.getTabMyCard().length; i++)
			{
			if (jPanelMyCard.getTabMyCard()[i].getCard() == carte)
				{
				jPanelMyCard.getTabMyCard()[i].setEnabled(false);
				jPanelMyCard.getTabMyCard()[i].setVisible(false);

				}
			}
		}

	private void sendDitribute()
		{
		// TODO Auto-generated method stub
		PacketMessage paquet = new PacketMessage(serverProgram.getPseudo(), PacketMessage.START_GAME_DISTRIBUTION, game.getTabCardPlayer2());
		serverProgram.sendPackage(paquet);
		}

	private void adjusteStateClientToServer()
		{
		state = stateClient;
		tourServerOrTourClient();
		}

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	// Tools
	private ServerProgram serverProgram;
	private Game game;
	private Card cardClient;
	private GameState stateClient;
	private Card[] cardServer;
	private List<Card> listCardServerWin;
	private List<Card> listCardClientWin;
	private int winner;
	private boolean endGame;
	private int scoreServer;
	private int scoreClient;
	}
