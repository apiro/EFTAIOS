package it.polimi.ingsw.cg_38.controller.notifyEvent;

import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.controller.event.NotifyEventType;
import it.polimi.ingsw.cg_38.model.Player;

/** rappresenta l'evento di chiusura del gioco */
public class EventClosingGame extends NotifyEvent {

	private static final long serialVersionUID = 1L;
	
	/** indica se ci sono ancora umani in gioco */
	private Boolean areThereOtherHumans;

	/** invoca il costruttore della superclasse e setta i vari dati
	 * 
	 * @param generator giocatore che ha generato l'evento
	 * @param areThereOtherHumans true se ci sono altri umani in gioco
	 */
	public EventClosingGame(Player generator, Boolean areThereOtherHumans) {
		super(generator, true);
		this.setAreThereOtherHumans(areThereOtherHumans);
		super.setType(NotifyEventType.CLOSINGGAME);
	}
	
	public Boolean getAreThereOtherHumans() {
		return areThereOtherHumans;
	}

	public void setAreThereOtherHumans(Boolean areThereOtherHumans) {
		this.areThereOtherHumans = areThereOtherHumans;
	}

}
