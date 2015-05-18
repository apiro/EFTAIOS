package it.polimi.ingsw.cg_38.controller;
import  it.polimi.ingsw.cg_38.model.*;

import java.util.*;

/**
 * 
 */
public class PlayerController {

    /**
     * 
     */
    public PlayerController(Player player) {
    	this.setPlayer(player);
    }

    /**
     * 
     */
    public Player player;

    /**
     * @return
     */
    public void run() {
        // TODO implement here
    	/**
    	 * il metodo run viene chiamato quando la partita è iniziata e il giocatore inizia a giocare ed è passata la fase 
    	 * di init della partita
    	 * 
    	 * riceve le azioni dal client corrispondente e le performa con performusercomands che gia dentro di se fa il 
    	 * controllo sulla effettiva eseguibilita della azione
    	 * 
    	 * */
    	
    	
    }

    public void init() {
    	//richiede al client la modalita di comunicazione
    	//richiede al client se vuole iniziare partita nuova o accedere ad una esistente
    	//manda un messaggio al client per farlo rimanere in attesa
    }
    
    /**
     * @param String room 
     * @return
     */
    public String accessToGame(String room) {
        // TODO implement here
        return "";
    }

    public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	/**
     * @param String room 
     * @return
     */
    public void createNewGame(String room) {
        // TODO implement here
    	
    }

    /**
     * @param GameAction action 
     * @return
     */
    public Object performUserCommands(GameAction action) {
        // TODO implement here
    	//se questo metodo ritorna null vuol dire che non è stato possibile performare l'azione passata
    	Object toReturn;
    	if(action.isPossible()) {
    		toReturn = action.perform();
    		return toReturn;
    	} else {
    		return false;
    	}
    }

}