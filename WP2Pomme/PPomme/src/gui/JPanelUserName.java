
package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.Random;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class JPanelUserName extends JPanel
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	public JPanelUserName()
		{
		geometry();
		control();
		appearance();
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	/**
	 * Retourne le pseudo entré dans le JTextField ou, s'il est vide, un pseudo aléatoire
	 */
	public String getPseudo()
		{
		if (!jTextFieldUserName.getText().trim().equals(""))
			{
			return jTextFieldUserName.getText().trim();
			}
		else
			{
			// Pseudo aléatoire
			return "user-" + random.nextInt(10000);
			}
		}

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
		this.labelUserName = new JLabel("Pseudo : ");
		this.jTextFieldUserName = new JTextField();
		// Layout : Specification
			{
			FlowLayout flowlayout = new FlowLayout(FlowLayout.CENTER);
			setLayout(flowlayout);

			// flowlayout.setHgap(20);
			// flowlayout.setVgap(20);
			}

		// JComponent : add
		add(labelUserName);
		add(jTextFieldUserName);
		}

	private void control()
		{
		// rien
		}

	private void appearance()
		{
		jTextFieldUserName.setPreferredSize(new Dimension(200, 30));
		this.setBackground(Color.WHITE);
		}

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	// Tools
	private static Random random = new Random();
	private JTextField jTextFieldUserName;
	private JLabel labelUserName;
	}
