package it.polimi.ingsw.cg_38.controller.notifyEvent;

import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.model.Player;

/** rappresenta l'evento di fine turno */
public class EventFinishedTurn extends NotifyEvent {

	private static final long serialVersionUID = 1L;
	
	/** invoca il costruttore della superclasse e setta i dati 
	 * 
	 * @param generator giocatore che ha generato l'evento
	 * @param finished true se il giocatore può finire il turno
	 */
	public EventFinishedTurn(Player generator, Boolean finished) {
		super(generator, false);
		this.setFinished(finished);
		
	}
	
	/** indica se il turno del giocatore è effettivamente finito */
	private Boolean finished;
	
	public Boolean getFinished() {
		return finished;
	}
	public void setFinished(Boolean finished) {
		this.finished = finished;
	}
	
}
