package it.polimi.ingsw.cg_38.controller.gameEvent;

import it.polimi.ingsw.cg_38.controller.event.GameEvent;
import it.polimi.ingsw.cg_38.controller.event.GameEventType;
import it.polimi.ingsw.cg_38.model.Player;

public class EventChat extends GameEvent {

	private static final long serialVersionUID = 1L;
	private String message;

	public EventChat(Player generator, String message) {
		super(generator, true);
		this.setMessage(message);
		super.setType(GameEventType.CHAT);
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
