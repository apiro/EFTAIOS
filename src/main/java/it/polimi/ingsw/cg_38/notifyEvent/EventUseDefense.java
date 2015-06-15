package it.polimi.ingsw.cg_38.notifyEvent;

import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.controller.event.NotifyEventType;
import it.polimi.ingsw.cg_38.model.Player;

public class EventUseDefense extends NotifyEvent {

	private static final long serialVersionUID = 1L;

	public EventUseDefense(Player generator, Boolean broadcast) {
		super(generator, broadcast);
		super.setType(NotifyEventType.useDefenseCard);
	}

}
