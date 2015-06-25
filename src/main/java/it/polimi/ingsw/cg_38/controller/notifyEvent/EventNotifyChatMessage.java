package it.polimi.ingsw.cg_38.controller.notifyEvent;

import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.controller.event.NotifyEventType;
import it.polimi.ingsw.cg_38.model.Player;

public class EventNotifyChatMessage extends NotifyEvent {

	private static final long serialVersionUID = 1L;
	
	/** contiene il messaggio che il giocatore vuole inviare agli altri giocatori della partita */
	private String message;

	public EventNotifyChatMessage(Player generator, String message) {
		super(generator, true);
		this.setMessage(message);
		super.setType(NotifyEventType.CHAT);
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
