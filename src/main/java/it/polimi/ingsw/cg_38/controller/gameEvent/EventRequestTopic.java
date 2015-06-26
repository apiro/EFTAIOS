package it.polimi.ingsw.cg_38.controller.gameEvent;


import it.polimi.ingsw.cg_38.controller.event.GameEvent;
import it.polimi.ingsw.cg_38.model.Player;

/** rappresenta l'evento di qichiesta dei topic */
public class EventRequestTopic extends GameEvent {
	
	private static final long serialVersionUID = 1L;

	/** invoca il costruttore della superclasse
	 * 
	 * @param generator giocatore che ha generato l'evento
	 * @param notifyEventIsBroadcast true se l'evento è broadcast
	 */
	public EventRequestTopic(Player generator, Boolean notifyEventIsBroadcast) {
		super(generator, notifyEventIsBroadcast);
	}
	
}
