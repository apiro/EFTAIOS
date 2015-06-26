package it.polimi.ingsw.cg_38.controller.notifyEvent;

import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.controller.event.NotifyEventType;
import it.polimi.ingsw.cg_38.model.Player;
import it.polimi.ingsw.cg_38.model.deck.Card;
import it.polimi.ingsw.cg_38.model.deck.ObjectCard;

/** rappresenta l'evento di pescaggio */
public class EventDrown extends NotifyEvent {

	@Override
	public String toString() {
		return "EventDrown [added=" + added + ", drown=" + drown + "]";
	}

	private static final long serialVersionUID = 1L;
	
	/** contiene la carta oggetto pescata */
	private ObjectCard added;
	
	/** contiene la carta settore pescata */
	private Card drown;
	
	/** invoca il costruttore della superclasse e setta i vari dati
	 * 
	 * @param generator giocatore che ha generato l'evento
	 * @param added carta oggetto pescata
	 * @param card carta settore pescata
	 */
	public EventDrown(Player generator, ObjectCard added, Card card) {
		super(generator, false);
		super.setType(NotifyEventType.DROWN);
		this.setAdded(added);
		this.setDrown(card);
	}

	public ObjectCard getAdded() {
		return added;
	}

	public void setAdded(ObjectCard added) {
		this.added = added;
	}

	public Card getDrown() {
		return drown;
	}

	public void setDrown(Card card) {
		this.drown = card;
	}

}
