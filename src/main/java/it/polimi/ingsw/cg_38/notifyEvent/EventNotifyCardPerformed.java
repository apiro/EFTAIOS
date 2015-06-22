package it.polimi.ingsw.cg_38.notifyEvent;

import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.controller.event.NotifyEventType;
import it.polimi.ingsw.cg_38.model.Player;

public class EventNotifyCardPerformed extends NotifyEvent {

	private static final long serialVersionUID = 1L;

	public EventNotifyCardPerformed(Player generator) {
		super(generator, false);
		super.setType(NotifyEventType.cardPerformed);
	}
}
