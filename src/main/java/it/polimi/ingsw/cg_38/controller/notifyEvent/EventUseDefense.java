package it.polimi.ingsw.cg_38.controller.notifyEvent;

import it.polimi.ingsw.cg_38.controller.event.NotifyEventType;
import it.polimi.ingsw.cg_38.model.Player;
import it.polimi.ingsw.cg_38.model.deck.ObjectCardType;

public class EventUseDefense extends EventCardUsed {

	private static final long serialVersionUID = 1L;
	private ObjectCardType typeCard;

	public EventUseDefense(Player generator, Boolean broadcast, ObjectCardType typeCard) {
		super(generator, broadcast, typeCard);
		super.setType(NotifyEventType.useDefenseCard);
		this.typeCard = typeCard;
	}

	@Override
	public ObjectCardType getTypeCard() {
		return typeCard;
	}

}
