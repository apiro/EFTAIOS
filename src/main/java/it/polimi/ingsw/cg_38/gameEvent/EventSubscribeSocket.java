package it.polimi.ingsw.cg_38.gameEvent;

import it.polimi.ingsw.cg_38.controller.event.GameEventType;
import it.polimi.ingsw.cg_38.model.Player;

import java.io.Serializable;

public class EventSubscribeSocket extends EventSubscribe implements Serializable{
	
	@Override
	public String toString() {
		return "EventSubscribeSocket [ TO CONNECT ME USE PORT=" + PORT + ", TO CONNECT ME USE HOST=" + HOST + "]";
	}

	private static final long serialVersionUID = 1L;
	private int PORT;
	private String HOST = "localhost";
	
	public EventSubscribeSocket(Player generator,  String room, String map, int PORT){
		super(generator, room, map);
		super.setType(GameEventType.subscribeSocket);
		this.setPORT(PORT);
	}
	
	public int getPORT() {
		return this.PORT;
	}

	public void setPORT(int PORT) {
		this.PORT = PORT;
	}

	public String getHOST() {
		return this.HOST;
	}

}
