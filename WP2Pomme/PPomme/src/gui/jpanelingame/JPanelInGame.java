
package gui.jpanelingame;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.Border;

import cartes.Card;
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
		this.btnEndTour = new JButton("Fin du tour");
		blackline = BorderFactory.createLineBorder(Color.black);
		jPanelBoard = new JPanelBoard();
		jPanelBoard.setBorder(blackline);
		jPanelMyCard = new JPanelMyCard(this, jPanelBoard);
		jPanelOpponentCard = new JPanelOpponentCard();

		// Layout : Specification
			{
			BorderLayout borderlayout = new BorderLayout();
			setLayout(borderlayout);

			// flowlayout.setHgap(20);
			// flowlayout.setVgap(20);
			}

		// JComponent : add
		add(this.btnEndTour, BorderLayout.EAST);
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
	public JPanelBoard getBoard()
		{
		return this.jPanelBoard;
		}

	public GameState getState()
		{
		return this.state;
		}

	public Card getCard()
		{
		return this.card;
		}

	// Tools
	protected GameState state;
	protected Card card;
	private Border blackline;
	protected JButton btnEndTour;
	protected JButtonCartes[] tabBtnCartes;
	protected JPanelBoard jPanelBoard;
	protected JPanelMyCard jPanelMyCard;
	private JPanelOpponentCard jPanelOpponentCard;
	}
