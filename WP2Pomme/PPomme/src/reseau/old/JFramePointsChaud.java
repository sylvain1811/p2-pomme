
package reseau.old;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

public class JFramePointsChaud extends JFrame
	{

	private JButton btnQuitter;
	private JButton btnCreate;
	private JButton btnJoin;

	private JTextField port;
	private JTextField ipAdversaire;

	public JFramePointsChaud()
		{
		geometrie();
		controle();
		apparence();
		}

	private void geometrie()
		{
		btnJoin = new JButton("Join partie");
		btnCreate = new JButton("Creer partie");
		port = new JTextField("5555");
		port.selectAll();
		ipAdversaire = new JTextField("127.0.0.1");
		ipAdversaire.selectAll();
		GridLayout layout = new GridLayout(0, 2);
		setLayout(layout);

		add(port);
		add(ipAdversaire);
		add(btnCreate);
		add(btnJoin);
		}

	private void controle()
		{
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		btnCreate.addActionListener(new ActionListener()
			{

			@Override
			public void actionPerformed(ActionEvent e)
				{
				startServer();
				}
			});
		btnJoin.addActionListener(new ActionListener()
			{

			@Override
			public void actionPerformed(ActionEvent e)
				{
				connectToServer();
				}
			});
		}

	private void startServer()
		{
		try
			{
			Server server = new Server(Integer.parseInt(port.getText()));
			Thread srv = new Thread(server);
			srv.start();
			}
		catch (Exception e)
			{
			System.out.print("Erreur");
			}

		}

	private void connectToServer()
		{
		Client client = new Client(ipAdversaire.getText(), Integer.parseInt(port.getText()));
		}

	private void apparence()
		{
		setSize(600, 400);
		setVisible(true);
		}
	}
