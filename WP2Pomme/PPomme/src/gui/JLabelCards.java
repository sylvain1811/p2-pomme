
package gui;

import java.awt.Dimension;

import javax.swing.JLabel;

import cartes.Card;

public class JLabelCards extends JLabel
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/
	public JLabelCards(Card carte)
		{
		super();
		this.carte = carte;
		Dimension size  = new Dimension(100,100);
		setPreferredSize(size);
		setMinimumSize(size);
		setMaximumSize(size);
		setText(""+carte.getNumber());
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

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/
	private Card carte;
	}

