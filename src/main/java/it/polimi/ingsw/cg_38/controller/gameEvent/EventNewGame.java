package it.polimi.ingsw.cg_38.controller.gameEvent;

import it.polimi.ingsw.cg_38.controller.event.GameEvent;
import it.polimi.ingsw.cg_38.controller.event.GameEventType;
import it.polimi.ingsw.cg_38.model.Player;

public class EventNewGame extends GameEvent {

	/** contiene il nome della nuova stanza che il giocatore ha creato */
	private String creatingRoomName;
	
	private static final long serialVersionUID = 1L;
	
	public EventNewGame(Player generator, String room) {
		super(generator, true);
		super.setType(GameEventType.NEWGAME);
		this.setCreatingRoomName(room);
	}
	
	public String getCreatingRoomName() {
		return creatingRoomName;
	}

	public void setCreatingRoomName(String creatingRoomName) {
		this.creatingRoomName = creatingRoomName;
	}

}
