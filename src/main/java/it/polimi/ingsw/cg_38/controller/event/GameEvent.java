package it.polimi.ingsw.cg_38.controller.event;

import it.polimi.ingsw.cg_38.model.Player;

public class GameEvent extends Event {

	private static final long serialVersionUID = 1L;
	public GameEvent(Player generator) {
		super(generator);
	}
	public GameEventType getType() {
		return type;
	}
	public void setType(GameEventType type) {
		this.type = type;
	}
	private GameEventType type;
	
}
