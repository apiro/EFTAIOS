package it.polimi.ingsw.cg_38.controller;

import it.polimi.ingsw.cg_38.controller.GameState;
import it.polimi.ingsw.cg_38.controller.action.Action;
import it.polimi.ingsw.cg_38.controller.action.ActionCreator;
import it.polimi.ingsw.cg_38.controller.action.GameAction;
import it.polimi.ingsw.cg_38.controller.event.GameEvent;
import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.model.Alien;
import it.polimi.ingsw.cg_38.model.GameModel;
import it.polimi.ingsw.cg_38.model.Human;
import it.polimi.ingsw.cg_38.model.Name;
import it.polimi.ingsw.cg_38.model.Turn;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.xml.parsers.ParserConfigurationException;

public class GameController implements Observer {
	
	/**
	 * arraylist delle interfacce per comunicare con i giocatori sottoscritti alla partita
	 * */
	private HashMap<String, Communicator> subscribers = new HashMap<String, Communicator>();
	
	public GameState getState() {
		return state;
	}

	public void setState(GameState state) {
		this.state = state;
	}

	/**
	 * arraylist dove il server pone gli eventi provenienti dal buffer coordinato relativi a questo topic(a questa partita) 
	 * */
	private ConcurrentLinkedQueue<GameEvent> buffer;

	private String topic;

	private GameModel gameModel;

	private Timer timer;

	private Boolean finishGame = false;
	
	private GameState state = GameState.STARTING;
	
    public void setFinishGame(Boolean finishGame) {
		this.finishGame = finishGame;
	}

    private String room;
    
	public String getRoom() {
		return room;
	}

	private Boolean canAcceptOtherPlayers = true;

	public GameController(String type, String room) throws ParserConfigurationException, Exception {
		
    	this.initGame(type, room);
    	this.startGame();
    }
	
	/**
	 * put an event in the queue of the events that this game has to handle
	 **/
	public void update(Observable o, Object arg) {
		this.addEventToTheQueue((GameEvent)arg);
	}
	
	public HashMap<String, Communicator> getSubscribers() {
		return subscribers;
	}

	public void publish(NotifyEvent evt) {
		if(evt.isBroadcast()) {
			for(Communicator comm: this.getSubscribers().values()) {
				comm.send(evt);
			}
		} else if (!evt.isBroadcast()) {
			(this.getSubscribers().get(evt.getGenerator().getName())).send(evt);
		}
	}

	/**
	 * method to add a message to the buffer of this game. added by playerController and ServerController.
	 * */
	public void addEventToTheQueue(GameEvent msg) {
		buffer.add(msg);
		synchronized(buffer) {
			buffer.notify();
		}
	}
	
	public NotifyEvent performUserCommands(GameAction action) {
		NotifyEvent notifyEvent = null;
		if(action.isPossible(this.getGameModel())) {
			notifyEvent = action.perform(this.getGameModel());
    		this.publish(notifyEvent);
    	}
		return notifyEvent; 
	}

	public Action processGameEvent() {
		GameEvent msg = this.getBuffer().poll();
		Action action = null;
		if(msg != null) {
			action = ActionCreator.createAction(msg);
		} else {
			try {
				synchronized(this.getBuffer()) {
					this.getBuffer().wait();
				}
			} catch (InterruptedException e) {
				System.err.println("Cannot wait on the queue!");
			}
		}
		return action;
	}

	public ConcurrentLinkedQueue<GameEvent> getBuffer() {
		return buffer;
	}

	public void setBuffer(ConcurrentLinkedQueue<GameEvent> buffer) {
		this.buffer = buffer;
	}

	public void initGame(String type, String room) throws ParserConfigurationException, Exception {
		this.setRoom(room);
	    this.setGameModel(new GameModel(type));
	    this.setTimer(new Timer());
	}
	
	public void startGame() {
    	
    	while(!this.getFinishGame()) {
    		Action generatedAction = this.processGameEvent();
    		NotifyEvent callbackEvent = this.performUserCommands((GameAction)generatedAction);
    		this.publish(callbackEvent);
    	} 	
    }
	
	public void closeGame() {
	    this.setState(GameState.FINISHED);
	    this.setFinishGame(true);
	}
	
	public void setFirstTurn() {
    	Turn actualTurn = new Turn(this.getGameModel().getGamePlayers().get(0));
    	this.getGameModel().setActualTurn(actualTurn);
    }
	
	public void changeTurn() {
    	Turn newTurn = new Turn(this.getGameModel().getNextPlayer());
    	this.getGameModel().setActualTurn(newTurn);
    }
	
	 public void assignAvatars() {
	    Collections.shuffle(getGameModel().getGamePlayers());
	   	for(int i =0; i<this.getGameModel().getGamePlayers().size(); i++) {
	   		int floor = this.getGameModel().getGamePlayers().size()/2;
	   		if(i<floor) {
	   			this.getGameModel().getGamePlayers().get(i).setAvatar(new Human(Name.valueOf("Human"+(i+1)), this.getGameModel().getGameMap().searchSectorByName("HumanStartingPoint")));
	    	} else {
	    		this.getGameModel().getGamePlayers().get(i).setAvatar(new Alien(Name.valueOf("Alien"+(i-floor+1)), this.getGameModel().getGameMap().searchSectorByName("AlienStartingPoint")));
	    	}
	   		System.out.println(this.getGameModel().getGamePlayers().get(i).getAvatar().getName());
	    }
	   	Collections.shuffle(getGameModel().getGamePlayers());
	 }
	
	public void waitingForPlayerConnection() {
    	final Boolean[] controllMyLoop = {true};
    	timer.schedule(new TimerTask() {
    		@Override
    	    public void run() {
    			controllMyLoop[0] = false;
    	    }
    	} , 10000);
    	
    	while(controllMyLoop[0]) {
    	}
    	setCanAcceptOtherPlayers(false);
    }
	
	public GameModel getGameModel() {
		return this.gameModel;
	}
	
	public Boolean getCanAcceptOtherPlayers() {
		return canAcceptOtherPlayers;
	}

	public void setCanAcceptOtherPlayers(Boolean canAcceptOtherPlayers) {
		this.canAcceptOtherPlayers = canAcceptOtherPlayers;
	}

	public boolean getFinishGame() {
		return this.finishGame;
	}

	public void setTimer(Timer timer) {
		this.timer = timer;
	}

	public void setGameModel(GameModel gameModel) {
		this.gameModel = gameModel;
	}

	public void setRoom(String topic) {
		this.topic = topic;
	}
}
