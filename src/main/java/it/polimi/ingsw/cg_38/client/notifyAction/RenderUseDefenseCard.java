package it.polimi.ingsw.cg_38.client.notifyAction;

import it.polimi.ingsw.cg_38.client.PlayerClient;
import it.polimi.ingsw.cg_38.controller.event.GameEvent;
import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.controller.gameEvent.EventContinue;
import it.polimi.ingsw.cg_38.controller.notifyEvent.EventCardUsed;

/** rappresenta il rendimento dell'utilizzo della carta difesa */
public class RenderUseDefenseCard extends RenderNoSideEffectCard {

	private static final long serialVersionUID = 1L;

	/** invoca il costruttore della superclasse
	 * 
	 * @param evt evento di notifica che ha generato l'azione
	 */
	public RenderUseDefenseCard(NotifyEvent evt) {
		super(evt);
	}
	
	/** se il giocatore associato al client è lo stesso che ha generato l'azione vengono aggiornate
	 * le carte possedute. Inoltre viene comunicata che è stata utilizza la carta oggetto di tipo difesa
	 * 
	 * @param client client sul quale performare l'azione
	 * @return ritorna l'evento di gioco di continuare l'azione
	 * 
	 */
	@Override
	public GameEvent render(PlayerClient client) {
		if(client.getPlayer().getName().equals(evt.getGenerator().getName())) {
			client.setPlayer(evt.getGenerator());
			client.updateCards();
		}
		client.getLogger().print(((EventCardUsed)this.getEvt()).getTypeCard().toString() +
				"card used by " + evt.getGenerator().getName() + " in sector: row: " +
				evt.getGenerator().getAvatar().getCurrentSector().getRow() + " col:" + 
				evt.getGenerator().getAvatar().getCurrentSector().getCol());
		return new EventContinue();
	}
}
