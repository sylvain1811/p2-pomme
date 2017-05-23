
package reseau.usekryonet;

import java.io.Serializable;

import cartes.Carte;

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

	public PacketMessage(String pseudo, int code, Carte carte)
		{
		this.pseudoFrom = pseudo;
		this.code = code;
		this.carte = carte;
		}

	public PacketMessage(String pseudo, int code, Carte[] tabCarte)
		{
		this.pseudoFrom = pseudo;
		this.code = code;
		this.tabCarte = tabCarte;
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

	/*------------------------------*\
	|*				Get				*|
	\*------------------------------*/

	public Carte[] getTabCarte()
		{
		return this.tabCarte;
		}

	public Carte getCarte()
		{
		return this.carte;
		}

	public String getMessage()
		{
		return message;
		}

	public int getCode()
		{
		return this.code;
		}

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	private String message = null;
	private String pseudoFrom;
	private int code;
	private Carte[] tabCarte = null;
	private Carte carte = null;

	/*------------------------------*\
	|*			  Static			*|
	\*------------------------------*/

	// Types de messages
	public static final int START_GAME_DISTRIBUTION = 1; // Distribution des cartes
	public static final int CARD_PLAYED = 2; // Une carte est jouée
	public static final int END_OF_TURN = 3; // Fin du tour
	public static final int CARD_ATOUT = 4; // Averti quel est l'atout
	public static final int SIMPLE_MESSAGE = 5; // En vue d'un éventuel chat
	public static final int SEND_CARD_CLIENT_TO_SERVER = 6; // Envoie du jeu de carte du client au serveur

	// Les erreurs ont un code de 100 ou plus
	public static final int ERROR_SERVER_FULL = 100;

	}
