
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
		serverStartTour = true;
		listCardServerWin = new ArrayList<>();
		listCardClientWin = new ArrayList<>();
		endGame = false;
		game = new Game();
		game.distribute();
		clientStart = false;
		sendDitribute();
		sendCardAtout(game.getTabCard()[game.getNumeroAtout()]);
		state = GameState.ECHANGE;
		stateClient = GameState.ECHANGE;
		sendStateClient();
		changeDisplayButton();
		jPanelMyCard.getJPanelBoard().addAtout(game.getTabCard()[game.getNumeroAtout()]);
		controlBtnEndTour();
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
		cleanDisplay();
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
		else if (stateClient == GameState.TOURSERVER)
			{
			for(int i = 0; i < jPanelMyCard.getTabMyCard().length; i++)
				{
				jPanelMyCard.getTabMyCard()[i].setEnabled(true);
				if (clientStart == true)
					{
					canPlayCards();
					}
				}
			}
		else
			{
			for(int i = 0; i < jPanelMyCard.getTabMyCard().length; i++)
				{
				jPanelMyCard.getTabMyCard()[i].setEnabled(false);
				}
			}
		}

	public void sendCardClientToServeurTwo(Card carteServer)
		{
		PacketMessage paquet = new PacketMessage(serverProgram.getPseudo(), PacketMessage.SEND_CARD_SERVER_TO_CLIENT, carteServer);
		serverProgram.sendPackage(paquet);
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
		cardServer = game.getTabCardPlayer1();
		for(int i = 0; i < 9; i++)
			{
			jPanelMyCard.getTabMyCard()[i].setCard(cardServer[i]);
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
		jPanelMyCard.getJPanelBoard().addOpponentCard(this.cardClient);
		//Afficher carte client sur le serveur
		if (jPanelMyCard.getTwoPlayerPlayed() == true)
			{
			calculateWinner();
			serverStartTour = true;
			}
		else
			{
			canPlayCards();
			jPanelMyCard.setTwoPlayerPlayed(true);
			serverStartTour = false;
			clientStart = false;
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
				sendCardToClient();
				//Si on le serveur est le deuxieme a jouer
				if (jPanelMyCard.getTwoPlayerPlayed() == true)
					{
					sendIsClientFirst(clientStart);
					sendStateClientChangeTour(); //Envoie state du serveur au client et change l'affichage du serveur
					calculateWinner();
					}
				else
					{
					clientStart = false;
					sendIsClientFirst(clientStart);
					state = GameState.TOURCLIENT;
					stateClient = GameState.TOURCLIENT; // on force pour l'affichage
					sendStateClientChangeTour(); //Envoie state du serveur au client et change l'affichage du serveur
					serverStartTour = true;
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
			sendEndOfGame();
			screenEndOfGame();
			}
		}

	private void screenEndOfGame()
		{
		//Partie fin du jeu
		//Afficher fenetre avec score (les deux variable sont rempli, manque juste affichage
		}

	private void calculateWinner()
		{
		if (serverStartTour == true)
			{
			game.setTabCartePose(jPanelMyCard.getTabCardOnBoard());
			winner = game.calculateWinnerTour();
			}
		else
			{
			Card temp = jPanelMyCard.getTabCardOnBoard()[0];
			jPanelMyCard.setTabCardOnBoard(jPanelMyCard.getTabCardOnBoard()[1], 0);
			jPanelMyCard.setTabCardOnBoard(temp, 1);
			game.setTabCartePose(jPanelMyCard.getTabCardOnBoard());
			winner = game.calculateWinnerTour();
			if (winner == 1)
				{
				winner = 0;
				}
			else
				{
				winner = 1;
				}
			}
		if (winner == 0)
			{
			listCardServerWin.add(jPanelMyCard.getTabCardOnBoard()[0]);
			listCardServerWin.add(jPanelMyCard.getTabCardOnBoard()[1]);
			jPanelMyCard.setTabCardOnBoard(null, 0);
			jPanelMyCard.setTabCardOnBoard(null, 1);
			jPanelMyCard.setTwoPlayerPlayed(false);
			state = GameState.TOURSERVER;
			stateClient = GameState.TOURSERVER; // on force pour l'affichage
			sendStateClientChangeTour(); //Envoie state du serveur au client et change l'affichage du serveur
			}
		else
			{
			listCardClientWin.add(jPanelMyCard.getTabCardOnBoard()[0]);
			listCardClientWin.add(jPanelMyCard.getTabCardOnBoard()[1]);
			jPanelMyCard.setTabCardOnBoard(null, 0);
			jPanelMyCard.setTabCardOnBoard(null, 1);
			jPanelMyCard.setTwoPlayerPlayed(false);
			clientStart = true;
			sendIsClientFirst(clientStart);
			state = GameState.TOURCLIENT;
			stateClient = GameState.TOURCLIENT; // on force pour l'affichage
			sendStateClientChangeTour(); //Envoie state du serveur au client et change l'affichage du serveur
			}
		try
			{
			Thread.sleep(3000);
			}
		catch (InterruptedException e)
			{
			e.printStackTrace();
			}
		cleanDisplay();
		endOfGame();
		}

	private void cleanDisplay()
		{

		jPanelMyCard.getJPanelBoard().clearCards();
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

	private void sendEndOfGame()
		{
		PacketMessage paquet = new PacketMessage(serverProgram.getPseudo(), PacketMessage.END_GAME);
		serverProgram.sendPackage(paquet);
		}

	private void sendIsClientFirst(boolean isClientFirst)
		{
		PacketMessage paquet = new PacketMessage(serverProgram.getPseudo(), PacketMessage.SEND_IS_FIRST_PLAYER, isClientFirst);
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

	private void canPlayCards()
		{
		boolean canPlaySuite = false;
		for(int i = 0; i < jPanelMyCard.getTabMyCard().length; i++)
			{
			jPanelMyCard.getTabMyCard()[i].setEnabled(false);
			//TODO
			if (jPanelMyCard.getTabMyCard()[i].getCard().getColor() == game.getTabCard()[game.getNumeroAtout()].getColor())
				{
				jPanelMyCard.getTabMyCard()[i].setEnabled(true);
				}
			if (jPanelMyCard.getTabMyCard()[i].getCard().getColor() == cardClient.getColor() && jPanelMyCard.getTabMyCard()[i].isVisible() == true)
				{
				jPanelMyCard.getTabMyCard()[i].setEnabled(true);
				canPlaySuite = true;
				}
			}
		if (canPlaySuite == false)
			{
			for(int i = 0; i < jPanelMyCard.getTabMyCard().length; i++)
				{
				jPanelMyCard.getTabMyCard()[i].setEnabled(true);
				}
			}
		}

	private void sendDitribute()
		{
		try
			{
			Thread.sleep(200);
			}
		catch (InterruptedException e)
			{
			e.printStackTrace();
			}
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
	private boolean serverStartTour;
	private int scoreServer;
	private int scoreClient;
	private boolean clientStart;
	}
