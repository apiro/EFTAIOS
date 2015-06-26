package it.polimi.ingsw.cg_38.controller.notifyEvent;

import it.polimi.ingsw.cg_38.controller.event.NotifyEventType;
import it.polimi.ingsw.cg_38.model.Player;
import it.polimi.ingsw.cg_38.model.deck.ObjectCardType;

/** rappresenta l'evento di uso di una carta oggetto di tipo difesa */
public class EventUseDefense extends EventCardUsed {

	private static final long serialVersionUID = 1L;
	
	private ObjectCardType typeCard;

	/** invoca il costruttore della superclasse e setta i vari dati
	 * 
	 * @param generator giocatore che ha generato l'evento
	 * @param broadcast true se l'evento è broadcast
	 * @param typeCard tipo della carta oggetto usata
	 */
	public EventUseDefense(Player generator, Boolean broadcast, ObjectCardType typeCard) {
		super(generator, broadcast, typeCard);
		super.setType(NotifyEventType.USEDEFENSECARD);
		this.typeCard = typeCard;
	}

	@Override
	public ObjectCardType getTypeCard() {
		return typeCard;
	}

}
