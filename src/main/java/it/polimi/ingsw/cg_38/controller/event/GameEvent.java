package it.polimi.ingsw.cg_38.controller.event;

import it.polimi.ingsw.cg_38.model.Player;

public class GameEvent extends Event {

	private Boolean notifyEventIsBroadcast;
	public Boolean getNotifyEventIsBroadcast() {
		return notifyEventIsBroadcast;
	}
	public void setNotifyEventIsBroadcast(Boolean notifyEventIsBroadcast) {
		this.notifyEventIsBroadcast = notifyEventIsBroadcast;
	}
	private static final long serialVersionUID = 1L;
	
	/**
	 * notifyEventIsBroadCast: 
	 * 	true: questo evento di gioco genera SOLO notify eventi di tipo broadcast 
	 *  false: questo evento di gioco genera NON SOLO notify event di tip broadcast(o solo personal)
	 * */
	
	public GameEvent(Player generator, Boolean notifyEventIsBroadcast) {
		super(generator);
		this.setNotifyEventIsBroadcast(notifyEventIsBroadcast);
	}
	public GameEventType getType() {
		return type;
	}
	public void setType(GameEventType type) {
		this.type = type;
	}
	private GameEventType type;
	
}
