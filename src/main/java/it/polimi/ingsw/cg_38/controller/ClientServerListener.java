package it.polimi.ingsw.cg_38.controller;

import it.polimi.ingsw.cg_38.controller.event.Event;

public class ClientServerListener extends Thread {
	
	private Client client;

	public ClientServerListener(Client client) {
		this.client = client;
	}
	
	public void run() {
		while(true) {
			Event msg = client.getToProcess().poll();
			if(msg != null) {
				client.handleSentNotifyEvent(msg);
			}
		}
	}

}
