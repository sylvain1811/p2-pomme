
package gui.jpanelingame;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import cartes.Card;
import gui.JButtonCartes;
import gui.JFrameHome;

public class JPanelMyCard extends JPanel
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	public JPanelMyCard(JPanelInGame jPanelInGame, JPanelBoard jPanelBoard)
		{
		this.jPanelInGame = jPanelInGame;
		this.jPanelBoard = jPanelBoard;
		jPanelInGame.btnEndTour.setEnabled(false);
		tabCardOnBoard = new Card[2];
		tabCardOnBoard[0] = null;
		tabCardOnBoard[1] = null;
		twoPlayerPlayed = false;
		jPanelBoard.myCard.setVisible(false);
		if (JFrameHome.getInstance().getServer() == null)
			{
			isServer = false;
			}
		else
			{
			isServer = true;
			}
		geometry();
		control();
		appearance();
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	public void resetDisplayAfterExchangeThreeCards()
		{
		for(int i = 0; i < 9; i++)
			{
			threeCards.setEnabled(false);
			}
		}
	/*------------------------------*\
	|*				Set				*|
	\*------------------------------*/

	public void setTabCardOnBoard(Card tabCardOnBoard, int index)
		{
		this.tabCardOnBoard[index] = tabCardOnBoard;
		}

	/*------------------------------*\
	|*				Get				*|
	\*------------------------------*/

	public JButtonCartes[] getTabMyCard()
		{
		return this.tabMyCard;
		}

	public JButtonCartes[] getTabCardChange()
		{
		return this.tabCardChange;
		}

	public Card getCardPosed()
		{
		return cardPosed;
		}

	public Card[] getTabCardOnBoard()
		{
		return this.tabCardOnBoard;
		}

	public void setTwoPlayerPlayed(boolean arePlayed)
		{
		this.twoPlayerPlayed = arePlayed;
		}

	public boolean getTwoPlayerPlayed()
		{
		return this.twoPlayerPlayed;
		}
	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	private void geometry()
		{
		// JComponent : Instanciation
		String imgPath = "/3carte.png";
		URL iconURL = getClass().getResource(imgPath);
		Icon icon = new ImageIcon(iconURL);
		threeCards = new JButton(icon);
		//troisCartes.setBorder(BorderFactory.createEmptyBorder());
		//troisCartes.setContentAreaFilled(false);
		threeCards.setEnabled(false);
		tabMyCard = new JButtonCartes[9];
		tabCardChange = new JButtonCartes[3];
		for(int i = 0; i < 9; i++)
			{
			tabMyCard[i] = new JButtonCartes("Carte");
			}
		// Layout : Specification
			{
			FlowLayout flowlayout = new FlowLayout(FlowLayout.CENTER);
			setLayout(flowlayout);

			// flowlayout.setHgap(20);
			// flowlayout.setVgap(20);
			}

		// JComponent : add
		for(int i = 0; i < 9; i++)
			{
			add(tabMyCard[i]);
			}
		add(threeCards);
		}

	private void control()
		{
		threeCards.addActionListener(new ActionListener()
			{

			@Override
			public void actionPerformed(ActionEvent e)
				{
				//appeler méthode de changement 3 carte
				Card[] cardToExchange = new Card[3];
				for(int i = 0; i < 3; i++)
					{
					cardToExchange[i] = tabCardChange[i].getCard();
					}
				if (JFrameHome.getInstance().getServer() == null)
					{
					((JPanelInGameClient)jPanelInGame).exchangeThreeCards(cardToExchange);
					}
				else
					{
					//cote serveur
					((JPanelInGameServer)jPanelInGame).exchangeThreeCards(cardToExchange);
					}
				jPanelBoard.myCard.setVisible(true);
				}
			});

		ActionListener actionListenerButtonCartes = new ActionListener()
			{

			@Override
			public void actionPerformed(ActionEvent e)
				{
				JButtonCartes card = (JButtonCartes)e.getSource();
				cardPosed = card.getCard();
				jPanelBoard.addMyCard(card.getCard());
				if (jPanelInGame.state == GameState.ECHANGE)
					{
					}
				else
					{
					jPanelInGame.btnEndTour.setEnabled(true);
					}
				if (jPanelInGame.getState() == GameState.ECHANGE)
					{
					addCardToExchange(card);
					}
				else if (jPanelInGame.getState() == GameState.TOURSERVER && isServer == true)
					{
					playCard(card);
					}
				else if (jPanelInGame.getState() == GameState.TOURCLIENT && isServer == false)
					{
					playCard(card);
					}
				}

			private void playCard(JButtonCartes card)
				{
				if (jPanelInGame.getState() == GameState.TOURCLIENT)
					{
					jPanelInGame.state = GameState.TOURSERVER;
					((JPanelInGameClient)jPanelInGame).sendStateClient();
					((JPanelInGameClient)jPanelInGame).changeDisplayButton();
					((JPanelInGameClient)jPanelInGame).tourServerOrTourPlayer();
					}

				else if (jPanelInGame.getState() == GameState.TOURSERVER)
					{
					((JPanelInGameServer)jPanelInGame).sendStateClient();
					((JPanelInGameServer)jPanelInGame).changeDisplayButton();
					((JPanelInGameServer)jPanelInGame).tourServerOrTourClient();
					}
				resetDisplay();
				}
			};
		for(int i = 0; i < 9; i++)
			{
			tabMyCard[i].addActionListener(actionListenerButtonCartes);
			}
		}

	private void resetDisplay()
		{
		this.repaint();
		this.revalidate();
		}

	private void appearance()
		{
		}

	private void addCardToExchange(JButtonCartes cardToExchange)
		{
		boolean toExchange = false;
		int i = 0;
			{
			do
				{
				if (tabCardChange[i] == cardToExchange) //Si deja dans la liste, on retire
					{
					tabCardChange[i] = null;
					cardToExchange.setBackground(Color.WHITE);
					cardToExchange.setBorder(BorderFactory.createLineBorder(Color.BLUE,0));
					toExchange = true;
					}
				i++;
				} while(i < 3 && toExchange == false);
			// Si pas dans la liste, on ajoute si la liste est pas pleine
			if (toExchange == false)
				{
				for(int y = 0; y < 3 && toExchange == false; y++)
					{
					if (tabCardChange[y] == null)
						{
						tabCardChange[y] = cardToExchange;
						cardToExchange.setBorderPainted(true);
						cardToExchange.setMargin(new Insets(5, 5, 5, 5));
						cardToExchange.setBorder(BorderFactory.createLineBorder(Color.BLUE,5));
						toExchange = true;
						}
					}
				}
			}
		boolean full = true;
		for(int a = 0; a < 3; a++)
			{
			if (tabCardChange[a] == null)
				{
				full = false;
				}
			}
		if (full == true)
			{
			threeCards.setEnabled(true);
			}
		else
			{
			threeCards.setEnabled(false);
			}
		}

	public JPanelBoard getJPanelBoard()
		{
		return this.jPanelBoard;
		}

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	// Tools
	private JButtonCartes[] tabMyCard;
	private JButton threeCards;
	private JButtonCartes[] tabCardChange;
	private JPanelInGame jPanelInGame;
	private JPanelBoard jPanelBoard;
	private Card[] tabCardOnBoard;
	private Card cardPosed; //On mettra la carte posé
	private boolean isServer;
	private boolean twoPlayerPlayed;

	}
