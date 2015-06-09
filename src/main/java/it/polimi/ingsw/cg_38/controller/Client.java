package it.polimi.ingsw.cg_38.controller;

import it.polimi.ingsw.cg_38.controller.event.Event;
import it.polimi.ingsw.cg_38.gameEvent.EventSubscribe;

import java.util.concurrent.ConcurrentLinkedQueue;

public class Client {

	protected Communicator communicator;
	
	public static Client clientCreator(String type, ConcurrentLinkedQueue<Event> queue, EventSubscribe evt) {
		if(type.equals("RMI")) {
			return new ClientRMI(queue, evt);
		} else if(type.equals("Socket")) {
			return new ClientSocket(queue, evt);
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
