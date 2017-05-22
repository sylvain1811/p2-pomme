
package cartes;

public class Carte
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	public Carte(int number, int couleur, int valeur)
		{
		this.number = number;
		this.couleur = couleur;
		this.valeur = valeur;
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	@Override
	public String toString()
		{
		//return "Carte [number=" + this.number + ", couleur=" + this.couleur + ", valeur=" + this.valeur + "]";
		return "[Carte]:" + TABLE_VALEUR[number] + " de " + TABLE_COULEUR[couleur];
		}

	/*------------------------------*\
	|*				Get				*|
	\*------------------------------*/

	public int getNumber()
		{
		return this.number;
		}

	public int getValeur()
		{
		return this.valeur;
		}

	public int getCouleur()
		{
		return this.couleur;
		}

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	private int number;
	private int couleur;
	private int valeur;

	/*------------------------------*\
	|*			  Static			*|
	\*------------------------------*/

	public static String[] TABLE_COULEUR = { "carreaux", "coeur", "pique", "trèfle" };
	public static String[] TABLE_VALEUR = { "6", "7", "8", "9", "10", "valais", "dame", "roi", "as" };

	}
