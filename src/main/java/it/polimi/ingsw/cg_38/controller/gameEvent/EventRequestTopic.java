package it.polimi.ingsw.cg_38.controller.gameEvent;


import it.polimi.ingsw.cg_38.controller.event.GameEvent;
import it.polimi.ingsw.cg_38.model.Player;

public class EventRequestTopic extends GameEvent {
	
	private static final long serialVersionUID = 1L;

	public EventRequestTopic(Player generator, Boolean notifyEventIsBroadcast) {
		super(generator, notifyEventIsBroadcast);
	}
	
}
