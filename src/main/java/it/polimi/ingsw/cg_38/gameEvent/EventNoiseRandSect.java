package it.polimi.ingsw.cg_38.gameEvent;

import it.polimi.ingsw.cg_38.controller.event.GameEvent;
import it.polimi.ingsw.cg_38.controller.event.GameEventType;
import it.polimi.ingsw.cg_38.model.Player;
import it.polimi.ingsw.cg_38.model.Sector;

public class EventNoiseRandSect extends GameEvent {

	private Sector toNoise;
	
	public Sector getToNoise() {
		return toNoise;
	}

	public void setToNoise(Sector toNoise) {
		this.toNoise = toNoise;
	}

	public EventNoiseRandSect(Player generator, Sector toNoise) {
		super(generator, true);
		super.setType(GameEventType.NoiseRandSect);
		this.setToNoise(toNoise);
	}
}
