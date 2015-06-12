package it.polimi.ingsw.cg_38.gameEvent;

import it.polimi.ingsw.cg_38.controller.event.GameEvent;
import it.polimi.ingsw.cg_38.controller.event.GameEventType;
import it.polimi.ingsw.cg_38.model.Player;

public class EventNoiseMySect extends GameEvent {
	
	private static final long serialVersionUID = 1L;

	public EventNoiseMySect(Player generator) {
		super(generator, true);
		super.setType(GameEventType.NoiseMySect);
	}

}
