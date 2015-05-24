package it.polimi.ingsw.cg_38.gameEvent;

import it.polimi.ingsw.cg_38.controller.event.GameEvent;
import it.polimi.ingsw.cg_38.controller.event.GameEventType;
import it.polimi.ingsw.cg_38.model.Player;

public class EventNoiseMySect extends GameEvent {

	public EventNoiseMySect(Player generator) {
		super(generator);
		super.setType(GameEventType.NoiseMySect);
	}

}
