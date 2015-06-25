package it.polimi.ingsw.cg_38.controller;

import it.polimi.ingsw.cg_38.controller.logger.Logger;
import it.polimi.ingsw.cg_38.controller.logger.LoggerCLI;
import it.polimi.ingsw.cg_38.controller.notifyEvent.EventNotifyEnvironment;
import it.polimi.ingsw.cg_38.controller.notifyEvent.EventNotifyTurn;

import java.util.Observable;

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

	public WaitingRoomController(GameController gc) {
		this.addObserver(gc);
		this.setGc(gc);
	}
	
	@Override
	public void run() {
		logger.print("---------------------------------------------------------------------\n");
		logger.print("ACCEPTING: " + gc.getTopic() + " ...");
		logger.print("---------------------------------------------------------------------\n");
		try {
			Thread.sleep(70000);
		} catch (InterruptedException e1) {
			logger.print("Problems during the rianimation of the room-handling-thread ...");
		}
		
		synchronized(gc) {
			//E' LA FASE DI SETTAGGIO A RUNNING DEL GIOCO
			logger.print("---------------------------------------------------------------------\n");
			logger.print("RUNNING: " + gc.getTopic() + " ...");
			logger .print("---------------------------------------------------------------------\n");
			gc.assignAvatars();
			gc.getBuffer().add(new EventNotifyEnvironment(gc.getGameModel().getGamePlayers(), gc.getGameModel().getGameMap()));
			this.setChanged();
			this.notifyObservers(gc.getTopic());
			
			gc.setFirstTurn();
			gc.getBuffer().add(new EventNotifyTurn(gc.getGameModel().getActualTurn().getCurrentPlayer()));
			this.setChanged();
			this.notifyObservers(gc.getTopic());
			gc.getGameModel().setGameState(GameState.RUNNING);
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
