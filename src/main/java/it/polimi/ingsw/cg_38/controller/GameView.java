package it.polimi.ingsw.cg_38.controller;

import it.polimi.ingsw.cg_38.controller.event.Event;
import it.polimi.ingsw.cg_38.controller.event.GameEvent;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.ConcurrentLinkedQueue;

public class GameView implements RMIGameInterface {

	/*private RMIClient client;*/
	private ConcurrentLinkedQueue<GameEvent> queue;

	public GameView(/*RMIClient client,*/ ConcurrentLinkedQueue<GameEvent> queue) throws RemoteException {
		try {
			UnicastRemoteObject.exportObject(this, 2344);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*this.client = client;*/
		this.queue = queue;
	}
	
	public void handleEvent(GameEvent evt) {
		queue.add(evt);
		synchronized(queue) {
			queue.notify();
		}
	}

	@Override
	public void trasmitEvent(Event evt) {
		// TODO Auto-generated method stub
		
	}
}
