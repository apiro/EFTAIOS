package it.polimi.ingsw.cg_38.notifyEvent;

import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.model.Map;
import it.polimi.ingsw.cg_38.model.Player;

public class EventAddedToGame extends NotifyEvent {

	private Boolean added;
	/*private Map map;
	
	public Map getMap() {
		return map;
	}

	public void setMap(Map map) {
		this.map = map;
	}*/

	public EventAddedToGame(Player generator, Boolean added, Boolean broadcast) {
		super(generator, broadcast);
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
