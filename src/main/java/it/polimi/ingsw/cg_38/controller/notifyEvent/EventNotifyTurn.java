package it.polimi.ingsw.cg_38.controller.notifyEvent;

import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.controller.event.NotifyEventType;
import it.polimi.ingsw.cg_38.model.Player;



/** rappresenta l'evento di notifica del turno */
public class EventNotifyTurn extends NotifyEvent {

	private static final long serialVersionUID = 1L;
	
	/** contiene il giocatore del turno attuale */
	private Player playerOfTurn;
	
	/** invoca il costruttore della superclasse e setta i dati. Nessun giocatore genera questo evento quindi 
	 * il generator è sempre null
	 * 
	 * @param playerOfTurn giocatore del turno
	 */
	public EventNotifyTurn(Player playerOfTurn) {
		super(null, true);
		this.setPlayerOfTurn(playerOfTurn);
		this.setType(NotifyEventType.NOTIFYTURN);
	}
	
	@Override
	public String toString() {
		return "EventNotifyTurn [playerOfTurn= " + playerOfTurn.getName() + "]";
	}

	public Player getPlayerOfTurn() {
		return playerOfTurn;
	}
	public void setPlayerOfTurn(Player playerOfTurn) {
		this.playerOfTurn = playerOfTurn;
	}

}
