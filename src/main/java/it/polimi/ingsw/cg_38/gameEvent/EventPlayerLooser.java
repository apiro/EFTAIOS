package it.polimi.ingsw.cg_38.gameEvent;

import it.polimi.ingsw.cg_38.controller.event.GameEvent;
import it.polimi.ingsw.cg_38.model.Player;

public class EventPlayerLooser extends GameEvent {

	public EventPlayerLooser(Player generator) {
		super(generator, true);
	}

	private static final long serialVersionUID = 1L;

}
