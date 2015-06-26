package it.polimi.ingsw.cg_38.controller.notifyEvent;

import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.model.Player;

/** rappresenta l'evento di notifica di non è il tuo turno */
public class EventNotYourTurn extends NotifyEvent {

	private static final long serialVersionUID = 1L;
	
	private String message = "This is not your turn !";
	
	/** invoca il costruttore della superclasse */
	public EventNotYourTurn(Player generator) {
		super(generator, false);		
	}
	
	public String getMessage() {
		return message;
	}

}
