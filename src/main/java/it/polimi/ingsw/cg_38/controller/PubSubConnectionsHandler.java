package it.polimi.ingsw.cg_38.controller;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class PubSubConnectionsHandler extends Thread {

	private ServerSocket serverSocketPubSub;
	private Boolean serverAlive;
	private HashMap<String, GameController> topics;

	public PubSubConnectionsHandler(ServerSocket serverSocketPubSub, Boolean serverAlive, HashMap<String, GameController> topics) {
		this.serverSocketPubSub = serverSocketPubSub;
		this.serverAlive = serverAlive;
		this.topics = topics;
	}
	
	public void run() {
		serverAlive = true;
		while(serverAlive) {
			Socket socket = null;
			try {
				socket = serverSocketPubSub.accept();
			} catch (IOException e) {
				e.printStackTrace();
			}
			//qui creo un thread per gestire l'aggiunta del communicator alla lista del corretto topic. se non posso fare 
			//questa cosa qui sotto aggiungo il communicator che mi è arrivato direttamente alla lista dei comm della 
			//partita che sta startando.
			SubscribeController subscribeHandler = null;
			subscribeHandler = new SubscribeController(new SocketCommunicator(socket), topics);
			subscribeHandler.start();
			subscribeHandler.setName("SubscribeControllerThread");			
		}
	    try {
			serverSocketPubSub.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
