package it.polimi.ingsw.cg_38.controller.notifyEvent;

import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.controller.event.NotifyEventType;
import it.polimi.ingsw.cg_38.model.Player;

public class EventNotifyLoose extends NotifyEvent {

	public EventNotifyLoose(Player generator) {
		super(generator, true);
		super.setType(NotifyEventType.NOTLOOSE);
	}

	private static final long serialVersionUID = 1L;

}
