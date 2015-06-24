package it.polimi.ingsw.cg_38.controller.pubsub;

import it.polimi.ingsw.cg_38.controller.ServerController;
import it.polimi.ingsw.cg_38.controller.connection.SocketCommunicator;
import it.polimi.ingsw.cg_38.controller.logger.Logger;
import it.polimi.ingsw.cg_38.controller.logger.LoggerCLI;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class PubSubConnectionsHandler extends Thread {

	private ServerSocket serverSocketPubSub;
	private Boolean serverAlive;
	private ServerController server;
	private Logger logger = new LoggerCLI();

	public PubSubConnectionsHandler(ServerSocket serverSocketPubSub, Boolean serverAlive, ServerController server) {
		this.serverSocketPubSub = serverSocketPubSub;
		this.serverAlive = serverAlive;
		this.server = server;
	}
	
	@Override
	public void run() {
		serverAlive = true;
		while(serverAlive) {
			Socket socket = null;
			try {
				socket = serverSocketPubSub.accept();
			} catch (IOException e) {
				logger.print("Socket arrived but there were some problems with the creation of server side socket ...");
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
			logger.print("Problems during closing of the socket ... ");
		}
	}	
}
