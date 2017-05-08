
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

	private JPanel contentPane;
	private JPanel panelMain;
	private JLabel lblJeuDeLa;
	private JTabbedPane tabbedPaneCenter;
	private JLabel lblPortCreer;
	private JTextField textFieldPortCreer;
	private JPanel panelTab1Srv;
	private JPanel panelTab2Client;
	private JLabel label;
	private JTextField textFieldPortRejoindre;
	private JButton btnCreerPartie;
	private JLabel lblAddrSrv;
	private JTextField textFieldAddrSrv;
	private JPanel panelPortRejoindre;
	private JPanel panelAddr;
	private JButton btnRejoindre;
	private JPanel panelInputRejoindre;

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

	/**
	 * Create the frame.
	 */
	private JFrameHome()
		{
		String imgPath = "pomme_logo.png";
		URL iconURL = getClass().getResource(imgPath);
		ImageIcon icon = new ImageIcon(iconURL);
		setIconImage(icon.getImage());
		setTitle("Jeu de la pomme");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		panelMain = new JPanel();
		panelMain.setBackground(Color.WHITE);
		contentPane.add(panelMain);
		panelMain.setLayout(new BorderLayout(0, 0));

		lblJeuDeLa = new JLabel("Jeu de la pomme");
		lblJeuDeLa.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblJeuDeLa.setHorizontalAlignment(SwingConstants.CENTER);
		panelMain.add(lblJeuDeLa, BorderLayout.NORTH);

		tabbedPaneCenter = new JTabbedPane(SwingConstants.LEFT);
		panelMain.add(tabbedPaneCenter, BorderLayout.CENTER);

		panelTab1Srv = new JPanel();
		panelTab1Srv.setBackground(Color.WHITE);
		tabbedPaneCenter.addTab("Créer partie", null, panelTab1Srv, null);

		lblPortCreer = new JLabel("Port :");

		panelTab1Srv.add(lblPortCreer);

		textFieldPortCreer = new JTextField();
		textFieldPortCreer.setText("55555");
		textFieldPortCreer.selectAll();
		panelTab1Srv.add(textFieldPortCreer);
		textFieldPortCreer.setColumns(10);

		btnCreerPartie = new JButton("Créer partie");
		btnCreerPartie.addActionListener(new ActionListener()
			{

			@Override
			public void actionPerformed(ActionEvent arg0)
				{
				//int port = getPort(textFieldPortCreer);
				try
					{
					// JFrameHome.this.server = new ServerProgram(port);
					JFrameHome.this.server = new ServerProgram();

					attendreClient();
					}
				catch (IOException e)
					{
					JOptionPane.showMessageDialog(JFrameHome.this, "Port indisponible", "Port indisponible", JOptionPane.ERROR_MESSAGE);
					e.printStackTrace();
					}
				}

			});

		panelTab1Srv.add(btnCreerPartie);

		panelTab2Client = new JPanel();
		panelTab2Client.setBackground(Color.WHITE);
		tabbedPaneCenter.addTab("Rejoindre partie", null, panelTab2Client, null);
		panelTab2Client.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		panelInputRejoindre = new JPanel();
		panelTab2Client.add(panelInputRejoindre);
		panelInputRejoindre.setLayout(new GridLayout(0, 1, 0, 0));

		panelPortRejoindre = new JPanel();
		panelInputRejoindre.add(panelPortRejoindre);
		panelPortRejoindre.setBackground(Color.WHITE);
		panelPortRejoindre.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		label = new JLabel("Port :");
		panelPortRejoindre.add(label);

		textFieldPortRejoindre = new JTextField();
		textFieldPortRejoindre.setText("55555");
		textFieldPortRejoindre.selectAll();
		panelPortRejoindre.add(textFieldPortRejoindre);
		textFieldPortRejoindre.setColumns(10);

		panelAddr = new JPanel();
		panelInputRejoindre.add(panelAddr);
		panelAddr.setBackground(Color.WHITE);
		panelAddr.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		lblAddrSrv = new JLabel("Adresse serveur :");
		panelAddr.add(lblAddrSrv);

		textFieldAddrSrv = new JTextField();
		textFieldAddrSrv.setText("127.0.0.1");
		textFieldAddrSrv.selectAll();
		panelAddr.add(textFieldAddrSrv);
		textFieldAddrSrv.setColumns(10);

		JPanel panel = new JPanel();
		panelInputRejoindre.add(panel);
		panel.setBackground(Color.WHITE);

		btnRejoindre = new JButton("Rejoindre");
		btnRejoindre.addActionListener(new ActionListener()
			{

			@Override
			public void actionPerformed(ActionEvent e)
				{
				String addresseServer = textFieldAddrSrv.getText();

				//int port = getPort(textFieldPortRejoindre);

				//client = new ClientProgram(addresseServer, port);
				client = new ClientProgram(addresseServer);
				JFrameHome.this.commencerPartie();
				}
			});
		panel.add(btnRejoindre);

		panelTab3ClientDiscover = new JPanel();
		panelTab3ClientDiscover.setBackground(Color.WHITE);
		tabbedPaneCenter.addTab("Rechercher serveur", null, panelTab3ClientDiscover, null);
		panelTab3ClientDiscover.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		panelDicover = new JPanel();
		panelTab3ClientDiscover.add(panelDicover);
		panelDicover.setLayout(new GridLayout(0, 1, 0, 0));

		panelPortDiscover = new JPanel();
		panelPortDiscover.setBackground(Color.WHITE);
		panelDicover.add(panelPortDiscover);
		panelPortDiscover.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		labelPortRechercher = new JLabel("Port :");
		panelPortDiscover.add(labelPortRechercher);

		textFieldPortDiscover = new JTextField();
		textFieldPortDiscover.setText("55555");
		textFieldPortDiscover.setColumns(10);
		panelPortDiscover.add(textFieldPortDiscover);

		panelButtonDiscover = new JPanel();
		panelButtonDiscover.setBackground(Color.WHITE);
		panelDicover.add(panelButtonDiscover);

		buttonRechercher = new JButton("Rechercher");
		buttonRechercher.addActionListener(new ActionListener()
			{

			@Override
			public void actionPerformed(ActionEvent arg0)
				{
				// TODO Générer liste des serveurs
				client = new ClientProgram();
				JFrameHome.this.commencerPartie();
				}
			});

		panelButtonDiscover.add(buttonRechercher);

		// Port automatique
		disableManualPort();
		}

	private void disableManualPort()
		{
		panelTab1Srv.remove(lblPortCreer);
		panelTab1Srv.remove(textFieldPortCreer);
		panelInputRejoindre.remove(panelPortRejoindre);
		panelDicover.remove(panelPortDiscover);
		revalidate();
		//repaint();
		}

	private int getPort(JTextField field)
		{
		try
			{
			return Integer.parseInt(field.getText());
			}
		catch (NumberFormatException e)
			{
			e.printStackTrace();
			return ServerProgram.PORT_TCP;
			}

		}

	public static synchronized JFrameHome getInstance()
		{
		if (INSTANCE == null)
			{
			INSTANCE = new JFrameHome();
			}
		return INSTANCE;
		}

	public void commencerPartie()
		{
		if (server != null)
			{
			this.panelMain.remove(jPanelAttente);
			this.jPanelInGame = new JPanelInGameServer(server);
			}
		else
			{
			this.panelMain.remove(tabbedPaneCenter);
			this.jPanelInGame = new JPanelInGameClient(client);
			}
		this.panelMain.add(this.jPanelInGame);
		revalidate();


		//repaint();
		}

	private void attendreClient()
		{
		this.panelMain.remove(tabbedPaneCenter);
		this.jPanelAttente = new JPanelAttente();
		this.panelMain.add(jPanelAttente);
		revalidate();
		//repaint();
		}

	private ClientProgram client;
	private ServerProgram server;
	private JPanelInGame jPanelInGame;
	private JPanelAttente jPanelAttente;

	private static JFrameHome INSTANCE = null;
	private JPanel panelTab3ClientDiscover;
	private JPanel panelDicover;
	private JPanel panelPortDiscover;
	private JLabel labelPortRechercher;
	private JTextField textFieldPortDiscover;
	private JPanel panelButtonDiscover;
	private JButton buttonRechercher;
	}
