package it.polimi.ingsw.cg_38.controller.notifyEvent;

import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.controller.event.NotifyEventType;
import it.polimi.ingsw.cg_38.model.Player;

public class EventClosingGame extends NotifyEvent {

	private Boolean areThereOtherHumans;

	public EventClosingGame(Player generator, Boolean areThereOtherHumans) {
		super(generator, true);
		this.setAreThereOtherHumans(areThereOtherHumans);
		super.setType(NotifyEventType.closingGame);
	}
	
	public Boolean getAreThereOtherHumans() {
		return areThereOtherHumans;
	}

	public void setAreThereOtherHumans(Boolean areThereOtherHumans) {
		this.areThereOtherHumans = areThereOtherHumans;
	}

	private static final long serialVersionUID = 1L;

}
