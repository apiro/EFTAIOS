package it.polimi.ingsw.cg_38.controller;

import it.polimi.ingsw.cg_38.controller.event.Event;
import it.polimi.ingsw.cg_38.controller.event.GameEvent;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ServerView extends UnicastRemoteObject implements RMIRemoteObjectInterface {

	/**
	 * OGGETTO REMOTO CHE OFFRE IL SERVER AI CLIENT E CHE PERMETTE AL CLIENT DI INVIARE EVENTI AL SERVER
	 * --> SE L'EVENTO DI NOTIFICA GENERATO DAL PROCESSING DEL SERVER HA IL BOOLEANO BROADCAST A TRUE ALLORA 
	 * 	   VERRA AGGIUNTO AL TOPIC RELATIVO, SE NON CE L'HA A TRUE MA E' UN MESSAGGIO DI RISPOSTA PERSONALE ALLORA
	 *	   L'EVENTO VERRA' PROCESSATO DIRETTAMENTE QUI NELLA SERVERVIEW E VERRA' INVIATO L'EVENTO DI NOTIFICA AL CLIENT
	 *     COME PARAMETRO DI RITORNO DEL METODO: public NotifyEvent trasmitEvent(Event evt){}   
	 * */
	
	private static final long serialVersionUID = 1L;
	//coda di eventi di gioco esportata in questa vista limitata del server
	//aggiungendo un evento di gioco qui si aggiunge un evento da risolvere al server
	private ConcurrentLinkedQueue<Event> queue;

	public ServerView(ConcurrentLinkedQueue<Event> queue/*,ArrayList<GameModel> gm*/) throws RemoteException {
		super();
		
		this.queue = queue;
	}
	
	@Override
	public void trasmitEvent(Event evt/*, RMIRemoteObjectInterface ClientView*/) {
		//Se l'evento genera un evento di notifica broadcast allora lo aggiungo alla queue se no lo processo qui 
		//infatti questo metodo dovrà ritornare un evento di tipo notify( che potrà essere null nel caso evt sia broadcast,
		//che potrà essere un evento realmente costruito nel caso il notifyevent sia personale.
		
		queue.add((GameEvent)evt);
		synchronized(queue) {
			queue.notify();
		}
	}
	
}
