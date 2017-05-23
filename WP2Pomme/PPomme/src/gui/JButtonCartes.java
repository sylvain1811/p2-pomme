
package gui;

import java.awt.Dimension;

import javax.swing.JButton;

import cartes.Carte;

public class JButtonCartes extends JButton
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	/*public JButtonCartes(Carte carte)
		{
		super();
		this.carte = carte;
		Dimension size  = new Dimension(100,100);
		setPreferredSize(size);
		setMinimumSize(size);
		setMaximumSize(size);
		//setText(carte.getName());
		}*/

	//temporary const :
	public JButtonCartes(String text)
		{
		super();
		Dimension size = new Dimension(100, 100);
		setPreferredSize(size);
		setMinimumSize(size);
		setMaximumSize(size);
		setText(text);
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	/*------------------------------*\
	|*				Set				*|
	\*------------------------------*/

	public void setCarte(Carte carte)
		{
		this.carte = carte;
		}

	/*------------------------------*\
	|*				Get				*|
	\*------------------------------*/

	public Carte getCarte()
		{
		return this.carte;
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/
	private Carte carte;

	}
