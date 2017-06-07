
package gui.jpanelingame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import cartes.Carte;
import cartes.Game;
import reseau.usekryonet.PacketMessage;
import reseau.usekryonet.ServerProgram;

public class JPanelInGameServer extends JPanelInGame
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	public JPanelInGameServer(ServerProgram serverProgram)
		{
		super();
		this.serverProgram = serverProgram;
		state = GameState.DEMMARAGE;
		scoreServeur = 0;
		scoreClient = 0;
		listeCarteServeurGagne = new ArrayList<>();
		listeCarteClientGagne = new ArrayList<>();
		jeuFini = false;
		game = new Game();
		game.distribuer(); //Distribution des cartes
		envoiDistribution();
		state = GameState.ECHANGE;
		sendStateClient();
		changerAffichageBouton();
		controlBtnFinTour();
		sendCarteAtout(game.getTabCarte()[game.getNumeroAtout()]);
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	public void echangerTroisCartes(Carte[] indexCartesARemplacer)
		{
		Game.echangerTroisCartes(carteServeur, indexCartesARemplacer);
		changerAffichageBouton();
		jPanelMyCard.remiseAffichageApresEchangeTroisCartes();
		state = GameState.TOURSERVEUR;
		sendStateClient();
		tourServeurOuTourJoueur();
		}

	public void tourServeurOuTourJoueur()
		{
		if (state == GameState.TOURCLIENT)
			{
			for(int i = 0; i < jPanelMyCard.getTabMyCard().length; i++)
				{
				jPanelMyCard.getTabMyCard()[i].setEnabled(false);
				btnFinTour.setEnabled(false);
				}
			}
		else
			{
			for(int i = 0; i < jPanelMyCard.getTabMyCard().length; i++)
				{
				jPanelMyCard.getTabMyCard()[i].setEnabled(true);
				//btnFinTour.setEnabled(true);
				}
			}
		}

	public void sendStateClient()
		{
		PacketMessage paquet = new PacketMessage(serverProgram.getPseudo(), PacketMessage.SEND_STATE_SERVER_TO_CLIENT, state);
		serverProgram.envoiPaquet(paquet);
		}

	public void sendStateClientChangementTour()
		{
		PacketMessage paquet = new PacketMessage(serverProgram.getPseudo(), PacketMessage.SEND_STATE_SERVER_TO_CLIENT, state);
		serverProgram.envoiPaquet(paquet);
		tourServeurOuTourJoueur();
		}

	/*------------------------------*\
	|*				Set				*|
	\*------------------------------*/

	public void setMAJCarteClient(Carte[] tabCarte)
		{
		game.setTabCarteJoueur2(tabCarte);
		}

	public void setStateClientUpdate(GameState gameState)
		{
		stateClient = gameState;
		tourServeurOuTourJoueur();
		}

	public void changerAffichageBouton()
		{
		// TODO Auto-generated method stub
		carteServeur = game.getTabCarteJoueur1();
		for(int i = 0; i < 9; i++)
			{
			jPanelMyCard.getTabMyCard()[i].setCarte(carteServeur[i]);
			//jPanelMyCard.getTabMyCard()[0].setText(String.valueOf(carteServeur[0].getNumber()));
			//System.out.println(JFrameHome.getInstance().getjPanelInGame().getjPanelMyCard().getTabMyCard()[1].getText());
			jPanelMyCard.getTabMyCard()[i].setText(String.valueOf(carteServeur[i].getNumber()));
			}
		}

	public void setChangementTour(GameState stateRecup)
		{
		stateClient = stateRecup;
		ajusterStateClientVersServeur();
		tourServeurOuTourJoueur();
		}

	public void setCarteAdverse(Carte carte)
		{
		jPanelMyCard.setTabCarteSurPlateau(carte, 1);
		}

	public void setCartePoseParClient(Carte carteClient)
		{
		this.carteClient = carteClient;
		jPanelMyCard.setTabCarteSurPlateau(this.carteClient, 1);
		System.out.println("Carte reçu par le Client : " + this.carteClient.getNumber());
		if (jPanelMyCard.getTwoPlayerPlayed() == true)
			{
			calculQuiAGagne();
			}
		else
			{
			jPanelMyCard.setTwoPlayerPlayed(true);
			}
		}
	/*------------------------------*\
	|*				Get				*|
	\*------------------------------*/

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	private void controlBtnFinTour()
		{
		this.btnFinTour.addActionListener(new ActionListener()
			{

			@Override
			public void actionPerformed(ActionEvent e)
				{
				PacketMessage paquet = new PacketMessage(serverProgram.getPseudo(), PacketMessage.END_OF_TURN);
				serverProgram.envoiPaquet(paquet);
				//Si on le serveur est le deuxieme a jouer
				if (jPanelMyCard.getTwoPlayerPlayed() == true)
					{
					sendStateClientChangementTour(); //Envoie state du serveur au client et change l'affichage du serveur
					sendCarteToClient();
					calculQuiAGagne();
					}
				else
					{
					state = GameState.TOURCLIENT;
					stateClient = GameState.TOURCLIENT; // on force pour l'affichage
					sendStateClientChangementTour(); //Envoie state du serveur au client et change l'affichage du serveur
					sendCarteToClient();
					jPanelMyCard.setTwoPlayerPlayed(true);
					}
				}
			});
		}

	private void finDuJeu()
		{
		jeuFini = true;
		for(int i = 0; i < 9; i++)
			{
			if (jPanelMyCard.getTabMyCard()[i].isVisible() == true)
				{
				jeuFini = false;
				}
			}
		if (jeuFini == true)
			{
			//Le jeu est fini
			//Création des paquet de carte a envoyé a la méthode
			if (listeCarteClientGagne.size() != 0)
				{
				Carte[] carteGagneClient = new Carte[listeCarteClientGagne.size()];
				for(int i = 0; i < listeCarteClientGagne.size(); i++)
					{
					carteGagneClient[i] = listeCarteClientGagne.get(i);
					}
				scoreClient = game.comptagePointsFinal(carteGagneClient);
				}
			if (listeCarteServeurGagne.size() != 0)
				{
				Carte[] carteGagneServeur = new Carte[listeCarteServeurGagne.size()];
				for(int i = 0; i < listeCarteServeurGagne.size(); i++)
					{
					carteGagneServeur[i] = listeCarteServeurGagne.get(i);
					}
				scoreServeur = game.comptagePointsFinal(carteGagneServeur);
				}
			sendScoreToClient();
			sendFinDeJeu();
			finDeJeu();
			}
		}

	private void finDeJeu()
		{
		//Partie fin du jeu
		//Afficher fenetre avec score (les deux variable sont rempli, manque juste affichage
		}

	private void calculQuiAGagne()
		{
		game.setTabCartePose(jPanelMyCard.getTabCarteSurPlateau());
		gagnant = game.calculGagnantTour();

		if (gagnant == 0)
			{
			System.out.println("Serveur gagnant");
			listeCarteServeurGagne.add(jPanelMyCard.getTabCarteSurPlateau()[0]);
			listeCarteServeurGagne.add(jPanelMyCard.getTabCarteSurPlateau()[1]);
			jPanelMyCard.setTabCarteSurPlateau(null, 0); //Suppression des carte sur le plateau
			jPanelMyCard.setTabCarteSurPlateau(null, 1);
			jPanelMyCard.setTwoPlayerPlayed(false);
			state = GameState.TOURSERVEUR;
			stateClient = GameState.TOURSERVEUR; // on force pour l'affichage
			sendStateClientChangementTour(); //Envoie state du serveur au client et change l'affichage du serveur
			}
		else
			{
			System.out.println("Client gagnant");
			listeCarteClientGagne.add(jPanelMyCard.getTabCarteSurPlateau()[0]);
			listeCarteClientGagne.add(jPanelMyCard.getTabCarteSurPlateau()[1]);
			jPanelMyCard.setTabCarteSurPlateau(null, 0); //Suppression des carte sur le plateau
			jPanelMyCard.setTabCarteSurPlateau(null, 1);
			jPanelMyCard.setTwoPlayerPlayed(false);
			state = GameState.TOURCLIENT;
			stateClient = GameState.TOURCLIENT; // on force pour l'affichage
			sendStateClientChangementTour(); //Envoie state du serveur au client et change l'affichage du serveur
			}
		finDuJeu();
		}

	private void sendCarteToClient()
		{
		PacketMessage paquet = new PacketMessage(serverProgram.getPseudo(), PacketMessage.SEND_CARD_SERVER_TO_CLIENT, jPanelMyCard.getCartePose());
		System.out.println("Carte joué par le Serveur  : " + jPanelMyCard.getCartePose().getNumber());
		ajouterCartePourControle(jPanelMyCard.getCartePose());
		buttonDisparition(jPanelMyCard.getCartePose());
		serverProgram.envoiPaquet(paquet);
		}

	private void sendScoreToClient()
		{
		PacketMessage paquet = new PacketMessage(serverProgram.getPseudo(), PacketMessage.SEND_SCORE_SERVER_TO_CLIENT_CLIENT, scoreClient);
		serverProgram.envoiPaquet(paquet);
		PacketMessage paquet2 = new PacketMessage(serverProgram.getPseudo(), PacketMessage.SEND_SCORE_SERVER_TO_CLIENT_SERVER, scoreServeur);
		serverProgram.envoiPaquet(paquet2);
		}

	private void sendFinDeJeu()
		{
		PacketMessage paquet = new PacketMessage(serverProgram.getPseudo(), PacketMessage.FIN_JEU);
		serverProgram.envoiPaquet(paquet);
		}

	private void sendCarteAtout(Carte carteAtout)
		{
		PacketMessage paquet = new PacketMessage(serverProgram.getPseudo(), PacketMessage.SEND_CARD_ATOUT_SERVER_TO_CLIENT, carteAtout);
		serverProgram.envoiPaquet(paquet);
		}

	private void ajouterCartePourControle(Carte carteAControler)
		{
		jPanelMyCard.setTabCarteSurPlateau(carteAControler, 0);
		}

	private void buttonDisparition(Carte carte)
		{
		for(int i = 0; i < jPanelMyCard.getTabMyCard().length; i++)
			{
			if (jPanelMyCard.getTabMyCard()[i].getCarte() == carte)
				{
				jPanelMyCard.getTabMyCard()[i].setEnabled(false);
				jPanelMyCard.getTabMyCard()[i].setVisible(false);

				}
			}
		}

	private void envoiDistribution()
		{
		// TODO Auto-generated method stub
		PacketMessage paquet = new PacketMessage(serverProgram.getPseudo(), PacketMessage.START_GAME_DISTRIBUTION, game.getTabCarteJoueur2());
		serverProgram.envoiPaquet(paquet);
		}

	private void ajusterStateClientVersServeur()
		{
		state = stateClient;
		tourServeurOuTourJoueur();
		}

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	// Tools
	private ServerProgram serverProgram;
	private Game game;
	private Carte carteClient;
	private GameState stateClient;
	private Carte[] carteServeur;
	private List<Carte> listeCarteServeurGagne;
	private List<Carte> listeCarteClientGagne;
	private int gagnant;
	private boolean jeuFini;
	private int scoreServeur;
	private int scoreClient;
	}
