package it.polimi.ingsw.cg_38.gameEvent;

import it.polimi.ingsw.cg_38.controller.event.GameEvent;
import it.polimi.ingsw.cg_38.controller.event.GameEventType;
import it.polimi.ingsw.cg_38.model.Player;

public class EventDraw extends GameEvent {

	public EventDraw(Player generator) {
		super(generator);
		super.setType(GameEventType.Draw);
	}

}
