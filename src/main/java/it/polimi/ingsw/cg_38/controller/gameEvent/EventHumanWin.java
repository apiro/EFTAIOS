package it.polimi.ingsw.cg_38.controller.gameEvent;

import it.polimi.ingsw.cg_38.controller.event.GameEvent;
import it.polimi.ingsw.cg_38.controller.event.GameEventType;
import it.polimi.ingsw.cg_38.model.Player;

public class EventHumanWin extends GameEvent {

	private static final long serialVersionUID = 1L;

	public EventHumanWin(Player generator) {
		super(generator, true);
		super.setType(GameEventType.HUMANWIN);
	}
}
