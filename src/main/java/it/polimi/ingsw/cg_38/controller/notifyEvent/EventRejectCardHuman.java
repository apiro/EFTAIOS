package it.polimi.ingsw.cg_38.controller.notifyEvent;

import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.controller.event.NotifyEventType;
import it.polimi.ingsw.cg_38.model.Player;
import it.polimi.ingsw.cg_38.model.deck.ObjectCard;

/** rappresenta l'evento di scarto di una carta da parte di un avatar di tipo umano */
public class EventRejectCardHuman extends NotifyEvent {

	private static final long serialVersionUID = 1L;
	
	/** contiene la carta oggetto che il giocatore vuole scartare */
	private ObjectCard card;

	/** invoca il costruttore della superclasse e setta i vari dati
	 * 
	 * @param generator giocatore che ha generato l'evento
	 * @param card carta oggetto scartata
	 */
	public EventRejectCardHuman(Player generator, ObjectCard card) {
		super(generator, true);
		super.setType(NotifyEventType.REJECTCARDHUMAN);
		this.setCard(card);
	}

	public ObjectCard getCard() {
		return card;
	}

	public void setCard(ObjectCard card) {
		this.card = card;
	}

}
