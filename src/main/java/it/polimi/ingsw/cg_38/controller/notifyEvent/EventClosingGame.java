package it.polimi.ingsw.cg_38.controller.notifyEvent;

import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.model.Player;

public class EventClosingGame extends NotifyEvent {

	public EventClosingGame(Player generator, Boolean broadcast) {
		super(generator, broadcast);
		// TODO Auto-generated constructor stub
	}
	
	private static final long serialVersionUID = 1L;

}
