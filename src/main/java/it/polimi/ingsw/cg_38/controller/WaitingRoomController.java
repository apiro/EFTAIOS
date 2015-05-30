package it.polimi.ingsw.cg_38.controller;

import it.polimi.ingsw.cg_38.controller.event.Event;
import it.polimi.ingsw.cg_38.notifyEvent.EventNotifyEnvironment;
import it.polimi.ingsw.cg_38.notifyEvent.EventNotifyTurn;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentLinkedQueue;

public class WaitingRoomController extends Thread {
	
	private ConcurrentLinkedQueue<Event> toDispatch;
	private GameController gc;
	private Timer timer;
	private final static Boolean[] controllMyLoop = {true};
	
	public Timer getTimer() {
		return timer;
	}

	public void setTimer(Timer timer) {
		this.timer = timer;
	}

	public ConcurrentLinkedQueue<Event> getToDispatch() {
		return toDispatch;
	}

	public void setToDispatch(ConcurrentLinkedQueue<Event> toDispatch) {
		this.toDispatch = toDispatch;
	}

	public GameController getGc() {
		return gc;
	}

	public void setGc(GameController gc) {
		this.gc = gc;
	}

	public WaitingRoomController(ConcurrentLinkedQueue<Event> toDispatch, GameController gc) {
		this.setGc(gc);
		this.setName(gc.getTopic() + " waiting area handler");
		this.setToDispatch(toDispatch);
		this.setTimer(new Timer());
	}
	
	public void run() {
		
    	timer.schedule(new TimerTask() {
    		@Override
    	    public void run() {
    			WaitingRoomController.controllMyLoop[0] = false;
    			System.out.println("DDDDIIIIIDDD");
    	    }
    	} , 15000);
    	
		while(true) {
			try {
				Thread.sleep(1500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(controllMyLoop[0]);
			if(controllMyLoop[0] == false || gc.getGameModel().getGamePlayers().size() < 3) {
				break;
			}
		}
		
		
		//E' LA FASE DI SETTAGGIO A RUNNING DEL GIOCO
		gc.setFirstTurn();
		this.getToDispatch().add(new EventNotifyTurn(gc.getGameModel().getActualTurn().getCurrentPlayer()));
		gc.assignAvatars();
		this.getToDispatch().add(new EventNotifyEnvironment(gc.getGameModel().getGamePlayers(), gc.getGameModel().getGameMap()));
		gc.getGameModel().setGameState(GameState.RUNNING);
		
		Thread.currentThread().interrupt();
		try {
			synchronized(this.getToDispatch()) {
				this.getToDispatch().wait();
			}
		} catch (InterruptedException e) {
			return;
		}
	}
}
