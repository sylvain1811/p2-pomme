
package cartes;

import java.util.Random;

public class Game
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	private Game()
		{
		init();
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	public static Game getInstance()
		{
		if (Game.INSTANCE == null)
			{
			INSTANCE = new Game();
			}
		return INSTANCE;
		}

	/*------------------------------*\
	|*				Set				*|
	\*------------------------------*/

	/*------------------------------*\
	|*				Get				*|
	\*------------------------------*/

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/
	private static Game INSTANCE = null;

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

	//	public static void main(String[] args)
	//		{
	//		int k = 0; // indice pour les numbers
	//		for(int i = 0; i < 4; i++) // boucle pour les couleurs
	//			{
	//			for(int j = 0; j < 9; j++) // boucle pour les valeurs
	//				{
	//				Carte cartes = new Carte(k, i, j);
	//				carte[k] = cartes;
	//				k++;
	//				}
	//			}
	//
	//		distribution();
	//
	//		for(int i = 0; i < 12; i++)
	//			{
	//			System.out.println("Joueur 1  ::  number : " + carteJoueur1[i].number() + "  couleur : " + Carte.TABLE_COULEUR[carteJoueur1[i].couleur()] + "  Valeur : " + Carte.TABLE_VALEUR[carteJoueur1[i].valeur()]);
	//			}
	//		for(int i = 0; i < 12; i++)
	//			{
	//			System.out.println("Joueur 2  ::  number : " + carteJoueur2[i].number() + "  couleur : " + Carte.TABLE_COULEUR[carteJoueur2[i].couleur()] + "  Valeur : " + Carte.TABLE_VALEUR[carteJoueur2[i].valeur()]);
	//			}
	//		System.out.println("Atout  ::  number : " + carte[numeroAtout].number() + "  couleur : " + Carte.TABLE_COULEUR[carte[numeroAtout].couleur()] + "  Valeur : " + Carte.TABLE_VALEUR[carte[numeroAtout].valeur()]);
	//
	//		echangeSixAtout();
	//		cartePose[0] = 1; //Joueur qui pose en premier	(1)
	//		cartePose[1] = 2; //Joueur qui pose en deuxieme	(2)
	//
	//		//Calcul de qui a gagné la manche
	//		calculScoreManche();
	//
	//		//calcul des points
	//		SommeJ1 = comptagePointsFinal(carteGagneJoueur1);
	//		SommeJ1 = comptagePointsFinal(carteGagneJoueur2);
	//
	//		}

	private static void init()
		{
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

		distribution();
		/*
				for(int i = 0; i < 12; i++)
					{
					System.out.println("Joueur 1  ::  number : " + carteJoueur1[i].number() + "  couleur : " + Carte.TABLE_COULEUR[carteJoueur1[i].couleur()] + "  Valeur : " + Carte.TABLE_VALEUR[carteJoueur1[i].valeur()]);
					}
				for(int i = 0; i < 12; i++)
					{
					System.out.println("Joueur 2  ::  number : " + carteJoueur2[i].number() + "  couleur : " + Carte.TABLE_COULEUR[carteJoueur2[i].couleur()] + "  Valeur : " + Carte.TABLE_VALEUR[carteJoueur2[i].valeur()]);
					}
				System.out.println("Atout  ::  number : " + carte[numeroAtout].number() + "  couleur : " + Carte.TABLE_COULEUR[carte[numeroAtout].couleur()] + "  Valeur : " + Carte.TABLE_VALEUR[carte[numeroAtout].valeur()]);
		*/

		echangeSixAtout();

		cartePose[0] = 1; //Joueur qui pose en premier	(1)
		cartePose[1] = 2; //Joueur qui pose en deuxieme	(2)

		//Calcul de qui a gagné la manche
		calculGagnantTour();

		//calcul des points
		SommeJ1 = comptagePointsFinal(carteGagneJoueur1);
		SommeJ1 = comptagePointsFinal(carteGagneJoueur2);

		}

	//Calcul de qui prend le tour
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
				// les deux joueurs ont joué atouts
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

	public static void echangeSixAtout()
		{
		for(int i = 0; i < 9; i++)
			{
			//Comparaison avec le deck du joueur concernant la couleur
			if (carteJoueur1[i].couleur() == carte[numeroAtout].couleur())
				{
				if (carteJoueur1[i].valeur() == 6)
					{
					Carte temp = carteJoueur1[i];
					carteJoueur1[i] = carte[numeroAtout];
					carte[numeroAtout] = temp;
					}
				}
			if (carteJoueur2[i].couleur() == carte[numeroAtout].couleur())
				{
				if (carteJoueur2[i].valeur() == 6)
					{
					Carte temp = carteJoueur2[i];
					carteJoueur2[i] = carte[numeroAtout];
					carte[numeroAtout] = temp;
					}
				}
			}
		}

	public static void distribution()
		{
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

	//calcul des points
	public static int methodeComptage(int c1)
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
	}
