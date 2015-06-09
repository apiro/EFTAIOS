package it.polimi.ingsw.cg_38.notifyEvent;

import java.util.ArrayList;

import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.model.Player;

public class EventNotifyTopics extends NotifyEvent {

	private ArrayList<String> topics;

	public EventNotifyTopics(Player generator, Boolean broadcast, ArrayList<String> topics) {
		super(generator, broadcast);
		this.topics = topics;
	}

	private static final long serialVersionUID = 1L;

}
