package it.polimi.ingsw.cg_38.controller.event;

import it.polimi.ingsw.cg_38.model.Player;

public class GameEvent extends Event {

	private static final long serialVersionUID = 1L;
	
	/** è settato a true se l'evento di gioco genera solo eventi di notifica di tipo broadcast ed è settato
	 * a false se l'evento genera eventi di notifica sia di tipo broadcast sia personali */
	private Boolean notifyEventIsBroadcast;
	
	private GameEventType type;

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
