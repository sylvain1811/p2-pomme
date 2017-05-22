package gui.jpanelingame;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import gui.JButtonCartes;

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
		opponentCard =  new JLabel("Ta carte");
		opponentCard.setEnabled(false);
		myCard =  new JButtonCartes("Ma carte");
		myCard.setEnabled(false);


		// Layout : Specification
			{
			GridLayout gridlayout = new GridLayout(2,1);
			setLayout(gridlayout);

			// flowlayout.setHgap(20);
			// flowlayout.setVgap(20);
			}

		// JComponent : add
			add(opponentCard);
			add(myCard);

		}

	private void control()
		{
		// rien
		}

	private void appearance()
		{
		// rien
		}

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	// Tools
	JLabel opponentCard;
	JButtonCartes myCard;

	}
