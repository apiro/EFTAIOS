package it.polimi.ingsw.cg_38.controller.gameEvent;

import it.polimi.ingsw.cg_38.controller.event.GameEvent;
import it.polimi.ingsw.cg_38.controller.event.GameEventType;
import it.polimi.ingsw.cg_38.model.Player;

/** rappresenta l'evento di invio di un messaggio di test */
public class EventChat extends GameEvent {

	private static final long serialVersionUID = 1L;
	
	/** contiene il messaggio di chat che il giocatore ha scelto di inviare */
	private String message;

	/** invoca il costruttore della superclasse e setta i vari dati
	 * 
	 * @param generator giocatore che ha generato l'evento
	 * @param message messaggio di chat che deve essere inviato
	 */
	public EventChat(Player generator, String message) {
		super(generator, true);
		this.setMessage(message);
		super.setType(GameEventType.CHAT);
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
