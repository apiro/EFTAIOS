package it.polimi.ingsw.cg_38.notifyEvent;

import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.controller.event.NotifyEventType;
import it.polimi.ingsw.cg_38.model.Player;

public class EventNotifyHumanWin extends NotifyEvent {

	private static final long serialVersionUID = 1L;
	private Boolean areThereOtherHumans;

	public EventNotifyHumanWin(Player generator, Boolean areThereOtherHumans) {
		super(generator, true);
		this.setAreThereOtherHumans(areThereOtherHumans);
		super.setType(NotifyEventType.humanWin);
	}

	public Boolean getAreThereOtherHumans() {
		return areThereOtherHumans;
	}

	public void setAreThereOtherHumans(Boolean areThereOtherHumans) {
		this.areThereOtherHumans = areThereOtherHumans;
	}
}
