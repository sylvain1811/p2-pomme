
package cartes;

import java.util.Random;

public class Game
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	public Game()
		{
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
		boolean isDistributed = false;

		//Rempli carte player 1
		for(int i = 0; i < 12; i++)
			{
			do
				{
				isDistributed = false;
				random = rand.nextInt(36);
				isDistributed = isAlreadyDistributed(random);
				if (isDistributed == false)
					{
					tabIndexCards[i] = random;
					distributionPlayer1(random, i);
					}
				else
					{
					}
				} while(isDistributed == true);
			}

		//Rempli carte joueur 2
		for(int i = 12; i < 24; i++)
			{
			do
				{
				isDistributed = false;
				random = rand.nextInt(36);
				isDistributed = isAlreadyDistributed(random);
				if (isDistributed == false)
					{
					tabIndexCards[i] = random;
					distributionPlayer2(random, i - 12);
					}
				} while(isDistributed == true);
			}

		//Choix de l'atout
		do
			{
			isDistributed = false;
			random = rand.nextInt(36);
			isDistributed = isAlreadyDistributed(random);
			if (isDistributed == false)
				{
				numberAtout = random;
				}
			} while(isDistributed == true);
		}

	public boolean isAlreadyDistributed(int random)
		{
		for(int y = 0; y < tabIndexCards.length; y++)
			{
			if (tabIndexCards[y] == random) { return true; }
			}
		return false;
		}

	public void distributionPlayer1(int alea, int pos)
		{
		tabCardsPlayer1[pos] = tabCards[alea];
		}

	public void distributionPlayer2(int alea, int pos)
		{
		tabCardsPlayer2[pos] = tabCards[alea];
		}

	public void playCard(Card carteJouee)
		{
		// On passe une carte que le joueur choisi. Si c'est le dernier joueur ÃƒÂ  poser une carte, on regarde qui gagne. (mÃƒÂ©thode private sÃƒÂ©parÃƒÂ©e)
		// Si c'est le premier, on bloque les cartes que le prochain joueur ne pourra pas jouer.

		}

	public int[] calculScore()
		{
		// Calcul des scores pour J1 et J2. Retourne un tableau avec les scores des deux joueurs.
		// Faire un get qui retourne le score avec la mÃ©thode de comptage

		return null;
		}

	//calcul des points
	private int methodCounting(int c1)
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

	public int comptagePointsFinal(Card[] cJoueur)
		{
		int somme = 0;
		for(int i = 0; i < cJoueur.length-1; i++)
			{
			if (cJoueur[i].getColor() == tabCards[numberAtout].getColor())
				{
				//atout
				switch(cJoueur[i].getValue())
					{
					case 3:
						somme += 14;
						break;
					case 5:
						somme += 20;
						break;
					default: //comptage normal
						somme += methodCounting(cJoueur[i].getValue());
						break;
					}
				}
			else
				{
				somme += methodCounting(cJoueur[i].getValue());
				}
			}
		return somme;
		}

	public int calculGagnantTour()
		{
		if (tabCardsPose[0].getColor() == tabCards[numberAtout].getColor())
			{
			if (tabCardsPose[1].getColor() != tabCards[numberAtout].getColor())
				{
				//Joueur 1 gagne !
				return 0;
				}
			else
				{
				//5 : valet, 3 : 9
				// les deux joueurs ont joué atouts
				switch(tabCardsPose[0].getValue())
					{
					case 5:
						//le joueur 1 gagne
						return 0;
					case 3:
						if (tabCardsPose[1].getValue() == 5)
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
						if (tabCardsPose[1].getValue() > tabCardsPose[0].getValue())
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
			if (tabCardsPose[1].getColor() == tabCards[numberAtout].getColor())
				{
				// joueur 2 gagne, il a jouer atout
				return 1;
				}
			else if (tabCardsPose[0].getColor() == tabCardsPose[1].getColor())
				{
				//le joueur a suivi la couleur
				if (tabCardsPose[0].getValue() > tabCardsPose[1].getValue())
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

	// Méthode appelée selon choix du joueur
	public static Card[] echangerTroisCartes(Card[] tabCardsSources, Card[] indexCardsToReplace)
		{
		// Input : tableau 12 cartes, tableau de 3 int (index des cartes ete  remplacer, max 8). Retourne un tableau de 9 cartes.
		// On passe le tableau de 12 carte, et le tableau des 3 carts été remplacer.
		// On retourne un tableau de 12 ou de 9 ( A CHOISIR AVEC LE RESTE DES METHODE)
		// tabCartesSources 9,10 et 11 seront les carte remplacées
		int a = 9;
		for(int i = 0; i < 9; i++)
			{
			for(int y = 0; y < 3; y++)
				{
				if (tabCardsSources[i] == indexCardsToReplace[y])
					{
					Card temp = tabCardsSources[i];
					tabCardsSources[i] = tabCardsSources[a];
					tabCardsSources[a] = temp;
					a++;
					}
				}
			}
		return tabCardsSources;
		}

	// Methode appelee selon choix du joueur
	public static Card[] exchangeSixAtout(Card[] jeu, Card atout)
		{
		// Place la carte d'atout dans le jeu. Enleve le 6.
		for(int i = 0; i < 9; i++)
			{
			//Comparaison avec le deck du joueur concernant la couleur
			if (jeu[i].getColor() == atout.getColor())
				{
				if (jeu[i].getValue() == 6)
					{
					Card temp = jeu[i];
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
	public void setTabCarteJoueur1(Card[] tabCarteJoueur1)
		{
		this.tabCardsPlayer1 = tabCarteJoueur1;
		}

	public void setTabCarteJoueur2(Card[] tabCarteJoueur2)
		{
		this.tabCardsPlayer2 = tabCarteJoueur2;
		}

	public void setTabCartePose(Card[] carte)
		{
		this.tabCardsPose = carte;
		}

	/*------------------------------*\
	|*				Get				*|
	\*------------------------------*/

	// Get sur tous les attributs (pour l'instant)

	public Card[] getTabCarteJoueur1()
		{
		return this.tabCardsPlayer1;
		}

	public Card[] getTabCarte()
		{
		return this.tabCards;
		}

	public Card[] getTabCarteJoueur2()
		{
		return this.tabCardsPlayer2;
		}

	public Card[] getTabCartePose()
		{
		return this.tabCardsPose;
		}

	public int getNumeroAtout()
		{
		return this.numberAtout;
		}
	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	private void init()
		{
		tabCardsPose[0] = null;
		tabCardsPose[1] = null;
		// Initialiser les cartes;
		int k = 0; // indice pour les numbers
		for(int i = 0; i < 4; i++) // boucle pour les couleurs
			{
			for(int j = 0; j < 9; j++) // boucle pour les valeurs
				{
				Card cards = new Card(k + 1, i, j);
				tabCards[k] = cards;
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

	private Card[] tabCards = new Card[36];
	private Card[] tabCardsPlayer1 = new Card[12];
	private Card[] tabCardsPlayer2 = new Card[12];
	private int[] tabIndexCards = new int[24];
	private Card[] tabCardsPose = new Card[2];
	private int numberAtout;
	}
