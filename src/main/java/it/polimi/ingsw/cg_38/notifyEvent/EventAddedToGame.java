package it.polimi.ingsw.cg_38.notifyEvent;

import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.model.Map;
import it.polimi.ingsw.cg_38.model.Player;

public class EventAddedToGame extends NotifyEvent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Boolean added;
	private Map map;
	
	public Map getMap() {
		return map;
	}

	public void setMap(Map map) {
		this.map = map;
	}

	public EventAddedToGame(Player generator, Boolean added, Boolean broadcast, Map map) {
		super(generator, broadcast);
		this.setMap(map);
		this.setAdded(added);
	}

	@Override
	public String toString() {
		return "EventAddedToGame [added=" + added + "]";
	}

	private void setAdded(Boolean added) {
		this.added = added;
	}

	public Boolean getAdded() {
		return added;
	}

}
