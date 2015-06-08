package it.polimi.ingsw.cg_38.controller;

import it.polimi.ingsw.cg_38.gameEvent.EventSubscribe;

import java.util.HashMap;

public class SubscribeController extends Thread {

	private SocketCommunicator socketCommunicator;
	private HashMap<String, GameController> topics;

	public SubscribeController(SocketCommunicator socketCommunicator, HashMap<String, GameController> topics) {
		this.socketCommunicator = socketCommunicator;
		this.topics = topics;
	}

	public void run() {
		EventSubscribe evt = (EventSubscribe) this.socketCommunicator.recieveEvent();
		for(GameController gc:topics.values()) {
			if(gc.getTopic().equals(evt.getRoom())) {
				gc.getSubscribers().add(socketCommunicator);
			}
		}
		Thread.currentThread().interrupt();
	}
	
}
