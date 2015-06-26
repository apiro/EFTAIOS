package it.polimi.ingsw.cg_38.client.notifyAction;

import it.polimi.ingsw.cg_38.client.PlayerClient;
import it.polimi.ingsw.cg_38.controller.event.GameEvent;
import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;

/** rappresenta il rendimento della chiusura di una partita */
public class RenderClosingGame extends NotifyAction {

	private static final long serialVersionUID = 1L;

	/** invoca il costruttore della superclasse
	 * 
	 * @param evt evento di notifica che ha generato l'azione
	 */
	public RenderClosingGame(NotifyEvent evt) {
		super(evt.getGenerator(), evt);
	}

	/** è sempre possibile performare questo tipo di azione
	 * 
	 * @param client client che ha generato l'azione
	 * @return true
	 */
	@Override
	public Boolean isPossible(PlayerClient client) {
		return true;
	}

	/** notifica ai giocatori che il gioco + in chiusura e chiude il client
	 * 
	 * @param client client che ha generato l'azione
	 * @return null
	 */
	@Override
	public GameEvent render(PlayerClient client) {
		client.getLogger().print("Game closing in 30 secs ... ");
		/*client.setIsInterfaceBlocked(true);
		client.setClientAlive(false);*/
		try {
			Thread.sleep(30000);
		} catch (InterruptedException e) {
			client.getLogger().print("EXCEPTION IN SLEEPING THE THREAD !");
		}
		client.closeClient();
		return null;
	}
}
