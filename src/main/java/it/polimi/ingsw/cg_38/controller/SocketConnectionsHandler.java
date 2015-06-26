package it.polimi.ingsw.cg_38.controller;

import it.polimi.ingsw.cg_38.controller.connection.PlayerController;
import it.polimi.ingsw.cg_38.controller.connection.SocketCommunicator;
import it.polimi.ingsw.cg_38.controller.event.Event;
import it.polimi.ingsw.cg_38.controller.logger.Logger;
import it.polimi.ingsw.cg_38.controller.logger.LoggerCLI;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.Queue;
/** gestisce la logica di creazione dei thread, quando richiesta */
public class SocketConnectionsHandler extends Thread implements Observer {

	private Boolean alive;
	
	private ServerSocket serverSocket;
	
	private Queue<Event> queue;
	
	private Map<String, GameController> topics;
	
	private Logger logger = new LoggerCLI();
	
	/** inizializza tutte le variabili necessarie 
	 * 
	 * @param serverSocket serverSocket della connessione
	 * @param queue coda di eventi 
	 * @param true se il thread è vivo
	 * @topics mappa di tutti i topics del server
	 * */
	public SocketConnectionsHandler(ServerSocket serverSocket, Queue<Event> queue, Boolean alive, Map<String, GameController> topics) {
		this.topics = topics;
		this.alive = alive;
		this.serverSocket = serverSocket;
		this.queue = queue;
		
	}
	
	/** finchè il thread è vivo rimane in attesa di ricevere nuove connessioni via Socket creando, di
	 * conseguenza, un nuovo controllore del giocatore */
	@Override
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
				logger.print("A client is probably disconnected ...");
			}		
		}
	    try {
			serverSocket.close();
		} catch (IOException e) {
			logger.print("A client is probably disconnected ...");
		}
	}
	
	@Override
	public void update(Observable o, Object arg) {
		this.alive = false;
	}
}
