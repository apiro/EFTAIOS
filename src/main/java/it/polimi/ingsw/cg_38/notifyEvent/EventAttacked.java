package it.polimi.ingsw.cg_38.notifyEvent;

import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.controller.event.NotifyEventType;
import it.polimi.ingsw.cg_38.model.Player;

public class EventAttacked extends NotifyEvent {

	private static final long serialVersionUID = 1L;
	
	private Boolean areYouPowered;
	
	public Boolean getAreYouPowered() {
		return areYouPowered;
	}

	public void setAreYouPowered(Boolean areYouPowered) {
		this.areYouPowered = areYouPowered;
	}

	public EventAttacked(Player generator) {
		super(generator, false);
		super.setType(NotifyEventType.Attacked);
		this.setAreYouPowered(generator.getAvatar().getIsPowered());
	}
}
