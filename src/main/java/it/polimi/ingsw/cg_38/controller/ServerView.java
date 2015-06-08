package it.polimi.ingsw.cg_38.controller;

import it.polimi.ingsw.cg_38.controller.event.Event;
import it.polimi.ingsw.cg_38.controller.event.GameEvent;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.ConcurrentLinkedQueue;

//l'oggetto SERVERVIEW che il server mette remotamente a disposizione dei client 
//i metodi di SERVERVIEW possono essere chiamati dai client quando esportano l'oggetto
// -> TRASMITEVENT(GAMEEVENT EVT) aggiunge alla coda di eventi da dispacciare del server
//		un evento di GIOCO.
// -> HANDLEEVENT
public class ServerView extends UnicastRemoteObject implements RMIRemoteObjectInterface {

	private static final long serialVersionUID = 1L;
	//coda di eventi di gioco esportata in questa vista limitata del server
	//aggiungendo un evento di gioco qui si aggiunge un evento da risolvere al server
	private ConcurrentLinkedQueue<Event> queue;
	private ServerController server;

	public ServerView(ServerController server) throws RemoteException {
		super();
		/*try {
			UnicastRemoteObject.exportObject(this, 2344);
		} catch (RemoteException e) {
			e.printStackTrace();
		}*/
		this.server = server;
		this.queue = server.getToDispatch();
	}
	@Override
	public void trasmitEvent(Event evt) {
		if(((GameEvent)evt).getNotifyEventIsBroadcast()) {
			queue.add((GameEvent)evt);
			synchronized(queue) {
				queue.notify();
			}
		} else {
			//come nel theradHandler performo l'evento all'interno di questo ramo di else se l'evento di ritorno non è broadcast
		}
	}
}
