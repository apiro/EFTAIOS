package it.polimi.ingsw.cg_38.controller.gameEvent;

import it.polimi.ingsw.cg_38.controller.event.GameEvent;
import it.polimi.ingsw.cg_38.controller.event.GameEventType;
import it.polimi.ingsw.cg_38.model.Player;

/** raprresenta l'evento di vincita degli avatar di tipo alieno */
public class EventAliensWinner extends GameEvent {

	private static final long serialVersionUID = 1L;
	
	/** indica se gli avatar hanno vinto */
	private Boolean areWinner;

	/** invoca il costruttore della superclasse e setta i vari dati
	 * 
	 * @param generator giocatore che ha generato l'evento
	 * @param areWinner true se gli avatar hanno effettivamente vinto
	 */
	public EventAliensWinner(Player generator, Boolean areWinner) {
		super(generator, true);
		this.setAreWinner(areWinner);
		super.setType(GameEventType.ALIENSWIN);
	}

	public Boolean getAreWinner() {
		return areWinner;
	}

	public void setAreWinner(Boolean areWinner) {
		this.areWinner = areWinner;
	}
}
