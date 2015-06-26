package it.polimi.ingsw.cg_38.controller.gameEvent;

import it.polimi.ingsw.cg_38.controller.event.GameEvent;
import it.polimi.ingsw.cg_38.controller.event.GameEventType;
import it.polimi.ingsw.cg_38.model.Player;

/** rappresenta l'evento di settore scialuppa bloccato */
public class EventHatchBlocked extends GameEvent {

	private static final long serialVersionUID = 1L;
	
	/** invoca il costruttore e setta i dati
	 * 
	 * @param generator giocatore che ha generato l'evento
	 */
	public EventHatchBlocked(Player generator) {
		super(generator, true);
		super.setType(GameEventType.BLOCKHATCH);
	}
	
}
