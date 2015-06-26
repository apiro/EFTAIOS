package it.polimi.ingsw.cg_38.controller.notifyEvent;

import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.controller.event.NotifyEventType;
import it.polimi.ingsw.cg_38.model.Player;

/** evento di scarto di una carta da parte di un avatar di tipo alieno */
public class EventRejectCardAlien extends NotifyEvent {

	private static final long serialVersionUID = 1L;

	/** invoca il costruttore della superclasse e setta i dati
	 * 
	 * @param generator giocatore che ha generato l'evento
	 */
	public EventRejectCardAlien(Player generator) {
		super(generator, false);
		super.setType(NotifyEventType.REJECTCARD);
	}
}
