package it.polimi.ingsw.cg_38.controller;
import it.polimi.ingsw.cg_38.controller.action.Action;
import it.polimi.ingsw.cg_38.controller.action.GameAction;
import it.polimi.ingsw.cg_38.controller.action.GameActionCreator;
import it.polimi.ingsw.cg_38.controller.event.Event;
import it.polimi.ingsw.cg_38.controller.event.GameEvent;
import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.model.*;
import it.polimi.ingsw.cg_38.notifyEvent.EventNotYourTurn;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class PlayerController extends Thread  {

	private Communicator communicator;
	
	/**
	 * è un buffer sul PlayerController nel quale il Server mette in una coda i messaggi che il ServerController invia 
	 * al PlayerHandler. 
	 * Il PlayerController dopodichè si preoccuperà di inviare i messaggi al suo client usando il metodo
	 * send(String msg), che a seconda del Communicator che ho istanziato nel momento della creazione del PlayerController
	 * invierà il messaggio con le modalita prevista dal Communicator che ho scelto.
	 * */

	private PlayerClient player;

	private ConcurrentLinkedQueue<Event> eventsToProcess;

	private HashMap<String, GameController> topics;

	
	public PlayerClient getPlayer() {
		return player;
	}

	public void setPlayer(PlayerClient player) {
		this.player = player;
	}

	public ConcurrentLinkedQueue<Event> getEventsToProcess() {
		return eventsToProcess;
	}

	public void setEventsToProcess(ConcurrentLinkedQueue<Event> eventsToProcess) {
		this.eventsToProcess = eventsToProcess;
	}
	
	public PlayerController(Communicator communicator, ConcurrentLinkedQueue<Event> toDispatch, HashMap<String, GameController> topics) throws IOException {
		//a questo passo la lista di topic(arraylist di gamecontroller)
		this.topics = topics;
		this.communicator = communicator;
		this.setEventsToProcess(toDispatch);
	}
	
	public void run() {
		while(true) {
			try {
				//quando il thread handler riceve un evento verifica se l'evento di risposta 
				//è broadcast o no se è broadcast lo aggiunge alla coda del server mentre se 
				//è personale lo processa direttmente qui e invia l'evento di risposta !
				Event evt = this.communicator.recieveEvent();
				if(!((GameEvent)evt).getNotifyEventIsBroadcast()){
					NotifyEvent callbackEvent = null;
					GameController gcFound = null;
					Action generatedAction = GameActionCreator.createGameAction(evt);
					gcFound = topics.get(evt.getGenerator().getName());
					if( evt.getGenerator().getName().equals(gcFound.getGameModel().getActualTurn().getCurrentPlayer().getName())) {
						//se l'evento viene dal giocatore del turno corrente
						callbackEvent = gcFound.performUserCommands((GameAction)generatedAction);
					} else {
						//se l'evento non viene dal gicatore del turno (qualcuno ha inviato un evento fuori turno)
						callbackEvent = new EventNotYourTurn(evt.getGenerator());
					}
					this.communicator.send(evt);
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
				e.printStackTrace();
			}
		}
	}

	/**
	 * StringTokenizer tokenizer = new StringTokenizer(String s);
	 * 
	 * tokenizer.nextToken(); ---> questo oggetto crea un oggetto wrapper alla stringa che gli passo e legge fino al primo 
	 * 							   spazio. La prima stringa(il primo token) verra restituito con nextToken() poi se gfaccio
	 * 							   ancora nextToken() mi rida la seconda parola della stringa.
	 **/
	
}
