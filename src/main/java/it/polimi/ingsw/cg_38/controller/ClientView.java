package it.polimi.ingsw.cg_38.controller;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.ConcurrentLinkedQueue;

import it.polimi.ingsw.cg_38.controller.event.Event;

public class ClientView extends UnicastRemoteObject implements RMIRemoteObjectInterface {
	
	private ConcurrentLinkedQueue<Event> queue;

	public ClientView(ConcurrentLinkedQueue<Event> queue) throws RemoteException {
		
		super();
		this.setQueue(queue);
		
	}

	public ConcurrentLinkedQueue<Event> getQueue() {
		return queue;
	}

	public void setQueue(ConcurrentLinkedQueue<Event> queue) {
		this.queue = queue;
	}
	
	private static final long serialVersionUID = 1L;

	@Override
	public void trasmitEvent(Event evt) throws RemoteException {
		
		this.getQueue().add((Event)evt);
		synchronized(queue) {
			queue.notify();
		}

	}

}
