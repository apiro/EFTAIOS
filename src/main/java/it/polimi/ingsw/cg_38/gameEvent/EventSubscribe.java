package it.polimi.ingsw.cg_38.gameEvent;

import java.io.Serializable;

import it.polimi.ingsw.cg_38.controller.event.GameEvent;
import it.polimi.ingsw.cg_38.controller.event.GameEventType;
import it.polimi.ingsw.cg_38.model.Player;

public class EventSubscribe extends GameEvent implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EventSubscribe(Player generator, String room, String map) {
		super(generator);
		super.setType(GameEventType.subscribe);
		this.setRoom(room);
		this.setMap(map);
	}
	
	@Override
	public String toString() {
		return "EventSubscribe [map=" + map + ", room=" + room + "]";
	}

	public String getMap() {
		return map;
	}
	public void setMap(String map) {
		this.map = map;
	}

	private String map;
	private String room;
	
	public String getRoom() {
		return room;
	}
	public void setRoom(String room) {
		this.room = room;
	}
}
