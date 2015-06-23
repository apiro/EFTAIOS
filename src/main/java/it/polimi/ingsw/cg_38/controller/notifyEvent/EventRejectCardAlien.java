package it.polimi.ingsw.cg_38.controller.notifyEvent;

import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.controller.event.NotifyEventType;
import it.polimi.ingsw.cg_38.model.Player;

public class EventRejectCardAlien extends NotifyEvent {

	private static final long serialVersionUID = 1L;

	public EventRejectCardAlien(Player generator) {
		super(generator, false);
		super.setType(NotifyEventType.rejectCard);
	}
}