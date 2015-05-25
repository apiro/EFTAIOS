package it.polimi.ingsw.cg_38.controller;

import it.polimi.ingsw.cg_38.controller.event.GameEvent;
import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.model.Player;
import it.polimi.ingsw.cg_38.notifyEvent.*;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.ConcurrentLinkedQueue;

//l'oggetto SERVERVIEW che il server mette remotamente a disposizione dei client 
//i metodi di SERVERVIEW possono essere chiamati dai client quando esportano l'oggetto
// -> TRASMITEVENT(GAMEEVENT EVT) aggiunge alla coda di eventi da dispacciare del server
//		un evento di GIOCO.
// -> HANDLEEVENT
public class GameView extends UnicastRemoteObject implements RMIGameInterface {

	private static final long serialVersionUID = 1L;
	//coda di eventi di gioco esportata in questa vista limitata del server
	//aggiungendo un evento di gioco qui si aggiunge un evento da risolvere al server
	private ConcurrentLinkedQueue<GameEvent> queue;

	public GameView(ConcurrentLinkedQueue<GameEvent> queue) throws RemoteException {
		super();
		/*try {
			UnicastRemoteObject.exportObject(this, 2344);
		} catch (RemoteException e) {
			e.printStackTrace();
		}*/
		this.queue = queue;
	}
	
	public NotifyEvent grabEvent() {
		//now i return a test event, but here i should return the notifyevent 
		//directed to this client, searching it in all the notifyevent that the
		//server produces.
		return new EventAddedToGame(new Player("albi"),false, false);
	}

	@Override
	public void trasmitEvent(GameEvent evt) {
		queue.add(evt);
		synchronized(queue) {
			queue.notify();
		}
	}
}
