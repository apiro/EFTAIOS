package it.polimi.ingsw.cg_38.controller;

import java.util.List;
import java.util.Observable;

import it.polimi.ingsw.cg_38.controller.action.ForceFinishTurn;
import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.controller.gameEvent.EventFinishTurn;
import it.polimi.ingsw.cg_38.controller.logger.Logger;
import it.polimi.ingsw.cg_38.controller.logger.LoggerCLI;

public class TurnTimerController extends Observable implements Runnable {

	private Logger logger;
	private GameController gc;

	public TurnTimerController(GameController gc) {
		this.addObserver(gc);
		this.logger = new LoggerCLI();
		this.gc = gc;
	}

	@Override
	public void run() {
		String oldPlayer = gc.getGameModel().getActualTurn().getCurrentPlayer().getName();
		logger.print("---------------------------------------------------------------------\n");
		logger.print("STARTING TURN TIMER");
		logger.print("---------------------------------------------------------------------\n");
		try {
			Thread.sleep(40000);
		} catch (InterruptedException e1) {
			logger.print("Problems during the rianimation of the room-handling-thread ...");
		}
		
		logger.print("---------------------------------------------------------------------\n");
		logger.print("TURN TIME EXCEDED ... THE TURN IS CHANGED !");
		logger.print("---------------------------------------------------------------------\n");
		
		synchronized(gc) {
			String newPlayer = gc.getGameModel().getActualTurn().getCurrentPlayer().getName();
			if(newPlayer.equals(oldPlayer)) {
				ForceFinishTurn act = new ForceFinishTurn(new EventFinishTurn(gc.getGameModel().getActualTurn().getCurrentPlayer()));
				List<NotifyEvent> callbackEvent = null;
				callbackEvent = act.perform(gc.getGameModel());
				
				Thread tH = new Thread(new TurnTimerController(gc), "TurnHandler");
				tH.start();
				
				if(callbackEvent != null) {
					gc.getBuffer().addAll(callbackEvent);
					this.setChanged();
					this.notifyObservers(gc.getTopic());
				}
			/*
			logger.print("---------------------------------------------------------------------\n");
			logger.print("FORCED TURN CHANGE");
			logger.print("---------------------------------------------------------------------\n");
			} else {
				logger.print("---------------------------------------------------------------------\n");
				logger.print("NATURAL TURN CHANGE");
				logger.print("---------------------------------------------------------------------\n");
			}*/
			}
		/*if(gc.getGameModel().areThereOtherHumans() || 
				gc.getGameModel().getGameState().equals(GameState.CLOSING)) {
			Thread tH = new Thread(new TurnTimerController(gc), "TurnHandler");
			tH.start();
		}*/
			Thread.currentThread().interrupt();
			return;
		}
	}
}
