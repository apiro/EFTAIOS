package it.polimi.ingsw.cg_38.notifyEvent;

import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.controller.event.NotifyEventType;
import it.polimi.ingsw.cg_38.model.Player;

public class EventNotifyError extends NotifyEvent {

	public EventNotifyError(Player generator) {
		super(generator, false);
		this.setType(NotifyEventType.Error);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
