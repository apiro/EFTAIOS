package it.polimi.ingsw.cg_38.client.notifyAction;

import it.polimi.ingsw.cg_38.client.PlayerClient;
import it.polimi.ingsw.cg_38.controller.event.GameEvent;
import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.controller.notifyEvent.EventRejectCardHuman;
import it.polimi.ingsw.cg_38.model.deck.ObjectCard;

/** rappresenta il rendimento dell'azione di scarto di una carta da parte di un giocatore umano */
public class RenderRejectHumanCard extends NotifyAction {

	private static final long serialVersionUID = 1L;
	
	/** contiene la carta  scartata */
	private ObjectCard card;

	/** invoca il costruttore della superclasse
	 * 
	 * @param evt evento di notifica che ha generato l'azione
	 */
	public RenderRejectHumanCard(NotifyEvent evt) {
		super(evt.getGenerator(), evt);
		this.card = ((EventRejectCardHuman)evt).getCard();
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
	 * @return null
	 */
	@Override
	public GameEvent render(PlayerClient client) {
		if(client.getPlayer().getName().equals(super.player.getName())) client.setPlayer(evt.getGenerator());
		client.getLogger().print(super.getPlayer().getName() + " has rejected a: " + card.getType().toString() + " card !");
		return null;
	}
}
