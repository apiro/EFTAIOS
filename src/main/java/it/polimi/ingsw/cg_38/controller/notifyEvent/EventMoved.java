package it.polimi.ingsw.cg_38.controller.notifyEvent;

import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.controller.event.NotifyEventType;
import it.polimi.ingsw.cg_38.model.Player;

/** raprresenta l'evento di avvenuta del movimento */
public class EventMoved extends NotifyEvent {

	private static final long serialVersionUID = 1L;

	/** invoca il costruttore della superclasse e setta i vari dati
	 * 
	 * @param generator giocatore che ha generato l'evento
	 * @param moved nome del settore su cui il giocatore si è mosso
	 */
	public EventMoved(Player generator, String moved) {
		super(generator, false);
		super.setType(NotifyEventType.MOVED);
		this.setMoved(moved);
	}
	
	/** contiene il nome del settore sul cui il giocatore ha effettuato il movimento */
	private String moved;
	
	public String getMoved() {
		return moved;
	}

	public void setMoved(String moved) {
		this.moved = moved;
	}


}
