package it.polimi.ingsw.cg_38.controller.gameEvent;

import it.polimi.ingsw.cg_38.controller.event.GameEvent;
import it.polimi.ingsw.cg_38.controller.event.GameEventType;
import it.polimi.ingsw.cg_38.model.Player;

public class EventDefense extends GameEvent {

	private static final long serialVersionUID = 1L;

	public EventDefense(Player generator) {
		super(generator, false);
		super.setType(GameEventType.DEFENSE);
	}
}
