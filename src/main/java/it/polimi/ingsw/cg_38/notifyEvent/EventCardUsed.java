package it.polimi.ingsw.cg_38.notifyEvent;

import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.controller.event.NotifyEventType;
import it.polimi.ingsw.cg_38.model.ObjectCardType;
import it.polimi.ingsw.cg_38.model.Player;

public class EventCardUsed extends NotifyEvent {

	private static final long serialVersionUID = 1L;
	
	private Boolean performed;

	private ObjectCardType typeCard;
	
	public EventCardUsed(Player generator, Boolean performed, ObjectCardType typeCard) {
		super(generator, true);
		super.setType(NotifyEventType.CardUsed);
		this.setPerformed(performed);
		this.typeCard = typeCard;
	}

	public ObjectCardType getTypeCard() {
		return typeCard;
	}

	@Override
	public String toString() {
		return "EventCardUsed [" + this.getTypeCard().toString() + "]";
	}

	public Boolean getPerformed() {
		return performed;
	}

	public void setPerformed(Boolean performed) {
		this.performed = performed;
	}

}
