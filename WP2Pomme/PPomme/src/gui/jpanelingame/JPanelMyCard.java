
package gui.jpanelingame;

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
		tabMyCard = new JButtonCartes[9];
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
			//tabMyCard[i].setText(String.valueOf(jPanelInGameClient.getTabCarteJoueur1()[i].getNumber()));
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
		}

	private void appearance()
		{
		// rien
		}

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	// Tools
	private JButtonCartes[] tabMyCard;
	private JButton troisCartes;
	}
