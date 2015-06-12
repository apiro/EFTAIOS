package it.polimi.ingsw.cg_38.gameEvent;

import it.polimi.ingsw.cg_38.controller.event.GameEvent;
import it.polimi.ingsw.cg_38.controller.event.GameEventType;
import it.polimi.ingsw.cg_38.model.Player;
import it.polimi.ingsw.cg_38.model.Sector;

public class EventMove extends GameEvent {

	private Sector toMove;
	private static final long serialVersionUID = 1L;
	
	public EventMove(Player generator, Sector toMove) {
		super(generator, false);
		super.setType(GameEventType.Move);
		this.setToMove(toMove);
	}

	public Sector getToMove() {
		return toMove;
	}

	public void setToMove(Sector toMove) {
		this.toMove = toMove;
	}

}
