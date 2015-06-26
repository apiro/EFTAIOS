package it.polimi.ingsw.cg_38.controller.gameEvent;

import it.polimi.ingsw.cg_38.controller.event.GameEvent;
import it.polimi.ingsw.cg_38.controller.event.GameEventType;
import it.polimi.ingsw.cg_38.model.Player;

/** rappresenta l'evento di fine turno */
public class EventFinishTurn extends GameEvent {

	private static final long serialVersionUID = 1L;
	
	/** invoca il costruttore e setta i dati
	 * 
	 * @param generator giocatore che ha generato l'evento
	 */
	public EventFinishTurn(Player generator) {
		super(generator, true);
		super.setType(GameEventType.FINISHTURN);
	}

}
