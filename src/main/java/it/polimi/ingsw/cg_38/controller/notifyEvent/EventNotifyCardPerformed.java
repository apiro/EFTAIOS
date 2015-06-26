package it.polimi.ingsw.cg_38.controller.notifyEvent;

import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.controller.event.NotifyEventType;
import it.polimi.ingsw.cg_38.model.Player;

/** rappresenta l'evento di notifica di effetto carta performato */
public class EventNotifyCardPerformed extends NotifyEvent {

	private static final long serialVersionUID = 1L;

	/** invoca il costruttore dellla superclasse e setta i dati */
	public EventNotifyCardPerformed(Player generator) {
		super(generator, false);
		super.setType(NotifyEventType.CARDPERFORMED);
	}
}
