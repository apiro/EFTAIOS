package it.polimi.ingsw.cg_38.controller.gameEvent;

import it.polimi.ingsw.cg_38.controller.event.GameEvent;
import it.polimi.ingsw.cg_38.controller.event.GameEventType;
import it.polimi.ingsw.cg_38.model.Player;
import it.polimi.ingsw.cg_38.model.deck.Card;

public class EventADRENALINE extends GameEvent {

	private Card toUse;
	private static final long serialVersionUID = 1L;
	
	public EventADRENALINE(Player generator, Card card) {
		super(generator, false);
		this.setToUse(card);
		super.setType(GameEventType.ADRENALINE);
	}

	public Card getToUse() {
		return toUse;
	}

	public void setToUse(Card toUse) {
		this.toUse = toUse;
	}

}
