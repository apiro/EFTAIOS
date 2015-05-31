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
		
		System.out.println("WAITING FOR OTHER PLAYERS IN ROOM: " + gc.getTopic() + " ...");
		try {
			Thread.sleep(60000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
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
		
		System.out.println("RUNNING: " + gc.getTopic() + " ...");
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
