
package reseau.usekryonet.listener;

import javax.swing.JOptionPane;

import com.esotericsoftware.kryonet.Connection;

import cartes.Card;
import gui.JFrameHome;
import gui.jpanelingame.GameState;
import gui.jpanelingame.JPanelInGameClient;
import reseau.usekryonet.ClientProgram;
import reseau.usekryonet.PacketMessage;

public class CustomListenerClient extends CustomListener
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	public CustomListenerClient(ClientProgram clientProgram)
		{
		super();
		this.clientProgram = clientProgram;
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	@Override
	public void disconnected(Connection connection)
		{
		// TODO : Gérer l'arrêt de la partie
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

	@Override
	protected void traiterPaquet(PacketMessage paquet)
		{
		// Si le code est vaut 100 ou plus alors c'est une erruer.
		if (paquet.getCode() < PacketMessage.ERROR_SERVER_FULL)
			{
			//clientProgram.log("New message from " + paquet.getPseudoFrom() + " : " + paquet.getMessage());
			//System.out.println("[" + paquet.getPseudoFrom() + " say ]: Message received from server : " + paquet.getMessage());
			switch(paquet.getCode())
				{
				case PacketMessage.START_GAME_DISTRIBUTION:
					distribute(paquet.getTabCards());
					break;
				case PacketMessage.SEND_STATE_SERVER_TO_CLIENT:
					changeState(paquet.getState());
					break;
				case PacketMessage.END_OF_TURN:
					changeStateEndOfTour(paquet.getState());
					break;
				case PacketMessage.SEND_CARD_SERVER_TO_CLIENT:
					snedCardServerToClient(paquet.getCard());
					break;
				case PacketMessage.SEND_SCORE_SERVER_TO_CLIENT_CLIENT:
					sendScoreClient(paquet.getScore());
					break;
				case PacketMessage.SEND_SCORE_SERVER_TO_CLIENT_SERVER:
					sendScoreServer(paquet.getScore());
					break;
				case PacketMessage.END_GAME:
					endOfGame();
					break;
				case PacketMessage.SEND_CARD_ATOUT_SERVER_TO_CLIENT:
					sendCardAtout(paquet.getCard());
					break;
				case PacketMessage.SEND_IS_FIRST_PLAYER:
					sendIsFirst(paquet.getFirst());
				default:
					break;
				}
			}

		else
			{
			switch(paquet.getCode())
				{
				case PacketMessage.ERROR_SERVER_FULL:
					clientProgram.logErr("Serveur plein, impossible de se connecter");
					JOptionPane.showMessageDialog(null, "Serveur plein, impossible de se connecter.", "Serveur plein", JOptionPane.ERROR_MESSAGE);
					break;

				default:
					break;
				}
			}
		}

	private void sendIsFirst(boolean isFirst)
	{
	jPanelInGameClient = (JPanelInGameClient)(JFrameHome.getInstance().getjPanelInGame());
	JPanelInGameClient.setIsFirst(isFirst);
	}
	private void endOfGame()
		{
		jPanelInGameClient = (JPanelInGameClient)(JFrameHome.getInstance().getjPanelInGame());
		JPanelInGameClient.setEndGame();
		}

	private void snedCardServerToClient(Card card)
		{
		jPanelInGameClient = (JPanelInGameClient)(JFrameHome.getInstance().getjPanelInGame());
		JPanelInGameClient.setCardPosedToServer(card);
		}

	private void sendScoreClient(int score)
		{
		jPanelInGameClient = (JPanelInGameClient)(JFrameHome.getInstance().getjPanelInGame());
		JPanelInGameClient.setUpdateScoreClient(score);
		}

	private void sendCardAtout(Card cardAtout)
		{
		jPanelInGameClient = (JPanelInGameClient)(JFrameHome.getInstance().getjPanelInGame());
		JPanelInGameClient.setCarteAtout(cardAtout);
		}

	private void sendScoreServer(int score)
		{
		jPanelInGameClient = (JPanelInGameClient)(JFrameHome.getInstance().getjPanelInGame());
		JPanelInGameClient.setUpdateScoreServer(score);
		}

	private void distribute(Card[] tabCards)
		{
		// TODO
		jPanelInGameClient = (JPanelInGameClient)(JFrameHome.getInstance().getjPanelInGame());
		while(jPanelInGameClient == null)
			{
			try
				{
				Thread.sleep(100);
				}
			catch (InterruptedException e)
				{
				// TODO Auto-generated catch block
				e.printStackTrace();
				}
			}
		jPanelInGameClient.setCardClient(tabCards);
		}

	private void changeState(GameState state)
		{
		// TODO
		jPanelInGameClient = (JPanelInGameClient)(JFrameHome.getInstance().getjPanelInGame());
		jPanelInGameClient.setStateUpdate(state);
		}

	private void changeStateEndOfTour(GameState state)
		{
		// TODO
		jPanelInGameClient = (JPanelInGameClient)(JFrameHome.getInstance().getjPanelInGame());
		jPanelInGameClient.setChangeTour(state);
		}
	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	// Input
	private ClientProgram clientProgram;
	private JPanelInGameClient jPanelInGameClient;
	}
