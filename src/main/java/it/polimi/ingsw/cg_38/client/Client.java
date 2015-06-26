package it.polimi.ingsw.cg_38.client;

import it.polimi.ingsw.cg_38.controller.connection.Communicator;
import it.polimi.ingsw.cg_38.controller.event.Event;
import it.polimi.ingsw.cg_38.controller.gameEvent.EventSubscribe;

import java.util.Queue;

/**
 *Classe astratta che definisce i metodi dei thread di invio messaggi dal client al server, verrà implementata da
 *ClientRMI o ClientSocket 
 * */
public abstract class Client implements Runnable {

	protected Communicator communicator;
	
	public static Client clientCreator(String type, Queue<Event> toSend, Queue<Event> toProcess, EventSubscribe evt) {
		if(("RMI").equals(type)) {
			return new ClientRMI(toSend, toProcess, evt);
		} else if(("Socket").equals(type)) {
			return new ClientSocket(toSend, toProcess, evt);
		} else return null;
	}
	
	protected String host = "127.0.0.1";;
	
	public Communicator getCommunicator() {
		return communicator;
	}
	public void setCommunicator(Communicator communicator) {
		this.communicator = communicator;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	
}
