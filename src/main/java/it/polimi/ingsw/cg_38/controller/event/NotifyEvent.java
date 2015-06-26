package it.polimi.ingsw.cg_38.controller.event;

import it.polimi.ingsw.cg_38.model.Player;

/** identifica un generico evento di notifica */
public class NotifyEvent extends Event {

	private static final long serialVersionUID = 1L;
	
	/**true se l'evento di gioco genera solo eventi di notifica di tipo broadcast
	 *se l'evento genera eventi di notifica sia di tipo broadcast sia personali */
	private Boolean broadcast;
	
	private NotifyEventType type;
	
	public NotifyEvent(Player generator, Boolean broadcast) {
		super(generator);
		this.setBroadcast(broadcast);
	}
	
	@Override
	public String toString() {
		return "NotifyEvent [type=" + type + "]";
	}

	public Boolean isBroadcast() {
		return broadcast;
	}

	public void setBroadcast(Boolean broadcast) {
		this.broadcast = broadcast;
	}

	
	public NotifyEventType getType() {
		return this.type;
	}

	public void setType(NotifyEventType type) {
		this.type = type;
	}

}
