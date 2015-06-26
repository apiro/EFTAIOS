package it.polimi.ingsw.cg_38.controller.notifyEvent;

import java.util.List;

import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.model.Player;

/** rappresenta l'evento di notifica dei topic */
public class EventNotifyTopics extends NotifyEvent {

	private static final long serialVersionUID = 1L;
	
	/** lista contente tutti i topic */
	private List<String> topics;

	/** invoca il costruttore della superclasse e setta i vari dati
	 * 
	 * @param generator giocatore che ha generato l'evento
	 * @param broadcast true se l'evento è broadcast
	 * @param topics lista dei topic presenti nel server
	 */
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



}
