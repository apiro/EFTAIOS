package it.polimi.ingsw.cg_38.controller.event;

import it.polimi.ingsw.cg_38.controller.event.GameEvent;
import it.polimi.ingsw.cg_38.model.Player;

public class SplashEvent extends GameEvent {

	public SplashEvent(String name, String map, String room) {
		super(new Player(name));
		this.setMap(map);
		this.setName(name);
		this.setRoom(room);
	}
	
	private static final long serialVersionUID = 1L;

	private String name;
	private String map;
	private String room;
	public void setName(String name) {
		this.name = name;
	}
	public void setMap(String map) {
		this.map = map;
	}
	public void setRoom(String room) {
		this.room = room;
	}

}
