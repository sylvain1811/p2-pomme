
package reseau.usekryonet;

import java.io.Serializable;

import cartes.Carte;

/**
 * Cette classe est utilis�e pour envoyer les donn�es � traver les r�seau.
 * @author sylvain.renaud
 */
public class PacketMessage implements Serializable
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	public PacketMessage()
		{
		this.message = "";
		}

	public PacketMessage(String message)
		{
		this.message = message;
		}

	public PacketMessage(int code)
		{
		this.code = code;
		}

	public PacketMessage(int code, Carte carte)
		{
		this.code = code;
		this.carte = carte;
		}

	public PacketMessage(int code, Carte[] carte)
		{
		this.code = code;
		}

	/*------------------------------*\
	|*				Set				*|
	\*------------------------------*/

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
	private int code;
	private Carte[] tabCarte = null;
	private Carte carte = null;

	/*------------------------------*\
	|*			  Static			*|
	\*------------------------------*/

	// Types de messages
	public static final int START_GAME_DISTRIBUTION = 1; // Distribution des cartes
	public static final int CARD_PLAYED = 2; // Une carte est jou�e
	public static final int END_OF_TURN = 3; // Fin du tour
	public static final int CARD_ATOUT = 4; // Averti quel est l'atout
	public static final int SIMPLE_MESSAGE = 5; // En vue d'un �ventuel chat

	// Les erreurs ont un code de 100 ou plus
	public static final int ERROR_SERVER_FULL = 100;

	}
