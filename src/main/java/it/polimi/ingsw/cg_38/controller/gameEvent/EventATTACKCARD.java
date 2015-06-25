package it.polimi.ingsw.cg_38.controller.gameEvent;

import it.polimi.ingsw.cg_38.controller.event.GameEvent;
import it.polimi.ingsw.cg_38.controller.event.GameEventType;
import it.polimi.ingsw.cg_38.model.Player;
import it.polimi.ingsw.cg_38.model.deck.Card;
import it.polimi.ingsw.cg_38.model.map.Sector;

public class EventATTACKCARD extends GameEvent {

	private static final long serialVersionUID = 1L;
	
	/** contiene il settore sul quale il giocatre ha scelto di attaccare */
	private Sector target;
	
	/** contiene la carta oggetto di tipo attacco che il giocatore ha utilizzato */
	private Card toUse;
	
	public Sector getTarget() {
		return target;
	}

	public void setTarget(Sector target) {
		this.target = target;
	}

	public Card getToUse() {
		return toUse;
	}

	public void setToUse(Card toUse) {
		this.toUse = toUse;
	}

	public EventATTACKCARD(Player generator, Card card) {
		super(generator, false);
		this.setTarget(super.getGenerator().getAvatar().getCurrentSector());
		this.setToUse(card);
		super.setType(GameEventType.ATTACKCARD);
	}
}
