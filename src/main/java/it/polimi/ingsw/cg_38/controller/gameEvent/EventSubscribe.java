package it.polimi.ingsw.cg_38.controller.gameEvent;

import java.io.Serializable;

import it.polimi.ingsw.cg_38.controller.event.GameEvent;
import it.polimi.ingsw.cg_38.controller.event.GameEventType;
import it.polimi.ingsw.cg_38.model.Player;

public class EventSubscribe extends GameEvent implements Serializable{

	private static final long serialVersionUID = 1L;

	/** contiene il nome della mappa del gioco al quale il giocatore ha deciso di sottoscriversi */
	private String map;
	
	/** contiene il nome della stanza alla quale il giocatore ha scelto di sottoscriversi */
	private String room;

	public EventSubscribe(Player generator, String room, String map) {
		super(generator, true);
		this.setRoom(room);
		this.setMap(map);
		this.setType(GameEventType.SUBSCRIBE);
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

	public String getRoom() {
		return room;
	}
	public void setRoom(String room) {
		this.room = room;
	}
}
