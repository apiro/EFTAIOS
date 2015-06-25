package it.polimi.ingsw.cg_38.controller.gameEvent;

import it.polimi.ingsw.cg_38.controller.event.GameEvent;
import it.polimi.ingsw.cg_38.controller.event.GameEventType;
import it.polimi.ingsw.cg_38.model.Player;
import it.polimi.ingsw.cg_38.model.deck.Card;
import it.polimi.ingsw.cg_38.model.map.Sector;

public class EventSPOTLIGHT extends GameEvent{

	private Sector target;
	private Card card;
	private static final long serialVersionUID = 1L;
	
	public Card getToUse() {
		return card;
	}

	public void setToUse(Card card) {
		this.card = card;
	}

	public EventSPOTLIGHT(Player generator, Sector target, Card card) {
		super(generator, false);
		super.setType(GameEventType.LIGHTS);
		this.setTarget(target);
		this.setToUse(card);
	}

	public Sector getTarget() {
		return target;
	}

	public void setTarget(Sector target) {
		this.target = target;
	}
}
