
package reseau.usekryonet.listener;

import javax.swing.JOptionPane;

import com.esotericsoftware.kryonet.Connection;

import cartes.Carte;
import gui.JFrameHome;
import gui.jpanelingame.GameState;
import gui.jpanelingame.JPanelInGameClient;
import reseau.usekryonet.ClientProgram;
import reseau.usekryonet.PacketMessage;

public class CustomListenerClient extends CustomListener
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	public CustomListenerClient(ClientProgram clientProgram)
		{
		super();
		this.clientProgram = clientProgram;
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	@Override
	public void disconnected(Connection connection)
		{
		// TODO : Gérer l'arrêt de la partie
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
		// Si le code est vaut 100 ou plus alors c'est une erruer.
		if (paquet.getCode() < PacketMessage.ERROR_SERVER_FULL)
			{
			clientProgram.log("New message from " + paquet.getPseudoFrom() + " : " + paquet.getMessage());
			//System.out.println("[" + paquet.getPseudoFrom() + " say ]: Message received from server : " + paquet.getMessage());
			switch(paquet.getCode())
				{
				case PacketMessage.START_GAME_DISTRIBUTION:
					traiterCasDistribution(paquet.getTabCarte());
					break;
				case PacketMessage.SEND_STATE_SERVER_TO_CLIENT:
					traiterState(paquet.getState());
					break;
				case PacketMessage.END_OF_TURN:
					traiterStateFinDeTour(paquet.getState());
					break;
				case PacketMessage.SEND_CARD_SERVER_TO_CLIENT:
					traiterEnvoieCarteServeurToClient(paquet.getCarte());
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
					clientProgram.logErr("Serveur plein, impossible de se connecter");
					JOptionPane.showMessageDialog(null, "Serveur plein, impossible de se connecter.", "Serveur plein", JOptionPane.ERROR_MESSAGE);
					break;

				default:
					break;
				}
			}
		}

	private void traiterEnvoieCarteServeurToClient(Carte carte)
		{
		jPanelInGameClient = (JPanelInGameClient)(JFrameHome.getInstance().getjPanelInGame());
		JPanelInGameClient.setCartePoseParServeur(carte);
		}

	private void traiterCasDistribution(Carte[] tabCartes)
		{
		// TODO Améliorer l'attente du jpanel
		jPanelInGameClient = (JPanelInGameClient)(JFrameHome.getInstance().getjPanelInGame());
		while(jPanelInGameClient == null)
			{
			try
				{
				Thread.sleep(100);
				jPanelInGameClient = (JPanelInGameClient)(JFrameHome.getInstance().getjPanelInGame());
				}
			catch (InterruptedException e)
				{
				e.printStackTrace();
				}
			}
		jPanelInGameClient.setCarteJoueurClient(tabCartes);
		}

	private void traiterState(GameState state)
		{
		// TODO
		jPanelInGameClient = (JPanelInGameClient)(JFrameHome.getInstance().getjPanelInGame());
		jPanelInGameClient.setStateUpdate(state);
		}

	private void traiterStateFinDeTour(GameState state)
		{
		// TODO
		jPanelInGameClient = (JPanelInGameClient)(JFrameHome.getInstance().getjPanelInGame());
		jPanelInGameClient.setChangementTour(state);
		}
	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	// Inputs
	private ClientProgram clientProgram;
	private JPanelInGameClient jPanelInGameClient;
	}
