package it.polimi.ingsw.cg_38.controller.notifyEvent;

import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.controller.event.NotifyEventType;
import it.polimi.ingsw.cg_38.model.Player;

public class EventNotifyWin extends NotifyEvent {

	public EventNotifyWin(Player generator) {
		super(generator, true);
		super.setType(NotifyEventType.NOTWIN);
	}

	private static final long serialVersionUID = 1L;

}
