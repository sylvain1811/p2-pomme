
package reseau.usekryonet;

import java.io.Serializable;

import cartes.Card;
import gui.jpanelingame.GameState;

/**
 * Cette classe est utilisée pour envoyer les données à traver les réseau.
 * @author sylvain.renaud
 */
public class PacketMessage implements Serializable
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	public PacketMessage()
		{
		this.pseudoFrom = "";
		this.message = "";
		}

	/**
	 * Message simple.
	 * @param pseudo
	 * @param message
	 */
	public PacketMessage(String pseudo, String message)
		{
		this.pseudoFrom = pseudo;
		this.message = message;
		}

	public PacketMessage(String pseudo, int code, GameState state)
		{
		this.pseudoFrom = pseudo;
		this.code = code;
		this.state = state;
		}

	/**
	 *
	 * @param pseudo
	 * @param code
	 */
	public PacketMessage(String pseudo, int code)
		{
		this.pseudoFrom = pseudo;
		this.code = code;
		}

	public PacketMessage(String pseudo, int code, Card card)
		{
		this.pseudoFrom = pseudo;
		this.code = code;
		this.card = card;
		}

	public PacketMessage(String pseudo, int code, int score)
		{
		this.pseudoFrom = pseudo;
		this.code = code;
		this.score = score;
		}

	public PacketMessage(String pseudo, int code, Card[] tabCards)
		{
		this.pseudoFrom = pseudo;
		this.code = code;
		this.cards = tabCards;
		}

	public PacketMessage(String pseudo, int code, boolean isFirst)
		{
		this.pseudoFrom = pseudo;
		this.code = code;
		this.isFirst = isFirst;
		}

	/*------------------------------*\
	|*				Set				*|
	\*------------------------------*/

	public String getPseudoFrom()
		{
		return this.pseudoFrom;
		}

	public void setMessage(String mess)
		{
		this.message = mess;
		}

	public void setCode(int code)
		{
		this.code = code;
		}

	public void setState(GameState state)
		{
		this.state = state;
		}
	/*------------------------------*\
	|*				Get				*|
	\*------------------------------*/

	public Card[] getTabCards()
		{
		return this.cards;
		}

	public Card getCard()
		{
		return this.card;
		}

	public String getMessage()
		{
		return message;
		}

	public int getCode()
		{
		return this.code;
		}

	public int getScore()
		{
		return this.score;
		}

	public GameState getState()
		{
		return this.state;
		}

	public boolean getFirst()
		{
		return this.isFirst;
		}

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	private String message = null;
	private String pseudoFrom;
	private int code;
	private int score;
	private Card[] cards;
	private Card card;
	private GameState state;
	private boolean isFirst;

	/*------------------------------*\
	|*			  Static			*|
	\*------------------------------*/

	// Types de messages
	public static final int START_GAME_DISTRIBUTION = 1; // Distribution des cartes
	public static final int CARD_PLAYED = 2; // Une carte est jouée
	public static final int END_OF_TURN = 3; // Fin du tour
	public static final int CARD_ATOUT = 4; // Averti quel est l'atout
	public static final int SIMPLE_MESSAGE = 5; // En vue d'un éventuel chat
	public static final int SEND_CARD_CLIENT_TO_SERVER = 6; // Envoie de la carte joué au serveur
	public static final int SEND_STATE_CLIENT_TO_SERVER = 7; // Envoie du state du client au serveur
	public static final int SEND_STATE_SERVER_TO_CLIENT = 8; // Envoie du state du serveur au client
	public final static int SEND_CARD_SERVER_TO_CLIENT = 9; // Envoie de la carte joué au client
	public static final int SEND_PAQUET_CARD_CLIENT_TO_SERVER = 10; // envoie le jeu du client au serveur
	public static final int SEND_SCORE_SERVER_TO_CLIENT_CLIENT = 11; // Envoie du score au client
	public static final int SEND_SCORE_SERVER_TO_CLIENT_SERVER = 12; //
	public static final int END_GAME = 13; //fin du jeu
	public static final int SEND_CARD_ATOUT_SERVER_TO_CLIENT = 14; //Carte Atout
	public static final int SEND_IS_FIRST_PLAYER = 15; //Envoie si le client est le premier ou pas

	// Les erreurs ont un code de 100 ou plus
	public static final int ERROR_SERVER_FULL = 100;

	}
