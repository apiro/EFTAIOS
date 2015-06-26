package it.polimi.ingsw.cg_38.controller.connection;

import it.polimi.ingsw.cg_38.controller.GameController;
import it.polimi.ingsw.cg_38.controller.ServerController;
import it.polimi.ingsw.cg_38.controller.action.Action;
import it.polimi.ingsw.cg_38.controller.action.GameAction;
import it.polimi.ingsw.cg_38.controller.action.GameActionCreator;
import it.polimi.ingsw.cg_38.controller.event.Event;
import it.polimi.ingsw.cg_38.controller.event.GameEvent;
import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.controller.logger.Logger;
import it.polimi.ingsw.cg_38.controller.logger.LoggerCLI;
import it.polimi.ingsw.cg_38.controller.notifyEvent.EventNotYourTurn;
import it.polimi.ingsw.cg_38.controller.notifyEvent.EventNotifyClosingTopic;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

//l'oggetto SERVERVIEW che il server mette remotamente a disposizione dei client 
//i metodi di SERVERVIEW possono essere chiamati dai client quando esportano l'oggetto
// -> TRASMITEVENT(GAMEEVENT EVT) aggiunge alla coda di eventi da dispacciare del server
//		un evento di GIOCO.
// -> HANDLEEVENT
public class ServerView extends UnicastRemoteObject implements RMIRemoteObjectInterface {

	private static final long serialVersionUID = 1L;
	//coda di eventi di gioco esportata in questa vista limitata del server
	//aggiungendo un evento di gioco qui si aggiunge un evento da risolvere al server
	private Queue<Event> queue;
	private ServerController server;
	private RMICommunicator communicator;
	private Logger logger = new LoggerCLI();

	public ServerView(ServerController server, RMIRemoteObjectInterface clientView) throws RemoteException {
		super();
		/*try {
			UnicastRemoteObject.exportObject(this, 2344);
		} catch (RemoteException e) {
			e.printStackTrace();
		}*/
		
		this.communicator = new RMICommunicator(clientView);
		this.server = server;
		this.queue = server.getToDispatch();
	}
	
	@Override
	public void trasmitEvent(Event evt) throws RemoteException {
		if(((GameEvent)evt).getNotifyEventIsBroadcast()) {
			//se l'evento che il client vuole aggiungere presuppone un evento di risposta
			//di tipo broadcast allora lo aggiungo alla coda del topic per poi inviarlo
			//via publish.
			synchronized(queue) {
				queue.add((GameEvent)evt);
			}
			synchronized(queue) {
				queue.notify();
			}
		} else {
			this.processEvent(evt);
		}
	}
	
	@Override
	public void processEvent(Event evt) throws RemoteException {
		List<NotifyEvent> callbackEvent = new ArrayList<NotifyEvent>();
		GameController gcFound = null;
		Action generatedAction = GameActionCreator.createGameAction(evt);
		gcFound = server.getTopics().get(evt.getGenerator().getName());
		if( evt.getGenerator().getName().equals(gcFound.getGameModel().getActualTurn().getCurrentPlayer().getName())) {
			synchronized(gcFound) {
				callbackEvent = gcFound.performUserCommands((GameAction)generatedAction);
			}
			if(callbackEvent == null) return;
		} else {
			return;
		}
		for(NotifyEvent e:callbackEvent) {
			if(e instanceof EventNotifyClosingTopic) {
				synchronized(gcFound) {
					server.removeTopic(gcFound);
					gcFound.notify();
				}
			} else {
				if(e.isBroadcast()) {
					synchronized(gcFound) {
						gcFound.addEventToTheQueue(e);
						try {
							gcFound.sendNotifyEvent();
						} catch (IOException e1) {
							logger.print("A client is probably disconnected ...");
						}
						gcFound.notify();
					}
				} else {
					this.communicator.send(e);
				}
			}
		}
	}
}
