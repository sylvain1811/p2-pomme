
package cartes;

public class Card
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	public Card(int number, int color, int value)
		{
		this.number = number;
		this.color = color;
		this.value = value;
		this.imgPath = "/" + number + ".png";
		}

	public Card()
		{
		//Rien

		}
	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	@Override
	public String toString()
		{
		//return "Carte [number=" + this.number + ", couleur=" + this.couleur + ", valeur=" + this.valeur + "]";
		return "[Carte]:" + TABLE_VALEUR[number] + " de " + TABLE_COULEUR[color];
		}

	/*------------------------------*\
	|*				Get				*|
	\*------------------------------*/

	public int getNumber()
		{
		return this.number;
		}

	public int getValue()
		{
		return this.value;
		}

	public int getColor()
		{
		return this.color;
		}

	public String getImgPath()
		{
		return this.imgPath;
		}

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	private int number;
	private int color;
	private int value;
	private String imgPath;

	/*------------------------------*\
	|*			  Static			*|
	\*------------------------------*/

	public static String[] TABLE_COULEUR = { "carreaux", "coeur", "pique", "trèfle" };
	public static String[] TABLE_VALEUR = { "6", "7", "8", "9", "10", "valais", "dame", "roi", "as" };

	}
