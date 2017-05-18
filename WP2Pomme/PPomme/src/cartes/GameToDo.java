
package cartes;

import java.util.Random;

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
		// Répartir 24 cartes entre deux tableaux aléatoirement. Les cartes doivent être uniques. Garder une carte pour l'atout.
		// Ces tableaux sont des variables de la classe.
		Random rand = new Random();
		int random;
		boolean dejaDistribue = false;

		//Rempli carte joueur 1
		for(int i = 0; i < 12; i++)
			{
			do
				{
				dejaDistribue = false;
				random = rand.nextInt(36);
				dejaDistribue = estDejaDistribuer(random);
				if (dejaDistribue == false)
					{
					tabIndexCarte[i] = random;
					distributionJoueur1(random, i);
					}
				else
					{
					}
				} while(dejaDistribue == true);
			}

		//Rempli carte joueur 2
		for(int i = 12; i < 24; i++)
			{
			do
				{
				dejaDistribue = false;
				random = rand.nextInt(36);
				dejaDistribue = estDejaDistribuer(random);
				if (dejaDistribue == false)
					{
					tabIndexCarte[i] = random;
					distributionJoueur2(random, i - 12);
					}
				} while(dejaDistribue == true);
			}

		//Choix de l'atout
		do
			{
			dejaDistribue = false;
			random = rand.nextInt(36);
			dejaDistribue = estDejaDistribuer(random);
			if (dejaDistribue == false)
				{
				numeroAtout = random;
				}
			} while(dejaDistribue == true);
		}

	public static boolean estDejaDistribuer(int random)
		{
		for(int y = 0; y < tabIndexCarte.length; y++)
			{
			if (tabIndexCarte[y] == random) { return true; }
			}
		return false;
		}

	public static void distributionJoueur1(int alea, int pos)
		{
		tabCarteJoueur1[pos] = tabCartes[alea];
		}

	public static void distributionJoueur2(int alea, int pos)
		{
		tabCarteJoueur2[pos] = tabCartes[alea];
		}

	public void jouerCarte(Carte carteJouee)
		{
		// On passe une carte que le joueur choisi. Si c'est le dernier joueur ï¿½ poser une carte, on regarde qui gagne. (mï¿½thode private sï¿½parï¿½e)
		// Si c'est le premier, on bloque les cartes que le prochain joueur ne pourra pas jouer.

		}

	public int[] calculScore()
		{
		// Calcul des scores pour J1 et J2. Retourne un tableau avec les scores des deux joueurs.
		// Faire un get qui retourne le score avec la mÃ©thode de comptage

		return null;
		}

	//calcul des points
	private static int methodeComptage(int c1)
		{
		switch(c1)
			{
			case 4:
				return 10;
			case 5:
				return 2;
			case 6:
				return 3;
			case 7:
				return 4;
			case 8:
				return 11;
			default:
				return 0;
			}
		}

	public static int comptagePointsFinal(Carte[] cJoueur)
		{
		int somme = 0;
		for(int i = 0; i < 18; i++)
			{
			if (cJoueur[i].couleur() == tabCartes[numeroAtout].couleur())
				{
				//atout
				switch(cJoueur[i].valeur())
					{
					case 3:
						somme += 14;
						break;
					case 5:
						somme += 20;
						break;
					default: //comptage normal
						somme += methodeComptage(cJoueur[i].valeur());
						break;
					}
				}
			else
				{
				somme += methodeComptage(cJoueur[i].valeur());
				}
			}
		return somme;
		}

	private static int calculGagnantTour()
		{
		if (tabCartes[tabIndexCartePose[0]].couleur() == tabCartes[numeroAtout].couleur())
			{
			if (tabCartes[tabIndexCartePose[0]].couleur() != tabCartes[numeroAtout].couleur())
				{
				//Joueur 1 gagne !
				return 0;
				}
			else
				{
				//5 : valet, 3 : 9
				// les deux joueurs ont jouï¿½ atouts
				switch(tabCartes[tabIndexCartePose[0]].valeur())
					{
					case 5:
						//le joueur 1 gagne
						return 0;
					case 3:
						if (tabCartes[tabIndexCartePose[1]].valeur() == 5)
							{
							//le joueur 2 prend le 9 d'atout
							return 1;
							}
						else
							{
							//le 9 d'atout prend tout
							return 0;
							}
					default:
						if (tabCartes[tabIndexCartePose[1]].valeur() > tabCartes[tabIndexCartePose[0]].valeur())
							{
							// Le joueur 1 gagne
							return 0;
							}
						else
							{
							// Le joueur 2 gagne
							return 1;
							}
					}
				}
			}
		else
			{
			//Pas atout
			if (tabCartes[tabIndexCartePose[1]].couleur() == tabCartes[numeroAtout].couleur())
				{
				// joueur 2 gagne, il a jouer atout
				return 1;
				}
			else if (tabCartes[tabIndexCartePose[0]].couleur() == tabCartes[tabIndexCartePose[1]].couleur())
				{
				//le joueur a suivi la couleur
				if (tabCartes[tabIndexCartePose[0]].valeur() > tabCartes[tabIndexCartePose[1]].valeur())
					{
					//Le joueur 1 gagne
					return 0;
					}
				else
					{
					//Le joueur 2 gagne, il y une plus grande carte
					return 1;
					}
				}
			else
				{
				// le joueur 1 gagne, le deux a pas suivi la couleur
				return 0;
				}
			}
		}
	/*------------------------------*\
	|*			  Static			*|
	\*------------------------------*/

	// Mï¿½thode appelï¿½e selon choix du joueur
	public static Carte[] echangerTroisCartes(Carte[] tabCartesSources, Carte[] indexCartesARemplacer)
		{
		// Input : tableau 12 cartes, tableau de 3 int (index des cartes ï¿½ remplacer, max 8). Retourne un tableau de 9 cartes.
		//On passe le tableau de 12 carte, et le tableau des 3 carts a remplacÃ©.
		//on retourne un tableau de 12 ou de 9 ( A CHOISIR AVEC LE RESTE DES METHODE)
		//tabCartesSources 9,10 et 11 seront les carte remplacÃ©
		int a = 9;
		for(int i = 0; i < 9; i++)
			{
			for(int y = 0; y < 3; y++)
				{
				if (tabCartesSources[i] == indexCartesARemplacer[y])
					{
					Carte temp = tabCartesSources[i];
					tabCartesSources[i] = tabCartesSources[a];
					tabCartesSources[a] = temp;
					a++;
					}
				}
			}
		return tabCartesSources;
		}

	// Mï¿½thode appelï¿½e selon choix du joueur
	public static Carte[] echangerSixAtout(Carte[] jeu)
		{
		// Place la carte d'atout dans le jeu. Enleve le 6.
		for(int i = 0; i < 9; i++)
			{
			//Comparaison avec le deck du joueur concernant la couleur
			if (jeu[i].couleur() == tabCartes[numeroAtout].couleur())
				{
				if (jeu[i].valeur() == 6)
					{
					Carte temp = jeu[i];
					jeu[i] = tabCartes[numeroAtout];
					tabCartes[numeroAtout] = temp;
					}
				}
			/**
			if (carteJoueur2[i].couleur() == carte[numeroAtout].couleur())
			{
				if (carteJoueur2[i].valeur() == 6)
				{
					Carte temp = carteJoueur2[i];
					carteJoueur2[i] = carte[numeroAtout];
					carte[numeroAtout] = temp;
				}
			}
			 */
			}
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
		int k = 0; // indice pour les numbers
		for(int i = 0; i < 4; i++) // boucle pour les couleurs
			{
			for(int j = 0; j < 9; j++) // boucle pour les valeurs
				{
				Carte cartes = new Carte(k, i, j);
				tabCartes[k] = cartes;
				k++;
				}
			}
		}

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	// private ServerProgram serverProgram;

	/*------------------------------*\
	|*			  Static			*|
	\*------------------------------*/

	private static Carte[] tabCartes = new Carte[36];
	private static Carte[] tabCarteJoueur1 = new Carte[12];
	private static Carte[] tabCarteJoueur2 = new Carte[12];
	private static Carte[] tabCarteGagneJoueur1 = new Carte[18];
	private static Carte[] tabCarteGagneJoueur2 = new Carte[18];
	private static int[] tabIndexCarte = new int[24];
	private static int[] tabIndexCartePose = new int[2];
	private static int sommeJ1 = 0;
	private static int sommeJ2 = 0;
	private static int numeroAtout;

	}
