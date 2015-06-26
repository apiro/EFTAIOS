package it.polimi.ingsw.cg_38.controller.gameEvent;

import it.polimi.ingsw.cg_38.controller.event.GameEvent;
import it.polimi.ingsw.cg_38.controller.event.GameEventType;
import it.polimi.ingsw.cg_38.model.Player;
import it.polimi.ingsw.cg_38.model.map.Sector;

/** rappresenta l'evento di movimento */
public class EventMove extends GameEvent {

	/** contiene il settore sul quale il giocatore ha scelto di muoversi */
	private Sector toMove;
	
	private static final long serialVersionUID = 1L;
	
	/** invoca il costruttore della superclasse e setta i vari dati
	 * 
	 * @param generator giocatore che ha generato l'evento
	 * @param toMove settore nel quale si desidera fare il movimento
	 */
	public EventMove(Player generator, Sector toMove) {
		super(generator, false);
		super.setType(GameEventType.MOVE);
		this.setToMove(toMove);
	}

	public Sector getToMove() {
		return toMove;
	}

	public void setToMove(Sector toMove) {
		this.toMove = toMove;
	}

}
