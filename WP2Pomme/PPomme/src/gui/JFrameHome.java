
package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;

import gui.jpanelingame.JPanelInGame;
import gui.jpanelingame.JPanelInGameClient;
import gui.jpanelingame.JPanelInGameServer;
import reseau.usekryonet.ClientProgram;
import reseau.usekryonet.ServerProgram;

public class JFrameHome extends JFrame
	{

	/**
	 * Launch the application. Generate with WindowBuilder.
	 */
	public static void main(String[] args)
		{
		EventQueue.invokeLater(new Runnable()
			{

			@Override
			public void run()
				{
				try
					{
					UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
					//UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					JFrameHome frame = JFrameHome.getInstance();
					frame.setVisible(true);
					}
				catch (Exception e)
					{
					e.printStackTrace();
					}
				}
			});
		}

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	private JFrameHome()
		{
		// Icon, title
		String imgPath = "pomme_logo.png";
		URL iconURL = getClass().getResource(imgPath);
		ImageIcon icon = new ImageIcon(iconURL);
		setIconImage(icon.getImage());
		setTitle("Jeu de la pomme");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);

		// Panel
		this.jPanelUserName = new JPanelUserName();

		jPanelContentPane = new JPanel();
		jPanelContentPane.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		setContentPane(jPanelContentPane);
		jPanelContentPane.setLayout(new BorderLayout(0, 0));

		jPanelMain = new JPanel();
		jPanelMain.setBackground(Color.WHITE);
		jPanelContentPane.add(jPanelMain);
		jPanelMain.setLayout(new BorderLayout(0, 0));

		lblJeuDeLa = new JLabel("Jeu de la pomme");
		lblJeuDeLa.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblJeuDeLa.setHorizontalAlignment(SwingConstants.CENTER);
		jPanelMain.add(lblJeuDeLa, BorderLayout.NORTH);

		jTabbedPaneCenter = new JTabbedPane(SwingConstants.LEFT);
		jTabbedPaneCenter.setBackground(Color.WHITE);
		//panelMain.add(tabbedPaneCenter, BorderLayout.CENTER);

		// Panel de démarrage du jeu, contient le champ pseudo et le tabbedPane pour lancer le jeu et srv ou client.
		this.jPanelStart = new JPanel();
		this.jPanelStart.setBackground(Color.WHITE);
		this.jPanelStart.setLayout(new BorderLayout());
		this.jPanelStart.add(jTabbedPaneCenter, BorderLayout.CENTER);
		this.jPanelStart.add(this.jPanelUserName, BorderLayout.SOUTH);
		jPanelMain.add(jPanelStart);

		jPanelTab1Srv = new JPanel();
		jPanelTab1Srv.setBackground(Color.WHITE);
		jTabbedPaneCenter.addTab("Créer partie", null, jPanelTab1Srv, null);

		btnCreerPartie = new JButton("Créer partie");
		jPanelTab1Srv.add(btnCreerPartie);

		jPanelTab2Client = new JPanel();
		jPanelTab2Client.setBackground(Color.WHITE);
		jTabbedPaneCenter.addTab("Rejoindre partie", null, jPanelTab2Client, null);
		jPanelTab2Client.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		jPanelInputRejoindre = new JPanel();
		jPanelTab2Client.add(jPanelInputRejoindre);
		jPanelInputRejoindre.setLayout(new GridLayout(0, 1, 0, 0));

		jPanelAddr = new JPanel();
		jPanelInputRejoindre.add(jPanelAddr);
		jPanelAddr.setBackground(Color.WHITE);
		jPanelAddr.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		lblAddrSrv = new JLabel("Adresse serveur :");
		jPanelAddr.add(lblAddrSrv);

		textFieldAddrSrv = new JTextField();
		textFieldAddrSrv.setText("127.0.0.1");
		textFieldAddrSrv.selectAll();
		jPanelAddr.add(textFieldAddrSrv);
		textFieldAddrSrv.setColumns(10);

		JPanel jPanelButtonRejoindre = new JPanel();
		jPanelInputRejoindre.add(jPanelButtonRejoindre);
		jPanelButtonRejoindre.setBackground(Color.WHITE);

		btnRejoindre = new JButton("Rejoindre");

		jPanelButtonRejoindre.add(btnRejoindre);

		jPanelTab3ClientDiscover = new JPanel();
		jPanelTab3ClientDiscover.setBackground(Color.WHITE);
		jTabbedPaneCenter.addTab("Rechercher serveur", null, jPanelTab3ClientDiscover, null);
		jPanelTab3ClientDiscover.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		jPanelDicover = new JPanel();
		jPanelTab3ClientDiscover.add(jPanelDicover);
		jPanelDicover.setLayout(new GridLayout(0, 1, 0, 0));

		jPanelButtonDiscover = new JPanel();
		jPanelButtonDiscover.setBackground(Color.WHITE);
		jPanelDicover.add(jPanelButtonDiscover);

		buttonRechercher = new JButton("Rechercher");

		jPanelButtonDiscover.add(buttonRechercher);

		/*------------------------------------------------------------------*\
		|*							Buttons listener						*|
		\*------------------------------------------------------------------*/

		// Bouton créer partie
		btnCreerPartie.addActionListener(new ActionListener()
			{

			@Override
			public void actionPerformed(ActionEvent arg0)
				{
				try
					{
					JFrameHome.this.server = new ServerProgram(JFrameHome.this.jPanelUserName.getPseudo());
					attendreClient();
					}
				catch (IOException e)
					{
					JOptionPane.showMessageDialog(JFrameHome.this, "Port indisponible", "Port indisponible", JOptionPane.ERROR_MESSAGE);
					//e.printStackTrace();
					}
				}

			});

		// Bouton rejoindre partie
		btnRejoindre.addActionListener(new ActionListener()
			{

			@Override
			public void actionPerformed(ActionEvent e)
				{
				String addresseServer = textFieldAddrSrv.getText();
				demarrerClient(addresseServer);
				JFrameHome.this.commencerPartie();
				}
			});

		// Rechercher partie
		buttonRechercher.addActionListener(new ActionListener()
			{

			@Override
			public void actionPerformed(ActionEvent arg0)
				{
				buttonRechercher.setText("Recherche en cours, patientez...");
				client = new ClientProgram(JFrameHome.this.jPanelUserName.getPseudo());
				JFrameHome.this.afficherServerDispo();
				}
			});
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	public void demarrerClient(String addresseServer)
		{
		client = new ClientProgram(JFrameHome.this.jPanelUserName.getPseudo(), addresseServer);
		}

	public void commencerPartie()
		{
		if (server != null)
			{
			this.jPanelMain.remove(jPanelAttente);
			this.jPanelInGame = new JPanelInGameServer(server);
			setTitle("server");
			}
		else
			{
			if (jTabbedPaneCenter != null)
				{
				this.jPanelMain.remove(jPanelStart);
				}
			if (jPanelListServer != null)
				{
				this.jPanelMain.remove(jPanelListServer);
				}
			this.jPanelInGame = new JPanelInGameClient(client);
			setTitle("client");
			}
		this.jPanelMain.add(this.jPanelInGame);
		this.setSize(1200, 800);
		revalidate();
		repaint();
		}

	/*------------------------------*\
	|*			  Static			*|
	\*------------------------------*/

	public static synchronized JFrameHome getInstance()
		{
		if (INSTANCE == null)
			{
			INSTANCE = new JFrameHome();
			}
		return INSTANCE;
		}

	/*------------------------------*\
	|*				Get				*|
	\*------------------------------*/

	public JPanelInGame getjPanelInGame()
		{
		return this.jPanelInGame;
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	private void afficherServerDispo()
		{
		this.jPanelMain.remove(jPanelStart);
		this.jPanelListServer = new JPanelListServer(client);
		this.jPanelMain.add(jPanelListServer);
		revalidate();
		repaint();
		}

	private void attendreClient()
		{
		this.jPanelMain.remove(jPanelStart);
		this.jPanelAttente = new JPanelAttente();
		this.jPanelMain.add(jPanelAttente);
		revalidate();
		repaint();
		}

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	// JPanel
	private JPanel jPanelContentPane;
	private JPanel jPanelMain;
	private JPanel jPanelTab1Srv;
	private JPanel jPanelTab2Client;
	private JPanel jPanelAddr;
	private JPanel jPanelInputRejoindre;
	private JPanel jPanelTab3ClientDiscover;
	private JPanel jPanelDicover;
	private JPanelUserName jPanelUserName;
	private JPanel jPanelStart;
	private JPanelInGame jPanelInGame;
	private JPanelAttente jPanelAttente;
	private JPanel jPanelButtonDiscover;
	private JPanelListServer jPanelListServer;
	private JTabbedPane jTabbedPaneCenter;

	// JLabel
	private JLabel lblJeuDeLa;
	private JLabel lblAddrSrv;
	private JTextField textFieldAddrSrv;

	//JButton
	private JButton btnCreerPartie;
	private JButton btnRejoindre;
	private JButton buttonRechercher;

	// Network
	private ClientProgram client;
	private ServerProgram server;

	/*------------------------------*\
	|*			  Static			*|
	\*------------------------------*/

	private static JFrameHome INSTANCE = null;

	}
