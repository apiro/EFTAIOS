package it.polimi.ingsw.cg_38.notifyEvent;

import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.controller.event.NotifyEventType;
import it.polimi.ingsw.cg_38.model.Player;

public class EventNotifyTurn extends NotifyEvent {

	//player generator qui sara sempre "null" perche questo non è un evento di callback ma è un evento che il server genera 
	//senza che un client ne abbia causato l'invio. l'event generator è il server !
	public EventNotifyTurn(Player playerOfTurn) {
		super(null, true);
		this.setPlayerOfTurn(playerOfTurn);
		this.setType(NotifyEventType.notifyTurn);
	}
	
	@Override
	public String toString() {
		return "EventNotifyTurn [playerOfTurn= " + playerOfTurn.getName() + "]";
	}

	private Player playerOfTurn;
	
	public Player getPlayerOfTurn() {
		return playerOfTurn;
	}
	public void setPlayerOfTurn(Player playerOfTurn) {
		this.playerOfTurn = playerOfTurn;
	}

	private static final long serialVersionUID = 1L;

}
