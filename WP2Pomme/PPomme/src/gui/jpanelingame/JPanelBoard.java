package gui.jpanelingame;

import java.awt.GridLayout;

import javax.swing.JPanel;

import cartes.Carte;
import gui.JLabelCartes;

public class JPanelBoard extends JPanel
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	public JPanelBoard()
		{
		geometry();
		control();
		appearance();
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	/*------------------------------*\
	|*				Set				*|
	\*------------------------------*/

	/*------------------------------*\
	|*				Get				*|
	\*------------------------------*/

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	private void geometry()
		{
		// JComponent : Instanciation
		atout =  new JLabelCartes(carte);

		// Layout : Specification
			{
			gridlayout = new GridLayout(2,1);
			setLayout(gridlayout);

			// flowlayout.setHgap(20);
			// flowlayout.setVgap(20);
			}

		// JComponent : add

		}

	private void control()
		{
		// rien
		}

	private void appearance()
		{
		// rien
		}
	private void addCarteMyCard(Carte carte){
		myCard =  new JLabelCartes(carte);
	}
	private void addOpponentCard(Carte carte){
		opponentCard =  new JLabelCartes(carte);
		add(opponentCard);
	}
	private void addAtout(Carte carte){
		atout =  new JLabelCartes(carte);
		add(atout);
	}


	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	// Tools
	JLabelCartes opponentCard;
	JLabelCartes myCard;
	JLabelCartes atout;
	GridLayout gridlayout;
	}
