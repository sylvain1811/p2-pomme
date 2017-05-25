
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
		if (state == GameState.TOURSERVEUR)
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
				btnFinTour.setEnabled(true);
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

	/*------------------------------*\
	|*				Get				*|
	\*------------------------------*/

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	private void ajusterStateServeurVersClient()
		{
		state = stateServeur;
		tourServeurOuTourJoueur();
		}

	private void sendNewCardServeur()
		{
		PacketMessage paquet = new PacketMessage(clientProgram.getPseudo(), PacketMessage.SEND_CARD_CLIENT_TO_SERVER, carteJoueurClient);
		clientProgram.envoiPaquet(paquet);
		}

	private void controlBtnFinTour()
		{
		this.btnFinTour.addActionListener(new ActionListener()
			{

			@Override
			public void actionPerformed(ActionEvent e)
				{
				PacketMessage paquet = new PacketMessage(clientProgram.getPseudo(), PacketMessage.END_OF_TURN);
				clientProgram.envoiPaquet(paquet);
				state = GameState.TOURSERVEUR;
				stateServeur = GameState.TOURSERVEUR; // on force pour l'affichage
				sendStateServerChangementTour();
				}
			});
		}

	private void sendStateServerChangementTour()
		{
		PacketMessage paquet = new PacketMessage(clientProgram.getPseudo(), PacketMessage.CARD_PLAYED, jPanelMyCard.getTabCarteSurPlateau()[1]);
		clientProgram.envoiPaquet(paquet);
		PacketMessage paquet2 = new PacketMessage(clientProgram.getPseudo(), PacketMessage.SEND_STATE_CLIENT_TO_SERVER, state);
		clientProgram.envoiPaquet(paquet2);
		tourServeurOuTourJoueur();
		}

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	// Tools
	private ClientProgram clientProgram;
	private Carte[] carteJoueurClient;
	private GameState stateServeur = GameState.ECHANGE;
	}
