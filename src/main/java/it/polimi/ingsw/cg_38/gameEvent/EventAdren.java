package it.polimi.ingsw.cg_38.gameEvent;

import it.polimi.ingsw.cg_38.controller.event.GameEvent;
import it.polimi.ingsw.cg_38.controller.event.GameEventType;
import it.polimi.ingsw.cg_38.model.Card;
import it.polimi.ingsw.cg_38.model.Player;

public class EventAdren extends GameEvent {

	private Card toUse;
	
	public EventAdren(Player generator, Card card) {
		super(generator, false);
		this.setToUse(card);
		super.setType(GameEventType.Adrenaline);
	}

	public Card getToUse() {
		return toUse;
	}

	public void setToUse(Card toUse) {
		this.toUse = toUse;
	}

}
