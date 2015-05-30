package it.polimi.ingsw.cg_38.notifyEvent;

import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.controller.event.NotifyEventType;
import it.polimi.ingsw.cg_38.model.Player;

public class EventCardUsed extends NotifyEvent {

	private static final long serialVersionUID = 1L;
	
	private Boolean performed;
	
	public EventCardUsed(Player generator, Boolean performed) {
		super(generator, false);
		super.setType(NotifyEventType.CardUsed);
		this.setPerformed(performed);
	}

	public Boolean getPerformed() {
		return performed;
	}

	public void setPerformed(Boolean performed) {
		this.performed = performed;
	}

}
