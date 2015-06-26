package it.polimi.ingsw.cg_38.controller.event;

import it.polimi.ingsw.cg_38.model.Player;

/** identifica un generico evento di gioco */
public class GameEvent extends Event {

	private static final long serialVersionUID = 1L;
	
	/** true se l'evento di gioco genera solo eventi di notifica di tipo broadcast
	 * false se l'evento genera eventi di notifica sia di tipo broadcast sia personali */
	private Boolean notifyEventIsBroadcast;
	
	private GameEventType type;

	/** invoca il costruttore della superclasse e setta i dati
	 * 
	 * @param generator giocatore che ha generato l'evento
	 * @param notifyEventIsBroadcast true se l'evento è boradcast
	 */
	public GameEvent(Player generator, Boolean notifyEventIsBroadcast) {
		super(generator);
		this.setNotifyEventIsBroadcast(notifyEventIsBroadcast);
	}
	
	@Override
	public String toString() {
		return "GameEvent [type=" + type + "]";
	}
	
	public Boolean getNotifyEventIsBroadcast() {
		return notifyEventIsBroadcast;
	}
	
	public void setNotifyEventIsBroadcast(Boolean notifyEventIsBroadcast) {
		this.notifyEventIsBroadcast = notifyEventIsBroadcast;
	}

	public GameEventType getType() {
		return type;
	}
	
	public void setType(GameEventType type) {
		this.type = type;
	}

	
}
