package it.polimi.ingsw.cg_38.controller.event;

import it.polimi.ingsw.cg_38.model.Player;

public class NotifyEvent extends Event {

	private static final long serialVersionUID = 1L;

	public NotifyEvent(Player generator, Boolean broadcast) {
		super(generator);
		this.setBroadcast(broadcast);
	}

	public Boolean isBroadcast() {
		return broadcast;
	}

	public void setBroadcast(Boolean broadcast) {
		this.broadcast = broadcast;
	}

	private Boolean broadcast;
	private NotifyEventType type;
	
	public NotifyEventType getType() {
		return this.type;
	}

	public void setType(NotifyEventType type) {
		this.type = type;
	}

}
