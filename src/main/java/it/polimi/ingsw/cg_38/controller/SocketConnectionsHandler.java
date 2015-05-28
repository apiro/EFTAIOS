package it.polimi.ingsw.cg_38.controller;

import it.polimi.ingsw.cg_38.controller.event.Event;
import it.polimi.ingsw.cg_38.controller.event.GameEvent;
import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SocketConnectionsHandler extends Thread implements Observer {
	
	/**
	 * il seguente è un oggetto che gestisce la logica di creazione dei thread, questo oggetto per esempio gestisce la logica di creazione 
	 * di un thread quando una sua creazione è richiesta
	 **/
	/*private ExecutorService executor;*/
	private Boolean serverAlive = true;
	private ServerSocket serverSocket;
	private ConcurrentLinkedQueue<Event> queue;
	
	public SocketConnectionsHandler(ServerSocket serverSocket, ConcurrentLinkedQueue<Event> queue) {
		/*this.executor = Executors.newCachedThreadPool();*/
		this.serverSocket = serverSocket;
		this.queue = queue;
		
	}
	
	public void run() {
		
		while(serverAlive) {
			Socket socket = null;
			try {
				socket = serverSocket.accept();
			} catch (IOException e) {
				e.printStackTrace();
			}
			//la riga dopo crea un thread per la gestione del socket arrivato
			PlayerController playerHandler = null;
			try {
				System.out.println("New Client Connected Using Socket ! ");
				playerHandler = new PlayerController(new SocketCommunicator(socket), queue);
				playerHandler.start();
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
		this.serverAlive = false;
	}
}
