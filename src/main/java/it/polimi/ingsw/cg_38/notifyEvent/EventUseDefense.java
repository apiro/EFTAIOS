package it.polimi.ingsw.cg_38.notifyEvent;

import it.polimi.ingsw.cg_38.controller.event.NotifyEventType;
import it.polimi.ingsw.cg_38.model.ObjectCardType;
import it.polimi.ingsw.cg_38.model.Player;

public class EventUseDefense extends EventCardUsed {

	private static final long serialVersionUID = 1L;
	private ObjectCardType typeCard;

	public EventUseDefense(Player generator, Boolean broadcast, ObjectCardType typeCard) {
		super(generator, broadcast, typeCard);
		super.setType(NotifyEventType.useDefenseCard);
		this.typeCard = typeCard;
	}

	public ObjectCardType getTypeCard() {
		return typeCard;
	}

}
