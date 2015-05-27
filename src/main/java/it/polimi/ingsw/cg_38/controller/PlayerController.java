package it.polimi.ingsw.cg_38.controller;
import it.polimi.ingsw.cg_38.controller.event.Event;
import it.polimi.ingsw.cg_38.controller.event.GameEvent;
import it.polimi.ingsw.cg_38.controller.event.GameEventType;
import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.gameEvent.EventSubscribeSocket;
import it.polimi.ingsw.cg_38.model.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
	
	public PlayerController(Communicator communicator, ConcurrentLinkedQueue<GameEvent> toDispatch) throws IOException {
		this.communicator = communicator;
		this.initCommunicator();
		this.setEventsToProcess(toDispatch);
	}

	public void initCommunicator() throws IOException{
		ObjectOutputStream out = new ObjectOutputStream(((SocketCommunicator)this.communicator).getSocket().getOutputStream());
		out.flush();
		ObjectInputStream in = new ObjectInputStream(((SocketCommunicator)this.communicator).getSocket().getInputStream());
		((SocketCommunicator)this.communicator).setOutputStream(out);
		((SocketCommunicator)this.communicator).setInputStream(in);
	}
	
	public void run() {
		while(true) {
			try {
				Event evt = this.communicator.recieveEvent();
				System.out.println("Recieving Event... : " + evt.toString());
				
				if((((GameEvent)evt).getType()).equals(GameEventType.subscribeSocket)){
					((EventSubscribeSocket) evt).setSocket(((SocketCommunicator) communicator).getSocket()); 
				}
				
				this.getEventsToProcess().add((GameEvent) evt);
				try {
					synchronized(this.getEventsToProcess()) {
						this.getEventsToProcess().wait();
					}
				} catch (InterruptedException e) {
					System.err.println("Cannot wait on the queue!");
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
