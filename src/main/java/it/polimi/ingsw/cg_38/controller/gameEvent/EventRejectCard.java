package it.polimi.ingsw.cg_38.controller.gameEvent;

import it.polimi.ingsw.cg_38.controller.event.GameEvent;
import it.polimi.ingsw.cg_38.controller.event.GameEventType;
import it.polimi.ingsw.cg_38.model.Player;
import it.polimi.ingsw.cg_38.model.deck.ObjectCard;

public class EventRejectCard extends GameEvent {

	private static final long serialVersionUID = 1L;
	private ObjectCard card;

	public EventRejectCard(Player generator, ObjectCard card) {
		super(generator, true);
		this.setCard(card);
		super.setType(GameEventType.REJECTCARD);
	}

	public ObjectCard getCard() {
		return card;
	}

	public void setCard(ObjectCard card) {
		this.card = card;
	}

}
