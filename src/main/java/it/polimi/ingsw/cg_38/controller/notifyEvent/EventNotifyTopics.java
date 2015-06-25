package it.polimi.ingsw.cg_38.controller.notifyEvent;

import java.util.List;

import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.model.Player;

public class EventNotifyTopics extends NotifyEvent {

	/** lista contente tutti i topic */
	private List<String> topics;

	public EventNotifyTopics(Player generator, Boolean broadcast, List<String> topics) {
		super(generator, broadcast);
		this.setTopics(topics);
	}

	public List<String> getTopics() {
		return topics;
	}

	public void setTopics(List<String> topics) {
		this.topics = topics;
	}

	private static final long serialVersionUID = 1L;

}
