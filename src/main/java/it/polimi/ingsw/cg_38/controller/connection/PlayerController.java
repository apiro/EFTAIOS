package it.polimi.ingsw.cg_38.controller.connection;
import it.polimi.ingsw.cg_38.controller.GameController;
import it.polimi.ingsw.cg_38.controller.action.Action;
import it.polimi.ingsw.cg_38.controller.action.GameAction;
import it.polimi.ingsw.cg_38.controller.action.GameActionCreator;
import it.polimi.ingsw.cg_38.controller.event.Event;
import it.polimi.ingsw.cg_38.controller.event.GameEvent;
import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.controller.gameEvent.EventRetired;
import it.polimi.ingsw.cg_38.controller.logger.Logger;
import it.polimi.ingsw.cg_38.controller.logger.LoggerCLI;
import it.polimi.ingsw.cg_38.controller.notifyEvent.EventNotYourTurn;
import it.polimi.ingsw.cg_38.controller.notifyEvent.EventNotifyClosingTopic;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class PlayerController extends Thread  {

	private Communicator communicator;
	
	/**
	 * è un buffer sul PlayerController nel quale il Server mette in una coda i messaggi che il ServerController invia 
	 * al PlayerHandler. 
	 * Il PlayerController dopodichè si preoccuperà di inviare i messaggi al suo client usando il metodo
	 * send(String msg), che a seconda del Communicator che ho istanziato nel momento della creazione del PlayerController
	 * invierà il messaggio con le modalita prevista dal Communicator che ho scelto.
	 * */

	private Queue<Event> eventsToProcess;

	private Map<String, GameController> topics;
	
	private Logger logger = new LoggerCLI();

	public Queue<Event> getEventsToProcess() {
		return eventsToProcess;
	}

	public void setEventsToProcess(Queue<Event> eventsToProcess) {
		this.eventsToProcess = eventsToProcess;
	}
	
	public PlayerController(Communicator communicator, Queue<Event> toDispatch, Map<String, GameController> topics) throws IOException {
		//a questo passo la lista di topic(arraylist di gamecontroller)
		this.topics = topics;
		this.communicator = communicator;
		((SocketCommunicator)communicator).initCommunicator();
		this.setEventsToProcess(toDispatch);
	}
	
	@Override
	public void run() {
		while(true) {
			try {
				//quando il thread handler riceve un evento verifica se l'evento di risposta 
				//è broadcast o no se è broadcast lo aggiunge alla coda del server mentre se 
				//è personale lo processa direttmente qui e invia l'evento di risposta !
				Event evt = this.communicator.recieveEvent();
				logger.print("---------------------------------------------------------------------\n");
				logger.print("Game Event arrived !\n");
				logger.print("Parsing Event... : " + evt.toString());
				if(!((GameEvent)evt).getNotifyEventIsBroadcast()){
					List<NotifyEvent> callbackEvent = new ArrayList<NotifyEvent>();
					GameController gcFound = null;
					Action generatedAction = GameActionCreator.createGameAction(evt);
					gcFound = topics.get(evt.getGenerator().getName());
					if( evt.getGenerator().getName().equals(gcFound.getGameModel().getActualTurn().getCurrentPlayer().getName())) {
						//se l'evento viene dal giocatore del turno corrente
						synchronized(gcFound) {
							callbackEvent = gcFound.performUserCommands((GameAction)generatedAction);
						}
						if(callbackEvent == null) return;
					} else {
						return;
					}
					/**
					 * invece che essere un evento callbackevent sara un arraylist di eventi. qui prendo l'arraylist
					 * che ho popolato prima e guardo se l'evento di notifica è broacast o no. se lo è lo aggiungo al 
					 * buffer del server --> this.getEventsToProcess().add((Event) evt); se non lo è --> this.communicator.send(callbackEvent);
					 * */
					for(NotifyEvent e:callbackEvent) {
						//un solo evento personale e quanti ne voglio broadcast ! per ogni evento arrivato
						if(e instanceof EventNotifyClosingTopic) {
							synchronized(gcFound) {
								this.removeTopic(gcFound);
								gcFound.notify();
							}
						} else {
							if(e.isBroadcast()) {
								synchronized(gcFound) {
									gcFound.addEventToTheQueue(e);
									gcFound.sendNotifyEvent();
									gcFound.notify();
								}
							} else {
								this.communicator.send(e);
							}
						}
					}
				} else {
					this.getEventsToProcess().add((Event) evt);
				}
				Thread.currentThread().interrupt();
				try {
					synchronized(this.getEventsToProcess()) {
						this.getEventsToProcess().wait();
					}
				} catch (InterruptedException e) {
					return;
				}
			} catch (RemoteException e) {
				logger.print("Client probably disconnected, default is set him as retired !");
			} catch (IOException e1) {
				logger.print("Client probably disconnected, default is set him as retired !");
				
			}
		}
	}
	
	public void removeTopic(GameController gcFound) {
		List<String> toRemove = new ArrayList<String>();
		for(String topic:topics.keySet()) {
			if(topics.get(topic).getTopic().equals(gcFound.getTopic())) {
				toRemove.add(topic);
			}
		}
		for(String s:toRemove) {
			topics.remove(s);
			logger.print("---------------------------------------------------------------------\n");
			logger.print(s + " removed from topic " + gcFound.getTopic() + " !");
			logger.print("---------------------------------------------------------------------\n");
		}
		logger.print("CLOSING: " + gcFound.getTopic());
		logger.print("---------------------------------------------------------------------\n");
	}
}
