package it.polimi.ingsw.cg_38.controller;

import it.polimi.ingsw.cg_38.controller.event.GameEvent;
import it.polimi.ingsw.cg_38.gameEvent.EventSubscribe;

import java.rmi.RemoteException;
import java.util.concurrent.ConcurrentLinkedQueue;

public class RegistrationView implements RMIRegistrationInterface {

	private ConcurrentLinkedQueue<GameEvent> queue;

	public RegistrationView(ConcurrentLinkedQueue<GameEvent> queue) {
		this.queue = queue;
	}
	
	public GameView register(EventSubscribe evt) {
		
		queue.add(evt);
		GameView view = null;
		try {
			view = new GameView(/*client,*/ queue);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return view;
	}
	
}
