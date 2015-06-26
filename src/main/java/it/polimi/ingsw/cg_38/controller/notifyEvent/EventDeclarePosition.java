package it.polimi.ingsw.cg_38.controller.notifyEvent;

import java.util.ArrayList;
import java.util.List;

import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.controller.event.NotifyEventType;
import it.polimi.ingsw.cg_38.model.Player;

/** rappresenta l'evento di dichiarazione della posizione */
public class EventDeclarePosition extends NotifyEvent {

	private static final long serialVersionUID = 1L;
	
	/** contiene i giocatori da dichiarare */
	private List<Player> toDeclare = new ArrayList<Player>();
	
	/** invoca il costruttore della superclasse e setta i vari dati
	 * 
	 * @param generator giocatore che ha generato l'evento
	 * @param toDeclare lista di giocatori di cui dichiare la posizione
	 */
	public EventDeclarePosition(Player generator, List<Player> toDeclare) {
		super(generator, true);
		super.setType(NotifyEventType.DECLAREPOSITION);
		this.setToDeclare(toDeclare);
	}

	public List<Player> getToDeclare() {
		return toDeclare;
	}

	public void setToDeclare(List<Player> toDeclare) {
		this.toDeclare = toDeclare;
	}
}
