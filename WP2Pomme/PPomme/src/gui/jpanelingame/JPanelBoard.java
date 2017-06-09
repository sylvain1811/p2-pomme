
package gui.jpanelingame;

import java.awt.BorderLayout;
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
		opponentCard = new JLabelCards();
		myCard = new JLabelCards();
		atout = new JLabelCards();
			{
			BorderLayout borderLayout = new BorderLayout();
			setLayout(borderLayout);
			// flowlayout.setHgap(20);
			// flowlayout.setVgap(20);
			}
		add(atout, BorderLayout.WEST);
		add(opponentCard, BorderLayout.NORTH);
		add(myCard, BorderLayout.SOUTH);
		}

	private void control()
		{
		// rien
		}

	private void appearance()
		{
		// rien
		}

	public void addMyCard(Card carte)
		{
		myCard.setCard(carte);
		this.repaint();
		this.revalidate();
		}

	public void addOpponentCard(Card carte)
		{
		opponentCard.setCard(carte);
		this.repaint();
		this.revalidate();
		}

	public void addAtout(Card carte)
		{
		atout.setCard(carte);
		this.repaint();
		this.revalidate();
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
