package it.polimi.ingsw.cg_38.client.notifyAction;

import it.polimi.ingsw.cg_38.client.PlayerClient;
import it.polimi.ingsw.cg_38.controller.event.GameEvent;
import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.controller.notifyEvent.EventCardUsed;

/** rappresenta il rendimento dell'azione di utilizzo di una carta oggetto */
public class RenderNoSideEffectCard extends NotifyAction {

	private static final long serialVersionUID = 1L;

	/** invoca il costruttore della superclasse
	 * 
	 * @param evt evento di notifica che ha generato l'azione
	 */
	public RenderNoSideEffectCard(NotifyEvent evt) {
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

	/** se il giocatore associagto al client è colui che ha utilizzato la carta
	 * vengono caricate le sue carte e viene settata la sua interfaccia.
	 * Infine il client viene avvertito dell'utilizzo della carta
	 * 
	 * @param client client sul quale performare l'azione
	 * @return null
	 */
	@Override
	public GameEvent render(PlayerClient client) {
		if(client.getPlayer().getName().equals(evt.getGenerator().getName())) {
			client.setPlayer(evt.getGenerator());
			client.updateCards();
			client.setIsInterfaceBlocked(true);
			client.setIsInterfaceBlocked(false);
		}
		client.getLogger().print(((EventCardUsed)this.getEvt()).getTypeCard().toString() +
				" card used by " + evt.getGenerator().getName() + " in sector: row: " +
				evt.getGenerator().getAvatar().getCurrentSector().getRow() + " col:" + 
				evt.getGenerator().getAvatar().getCurrentSector().getCol());
		return null;
	}
}
