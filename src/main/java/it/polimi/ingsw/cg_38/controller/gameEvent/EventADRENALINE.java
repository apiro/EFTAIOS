package it.polimi.ingsw.cg_38.controller.gameEvent;

import it.polimi.ingsw.cg_38.controller.event.GameEvent;
import it.polimi.ingsw.cg_38.controller.event.GameEventType;
import it.polimi.ingsw.cg_38.model.Player;
import it.polimi.ingsw.cg_38.model.deck.Card;

/** rappresenta l'evento di uso della carta adrenalina */
public class EventADRENALINE extends GameEvent {

	/** contiene la carta adrenalina che il giocatore ha scelto di utilizzare */
	private Card toUse;
	
	private static final long serialVersionUID = 1L;
	
	/** invoca il costruttore della superclasse e setta i vati dati
	 * 
	 * @param generator giocatore che ha generato l'evento
	 * @param card carta da utilizzare
	 */
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
