package it.polimi.ingsw.cg_38.client;

import it.polimi.ingsw.cg_38.controller.connection.Communicator;
import it.polimi.ingsw.cg_38.controller.connection.SocketCommunicator;
import it.polimi.ingsw.cg_38.controller.event.Event;
import it.polimi.ingsw.cg_38.controller.gameEvent.EventSubscribe;
import it.polimi.ingsw.cg_38.controller.logger.Logger;
import it.polimi.ingsw.cg_38.controller.logger.LoggerCLI;

import java.io.IOException;
import java.net.Socket;
import java.util.Queue;

/**
 * Thread che si occupa di ricevere i messaggi dal PUB SUB e che invia un unico messaggio iniziale sul socket 
 * del pub sub
 * */
public class Subscriber implements Runnable {

	private int portPubSub = 3111;
	private String host = "127.0.0.1";
	private Queue<Event> toProcess;
	private Boolean clientAlive = true;
	private EventSubscribe evt;
	private Communicator communicator;
	private Logger logger = new LoggerCLI();
	
	public Subscriber(EventSubscribe evt, Queue<Event> toProcess) {
		this.toProcess = toProcess;
		this.evt = evt;
		this.initSubscriber();
	}
	
	/**
	 * Crea il socket con la serversocket pub sub e rimane in ascolto su questo socket per ricevere i messaggi 
	 * in broadcast del server
	 * */
	public void initSubscriber() {
		Socket socketPubSub;
		
		try {
			socketPubSub = new Socket(host, portPubSub);
			communicator = new SocketCommunicator(socketPubSub);
			logger.print("Creating a socket with the PUB/SUB serverSocket !");
			((SocketCommunicator)communicator).initCommunicator();
			logger.print("Sending " + evt.toString() + " ...");
			((SocketCommunicator)communicator).getOutputStream().writeObject(evt);
			((SocketCommunicator)communicator).setOutputStream(null);
		} catch (IOException e) {
			logger.print("Problems with socket connection ! Check if the server is online ...");
		}
	}
	
	@Override
	public void run() {
		while(clientAlive) {
			try {
				Event notEvt = communicator.recieveEvent();
				if(notEvt != null) {
					toProcess.add(notEvt);
					synchronized(toProcess) {
						toProcess.notify();
					}
				}
			} catch (IOException e) {
				logger.print("A client is probably disconnected ...");
			}
		}
	}

}
