package it.polimi.ingsw.cg_38.controller.gameEvent;

import it.polimi.ingsw.cg_38.controller.event.GameEvent;
import it.polimi.ingsw.cg_38.controller.event.GameEventType;
import it.polimi.ingsw.cg_38.model.Player;

public class EventAliensWinner extends GameEvent {

	private static final long serialVersionUID = 1L;
	private Boolean areWinner;

	public EventAliensWinner(Player generator, Boolean areWinner) {
		super(generator, true);
		this.setAreWinner(areWinner);
		super.setType(GameEventType.aliensWin);
	}

	public Boolean getAreWinner() {
		return areWinner;
	}

	public void setAreWinner(Boolean areWinner) {
		this.areWinner = areWinner;
	}
}
