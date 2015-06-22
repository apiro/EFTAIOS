package it.polimi.ingsw.cg_38.controller;

import it.polimi.ingsw.cg_38.notifyEvent.EventNotifyEnvironment;
import it.polimi.ingsw.cg_38.notifyEvent.EventNotifyTurn;

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
	
	public void run() {
		logger.print("---------------------------------------------------------------------\n");
		logger.print("ACCEPTING: " + gc.getTopic() + " ...");
		logger.print("---------------------------------------------------------------------\n");
		try {
			Thread.sleep(30000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		/*
		System.out.println("PRIMA DELLA TIMERTASK");
    	timer.schedule(new TimerTask() {
    		@Override
    	    public void run() {
    			controllMyLoop[0] = false;
    			System.out.println("SETTATO A FALSE");
    	    }
    	} , 3000);
    	System.out.println("DOPO LA TIMERTASK");
		while(true) {
			
			if(controllMyLoop[0] == false) {
				System.out.println("PRIMA DEL BREAK");
				break;
			}
		}
		
		System.out.println("PRIMA DI INVIARE EVENTI");
		*/
		logger.print("---------------------------------------------------------------------\n");
		logger.print("RUNNING: " + gc.getTopic() + " ...");
		logger .print("---------------------------------------------------------------------\n");
		//E' LA FASE DI SETTAGGIO A RUNNING DEL GIOCO
		gc.assignAvatars();
		gc.getBuffer().add(new EventNotifyEnvironment(gc.getGameModel().getGamePlayers(), gc.getGameModel().getGameMap()));
		this.setChanged();
		this.notifyObservers(gc.getTopic());
		
		gc.setFirstTurn();
		gc.getBuffer().add(new EventNotifyTurn(gc.getGameModel().getActualTurn().getCurrentPlayer()));
		this.setChanged();
		this.notifyObservers(gc.getTopic());
		gc.getGameModel().setGameState(GameState.RUNNING);
		
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
