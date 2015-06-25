package it.polimi.ingsw.cg_38.controller.gameEvent;

import it.polimi.ingsw.cg_38.controller.event.GameEvent;
import it.polimi.ingsw.cg_38.controller.event.GameEventType;
import it.polimi.ingsw.cg_38.model.Player;
import it.polimi.ingsw.cg_38.model.deck.Card;

public class EventTELEPORT extends GameEvent {

	/** contiene la carta oggetto di tipo TELEPORT che il giocatore ha deciso di utilizzare */
	private Card toUse;
	
	private static final long serialVersionUID = 1L;
	
	public EventTELEPORT(Player generator, Card card) {
		super(generator, false);
		super.setType(GameEventType.TELEPORT);
		this.setToUse(card);
	}

	public Card getToUse() {
		return toUse;
	}

	public void setToUse(Card toUse) {
		this.toUse = toUse;
	}
}
