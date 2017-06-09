
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
			if (stateServer == GameState.TOURSERVER)
				{
				for(int i = 0; i < jPanelMyCard.getTabMyCard().length; i++)
					{
					jPanelMyCard.getTabMyCard()[i].setEnabled(false);
					btnEndTour.setEnabled(false);
					}
				}
			else if(stateServer == GameState.ECHANGE)
				{
				for(int i = 0; i < jPanelMyCard.getTabMyCard().length; i++)
					{
					jPanelMyCard.getTabMyCard()[i].setEnabled(false);
					}
				}
			else
				{
				for(int i = 0; i < jPanelMyCard.getTabMyCard().length; i++)
					{
					jPanelMyCard.getTabMyCard()[i].setEnabled(true);
					if(firstPlayer == false && stateServer != GameState.ECHANGE && cardPosedToServer !=null)
						{
						canPlayCards();
						}
					}
				}
			}
		}

	public void sendStateClient()
		{
		PacketMessage paquet = new PacketMessage(clientProgram.getPseudo(), PacketMessage.SEND_STATE_CLIENT_TO_SERVER, state);
		clientProgram.sendPackage(paquet);
		}

	public void changeDisplayButton()
		{
		for(int i = 0; i < 9; i++)
			{
			jPanelMyCard.getTabMyCard()[i].setCard(tabCardClient[i]);
			}
		}

	/*------------------------------*\
	|*				Set				*|
	\*------------------------------*/

	public void setCardClient(Card[] tabCardClient)
		{
		this.tabCardClient = tabCardClient;
		changeDisplayButton();
		}

	public static void setEndGame()
		{
		//TODO
		//Afficher fenetre avec score (les deux variable sont rempli, manque juste affichage

		}

	public void exchangeThreeCards(Card[] indexCardsToReplace)
		{
		Game.exchangeThreeCards(tabCardClient, indexCardsToReplace);
		changeDisplayButton();
		jPanelMyCard.resetDisplayAfterExchangeThreeCards();
		sendNewCardServeur();//Envoie des carte au serveur
		state = GameState.TOURSERVER;
		sendStateClient();
		tourServerOrTourPlayer();
		}

	public void setStateUpdate(GameState state)
		{
		stateServer = state;
		tourServerOrTourPlayer();
		}

	public static void setUpdateScoreClient(int score)
		{
		scoreClient = score;
		}

	public static void setCarteAtout(Card carteAtout2)
		{
		cardAtout = carteAtout2;
		//Afficher l'atout
		//TODO
		}

	public static void setIsFirst(boolean isFirst)
	{
		firstPlayer = isFirst;
	}
	public static void setUpdateScoreServer(int score)
		{
		scoreServer = score;
		}

	public void setChangeTour(GameState state)
		{
		stateServer = state;
		adjusteStateServerToClient();
		tourServerOrTourPlayer();
		}

	public static void setCardPosedToServer(Card card)
		{
		cardPosedToServer = card;
		System.out.println(cardPosedToServer.getColor());
		//Afficher la carte reçu par le serveur

		//TODO
		}
	/*------------------------------*\
	|*				Get				*|
	\*------------------------------*/

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	private void buttonDisplayInvisible(Card card)
		{
		for(int i = 0; i < jPanelMyCard.getTabMyCard().length; i++)
			{
			if (jPanelMyCard.getTabMyCard()[i].getCard() == card)
				{
				jPanelMyCard.getTabMyCard()[i].setEnabled(false);
				jPanelMyCard.getTabMyCard()[i].setVisible(false);
				}
			}
		}

	private void adjusteStateServerToClient()
		{
		state = stateServer;
		tourServerOrTourPlayer();
		}

	private void canPlayCards()
		{
		boolean canPlaySuite = false;
		for(int i = 0; i < jPanelMyCard.getTabMyCard().length; i++)
			{
			System.out.println(jPanelMyCard.getTabMyCard()[i].getCard().getColor());
			System.out.println(cardPosedToServer.getColor());
			jPanelMyCard.getTabMyCard()[i].setEnabled(false);
			//TODO
			if (jPanelMyCard.getTabMyCard()[i].getCard().getColor() == cardAtout.getColor())
				{
				jPanelMyCard.getTabMyCard()[i].setEnabled(true);
				}
			if (jPanelMyCard.getTabMyCard()[i].getCard().getColor() == cardPosedToServer.getColor() && jPanelMyCard.getTabMyCard()[i].isVisible() == true )
				{
				//Peut suivre
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

	private void sendNewCardServeur()
		{
		PacketMessage paquet = new PacketMessage(clientProgram.getPseudo(), PacketMessage.SEND_PAQUET_CARD_CLIENT_TO_SERVER, tabCardClient);
		clientProgram.sendPackage(paquet);
		}

	private void controlBtnEndTour()
		{
		this.btnEndTour.addActionListener(new ActionListener()
			{

			@Override
			public void actionPerformed(ActionEvent e)
				{
				//Afficher carte du client
				PacketMessage paquet = new PacketMessage(clientProgram.getPseudo(), PacketMessage.END_OF_TURN); //envoi paquet fin de tour
				clientProgram.sendPackage(paquet);
				state = GameState.TOURSERVER; //change le tour
				stateServer = GameState.TOURSERVER; // on force pour l'affichage
				sendStateServerChangementTour();
				sendCarteToServer();
				}
			});
		}

	private void sendStateServerChangementTour()
		{
		PacketMessage paquet = new PacketMessage(clientProgram.getPseudo(), PacketMessage.CARD_PLAYED, jPanelMyCard.getTabCardOnBoard()[1]);
		clientProgram.sendPackage(paquet);
		PacketMessage paquet2 = new PacketMessage(clientProgram.getPseudo(), PacketMessage.SEND_STATE_CLIENT_TO_SERVER, state);
		clientProgram.sendPackage(paquet2);
		tourServerOrTourPlayer();
		}

	private void sendCarteToServer()
		{
		PacketMessage paquet = new PacketMessage(clientProgram.getPseudo(), PacketMessage.SEND_CARD_CLIENT_TO_SERVER, jPanelMyCard.getCardPosed());
		buttonDisplayInvisible(jPanelMyCard.getCardPosed());
		clientProgram.sendPackage(paquet);
		}

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	// Tools
	private ClientProgram clientProgram;
	private Card[] tabCardClient;
	private static Card cardPosedToServer;
	private GameState stateServer = GameState.ECHANGE;
	private static int scoreClient;
	private static int scoreServer;
	private static Card cardAtout;
	private static boolean firstPlayer;
	}
