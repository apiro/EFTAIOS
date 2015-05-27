package it.polimi.ingsw.cg_38.gameEvent;

import it.polimi.ingsw.cg_38.controller.event.GameEventType;
import it.polimi.ingsw.cg_38.model.Player;

import java.io.Serializable;
import java.net.Socket;

public class EventSubscribeSocket extends EventSubscribe implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Socket socket;
	
	public EventSubscribeSocket(Player generator,  String room, String map, Socket socket){
		super(generator, room, map);
		super.setType(GameEventType.subscribeSocket);
		this.setSocket(socket);
	}

	@Override
	public String toString() {
		return "EventSubscribeSocket [socket=" + socket + "]";
	}

	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}

}
