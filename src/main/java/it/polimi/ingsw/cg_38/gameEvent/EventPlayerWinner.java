package it.polimi.ingsw.cg_38.gameEvent;

import it.polimi.ingsw.cg_38.controller.event.GameEvent;
import it.polimi.ingsw.cg_38.controller.event.GameEventType;
import it.polimi.ingsw.cg_38.model.Player;

public class EventPlayerWinner extends GameEvent {

	public EventPlayerWinner(Player generator) {
		super(generator, true);
		super.setType(GameEventType.winner);
	}

	private static final long serialVersionUID = 1L;

}
