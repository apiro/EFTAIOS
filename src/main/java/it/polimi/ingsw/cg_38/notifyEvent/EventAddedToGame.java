package it.polimi.ingsw.cg_38.notifyEvent;

import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.model.Player;

public class EventAddedToGame extends NotifyEvent {

	private Boolean added;
	
	public EventAddedToGame(Player generator, Boolean broadcast, Boolean added) {
		super(generator, broadcast);
		this.setAdded(added);
	}

	private void setAdded(Boolean added) {
		this.added = added;
	}

	public Boolean getAdded() {
		return added;
	}

}
