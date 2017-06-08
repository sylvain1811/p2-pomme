
package reseau.usekryonet.listener;

import javax.swing.JOptionPane;

import com.esotericsoftware.kryonet.Connection;

import cartes.Card;
import gui.JFrameHome;
import gui.jpanelingame.GameState;
import gui.jpanelingame.JPanelInGameServer;
import reseau.usekryonet.PacketMessage;
import reseau.usekryonet.ServerProgram;

public class CustomListenerServer extends CustomListener
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	public CustomListenerServer(ServerProgram serverProgram)
		{
		super();
		this.serverProgram = serverProgram;
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	@Override
	public void disconnected(Connection connection)
		{
		this.serverProgram.removeClient(connection);
		serverProgram.log("A client disconnected!");
		// TODO: gérer l'arrêt de la partie, retour au menu
		}

	@Override
	public void connected(Connection connection)
		{
		if (this.serverProgram.addClient(connection))
			{
			serverProgram.log("Client connected successfully");
			PacketMessage message = new PacketMessage(serverProgram.getPseudo(), "Welcome");
			connection.sendTCP(message);
			JFrameHome.getInstance().commencerPartie();
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

	@Override
	protected void traiterPaquet(PacketMessage paquet)
		{
		//serverProgram.log("Message received from " + paquet.getPseudoFrom() + " : " + paquet.getMessage());
		// Si le code est vaut 100 ou plus alors c'est une erruer.
		if (paquet.getCode() < PacketMessage.ERROR_SERVER_FULL)
			{
			serverProgram.log("New message from " + paquet.getPseudoFrom() + " : " + paquet.getMessage());
			//System.out.println("[" + paquet.getPseudoFrom() + " say ]: Message received from server : " + paquet.getMessage());
			switch(paquet.getCode())
				{
				case PacketMessage.SEND_PAQUET_CARD_CLIENT_TO_SERVER:
					traiterRecuperationTabCarte(paquet.getTabCarte());
					break;
				case PacketMessage.SEND_CARD_CLIENT_TO_SERVER:
					traiterEnvoieCarteClientToServeur(paquet.getCarte());
					break;
				case PacketMessage.SEND_STATE_CLIENT_TO_SERVER:
					traiterState(paquet.getState());
					break;
				case PacketMessage.END_OF_TURN:
					traiterStateFinDeTour(paquet.getState());
					break;
				case PacketMessage.CARD_PLAYED:
					traiterCartePose(paquet.getCarte());
					break;
				default:
					break;
				}
			}

		else
			{
			switch(paquet.getCode())
				{
				case PacketMessage.ERROR_SERVER_FULL:
					serverProgram.logErr("Serveur plein, impossible de se connecter");
					JOptionPane.showMessageDialog(null, "Serveur plein, impossible de se connecter.", "Serveur plein", JOptionPane.ERROR_MESSAGE);
					break;

				default:
					break;
				}
			}
		}

	private void traiterRecuperationTabCarte(Card[] tabCartes)
		{
		// TODO
		jPanelInGameServer = (JPanelInGameServer)(JFrameHome.getInstance().getjPanelInGame());
		jPanelInGameServer.setMAJCarteClient(tabCartes);
		}

	private void traiterEnvoieCarteClientToServeur(Card carte)
		{
		// TODO
		jPanelInGameServer = (JPanelInGameServer)(JFrameHome.getInstance().getjPanelInGame());
		jPanelInGameServer.setCartePoseParClient(carte);
		}

	private void traiterState(GameState state)
		{
		// TODO
		jPanelInGameServer = (JPanelInGameServer)(JFrameHome.getInstance().getjPanelInGame());
		jPanelInGameServer.setStateClientUpdate(state);
		}

	private void traiterCartePose(Card carte)
		{
		// TODO
		jPanelInGameServer = (JPanelInGameServer)(JFrameHome.getInstance().getjPanelInGame());
		jPanelInGameServer.setCarteAdverse(carte);
		}

	private void traiterStateFinDeTour(GameState state)
		{
		// TODO
		jPanelInGameServer = (JPanelInGameServer)(JFrameHome.getInstance().getjPanelInGame());
		jPanelInGameServer.setChangementTour(state);
		}
	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	private ServerProgram serverProgram;
	private JPanelInGameServer jPanelInGameServer;

	}
