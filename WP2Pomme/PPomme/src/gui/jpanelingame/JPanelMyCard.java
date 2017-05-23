
package gui.jpanelingame;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import cartes.Carte;
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

	public JButtonCartes[] getTabMyCard()
		{
		return this.tabMyCard;
		}

	public JButtonCartes[] getTabCardChange()
		{
		return this.tabCardChange;
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	private void geometry()
		{
		// JComponent : Instanciation
		String imgPath = "../3carte.png";
		URL iconURL = getClass().getResource(imgPath);
		Icon icon = new ImageIcon(iconURL);
		troisCartes = new JButton(icon);
		//troisCartes.setBorder(BorderFactory.createEmptyBorder());
		//troisCartes.setContentAreaFilled(false);
		troisCartes.setEnabled(false);
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
			//tabMyCard[i].setText(String.valueOf(jPanelInG.getTabCarteJoueur1()[i].getNumber()));
			add(tabMyCard[i]);
			}
		add(troisCartes);
		}

	private void control()
		{
		troisCartes.addActionListener(new ActionListener()
			{

			@Override
			public void actionPerformed(ActionEvent e)
				{
				// TODO Auto-generated method stub
				//appeler méthode de changement 3 carte
				Carte[] carteAEchanger = new Carte[3];
				for(int i = 0; i < 3; i++)
					{
					carteAEchanger[i] = tabCardChange[i].getCarte();
					}
				if (JFrameHome.getInstance().getServer() == null)
					{
					((JPanelInGameClient)jPanelInGame).echangerTroisCartes(carteAEchanger);
					}
				else
					{
					//cote serveur
					((JPanelInGameServer)jPanelInGame).echangerTroisCartes(carteAEchanger);
					}
				}
			});

		ActionListener actionListenerButtonCartes = new ActionListener()
			{

			@Override
			public void actionPerformed(ActionEvent e)
				{
				JButtonCartes carte = (JButtonCartes)e.getSource();
				if (jPanelInGame.getState() == GameState.ECHANGE)
					{
						ajoutCardPourEchange(carte);
					}
				else
					{
					jouerCarte(carte);
					}
				}

			private void jouerCarte(JButtonCartes carte)
				{
				jPanelBoard.addMyCard(carte.getCarte());
				//ici il faut encore désactiver les boutons
				}
			};
		for(int i = 0; i < 9; i++)
			{
			tabMyCard[i].addActionListener(actionListenerButtonCartes);
			tabMyCard[i].setBackground(Color.WHITE);
			}
		}

	public void remiseAffichageApresEchangeTroisCartes()
	{
	for(int i = 0; i < 9; i++)
		{
		//On remet les couleur a bleu
		tabMyCard[i].setBackground(Color.WHITE);
		//On rend le bouton useless
		troisCartes.setEnabled(false);
		}
	}

	private void appearance()
		{
		// rien
		}

	private void ajoutCardPourEchange(JButtonCartes carteEchanger)
		{
			boolean aEchanger = false;
			int i = 0;
				{
				do
					{
					if (tabCardChange[i] == carteEchanger) //Si deja dans la liste, on retire
						{
						tabCardChange[i] = null;
						carteEchanger.setBackground(Color.WHITE);
						aEchanger = true;
						}
					i++;
					} while(i < 3 && aEchanger == false);
				// Si pas dans la liste, on ajoute si la liste est pas pleine
				if (aEchanger == false)
					{
					for(int y = 0; y < 3 && aEchanger == false; y++)
						{
						if (tabCardChange[y] == null)
							{
							tabCardChange[y] = carteEchanger;
							carteEchanger.setBackground(Color.GRAY);
							aEchanger = true;
							}
						}
					}
				}
			boolean toutRempli = true;
			for(int a = 0; a < 3; a++)
				{
				if (tabCardChange[a] == null)
					{
					toutRempli = false;
					}
				}
			if (toutRempli == true)
				{
				troisCartes.setEnabled(true);
				}
			else
				{
				troisCartes.setEnabled(false);
				}
		}

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	// Tools
	private JButtonCartes[] tabMyCard;
	private JButton troisCartes;
	private JButtonCartes[] tabCardChange;
	private JPanelInGame jPanelInGame;
	private JPanelBoard jPanelBoard;
	}
