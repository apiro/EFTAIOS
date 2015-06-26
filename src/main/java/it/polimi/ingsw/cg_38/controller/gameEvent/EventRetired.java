package it.polimi.ingsw.cg_38.controller.gameEvent;

import it.polimi.ingsw.cg_38.controller.event.GameEvent;
import it.polimi.ingsw.cg_38.controller.event.GameEventType;
import it.polimi.ingsw.cg_38.model.Player;

/** rappresenta l'evento di ritiro */
public class EventRetired extends GameEvent {

	private static final long serialVersionUID = 1L;

	/** invoca il costruttore della superclasse e setta i dati
	 * 
	 * @param generator giocatore che ha generato l'evento
	 */
	public EventRetired(Player generator) {
		super(generator, true);
		super.setType(GameEventType.RETIRED);
	}
}
