
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
		controlBtnFinTour();
		state = GameState.DEMMARAGE;
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	/*------------------------------*\
	|*				Set				*|
	\*------------------------------*/

	public void setCarteJoueurClient(Carte[] carteJoueurClient)
		{
		this.carteJoueurClient = carteJoueurClient;
		changerAffichageBouton();
		state = GameState.ECHANGE;
		}

	public void echangerTroisCartes(Carte[] indexCartesARemplacer)
		{
		Game.echangerTroisCartes(carteJoueurClient, indexCartesARemplacer);
		changerAffichageBouton();
		state = GameState.TOURSERVEUR;
		//Envoyer nouvelle carte serveur
		}

	public void setNewCardServeur()
		{

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
				PacketMessage paquet = new PacketMessage(clientProgram.getPseudo(), PacketMessage.END_OF_TURN);
				clientProgram.envoiPaquet(paquet);
				state= GameState.TOURSERVEUR;
				}
			});
		}

	private void changerAffichageBouton()
		{
		// TODO Auto-generated method stub
		for(int i = 0; i < 9; i++)
			{
			jPanelMyCard.getTabMyCard()[i].setCarte(carteJoueurClient[i]);
			//jPanelMyCard.getTabMyCard()[i].setText(Carte.TABLE_COULEUR[carteJoueurClient[i].getCouleur()] + "  Valeur : " + Carte.TABLE_VALEUR[carteJoueurClient[i].getValeur()]);
			jPanelMyCard.getTabMyCard()[i].setText(String.valueOf(carteJoueurClient[i].getNumber()));

			}
		}

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	// Tools
	private ClientProgram clientProgram;
	private Carte[] carteJoueurClient;
	}
