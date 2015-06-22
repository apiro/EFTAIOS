package it.polimi.ingsw.cg_38.controller.notifyEvent;

import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.controller.event.NotifyEventType;
import it.polimi.ingsw.cg_38.model.Player;
import it.polimi.ingsw.cg_38.model.deck.ObjectCard;

public class EventRejectCardHuman extends NotifyEvent {

	private static final long serialVersionUID = 1L;
	private ObjectCard card;

	public EventRejectCardHuman(Player generator, ObjectCard card) {
		super(generator, true);
		super.setType(NotifyEventType.rejectCardHuman);
		this.setCard(card);
	}

	public ObjectCard getCard() {
		return card;
	}

	public void setCard(ObjectCard card) {
		this.card = card;
	}

}
