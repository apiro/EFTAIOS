package it.polimi.ingsw.cg_38.controller.connection;

import it.polimi.ingsw.cg_38.client.ErrorView;
import it.polimi.ingsw.cg_38.controller.ServerController;
import it.polimi.ingsw.cg_38.controller.action.GameActionCreator;
import it.polimi.ingsw.cg_38.controller.action.Subscribe;
import it.polimi.ingsw.cg_38.controller.event.Event;
import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.controller.gameEvent.EventSubscribe;
import it.polimi.ingsw.cg_38.controller.logger.Logger;
import it.polimi.ingsw.cg_38.controller.logger.LoggerCLI;
import it.polimi.ingsw.cg_38.controller.notifyEvent.EventAddedToGame;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class RegistrationView extends UnicastRemoteObject implements RMIRegistrationInterface {

	private static final long serialVersionUID = 1L;
	private Queue<NotifyEvent> queue = new ConcurrentLinkedQueue<NotifyEvent>();
	private ServerController server;
	private Logger logger = new LoggerCLI();

	public RegistrationView(ServerController server) throws RemoteException {
		super();
		this.server = server;
	}
	
	@Override
	public RMIRemoteObjectInterface register(RMIRemoteObjectInterface clientView, EventSubscribe subEvent) throws RemoteException {

		logger.print("---------------------------------------------------------------------");
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
			logger.print("Problems during trasmission of the event ... ");
		}
		
		return new ServerView(server, clientView);
	}	
	
	@Override
	public void trasmitEventToPublisher(Event evt) {
		
		this.queue = server.getTopics().get(evt.getGenerator().getName()).getBuffer();
		synchronized(queue) {
			queue.add((NotifyEvent)evt);
			try {
				server.getTopics().get(evt.getGenerator().getName()).sendNotifyEvent();
			} catch (IOException e) {
				logger.print("A client is probably disconnected ...");
			}
		}
		synchronized(queue) {
			queue.notify();
		}
	}
	
	@Override
	public boolean isLoginValid(String username) throws RemoteException {
		if(("test").equals(username)) {
			return true;
		}
		return false;
	}
	
}
