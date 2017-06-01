
package gui.jpanelingame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import cartes.Carte;
import cartes.Game;
import reseau.usekryonet.ClientProgram;
import reseau.usekryonet.PacketMessage;

public class JPanelInGameClient extends JPanelInGame
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	public JPanelInGameClient(ClientProgram clientProgram)
		{
		super();
		this.clientProgram = clientProgram;
		state = GameState.DEMMARAGE;
		ajusterStateServeurVersClient();
		controlBtnFinTour();
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	public void tourServeurOuTourJoueur()
		{
		if (state == GameState.ECHANGE)
			{
			}
		else
			{
			if (stateServeur == GameState.TOURSERVEUR)
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
		}

	public void sendStateClient()
		{
		PacketMessage paquet = new PacketMessage(clientProgram.getPseudo(), PacketMessage.SEND_STATE_CLIENT_TO_SERVER, state);
		clientProgram.envoiPaquet(paquet);
		}

	public void changerAffichageBouton()
		{
		// TODO Auto-generated method stub
		for(int i = 0; i < 9; i++)
			{
			jPanelMyCard.getTabMyCard()[i].setCarte(carteJoueurClient[i]);
			//jPanelMyCard.getTabMyCard()[i].setText(Carte.TABLE_COULEUR[carteJoueurClient[i].getCouleur()] + "  Valeur : " + Carte.TABLE_VALEUR[carteJoueurClient[i].getValeur()]);
			jPanelMyCard.getTabMyCard()[i].setText(String.valueOf(carteJoueurClient[i].getNumber()));
			}
		}

	/*------------------------------*\
	|*				Set				*|
	\*------------------------------*/

	public void setCarteJoueurClient(Carte[] carteJoueurClient)
		{
		this.carteJoueurClient = carteJoueurClient;
		changerAffichageBouton();
		}

	public void echangerTroisCartes(Carte[] indexCartesARemplacer)
		{
		Game.echangerTroisCartes(carteJoueurClient, indexCartesARemplacer);
		changerAffichageBouton();
		jPanelMyCard.remiseAffichageApresEchangeTroisCartes();
		sendNewCardServeur();//Envoie des carte au serveur
		state = GameState.TOURSERVEUR;
		sendStateClient();
		tourServeurOuTourJoueur();
		}

	public void setStateUpdate(GameState stateRecup)
		{
		stateServeur = stateRecup;
		tourServeurOuTourJoueur();
		}

	public void setChangementTour(GameState stateRecup)
		{
		stateServeur = stateRecup;
		ajusterStateServeurVersClient();
		tourServeurOuTourJoueur();
		}

	public static void setCartePoseParServeur(Carte carte) //static OBLIGATOIRE !!
		{
		cartePoseParServeur = carte;
		System.out.println("Carte reçu par le Serveur : " + cartePoseParServeur.getNumber());
		//System.out.println("Client : " + cartePoseParServeur.getNumber()); //carte reçu du serveur
		//jPanelMyCard.setTabCarteSurPlateau(cartePoseParServeur, 0);
		//FINIR TRAITEMENT AVEC LA CARTE REçU (AFFICHER LA CARTE)
		//RESTE SENS INVERSE A FINIR
		}
	/*------------------------------*\
	|*				Get				*|
	\*------------------------------*/

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

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

	private void ajusterStateServeurVersClient()
		{
		state = stateServeur;
		tourServeurOuTourJoueur();
		}

	private void sendNewCardServeur()
		{
		PacketMessage paquet = new PacketMessage(clientProgram.getPseudo(), PacketMessage.SEND_PAQUET_CARD_CLIENT_TO_SERVER, carteJoueurClient);
		clientProgram.envoiPaquet(paquet);
		}

	private void controlBtnFinTour()
		{
		this.btnFinTour.addActionListener(new ActionListener()
			{

			@Override
			public void actionPerformed(ActionEvent e)
				{
				PacketMessage paquet = new PacketMessage(clientProgram.getPseudo(), PacketMessage.END_OF_TURN); //envoi paquet fin de tour
				clientProgram.envoiPaquet(paquet);
				state = GameState.TOURSERVEUR; //change le tour
				stateServeur = GameState.TOURSERVEUR; // on force pour l'affichage
				sendStateServerChangementTour(); //Envoie la carte qui a été joué (ENCORE A FINIR) et envoi l'état du client vers le serveur
				sendCarteToServer();
				}
			});
		}

	private void sendStateServerChangementTour()
		{
		PacketMessage paquet = new PacketMessage(clientProgram.getPseudo(), PacketMessage.CARD_PLAYED, jPanelMyCard.getTabCarteSurPlateau()[1]);
		//System.out.println(jPanelMyCard.getTabCarteSurPlateau()[1].getCouleur());
		clientProgram.envoiPaquet(paquet);
		PacketMessage paquet2 = new PacketMessage(clientProgram.getPseudo(), PacketMessage.SEND_STATE_CLIENT_TO_SERVER, state);
		clientProgram.envoiPaquet(paquet2);
		tourServeurOuTourJoueur();
		}

	private void sendCarteToServer()
		{
		PacketMessage paquet = new PacketMessage(clientProgram.getPseudo(), PacketMessage.SEND_CARD_CLIENT_TO_SERVER, jPanelMyCard.getCartePose());
		System.out.println("Carte joué par le Client  : " + jPanelMyCard.getCartePose().getNumber());
		buttonDisparition(jPanelMyCard.getCartePose());
		clientProgram.envoiPaquet(paquet);
		}

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	// Tools
	private ClientProgram clientProgram;
	private Carte[] carteJoueurClient;
	private static Carte cartePoseParServeur;
	private GameState stateServeur = GameState.ECHANGE;
	}
