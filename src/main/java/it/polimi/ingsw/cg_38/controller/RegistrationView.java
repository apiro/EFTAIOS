package it.polimi.ingsw.cg_38.controller;

import it.polimi.ingsw.cg_38.controller.event.GameEvent;
import it.polimi.ingsw.cg_38.gameEvent.EventSubscribe;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.ConcurrentLinkedQueue;

public class RegistrationView extends UnicastRemoteObject implements RMIRegistrationInterface {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ConcurrentLinkedQueue<GameEvent> queue;

	public RegistrationView(ConcurrentLinkedQueue<GameEvent> queue) throws RemoteException {
		super();
		this.queue = queue;
	}
	
	public ServerView register(/*EventSubscribe evt*/) throws RemoteException {
		
		/*queue.add(evt);*/
		ServerView view = null;
		view = new ServerView(queue);
		return view;
	}
	
	@Override
	public boolean isLoginValid(String username) throws RemoteException {
		if(username.equals("test")) {
			return true;
		}
		return false;
	}
	
}
