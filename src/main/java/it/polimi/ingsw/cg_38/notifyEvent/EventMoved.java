package it.polimi.ingsw.cg_38.notifyEvent;

import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.controller.event.NotifyEventType;
import it.polimi.ingsw.cg_38.model.Player;

public class EventMoved extends NotifyEvent {

	private String moved;
	
	public String getMoved() {
		return moved;
	}

	public void setMoved(String moved) {
		this.moved = moved;
	}

	public EventMoved(Player generator, String moved) {
		super(generator, false);
		super.setType(NotifyEventType.Moved);
		this.setMoved(moved);
	}

}
