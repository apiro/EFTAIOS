package it.polimi.ingsw.cg_38.controller.notifyEvent;

import it.polimi.ingsw.cg_38.controller.event.NotifyEventType;
import it.polimi.ingsw.cg_38.model.Player;

/** rappresenta l'evento di notifica di teletrasporto */
public class EventNotifyTeleport extends EventMoved {

	private static final long serialVersionUID = 1L;

	/** invoca il costruttore della superclasse e setta i vari dati
	 * 
	 * @param generator giocatore che ha generato l'evento
	 * @param moved nome del settore sul quale è stato effettuato il movimento 
	 */
	public EventNotifyTeleport(Player generator, String moved) {
		super(generator, moved);
		super.setBroadcast(false);
		super.setType(NotifyEventType.NOTTELEPORT);
	}
}
