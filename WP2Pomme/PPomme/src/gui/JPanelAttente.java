
package gui;

import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import reseau.usekryonet.ServerProgram;

public class JPanelAttente extends JPanel
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	public JPanelAttente()
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
		this.labelAttente = new JLabel("<html><div style=\"text-align:center;\"><br>Attente d'un joueur...<br><br>Adresse IP : " + ServerProgram.getIp() + "</div>");
		// Layout : Specification
			{
			FlowLayout flowLayout = new FlowLayout(FlowLayout.CENTER);
			setLayout(flowLayout);
			}

		// JComponent : add
		add(labelAttente);
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
	private JLabel labelAttente;
	}
