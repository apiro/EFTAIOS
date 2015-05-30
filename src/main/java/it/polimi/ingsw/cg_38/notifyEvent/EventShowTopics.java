package it.polimi.ingsw.cg_38.notifyEvent;

import java.util.ArrayList;

import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;

public class EventShowTopics extends NotifyEvent {

	public EventShowTopics(ArrayList<String> topics) {
		super(null, true);
		this.setTopics(topics);
	}

	private ArrayList<String> topics;
	
	public ArrayList<String> getTopics() {
		return topics;
	}
	public void setTopics(ArrayList<String> topics) {
		this.topics = topics;
	}

	private static final long serialVersionUID = 1L;

}
