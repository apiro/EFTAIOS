package it.polimi.ingsw.cg_38.controller;

import it.polimi.ingsw.cg_38.controller.action.GameActionCreator;
import it.polimi.ingsw.cg_38.controller.action.Subscribe;
import it.polimi.ingsw.cg_38.controller.event.Event;
import it.polimi.ingsw.cg_38.controller.event.GameEvent;
import it.polimi.ingsw.cg_38.gameEvent.EventSubscribe;
import it.polimi.ingsw.cg_38.notifyEvent.EventAddedToGame;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class RegistrationView extends UnicastRemoteObject implements RMIRegistrationInterface {

	private static final long serialVersionUID = 1L;
	private ConcurrentLinkedQueue<Event> queue;
	private Event evt;
	private HashMap<String, GameController> topics;
	private ServerController server;

	public RegistrationView(ServerController server) throws RemoteException {
		super();
		this.server = server;
		this.queue = server.getToDispatch();
	}
	
	public RMIRemoteObjectInterface register(RMIRemoteObjectInterface clientView, EventSubscribe subEvent) throws RemoteException {
		
		this.evt = subEvent;
		System.out.println("---------------------------------------------------------------------");
		/**
		 * questo metodo prenderà come parametro la clientView e il topic a cui il client si vuole sottoscrivere, crea 
		 * un RMICommunicator. cerca il topic tra quelli che ci sono nella mappa che il server gli ha passato in fase di 
		 * costruzione di questo oggetto e aggiunge alla lista di communicator di quel topic trovato questo communicator che ha 
		 * appena creato
		 * --> il metodo public EventAddedToGame generalEventGenerator(Communicator c, ServerController server) della azione
		 * 	   di SUBSCRIBE che verifica se il giocatore si puo aggiungere al topic che ha scelto non sarà piu una azione ma sarà
		 * 	   direttamente un metodo del GameController, cosicche qui prima di aggiungere il communicator ad un GameController
		 * 	   ho la possibilità di verificare se posso farlo . se posso invio al giocatore che si è registrato un evento di 
		 * 	   addedToGame con true se no uno con false.
		 * */
		System.out.println("New Client is trying to connect using RMI ! ");
		ServerView view = null;
		Communicator c = new RMICommunicator(clientView);
		
		Subscribe subscribeAction = (Subscribe) GameActionCreator.createGameAction(subEvent);
		EventAddedToGame evt = null;
		
		try {
			
			evt = (EventAddedToGame) subscribeAction.generalEventGenerator(c, server);
			if(evt.isBroadcast()) {
				this.trasmitEventToPublisher(evt);
			} else {
				clientView.trasmitEvent(evt);
				return new ErrorView();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return new ServerView(server, clientView);
	}	
	
	@Override
	public void trasmitEventToPublisher(Event evt) {
		queue.add((GameEvent)evt);
		synchronized(queue) {
			queue.notify();
		}
	}
	
	@Override
	public boolean isLoginValid(String username) throws RemoteException {
		if(username.equals("test")) {
			return true;
		}
		return false;
	}
	
}
