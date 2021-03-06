package it.polimi.ingsw.cg_38.controller.notifyEvent;

import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.controller.event.NotifyEventType;
import it.polimi.ingsw.cg_38.model.Player;
import it.polimi.ingsw.cg_38.model.deck.ObjectCardType;

/** rappresenta l'evento di carta usata */
public class EventCardUsed extends NotifyEvent {

	private static final long serialVersionUID = 1L;
	
	/** indica se l'azione è stata performata correttamente */
	private Boolean performed;

	/** indica il tipo della carta oggetto */
	private ObjectCardType typeCard;
	
	/** invoca il costruttore della superclasse e setta i vari dati
	 * 
	 * @param generator giocatore che ha generato l'evento
	 * @param performed true se l'azione è stata performata correttamente
	 * @param typeCard indica il tipo della carta oggetto da utilizzare
	 */
	public EventCardUsed(Player generator, Boolean performed, ObjectCardType typeCard) {
		super(generator, true);
		super.setType(NotifyEventType.CARDUSED);
		this.setPerformed(performed);
		this.typeCard = typeCard;
	}

	public ObjectCardType getTypeCard() {
		return typeCard;
	}

	@Override
	public String toString() {
		return "EventCardUsed [" + this.getTypeCard().toString() + "]";
	}

	public Boolean getPerformed() {
		return performed;
	}

	public void setPerformed(Boolean performed) {
		this.performed = performed;
	}

}
