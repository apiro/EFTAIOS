package it.polimi.ingsw.cg_38.controller;

import it.polimi.ingsw.cg_38.controller.logger.Logger;
import it.polimi.ingsw.cg_38.controller.logger.LoggerCLI;
import it.polimi.ingsw.cg_38.controller.notifyEvent.EventNotifyEnvironment;
import it.polimi.ingsw.cg_38.controller.notifyEvent.EventNotifyTurn;

import java.util.Observable;

/**
 * Oggetto utilizzato per gestire l'esecuzione di una partita 
 *  */
public class WaitingRoomController extends Observable implements Runnable {
	
	private GameController gc;
	/*private final Boolean[] controllMyLoop = {true};*/
	private Logger logger = new LoggerCLI();
	
	
	public GameController getGc() {
		return gc;
	}

	public void setGc(GameController gc) {
		this.gc = gc;
	}

	/** 
	 * Prende in ingresso il gameController della partita di cui dovrà gestire l'inizio
	 * @param gc GameController della partita 
	 * */
	public WaitingRoomController(GameController gc) {
		this.addObserver(gc);
		this.setGc(gc);
	}
	
	/** 
	 * Vengono assegnati gli avatar ai vari giocatori della partita e viene settato il 
	 * primo turno dopo aver aspettato il tempo definito dalla waiting room
	 * */
	@Override
	public void run() {
		logger.print("---------------------------------------------------------------------\n");
		logger.print("ACCEPTING: " + gc.getTopic() + " ...");
		logger.print("---------------------------------------------------------------------\n");
		try {
			Thread.sleep(60000);
		} catch (InterruptedException e1) {
			logger.print("Problems during the rianimation of the room-handling-thread ...");
		}
		
		synchronized(gc) {
			//E' LA FASE DI SETTAGGIO A RUNNING DEL GIOCO
			gc.getGameModel().setGameState(GameState.RUNNING);
			logger.print("---------------------------------------------------------------------\n");
			logger.print("RUNNING: " + gc.getTopic() + " ...");
			logger.print("---------------------------------------------------------------------\n");
			gc.assignAvatars();
			gc.getBuffer().add(new EventNotifyEnvironment(gc.getGameModel().getGamePlayers(), gc.getGameModel().getGameMap()));
			this.setChanged();
			this.notifyObservers(gc.getTopic());
			
			gc.setFirstTurn();
			gc.getBuffer().add(new EventNotifyTurn(gc.getGameModel().getActualTurn().getCurrentPlayer()));
			this.setChanged();
			this.notifyObservers(gc.getTopic());
			gc.notify();
			
			Thread.currentThread().interrupt();
			
			try {
				synchronized(gc.getBuffer()) {
					gc.getBuffer().wait();
				}
			} catch (InterruptedException e) {
				return;
			}
		}
	}
}
