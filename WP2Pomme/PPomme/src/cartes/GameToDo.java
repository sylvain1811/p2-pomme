
package cartes;

public class GameToDo
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	public GameToDo(/*ServerProgram serverProgram*/)
		{
		//this.serverProgram = serverProgram;
		this.init();
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	public void distribuer()
		{
		// R�partir 24 cartes entre deux tableaux al�atoirement.
		}

	/*------------------------------*\
	|*			  Static			*|
	\*------------------------------*/

	// M�thode appel�e selon choix du joueur
	public static Carte[] echangerTroisCartes(Carte[] tabCartesSources)
		{
		// Input : tableau 12 cartes, tableau de 3 int (index des cartes � remplacer, max 8). Retourne un tableau de 9 cartes.
		return null;
		}

	// M�thode appel�e selon choix du joueur
	public static Carte[] echangerSixAtout(Carte atout, Carte[] jeu)
		{
		// Place la carte d'atout dans le jeu. Enleve le 6.
		return jeu;
		}

	/*------------------------------*\
	|*				Set				*|
	\*------------------------------*/

	// Set sur tous les attributs (pour l'instant)

	/*------------------------------*\
	|*				Get				*|
	\*------------------------------*/

	// Get sur tous les attributs (pour l'instant)

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	private void init()
		{
		// Initialiser les cartes;
		}

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	// private ServerProgram serverProgram;
	}
