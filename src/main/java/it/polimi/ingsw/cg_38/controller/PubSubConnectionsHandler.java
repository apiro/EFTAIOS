package it.polimi.ingsw.cg_38.controller;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class PubSubConnectionsHandler extends Thread {

	private ServerSocket serverSocketPubSub;
	private Boolean serverAlive;
	private ServerController server;

	public PubSubConnectionsHandler(ServerSocket serverSocketPubSub, Boolean serverAlive, ServerController server) {
		this.serverSocketPubSub = serverSocketPubSub;
		this.serverAlive = serverAlive;
		this.server = server;
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
			subscribeHandler = new SubscribeController(new SocketCommunicator(socket), server);
			Thread t = new Thread(subscribeHandler, "SubscribeControllerThread");
			t.start();
		}
	    try {
			serverSocketPubSub.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
