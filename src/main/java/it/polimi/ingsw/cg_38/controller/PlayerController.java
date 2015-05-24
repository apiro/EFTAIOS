package it.polimi.ingsw.cg_38.controller;
import it.polimi.ingsw.cg_38.controller.action.GameAction;
import it.polimi.ingsw.cg_38.controller.event.GameEvent;
import  it.polimi.ingsw.cg_38.model.*;

import java.util.*;
import java.io.PrintWriter;
import java.util.Observable;
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
	private ConcurrentLinkedQueue<GameEvent> buffer;

	private Player player;
	
	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public PlayerController(Communicator communicator) {
		this.communicator = communicator;
	}

	public void run() {
		// TODO Auto-generated method stub
		
	}

	/**
	 * StringTokenizer tokenizer = new StringTokenizer(String s);
	 * 
	 * tokenizer.nextToken(); ---> questo oggetto crea un oggetto wrapper alla stringa che gli passo e legge fino al primo 
	 * 							   spazio. La prima stringa(il primo token) verra restituito con nextToken() poi se gfaccio
	 * 							   ancora nextToken() mi rida la seconda parola della stringa.
	 **/
	
}
