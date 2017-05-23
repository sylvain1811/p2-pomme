
package gui.jpanelingame;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import gui.JButtonCartes;

public class JPanelMyCard extends JPanel
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	public JPanelMyCard()
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
		troisCartes.setBorder(BorderFactory.createEmptyBorder());
		troisCartes.setContentAreaFilled(false);
		troisCartes.setText("test");
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

				}
			});
		ActionListener l = new ActionListener()
			{

			@Override
			public void actionPerformed(ActionEvent e)
				{
				// TODO Auto-generated method stub
				JButtonCartes carte = (JButtonCartes)e.getSource();
				ajoutCardPourEchange(carte);
				System.out.println(carte.getText());
				System.out.println("1 : " + tabCardChange[0]);
				System.out.println("2 : " + tabCardChange[1]);
				System.out.println("3 : " + tabCardChange[2]);

				}
			};
		for(int i = 0; i < 9; i++)
			{
			tabMyCard[i].addActionListener(l);
			tabMyCard[i].setBackground(Color.blue);
			}
		}

	private void appearance()
		{
		// rien
		}

	private void ajoutCardPourEchange(JButtonCartes carteAEchanger)
		{
		boolean aEchanger = false;
		int i = 0;
			{
			do
				{
				if (tabCardChange[i] == carteAEchanger) //Si deja dans la liste, on retire
					{
					System.out.println("test");
					tabCardChange[i] = null;
					carteAEchanger.setBackground(Color.GREEN);
					aEchanger = true;
					}
				i++;
				} while(i < 3 && aEchanger == false);
			System.out.println("coucou");
			// Si pas dans la liste, on ajoute si la liste est pas pleine
			if (aEchanger == false)
				{
				for(int y = 0; y < 3 && aEchanger == false; y++)
					{
					if (tabCardChange[y] == null)
						{
						tabCardChange[y] = carteAEchanger;
						carteAEchanger.setBackground(Color.BLACK);
						aEchanger = true;
						}
					}
				}
			}
		}

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	// Tools
	private JButtonCartes[] tabMyCard;
	private JButton troisCartes;
	private JButtonCartes[] tabCardChange;
	}
