package it.polimi.ingsw.cg_38.controller;
import it.polimi.ingsw.cg_38.controller.event.Event;
import it.polimi.ingsw.cg_38.controller.event.GameEvent;
import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import  it.polimi.ingsw.cg_38.model.*;

import java.rmi.RemoteException;
import java.util.concurrent.ConcurrentLinkedQueue;

public class PlayerController implements Runnable {

	private Communicator communicator;
	
	/**
	 * è un buffer sul PlayerController nel quale il Server mette in una coda i messaggi che il ServerController invia 
	 * al PlayerHandler. 
	 * Il PlayerController dopodichè si preoccuperà di inviare i messaggi al suo client usando il metodo
	 * send(String msg), che a seconda del Communicator che ho istanziato nel momento della creazione del PlayerController
	 * invierà il messaggio con le modalita prevista dal Communicator che ho scelto.
	 * */

	private Player player;

	private ConcurrentLinkedQueue<GameEvent> eventsToProcess;
	private ConcurrentLinkedQueue<NotifyEvent> eventsToSend;

	
	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public ConcurrentLinkedQueue<GameEvent> getEventsToProcess() {
		return eventsToProcess;
	}

	public void setEventsToProcess(ConcurrentLinkedQueue<GameEvent> eventsToProcess) {
		this.eventsToProcess = eventsToProcess;
	}

	public ConcurrentLinkedQueue<NotifyEvent> getEventsToSend() {
		return eventsToSend;
	}

	public void setEventsToSend(ConcurrentLinkedQueue<NotifyEvent> eventsToSend) {
		this.eventsToSend = eventsToSend;
	}
	
	public PlayerController(Communicator communicator, ConcurrentLinkedQueue<GameEvent> toDispatch, ConcurrentLinkedQueue<NotifyEvent> toDistribute) {
		this.communicator = communicator;
		this.setEventsToProcess(toDispatch);
		this.setEventsToSend(toDistribute);
	}

	public void run() {
		while(true) {
			try {
				Event evt = this.communicator.recieveEvent();
				this.getEventsToProcess().add((GameEvent) evt);
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
