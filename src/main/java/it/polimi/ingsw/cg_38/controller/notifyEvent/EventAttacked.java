package it.polimi.ingsw.cg_38.controller.notifyEvent;

import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.controller.event.NotifyEventType;
import it.polimi.ingsw.cg_38.model.Player;

public class EventAttacked extends NotifyEvent {

	private static final long serialVersionUID = 1L;
	
	/** è settato a true se il giocatore risulta potenziato */
	private Boolean areYouPowered;
	
	private Boolean areThereOtherHumans;
	
	public Boolean getAreThereOtherHumans() {
		return areThereOtherHumans;
	}

	public void setAreThereOtherHumans(Boolean areThereOtherHumans) {
		this.areThereOtherHumans = areThereOtherHumans;
	}
	
	public Boolean getAreYouPowered() {
		return areYouPowered;
	}

	public void setAreYouPowered(Boolean areYouPowered) {
		this.areYouPowered = areYouPowered;
	}

	public EventAttacked(Player generator, Boolean areThereOtherHumans) {
		super(generator, false);
		this.setAreThereOtherHumans(areThereOtherHumans);
		super.setType(NotifyEventType.ATTACKED);
		this.setAreYouPowered(generator.getAvatar().getIsPowered());
	}

}
