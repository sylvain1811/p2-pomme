
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
		// R�partir 24 cartes entre deux tableaux al�atoirement. Les cartes doivent �tre uniques. Garder une carte pour l'atout.
		// Ces tableaux sont des variables de la classe.
		}

	public void jouerCarte(Carte carteJouee)
		{
		// On passe une carte que le joueur choisi. Si c'est le dernier joueur � poser une carte, on regarde qui gagne. (m�thode private s�par�e)
		// Si c'est le premier, on bloque les cartes que le prochain joueur ne pourra pas jouer.
		}

	public int[] calculScore()
		{
		// Calcul des scores pour J1 et J2. Retourne un tableau avec les scores des deux joueurs.
		return null;
		}

	/*------------------------------*\
	|*			  Static			*|
	\*------------------------------*/

	// M�thode appel�e selon choix du joueur
	public static Carte[] echangerTroisCartes(Carte[] tabCartesSources, int[] indexCartesARemplacer)
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
