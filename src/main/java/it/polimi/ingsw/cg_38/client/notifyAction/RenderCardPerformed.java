package it.polimi.ingsw.cg_38.client.notifyAction;

import it.polimi.ingsw.cg_38.client.PlayerClient;
import it.polimi.ingsw.cg_38.controller.event.GameEvent;
import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.controller.gameEvent.EventContinue;

/** rappresneta il rendimento dell'utilizzo di una carta oggetto */ 
public class RenderCardPerformed extends NotifyAction {

	private static final long serialVersionUID = 1L;

	/** invoca il costruttore della superclasse
	 * 
	 * @param evt evento di notifica che ha generato l'azione
	 */
	public RenderCardPerformed(NotifyEvent evt) {
		super(evt.getGenerator(), evt);
	}

	/** verifica se è possibile performare l'azione
	 * 
	 * @param client client sul quale fare la verifica
	 * @return true se l'avatar associato al client è vivo ed è in gioco
	 */
	@Override
	public Boolean isPossible(PlayerClient client) {
		return super.check(client);
	}

	/** notifica al client che l'azione è stata performata
	 * 
	 * @param client client sul quale performare l'azione
	 * @return ritorna un evento di gioco di continuare l'azione
	 */
	@Override
	public GameEvent render(PlayerClient client) {
		client.getLogger().print("The card's effect has been performed !");
		client.setPlayer(evt.getGenerator());
		client.updateCards();
		return new EventContinue();
	}
}
