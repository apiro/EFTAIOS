package it.polimi.ingsw.cg_38.controller.notifyEvent;

import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.controller.event.NotifyEventType;
import it.polimi.ingsw.cg_38.model.Player;

public class EventAddedToGame extends NotifyEvent {

	private static final long serialVersionUID = 1L;
	
	private Boolean added;
	private NotifyEventType type;
	
	@Override
	public NotifyEventType getType() {
		return type;
	}

	@Override
	public void setType(NotifyEventType type) {
		this.type = type;
	}
	
	public EventAddedToGame(Player generator, Boolean added, Boolean broadcast) {
		super(generator, broadcast);
		this.setAdded(added);
		this.setType(NotifyEventType.ADDED);
	}

	@Override
	public String toString() {
		return "EventAddedToGame [added=" + added + " player= " + super.getGenerator().getName() + "]";
	}

	private void setAdded(Boolean added) {
		this.added = added;
	}

	public Boolean getAdded() {
		return added;
	}

}
