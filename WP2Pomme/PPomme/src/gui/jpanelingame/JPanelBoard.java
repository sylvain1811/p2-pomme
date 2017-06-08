package gui.jpanelingame;

import java.awt.GridLayout;

import javax.swing.JPanel;

import cartes.Card;
import gui.JLabelCards;

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
	public void addMyCard(Card carte){
		myCard =  new JLabelCards(carte);
		add(myCard);
		this.repaint();
		this.revalidate();
	}
	public void addOpponentCard(Card carte){
		opponentCard =  new JLabelCards(carte);
		add(opponentCard);
		this.repaint();
		this.revalidate();
	}
	public void addAtout(Card carte){
		atout =  new JLabelCards(carte);
		add(atout);
	}

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	// Tools
	JLabelCards opponentCard;
	JLabelCards myCard;
	JLabelCards atout;
	GridLayout gridlayout;
	}
