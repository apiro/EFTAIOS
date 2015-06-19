package it.polimi.ingsw.cg_38.gameEvent;

import it.polimi.ingsw.cg_38.controller.event.GameEvent;
import it.polimi.ingsw.cg_38.controller.event.GameEventType;
import it.polimi.ingsw.cg_38.model.Card;
import it.polimi.ingsw.cg_38.model.Player;

public class EventSedatives extends GameEvent {

	private Card toUse;
	private static final long serialVersionUID = 1L;
	
	public Card getToUse() {
		return toUse;
	}

	public void setToUse(Card toUse) {
		this.toUse = toUse;
	}

	public EventSedatives(Player generator, Card card) {
		super(generator, true);
		super.setType(GameEventType.Sedatives);
		this.setToUse(card);
	}

}
