package it.polimi.ingsw.cg_38.controller.notifyEvent;

import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.controller.event.NotifyEventType;
import it.polimi.ingsw.cg_38.model.Player;

/** rappresenta l'evento di notifica di sconfitta */
public class EventNotifyLoose extends NotifyEvent {

	private static final long serialVersionUID = 1L;
	
	/** invoca il costruttore della superclasse e setta i dati
	 * 
	 * @param generator giocatore che ha generato l'evento
	 */
	public EventNotifyLoose(Player generator) {
		super(generator, true);
		super.setType(NotifyEventType.NOTLOOSE);
	}


}
