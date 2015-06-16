package it.polimi.ingsw.cg_38.notifyEvent;

import it.polimi.ingsw.cg_38.controller.event.NotifyEventType;
import it.polimi.ingsw.cg_38.model.Player;

public class EventNotifyTeleport extends EventMoved {

	private static final long serialVersionUID = 1L;

	public EventNotifyTeleport(Player generator, String moved) {
		super(generator, moved);
		super.setBroadcast(false);
		super.setType(NotifyEventType.notTeleport);
	}
}
