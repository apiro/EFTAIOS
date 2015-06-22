package it.polimi.ingsw.cg_38.controller.notifyEvent;

import java.util.ArrayList;

import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.model.Player;

public class EventNotifyTopics extends NotifyEvent {

	private ArrayList<String> topics;

	public EventNotifyTopics(Player generator, Boolean broadcast, ArrayList<String> topics) {
		super(generator, broadcast);
		this.setTopics(topics);
	}

	public ArrayList<String> getTopics() {
		return topics;
	}

	public void setTopics(ArrayList<String> topics) {
		this.topics = topics;
	}

	private static final long serialVersionUID = 1L;

}
