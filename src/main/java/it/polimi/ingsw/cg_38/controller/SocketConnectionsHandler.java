package it.polimi.ingsw.cg_38.controller;

import it.polimi.ingsw.cg_38.controller.connection.SocketCommunicator;
import it.polimi.ingsw.cg_38.controller.event.Event;
import it.polimi.ingsw.cg_38.controller.logger.Logger;
import it.polimi.ingsw.cg_38.controller.logger.LoggerCLI;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ConcurrentLinkedQueue;

public class SocketConnectionsHandler extends Thread implements Observer {
	
	/**
	 * il seguente è un oggetto che gestisce la logica di creazione dei thread, questo oggetto per esempio gestisce la logica di creazione 
	 * di un thread quando una sua creazione è richiesta
	 **/
	private Boolean alive;
	private ServerSocket serverSocket;
	private ConcurrentLinkedQueue<Event> queue;
	private HashMap<String, GameController> topics;
	private Logger logger = new LoggerCLI();
	
	public SocketConnectionsHandler(ServerSocket serverSocket, ConcurrentLinkedQueue<Event> queue, Boolean alive, HashMap<String, GameController> topics) {
		this.topics = topics;
		this.alive = alive;
		this.serverSocket = serverSocket;
		this.queue = queue;
		
	}
	
	public void run() {
		alive = true;
		while(alive) {
			Socket socket = null;
			try {
				socket = serverSocket.accept();
			} catch (IOException e) {
				logger.print("Problems with the server socket ...");
			}
			PlayerController playerHandler = null;
			try {
				playerHandler = new PlayerController(new SocketCommunicator(socket), queue, topics);
				playerHandler.start();
				playerHandler.setName("PlayerControllerThread");
			} catch (IOException e) {
				logger.print("Problems with the creation of the thread ...");
			}		
		}
	    try {
			serverSocket.close();
		} catch (IOException e) {
			logger.print("Problems closing the server socket ...");
		}
	}
	
	@Override
	public void update(Observable o, Object arg) {
		this.alive = false;
	}
}
