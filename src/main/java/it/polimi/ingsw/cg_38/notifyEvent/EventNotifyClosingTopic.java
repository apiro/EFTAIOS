package it.polimi.ingsw.cg_38.notifyEvent;

import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.model.Player;

public class EventNotifyClosingTopic extends NotifyEvent {

	private static final long serialVersionUID = 1L;

	public EventNotifyClosingTopic(Player generator) {
		super(generator, null);
	}
}
