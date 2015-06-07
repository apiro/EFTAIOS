package it.polimi.ingsw.cg_38.gameEvent;

import it.polimi.ingsw.cg_38.controller.event.GameEvent;
import it.polimi.ingsw.cg_38.controller.event.GameEventType;
import it.polimi.ingsw.cg_38.model.Player;

public class EventNewGame extends GameEvent {

	private String creatingRoomName;
	
	public String getCreatingRoomName() {
		return creatingRoomName;
	}

	public void setCreatingRoomName(String creatingRoomName) {
		this.creatingRoomName = creatingRoomName;
	}

	public EventNewGame(Player generator, String room) {
		super(generator, true);
		super.setType(GameEventType.NewGame);
		this.setCreatingRoomName(room);
	}
}
