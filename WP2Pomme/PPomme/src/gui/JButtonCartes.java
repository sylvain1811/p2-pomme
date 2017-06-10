
package gui;

import java.awt.Dimension;
import java.awt.Insets;
import java.net.URL;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import cartes.Card;

public class JButtonCartes extends JButton
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/
	//temporary const :

	public JButtonCartes(String text)
		{
		super();
		Dimension size = new Dimension(100, 100);
		setPreferredSize(size);
		setMinimumSize(size);
		setMaximumSize(size);
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	/*------------------------------*\
	|*				Set				*|
	\*------------------------------*/

	public void setCard(Card carte)
		{
		this.carte = carte;
		URL iconURL = getClass().getResource(carte.getImgPath());
		Icon picture = new ImageIcon(iconURL);
		setBorderPainted(false);
		setBorder(null);
		setMargin(new Insets(0, 0, 0, 0));
		setContentAreaFilled(false);
		setBorder(null);
		setIcon(picture);
		}

	/*------------------------------*\
	|*				Get				*|
	\*------------------------------*/

	public Card getCard()
		{
		return this.carte;
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/
	private Card carte;

	}
