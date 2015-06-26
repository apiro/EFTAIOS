package it.polimi.ingsw.cg_38.controller.gameEvent;

import it.polimi.ingsw.cg_38.controller.event.GameEvent;
import it.polimi.ingsw.cg_38.controller.event.GameEventType;
import it.polimi.ingsw.cg_38.model.Player;
import it.polimi.ingsw.cg_38.model.deck.Card;
import it.polimi.ingsw.cg_38.model.map.Sector;

/** rappresenta l'evento di utilizzo della carta oggetto luci */
public class EventSPOTLIGHT extends GameEvent{

	/** contiene il settore sul quale il giocatore ha scelto di fare luce */
	private Sector target;
	
	/** contiene la carta di tipo SPOTLIGHT che il giocatore ha scelto di utilizzare */
	private Card card;
	
	private static final long serialVersionUID = 1L;
	
	public Card getToUse() {
		return card;
	}

	public void setToUse(Card card) {
		this.card = card;
	}

	/** invoca il costruttore della superclasse e setta i vari dati
	 * 
	 * @param generator giocatore che ha generato l'evento
	 * @param target settore nel quale viene utilizzato l'effetto della carta
	 * @param card carta utilizzata
	 */
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
