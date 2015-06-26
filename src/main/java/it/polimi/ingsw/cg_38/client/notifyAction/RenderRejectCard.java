package it.polimi.ingsw.cg_38.client.notifyAction;

import it.polimi.ingsw.cg_38.client.PlayerClient;
import it.polimi.ingsw.cg_38.controller.event.GameEvent;
import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.controller.gameEvent.EventContinue;

/** rappresenta il rendimento dell'azione dello scarto di una carta oggetto */
public class RenderRejectCard extends NotifyAction {

	private static final long serialVersionUID = 1L;

	/** invoca il costruttore della superclasse
	 * 
	 * @param evt evento di notifica che ha generato l'azione
	 */
	public RenderRejectCard(NotifyEvent evt) {
		super(evt.getGenerator(), evt);
	}

	/** verifica se è possibile performare l'azione sul client
	 * 
	 * @param client client sul quale fare la verifica
	 * @return true se l'avatar associato al client è vivo ed è in gioco
	 */
	@Override
	public Boolean isPossible(PlayerClient client) {
		return super.check(client);
	}

	/** comunica al client che la carta è stata scartata
	 * 
	 * @param client client sul quale performare l'azione
	 * @return ritorna l'evento di continuare l'azione
	 */
	@Override
	public GameEvent render(PlayerClient client) {
		client.getLogger().print("Rejected card !");
		client.setPlayer(evt.getGenerator());
		client.updateCards();
		return new EventContinue();
	}
}
