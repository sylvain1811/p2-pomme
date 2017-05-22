
package gui.jpanelingame;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.Border;

import gui.JButtonCartes;

public abstract class JPanelInGame extends JPanel
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	public JPanelInGame()
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

	public JPanelMyCard getjPanelMyCard()
		{
		return this.jPanelMyCard;
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	private void geometry()
		{
		// JComponent : Instanciation
		this.btnFinTour = new JButton("Fin du tour");
		blackline = BorderFactory.createLineBorder(Color.black);
		jPanelBoard = new JPanelBoard();
		jPanelBoard.setBorder(blackline);
		jPanelMyCard = new JPanelMyCard();
		jPanelOpponentCard = new JPanelOpponentCard();

		// Layout : Specification
			{
			BorderLayout borderlayout = new BorderLayout();
			setLayout(borderlayout);

			// flowlayout.setHgap(20);
			// flowlayout.setVgap(20);
			}

		// JComponent : add
		add(this.btnFinTour, BorderLayout.EAST);
		add(jPanelBoard, BorderLayout.CENTER);
		add(jPanelOpponentCard, BorderLayout.NORTH);
		add(jPanelMyCard, BorderLayout.SOUTH);
		}

	private void control()
		{
		// rien
		}

	private void appearance()
		{
		setBackground(Color.WHITE);
		}

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	// Tools
	private Border blackline;
	protected JButton btnFinTour;
	protected JButtonCartes[] tabBtnCartes;
	private JPanelBoard jPanelBoard;
	private JPanelMyCard jPanelMyCard;
	private JPanelOpponentCard jPanelOpponentCard;
	}
