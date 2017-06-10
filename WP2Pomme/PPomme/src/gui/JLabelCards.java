
package gui;

import java.net.URL;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import cartes.Card;

public class JLabelCards extends JLabel
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/
	public JLabelCards()
		{
		}
	public void setCard(Card carte)
		{
		this.carte = carte;
		URL iconURL = getClass().getResource(carte.getImgPath());
		Icon picture = new ImageIcon(iconURL);
		setBorder(null);
		setIcon(picture);
		setHorizontalTextPosition(SwingConstants.CENTER);
		setVerticalTextPosition(SwingConstants.BOTTOM);
		setFont (getFont ().deriveFont (32.0f));
		}
	public void clear(){
		carte=null;
		setIcon(null);
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

