package it.polimi.ingsw.cg_38.controller;

import it.polimi.ingsw.cg_38.controller.action.SubscribeRMI;
import it.polimi.ingsw.cg_38.controller.event.Event;
import it.polimi.ingsw.cg_38.gameEvent.EventSubscribe;
import it.polimi.ingsw.cg_38.model.Player;
import it.polimi.ingsw.cg_38.notifyEvent.EventAddedToGame;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.xml.parsers.ParserConfigurationException;

public class RegistrationView extends UnicastRemoteObject implements RMIRegistrationInterface {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ConcurrentLinkedQueue<Event> queue;
	private ServerController server;
	private EventSubscribe evt;

	public RegistrationView(ConcurrentLinkedQueue<Event> queue, ServerController server) throws RemoteException {
		
		super();
		this.server = server;
		this.queue = queue;
	}
	
	public ServerView register(RMIRemoteObjectInterface clientView, EventSubscribe subEvent) throws RemoteException {
		
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
		/*System.out.println("New Client Connected Using RMI ! ");*/
		Communicator c = new RMICommunicator(clientView);
		for(GameController gc:server.getTopics().values()) {
			if(gc.getTopic().equals(subEvent.getRoom()) && gc.canAccept(subEvent.getGenerator())) {
				ServerView view = null;
				view = new ServerView(queue);
				return view;
			}
		}
	}
	
	@Override
	public boolean isLoginValid(String username) throws RemoteException {
		if(username.equals("test")) {
			return true;
		}
		return false;
	}
	
	public EventAddedToGame generalEventGenerator(Communicator c, ServerController server) throws ParserConfigurationException, Exception {
		
		for(GameController gc:server.getTopics().values()) {
			if(gc.getGameModel().getGamePlayers().contains(this.evt.getGenerator())) {
				gc.getSubscribers().add(c);
				server.getTopics().put(this.evt.getGenerator().getName(), gc);
				return new EventAddedToGame(this.evt.getGenerator(), false, false);
			}
		}
		if(this.isPossible(server)) {
			//il topic proposto è tra i topic già presenti
			//E' LA FASE DI ACCEPTING
			for(GameController gc:server.getTopics().values()) {
				if(gc.getTopic().equals(this.evt.getRoom())) {
					if(/*gc.getGameModel().getGamePlayers().size()<8 &&
							gc.getCanAcceptOtherPlayers()*/ gc.getGameModel().getGameState().equals(GameState.ACCEPTING) ) {
						gc.getSubscribers().add(c);
						gc.getGameModel().getGamePlayers().add(this.evt.getGenerator());
						server.getTopics().put(this.evt.getGenerator().getName(), gc);
						return new EventAddedToGame(this.evt.getGenerator(), true, true);
					}  else {
						gc.getSubscribers().add(c);
						server.getTopics().put(this.evt.getGenerator().getName(), gc);
						return new EventAddedToGame(this.evt.getGenerator(), false, false);	
					}
				}
			}
		}
		//il topic proposto NON è tra le topic già presenti
		//E' LA FASE DI INIT DEL GIOCO ! STATO 0 DEL GIOCO, QUANDO UN GIOCATORE RICHIEDE DI GIOCARE IN UNA ROOM NON PRESENTE
		GameController newGc = server.initAndStartANewGame(this.evt.getMap(), this.evt.getRoom());
		server.addObserver(newGc);
		newGc.getSubscribers().add(c);
		newGc.getGameModel().getGamePlayers().add(this.evt.getGenerator());
		server.getTopics().put(this.evt.getGenerator().getName(), newGc);
		newGc.getGameModel().setGameState(GameState.ACCEPTING);
		
		Thread waitingRoomController = new Thread(new WaitingRoomController(newGc), "WaitingRoomControllerThread");
		waitingRoomController.start();
		
		return new EventAddedToGame(this.evt.getGenerator(), true, true);
	}
	
	public Boolean isPossible(ServerController server) {
		//true -> giocatore chiede room gia presente false->giocatore chiede room da istanziare
		Boolean found = false;
		
		for(GameController gc:server.getTopics().values()) {
			if(gc.getTopic().equals(this.evt.getRoom())) {
				found = true;
			}
		}
		return found;
	}
}
