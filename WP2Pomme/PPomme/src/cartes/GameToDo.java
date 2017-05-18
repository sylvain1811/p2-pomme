
package cartes;

import java.util.Random;

public class GameToDo
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/
		static Carte[] carte = new Carte[36];
		static Carte[] carteJoueur1 = new Carte[12];
		static Carte[] carteJoueur2 = new Carte[12];
		static Carte[] carteGagneJoueur1 = new Carte[18];
		static Carte[] carteGagneJoueur2 = new Carte[18];
		static int[] tabcarte = new int[24];
		static int[] cartePose = new int[2];
		static int SommeJ1 = 0;
		static int SommeJ2 = 0;
		static int numeroAtout;

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
						tabcarte[i] = random;
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
						tabcarte[i] = random;
						distributionJoueur2(random, i - 12);
					}
					else
					{
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
				else
				{
				}
			} while(dejaDistribue == true);

		}

		public static boolean estDejaDistribuer(int random)
		{
			for(int y = 0; y < tabcarte.length; y++)
			{
				if (tabcarte[y] == random) { return true; }
			}
			return false;
		}

		public static void distributionJoueur1(int alea, int pos)
		{
			carteJoueur1[pos] = carte[alea];
		}

		public static void distributionJoueur2(int alea, int pos)
		{
			carteJoueur2[pos] = carte[alea];
		}

	public void jouerCarte(Carte carteJouee)
		{
		// On passe une carte que le joueur choisi. Si c'est le dernier joueur � poser une carte, on regarde qui gagne. (m�thode private s�par�e)
		// Si c'est le premier, on bloque les cartes que le prochain joueur ne pourra pas jouer.

		}

	public int[] calculScore()
		{
		// Calcul des scores pour J1 et J2. Retourne un tableau avec les scores des deux joueurs.
			// Faire un get qui retourne le score avec la méthode de comptage

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
				if (cJoueur[i].couleur() == carte[numeroAtout].couleur())
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
			if (carte[cartePose[0]].couleur() == carte[numeroAtout].couleur())
			{
				if (carte[cartePose[0]].couleur() != carte[numeroAtout].couleur())
				{
					//Joueur 1 gagne !
					return 0;
				}
				else
				{
					//5 : valet, 3 : 9
					// les deux joueurs ont jou� atouts
					switch(carte[cartePose[0]].valeur())
					{
						case 5:
							//le joueur 1 gagne
							return 0;
						case 3:
							if (carte[cartePose[1]].valeur() == 5)
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
							if (carte[cartePose[1]].valeur() > carte[cartePose[0]].valeur())
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
				if (carte[cartePose[1]].couleur() == carte[numeroAtout].couleur())
				{
					// joueur 2 gagne, il a jouer atout
					return 1;
				}
				else if (carte[cartePose[0]].couleur() == carte[cartePose[1]].couleur())
				{
					//le joueur a suivi la couleur
					if (carte[cartePose[0]].valeur() > carte[cartePose[1]].valeur())
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

	// M�thode appel�e selon choix du joueur
	public static Carte[] echangerTroisCartes(Carte[] tabCartesSources, Carte[] indexCartesARemplacer)
		{
		// Input : tableau 12 cartes, tableau de 3 int (index des cartes � remplacer, max 8). Retourne un tableau de 9 cartes.
			//On passe le tableau de 12 carte, et le tableau des 3 carts a remplacé.
			//on retourne un tableau de 12 ou de 9 ( A CHOISIR AVEC LE RESTE DES METHODE)
			//tabCartesSources 9,10 et 11 seront les carte remplacé
			int a = 9;
			for(int i=0; i<9;i++)
			{
				for(int y =0 ; y<3;y++) {
					if (tabCartesSources[i] == indexCartesARemplacer[y]) {
						Carte temp = tabCartesSources[i];
						tabCartesSources[i] = tabCartesSources[a];
						tabCartesSources[a] = temp;
						a++;
					}
				}
			}
		return tabCartesSources;
		}

	// M�thode appel�e selon choix du joueur
	public static Carte[] echangerSixAtout(Carte[] jeu)
		{
		// Place la carte d'atout dans le jeu. Enleve le 6.
			for(int i = 0; i < 9; i++)
			{
				//Comparaison avec le deck du joueur concernant la couleur
				if (jeu[i].couleur() == carte[numeroAtout].couleur())
				{
					if (jeu[i].valeur() == 6)
					{
						Carte temp = jeu[i];
						jeu[i] = carte[numeroAtout];
						carte[numeroAtout] = temp;
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
					carte[k] = cartes;
					k++;
					}
				}
		}

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	// private ServerProgram serverProgram;

	}
