
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
import javax.swing.border.BevelBorder;

import reseau.usekryonet.ClientProgram;
import reseau.usekryonet.ServerProgram;

public class JFrameHome extends JFrame
	{

	private JPanel contentPane;
	private JPanel panelMain;
	private JLabel lblJeuDeLa;
	private JTabbedPane tabbedPaneCenter;
	private JLabel lblNewLabel;
	private JTextField textFieldPortCreer;
	private JPanel panelTab1Srv;
	private JPanel panelTab2Client;
	private JLabel label;
	private JTextField textFieldPortSrv;
	private JButton btnCreerPartie;
	private JLabel lblAddrSrv;
	private JTextField textFieldAddrSrv;
	private JPanel panelPort;
	private JPanel panelAddr;
	private JButton btnRejoindre;

	/**
	 * Launch the application.
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
					JFrameHome frame = new JFrameHome();
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
	public JFrameHome()
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
		tabbedPaneCenter.addTab("Cr\u00E9er partie", null, panelTab1Srv, null);

		lblNewLabel = new JLabel("Port :");
		panelTab1Srv.add(lblNewLabel);

		textFieldPortCreer = new JTextField();
		textFieldPortCreer.setText("55555");
		textFieldPortCreer.selectAll();
		panelTab1Srv.add(textFieldPortCreer);
		textFieldPortCreer.setColumns(10);

		btnCreerPartie = new JButton("Cr\u00E9er partie");
		btnCreerPartie.addActionListener(new ActionListener()
			{

			@Override
			public void actionPerformed(ActionEvent arg0)
				{
				int port = Integer.getInteger(textFieldPortCreer.getText(), 60000);
				try
					{
					JFrameHome.this.server = new ServerProgram(port);
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

		JPanel panelInput = new JPanel();
		panelTab2Client.add(panelInput);
		panelInput.setLayout(new GridLayout(0, 1, 0, 0));

		panelPort = new JPanel();
		panelInput.add(panelPort);
		panelPort.setBackground(Color.WHITE);
		panelPort.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		label = new JLabel("Port :");
		panelPort.add(label);

		textFieldPortSrv = new JTextField();
		textFieldPortSrv.setText("55555");
		textFieldPortSrv.selectAll();
		panelPort.add(textFieldPortSrv);
		textFieldPortSrv.setColumns(10);

		panelAddr = new JPanel();
		panelInput.add(panelAddr);
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
		panelInput.add(panel);
		panel.setBackground(Color.WHITE);

		btnRejoindre = new JButton("Rejoindre");
		btnRejoindre.addActionListener(new ActionListener()
			{

			@Override
			public void actionPerformed(ActionEvent e)
				{
				String addresseServer = textFieldAddrSrv.getText();
				int port = Integer.getInteger(textFieldPortCreer.getText(), 60000);
				client = new ClientProgram(addresseServer, port);
				// JFrameHome.this.conte
				}
			});
		panel.add(btnRejoindre);
		}

	public void commencerPartie()
		{
		this.panelMain.remove(jPanelAttente);
		this.jPanelInGame = new JPanelInGame();
		this.add(this.jPanelInGame);
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
	}
