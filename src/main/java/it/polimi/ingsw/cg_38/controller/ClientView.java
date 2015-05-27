package it.polimi.ingsw.cg_38.controller;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.ConcurrentLinkedQueue;

import it.polimi.ingsw.cg_38.controller.event.Event;
import it.polimi.ingsw.cg_38.controller.event.GameEvent;
import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;


public class ClientView extends UnicastRemoteObject implements RMIRemoteObjectInterface {
	
	private ConcurrentLinkedQueue<NotifyEvent> queue;

	public ClientView(ConcurrentLinkedQueue<NotifyEvent> queue) throws RemoteException {
		
		super();
		this.setQueue(queue);
		
	}

	public ConcurrentLinkedQueue<NotifyEvent> getQueue() {
		return queue;
	}

	public void setQueue(ConcurrentLinkedQueue<NotifyEvent> queue) {
		this.queue = queue;
	}
	
	private static final long serialVersionUID = 1L;

	@Override
	public void trasmitEvent(Event evt) throws RemoteException {
		
		this.getQueue().add((NotifyEvent)evt);
		synchronized(queue) {
			queue.notify();
		}

	}

}
