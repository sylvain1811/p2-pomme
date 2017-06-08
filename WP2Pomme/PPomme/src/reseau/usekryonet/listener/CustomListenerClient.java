
package reseau.usekryonet.listener;

import javax.swing.JOptionPane;

import com.esotericsoftware.kryonet.Connection;

import cartes.Card;
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
				case PacketMessage.SEND_SCORE_SERVER_TO_CLIENT_CLIENT:
					traiterScoreClient(paquet.getScore());
					break;
				case PacketMessage.SEND_SCORE_SERVER_TO_CLIENT_SERVER:
					traiterScoreServeur(paquet.getScore());
					break;
				case PacketMessage.END_GAME:
					traiterFinJeu();
					break;
				case PacketMessage.SEND_CARD_ATOUT_SERVER_TO_CLIENT:
					traiterCarteAtout(paquet.getCarte());
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

	private void traiterFinJeu()
		{
		jPanelInGameClient = (JPanelInGameClient)(JFrameHome.getInstance().getjPanelInGame());
		JPanelInGameClient.setFinJeu();
		}

	private void traiterEnvoieCarteServeurToClient(Card carte)
		{
		jPanelInGameClient = (JPanelInGameClient)(JFrameHome.getInstance().getjPanelInGame());
		JPanelInGameClient.setCartePoseParServeur(carte);
		}

	private void traiterScoreClient(int score)
		{
		jPanelInGameClient = (JPanelInGameClient)(JFrameHome.getInstance().getjPanelInGame());
		JPanelInGameClient.setUpdateScoreClient(score);
		}

	private void traiterCarteAtout(Card carteAtout)
		{
		jPanelInGameClient = (JPanelInGameClient)(JFrameHome.getInstance().getjPanelInGame());
		JPanelInGameClient.setCarteAtout(carteAtout);
		}

	private void traiterScoreServeur(int score)
		{
		jPanelInGameClient = (JPanelInGameClient)(JFrameHome.getInstance().getjPanelInGame());
		JPanelInGameClient.setUpdateScoreServeur(score);
		}

	private void traiterCasDistribution(Card[] tabCartes)
		{
		// TODO
		jPanelInGameClient = (JPanelInGameClient)(JFrameHome.getInstance().getjPanelInGame());
		while(jPanelInGameClient == null)
			{
			try
				{
				Thread.sleep(100);
				}
			catch (InterruptedException e)
				{
				// TODO Auto-generated catch block
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

	// Input
	private ClientProgram clientProgram;
	private JPanelInGameClient jPanelInGameClient;
	}
