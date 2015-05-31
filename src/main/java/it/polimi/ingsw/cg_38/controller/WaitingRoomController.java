package it.polimi.ingsw.cg_38.controller;

import it.polimi.ingsw.cg_38.notifyEvent.EventNotifyEnvironment;
import it.polimi.ingsw.cg_38.notifyEvent.EventNotifyTurn;

import java.util.Observable;
import java.util.Timer;
import java.util.TimerTask;

public class WaitingRoomController extends Observable implements Runnable {
	
	private GameController gc;
	private Timer timer;
	private final Boolean[] controllMyLoop = {true};
	
	public Timer getTimer() {
		return timer;
	}

	public void setTimer(Timer timer) {
		this.timer = timer;
	}
	
	public GameController getGc() {
		return gc;
	}

	public void setGc(GameController gc) {
		this.gc = gc;
	}

	public WaitingRoomController(GameController gc) {
		this.addObserver(gc);
		this.setGc(gc);
		this.setTimer(new Timer());
	}
	
	public void run() {
		
    	timer.schedule(new TimerTask() {
    		@Override
    	    public void run() {
    			controllMyLoop[0] = false;
    	    }
    	} , 30000);
    	
		while(true) {
			if(controllMyLoop[0] == false || gc.getGameModel().getGamePlayers().size() == 3) {
				break;
			}
		}
		
		
		//E' LA FASE DI SETTAGGIO A RUNNING DEL GIOCO
		gc.setFirstTurn();
		gc.getBuffer().add(new EventNotifyTurn(gc.getGameModel().getActualTurn().getCurrentPlayer()));
		this.setChanged();
		this.notifyObservers(gc.getTopic());
		
		gc.assignAvatars();
		gc.getBuffer().add(new EventNotifyEnvironment(gc.getGameModel().getGamePlayers(), gc.getGameModel().getGameMap()));
		this.setChanged();
		this.notifyObservers(gc.getTopic());
		gc.getGameModel().setGameState(GameState.RUNNING);
		
		System.err.println("Topic: " + gc.getTopic() + " is starting ! ");
		
		Thread.currentThread().interrupt();
		timer.cancel();
		try {
			synchronized(gc.getBuffer()) {
				gc.getBuffer().wait();
			}
		} catch (InterruptedException e) {
			return;
		}
	}
}
