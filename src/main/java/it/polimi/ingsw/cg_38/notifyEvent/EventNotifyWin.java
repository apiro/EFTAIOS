package it.polimi.ingsw.cg_38.notifyEvent;

import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.controller.event.NotifyEventType;
import it.polimi.ingsw.cg_38.model.Player;

public class EventNotifyWin extends NotifyEvent {

	public EventNotifyWin(Player generator) {
		super(generator, true);
		super.setType(NotifyEventType.notWin);
	}

	private static final long serialVersionUID = 1L;

}
