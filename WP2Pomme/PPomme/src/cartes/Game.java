
package cartes;

import java.util.Random;

public class Game
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	public Game(/*ServerProgram serverProgram*/)
		{
		//this.serverProgram = serverProgram;
		this.init();
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	public void distribuer()
		{
		// RÃ©partir 24 cartes entre deux tableaux alÃ©atoirement. Les cartes doivent Ãªtre uniques. Garder une carte pour l'atout.
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

	public boolean estDejaDistribuer(int random)
		{
		for(int y = 0; y < tabIndexCarte.length; y++)
			{
			if (tabIndexCarte[y] == random) { return true; }
			}
		return false;
		}

	public void distributionJoueur1(int alea, int pos)
		{
		tabCarteJoueur1[pos] = tabCartes[alea];
		}

	public void distributionJoueur2(int alea, int pos)
		{
		tabCarteJoueur2[pos] = tabCartes[alea];
		}

	public void jouerCarte(Carte carteJouee)
		{
		// On passe une carte que le joueur choisi. Si c'est le dernier joueur Ã  poser une carte, on regarde qui gagne. (mÃ©thode private sÃ©parÃ©e)
		// Si c'est le premier, on bloque les cartes que le prochain joueur ne pourra pas jouer.

		}

	public int[] calculScore()
		{
		// Calcul des scores pour J1 et J2. Retourne un tableau avec les scores des deux joueurs.
		// Faire un get qui retourne le score avec la méthode de comptage

		return null;
		}

	//calcul des points
	private int methodeComptage(int c1)
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

	public int comptagePointsFinal(Carte[] cJoueur)
		{
		int somme = 0;
		System.out.println(cJoueur[0].getNumber());
		for(int i = 0; i < cJoueur.length-1; i++)
			{
			if (cJoueur[i].getCouleur() == tabCartes[numeroAtout].getCouleur())
				{
				//atout
				switch(cJoueur[i].getValeur())
					{
					case 3:
						somme += 14;
						break;
					case 5:
						somme += 20;
						break;
					default: //comptage normal
						somme += methodeComptage(cJoueur[i].getValeur());
						break;
					}
				}
			else
				{
				somme += methodeComptage(cJoueur[i].getValeur());
				}
			}
		return somme;
		}

	public int calculGagnantTour()
		{
		if (tabCartePose[0].getCouleur() == tabCartes[numeroAtout].getCouleur())
			{
			if (tabCartePose[1].getCouleur() != tabCartes[numeroAtout].getCouleur())
				{
				//Joueur 1 gagne !
				return 0;
				}
			else
				{
				//5 : valet, 3 : 9
				// les deux joueurs ont jouÃ© atouts
				switch(tabCartePose[0].getValeur())
					{
					case 5:
						//le joueur 1 gagne
						return 0;
					case 3:
						if (tabCartePose[1].getValeur() == 5)
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
						if (tabCartePose[1].getValeur() > tabCartePose[0].getValeur())
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
			if (tabCartePose[1].getCouleur() == tabCartes[numeroAtout].getCouleur())
				{
				// joueur 2 gagne, il a jouer atout
				return 1;
				}
			else if (tabCartePose[0].getCouleur() == tabCartePose[1].getCouleur())
				{
				//le joueur a suivi la couleur
				if (tabCartePose[0].getValeur() > tabCartePose[1].getValeur())
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

	// MÃ©thode appelÃ©e selon choix du joueur
	public static Carte[] echangerTroisCartes(Carte[] tabCartesSources, Carte[] indexCartesARemplacer)
		{
		// Input : tableau 12 cartes, tableau de 3 int (index des cartes Ã  remplacer, max 8). Retourne un tableau de 9 cartes.
		// On passe le tableau de 12 carte, et le tableau des 3 carts Ã  remplacer.
		// On retourne un tableau de 12 ou de 9 ( A CHOISIR AVEC LE RESTE DES METHODE)
		// tabCartesSources 9,10 et 11 seront les carte remplacÃ©es
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

	// MÃ©thode appelee selon choix du joueur
	public static Carte[] echangerSixAtout(Carte[] jeu, Carte atout)
		{
		// Place la carte d'atout dans le jeu. Enleve le 6.
		for(int i = 0; i < 9; i++)
			{
			//Comparaison avec le deck du joueur concernant la couleur
			if (jeu[i].getCouleur() == atout.getCouleur())
				{
				if (jeu[i].getValeur() == 6)
					{
					Carte temp = jeu[i];
					jeu[i] = atout;
					atout = temp;
					}
				}
			}
		return jeu;
		//Si le jeu a changer, on met le 6 en tant que atout sur le tas
		}

	/*------------------------------*\
	|*				Set				*|
	\*------------------------------*/

	// Set sur tous les attributs (pour l'instant)
	public void setTabCarteJoueur1(Carte[] tabCarteJoueur1)
		{
		this.tabCarteJoueur1 = tabCarteJoueur1;
		}

	public void setTabCarteJoueur2(Carte[] tabCarteJoueur2)
		{
		this.tabCarteJoueur2 = tabCarteJoueur2;
		}

	public void setTabCartePose(Carte[] carte)
		{
		this.tabCartePose = carte;
		}

	/*------------------------------*\
	|*				Get				*|
	\*------------------------------*/

	// Get sur tous les attributs (pour l'instant)

	public Carte[] getTabCarteJoueur1()
		{
		return this.tabCarteJoueur1;
		}

	public Carte[] getTabCarte()
		{
		return this.tabCartes;
		}

	public Carte[] getTabCarteJoueur2()
		{
		return this.tabCarteJoueur2;
		}

	public Carte[] getTabCartePose()
		{
		return this.tabCartePose;
		}

	public int getNumeroAtout()
		{
		return this.numeroAtout;
		}
	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	private void init()
		{
		tabCartePose[0] = null;
		tabCartePose[1] = null;
		// Initialiser les cartes;
		int k = 0; // indice pour les numbers
		for(int i = 0; i < 4; i++) // boucle pour les couleurs
			{
			for(int j = 0; j < 9; j++) // boucle pour les valeurs
				{
				Carte cartes = new Carte(k + 1, i, j);
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

	private Carte[] tabCartes = new Carte[36];
	private Carte[] tabCarteJoueur1 = new Carte[12];
	private Carte[] tabCarteJoueur2 = new Carte[12];
	private int[] tabIndexCarte = new int[24];
	private Carte[] tabCartePose = new Carte[2];
	private int numeroAtout;
	}
