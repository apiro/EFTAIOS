package it.polimi.ingsw.cg_38.client;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.ConcurrentLinkedQueue;

import it.polimi.ingsw.cg_38.controller.connection.RMIRemoteObjectInterface;
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
		
		synchronized(queue) {
			queue.add((Event)evt);
		}
		synchronized(queue) {
			queue.notify();
		}

	}

	@Override
	public void processEvent(Event evt) throws RemoteException {
		
	}

}
