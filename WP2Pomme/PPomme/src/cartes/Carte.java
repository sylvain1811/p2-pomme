
package cartes;

public class Carte
	{

	private int number;
	private int couleur;
	private int valeur;

	public static String[] TABLE_COULEUR = { "carreaux", "coeur", "pique", "trèfle" };

	public static String[] TABLE_VALEUR = { "6", "7", "8", "9", "10", "valais", "dame", "roi", "as" };

	public Carte(int number, int couleur, int valeur)
		{
		this.number = number;
		this.couleur = couleur;
		this.valeur = valeur;
		}

	public int number()
		{
		return this.number;
		}

	public int valeur()
		{
		return this.valeur;
		}

	public int couleur()
		{
		return this.couleur;
		}
	}
