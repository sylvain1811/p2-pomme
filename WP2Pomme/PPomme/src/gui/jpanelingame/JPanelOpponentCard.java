
package gui.jpanelingame;

import java.awt.FlowLayout;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class JPanelOpponentCard extends JPanel
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	public JPanelOpponentCard()
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
		tabOpponentCard = new JLabel[9];
		String imgPath = "/Dos.png";
		URL iconURL = getClass().getResource(imgPath);
		dosDeCarte = new ImageIcon(iconURL);
		imgPath = "/3carteB.png";
		iconURL = getClass().getResource(imgPath);
		troisCartesPic= new ImageIcon(iconURL);
		troisCarte = new JLabel(troisCartesPic);

		for(int i = 0; i < 9; i++)
			{
			tabOpponentCard[i] = new JLabel(dosDeCarte);
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
			add(tabOpponentCard[i]);
			}
		add(troisCarte);
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
	private JLabel[] tabOpponentCard;
	private ImageIcon dosDeCarte;
	private ImageIcon troisCartesPic;
	private JLabel troisCarte;
	}
