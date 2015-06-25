package it.polimi.ingsw.cg_38.controller.gameEvent;

import it.polimi.ingsw.cg_38.controller.event.GameEvent;
import it.polimi.ingsw.cg_38.controller.event.GameEventType;
import it.polimi.ingsw.cg_38.model.Player;
import it.polimi.ingsw.cg_38.model.deck.Card;

public class EventSEDATIVES extends GameEvent {

	private Card toUse;
	private static final long serialVersionUID = 1L;
	
	public Card getToUse() {
		return toUse;
	}

	public void setToUse(Card toUse) {
		this.toUse = toUse;
	}

	public EventSEDATIVES(Player generator, Card card) {
		super(generator, false);
		super.setType(GameEventType.SEDATIVES);
		this.setToUse(card);
	}

}
