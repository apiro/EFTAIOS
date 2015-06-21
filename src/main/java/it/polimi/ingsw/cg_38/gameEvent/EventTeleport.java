package it.polimi.ingsw.cg_38.gameEvent;

import it.polimi.ingsw.cg_38.controller.event.GameEvent;
import it.polimi.ingsw.cg_38.controller.event.GameEventType;
import it.polimi.ingsw.cg_38.model.Card;
import it.polimi.ingsw.cg_38.model.Player;

public class EventTeleport extends GameEvent {

	private Card toUse;
	private static final long serialVersionUID = 1L;
	
	public EventTeleport(Player generator, Card card) {
		super(generator, false);
		super.setType(GameEventType.Teleport);
		this.setToUse(card);
	}

	public Card getToUse() {
		return toUse;
	}

	public void setToUse(Card toUse) {
		this.toUse = toUse;
	}
}
