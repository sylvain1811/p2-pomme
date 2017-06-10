
package gui.jpanelingame;

import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

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
		atout.setText("Atout");
		JPanel content = new JPanel();
		GridBagLayout gridBag = new GridBagLayout();
		setLayout(gridBag);

		BorderLayout borderLayout = new BorderLayout();
		borderLayout.setHgap(20);
		borderLayout.setVgap(20);
		content.setLayout(borderLayout);
		content.setBorder(new EmptyBorder(5, 50, 5, 0) );
		add(atout);
		content.add(opponentCard, BorderLayout.NORTH);
		content.add(myCard, BorderLayout.SOUTH);
		add(content);

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

	public JLabelCards getMyCard()
	{
		return myCard;
	}

	public JLabelCards getCardOpponent()
	{
		return opponentCard;
	}

	public void addAtout(Card carte)
		{
		atout.setCard(carte);
		this.repaint();
		this.revalidate();
		}
	public void clearCards(){
		opponentCard.clear();
		myCard.clear();
		this.repaint();
		this.revalidate();
	}
	public void clearAtout(){
		atout.clear();
		this.repaint();
		this.revalidate();
	}

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	// Tools
	private JLabelCards opponentCard = null;
	private JLabelCards myCard = null;
	private JLabelCards atout;
	private GridLayout gridlayout;
	}
