package it.polimi.ingsw.cg_38.controller;

import it.polimi.ingsw.cg_38.controller.event.Event;
import it.polimi.ingsw.cg_38.gameEvent.EventSubscribe;

import java.io.IOException;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ClientSocket extends Client implements Runnable {

	private ConcurrentLinkedQueue<Event> queue;
	private Boolean clientAlive;
	private int clientServerPort;
	private EventSubscribe evt;

	/**
	 * QUESTO OGGETTO INVIA AL SERVER I MESSAGGI CHE TROVA NELLA SUA CODA.
	 * */
	
	public ClientSocket(ConcurrentLinkedQueue<Event> queue, EventSubscribe evt) {
		this.queue = queue;
		this.evt = evt;
	}

	@Override
	public void run() {
		while(clientAlive) {
			Event msg = queue.poll();
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
				} catch (RemoteException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
