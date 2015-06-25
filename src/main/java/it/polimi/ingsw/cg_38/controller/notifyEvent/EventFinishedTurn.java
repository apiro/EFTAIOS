package it.polimi.ingsw.cg_38.controller.notifyEvent;

import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.model.Player;

public class EventFinishedTurn extends NotifyEvent {

	public EventFinishedTurn(Player generator, Boolean finished) {
		super(generator, false);
		this.setFinished(finished);
		
	}

	private static final long serialVersionUID = 1L;
	
	/* è settato a true se il turno del giocatore è effettivamente finito */
	private Boolean finished;
	
	public Boolean getFinished() {
		return finished;
	}
	public void setFinished(Boolean finished) {
		this.finished = finished;
	}
	
}
