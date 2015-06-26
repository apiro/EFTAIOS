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
import it.polimi.ingsw.cg_38.controller.notifyEvent.EventNotifyClosingTopic;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

/**
 * E' l'oggetto SERVERVIEW che il server mette remotamente a disposizione dei client 
 * i metodi di SERVERVIEW possono essere chiamati dai client quando esportano l'oggetto
 * -> TRASMITEVENT(GAMEEVENT EVT) aggiunge alla coda di eventi da dispacciare del server
 *		un evento di GIOCO.
 * -> HANDLEEVENT
 * 
 **/
public class ServerView extends UnicastRemoteObject implements RMIRemoteObjectInterface {

	private static final long serialVersionUID = 1L;
	
	/**coda di eventi di gioco esportata in questa vista limitata del server
	  *aggiungendo un evento di gioco qui si aggiunge un evento da risolvere al server
	  **/
	private Queue<Event> queue;
	private ServerController server;
	private RMICommunicator communicator;
	private Logger logger = new LoggerCLI();

	public ServerView(ServerController server, RMIRemoteObjectInterface clientView) throws RemoteException {
		super();
		this.communicator = new RMICommunicator(clientView);
		this.server = server;
		this.queue = server.getToDispatch();
	}
	
	@Override
	public void trasmitEvent(Event evt) throws RemoteException {
		logger.print("---------------------------------------------------------------------\n");
		logger.print("[SV]Game Event arrived !\n");
		logger.print("Parsing Event... : " + evt.toString());
		
		if(((GameEvent)evt).getNotifyEventIsBroadcast()) {
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
	
	/**
	 * quando riceve un evento verifica se l'evento di risposta 
	 * è broadcast o no se è broadcast lo aggiunge alla coda del server mentre se 
	 * è personale lo processa direttmente qui e invia l'evento di risposta !
	 * genera un arraylist di eventi. qui prendo l'arraylist
	 * che ho popolato prima e guardo se l'evento di notifica è broacast o no. se lo è lo aggiungo al 
	 * buffer del server --> this.getEventsToProcess().add((Event) evt); se non lo è --> this.communicator.send(callbackEvent);
	 **/ 
	@Override
	public void processEvent(Event evt) throws RemoteException {
		List<NotifyEvent> callbackEvent = new ArrayList<NotifyEvent>();
		GameController gcFound = null;
		Action generatedAction = GameActionCreator.createGameAction(evt);
		gcFound = server.getTopics().get(evt.getGenerator().getName());
		if( evt.getGenerator().getName().equals(gcFound.getGameModel().getActualTurn().getCurrentPlayer().getName())) {
			synchronized(gcFound.getGameModel()) {
				callbackEvent = gcFound.performUserCommands((GameAction)generatedAction);
			}
			gcFound.getGameModel().notify();
			if(callbackEvent == null) {
				System.out.println("callbackEvent == nullsline 80");
				return;
			}
		} else {
			return;
		}
		for(NotifyEvent e:callbackEvent) {
			if(e instanceof EventNotifyClosingTopic) {
				synchronized(gcFound) {
					server.removeTopic(gcFound);
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
					}
				} else {
					this.communicator.send(e);
				}
			}
		}
	}
}
