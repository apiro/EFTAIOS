package it.polimi.ingsw.cg_38.client;

import it.polimi.ingsw.cg_38.controller.connection.SocketCommunicator;
import it.polimi.ingsw.cg_38.controller.event.Event;
import it.polimi.ingsw.cg_38.controller.event.GameEvent;
import it.polimi.ingsw.cg_38.controller.gameEvent.EventSubscribe;
import it.polimi.ingsw.cg_38.controller.logger.Logger;
import it.polimi.ingsw.cg_38.controller.logger.LoggerCLI;

import java.io.IOException;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ClientSocket extends Client implements Runnable {

	private Boolean clientAlive = true;
	private int clientServerPort= 4322;
	private ConcurrentLinkedQueue<Event> toSend;
	private ConcurrentLinkedQueue<Event> toProcess;
	private Thread pubSubReceiver;
	private Logger logger = new LoggerCLI();

	/**
	 * QUESTO OGGETTO INVIA AL SERVER I MESSAGGI CHE TROVA NELLA SUA CODA E GENERA IL THREAD DI RICEZIONE MESSAGGI
	 * PUB SUB
	 * */
	
	public ClientSocket(ConcurrentLinkedQueue<Event> toSend, ConcurrentLinkedQueue<Event> toProcess, EventSubscribe evt) {
		this.toSend = toSend;
		this.toProcess = toProcess;
		Subscriber subscriber = new Subscriber(evt, toProcess);
		pubSubReceiver = new Thread(subscriber, "PubSubReceiver");
		pubSubReceiver.start();
	}

	public Thread getPubSubReceiver() {
		return pubSubReceiver;
	}

	@Override
	public void run() {
		while(clientAlive) {
			Event msg = toSend.poll();
			if(msg != null) {
				try {
					 Socket socket = null;
					try {
						socket = new Socket(host, clientServerPort);
						communicator = new SocketCommunicator(socket);
						 ((SocketCommunicator)communicator).initCommunicator(); 
					} catch (IOException e) {
						e.printStackTrace();
					}
					communicator.send(msg);
					if(!((GameEvent)msg).getNotifyEventIsBroadcast()) {
						Event received = communicator.recieveEvent();
						this.toProcess.add(received);
					}
					communicator.closeCommunicator();
				} catch (RemoteException e) {
					logger.print("Problems with socket connection ! Check if the server is online ...");
				}
			}
		}
	}
}