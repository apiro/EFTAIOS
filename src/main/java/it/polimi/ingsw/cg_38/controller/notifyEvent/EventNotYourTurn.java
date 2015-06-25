package it.polimi.ingsw.cg_38.controller.notifyEvent;

import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.model.Player;

public class EventNotYourTurn extends NotifyEvent {

	public EventNotYourTurn(Player generator) {
		super(generator, false);
		
	}
	
	public String getMessage() {
		return message;
	}

	private static final long serialVersionUID = 1L;
	
	private String message = "This is not your turn !";
	
}
