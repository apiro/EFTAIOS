package it.polimi.ingsw.cg_38.controller;

import it.polimi.ingsw.cg_38.controller.connection.SocketCommunicator;
import it.polimi.ingsw.cg_38.controller.event.Event;

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
	/*private ExecutorService executor;*/
	private Boolean alive;
	private ServerSocket serverSocket;
	private ConcurrentLinkedQueue<Event> queue;
	private HashMap<String, GameController> topics;
	
	public SocketConnectionsHandler(ServerSocket serverSocket, ConcurrentLinkedQueue<Event> queue, Boolean alive, HashMap<String, GameController> topics) {
		/*this.executor = Executors.newCachedThreadPool();*/
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
				e.printStackTrace();
			}
			//la riga dopo crea un thread per la gestione del socket arrivato
			PlayerController playerHandler = null;
			try {
				playerHandler = new PlayerController(new SocketCommunicator(socket), queue, topics);
				playerHandler.start();
				playerHandler.setName("PlayerControllerThread");
			} catch (IOException e) {
				e.printStackTrace();
			}
			//fa partire il thread con la logia del ExecutorService
			/*executor.submit(playerHandler);*/			
		}
	    /*executor.shutdown();*/
	    try {
			serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//TODO !!!!
	@Override
	public void update(Observable o, Object arg) {
		this.alive = false;
	}
}
