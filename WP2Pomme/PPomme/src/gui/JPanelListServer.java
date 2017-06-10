
package gui;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;

import reseau.usekryonet.ClientProgram;

public class JPanelListServer extends JPanel
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	public JPanelListServer(ClientProgram client)
		{
		this.client = client;
		this.listServer = client.getListServer();
		//System.out.println(listServer);
		this.tabServer = new InetAddress[listServer.size()];
		this.tabServer = listServer.toArray(tabServer);
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
		jList = new JList<InetAddress>(this.tabServer);
		this.btnConnect = new JButton("Se connecter");

		// Un seul élément séléctionné à la fois
		this.jList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		// Layout : Specification
			{
			FlowLayout flowlayout = new FlowLayout(FlowLayout.CENTER);
			setLayout(flowlayout);

			// flowlayout.setHgap(20);
			// flowlayout.setVgap(20);
			}

		// JComponent : add
		add(jList);
		add(btnConnect);
		}

	private void control()
		{
		if (listServer.isEmpty())
			{
			btnConnect.setEnabled(false);
			btnConnect.setText("Aucun serveur trouvé. Pensez à vérifier les paramètres du pare-feu.");
			}

		btnConnect.addActionListener(new ActionListener()
			{

			@Override
			public void actionPerformed(ActionEvent e)
				{
				System.out.println(jList.getSelectedValue().getHostAddress());
				String addresseServer = jList.getSelectedValue().toString().substring(1);
				if (client.connectToServer(addresseServer))
					{
					JFrameHome.getInstance().commencerPartie();
					}
				else
					{
					JOptionPane.showMessageDialog(null, "Aucun serveur disponible à cette adresse. Impossible de lancer une partie.", "Aucun server trouvé", JOptionPane.ERROR_MESSAGE);
					}
				}
			});

		}

	private void appearance()
		{
		setBackground(Color.WHITE);
		}

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	// Tools
	private JList<InetAddress> jList;
	private List<InetAddress> listServer;
	private InetAddress[] tabServer;
	private JButton btnConnect;
	private ClientProgram client;
	}
