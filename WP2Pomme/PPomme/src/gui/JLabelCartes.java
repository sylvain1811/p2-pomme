
package gui;

import java.awt.Dimension;

import javax.swing.JLabel;

import cartes.Carte;

public class JLabelCartes extends JLabel
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/
	public JLabelCartes(Carte carte)
		{
		super();
		this.carte = carte;
		Dimension size  = new Dimension(100,100);
		setPreferredSize(size);
		setMinimumSize(size);
		setMaximumSize(size);
		//setText(carte.getName());
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
	private Carte carte;
	}

