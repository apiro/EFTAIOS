package it.polimi.ingsw.cg_38.controller;

import it.polimi.ingsw.cg_38.controller.GameState;
import it.polimi.ingsw.cg_38.controller.action.GameAction;
import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.model.Alien;
import it.polimi.ingsw.cg_38.model.GameModel;
import it.polimi.ingsw.cg_38.model.Human;
import it.polimi.ingsw.cg_38.model.Name;
import it.polimi.ingsw.cg_38.model.Turn;

import java.rmi.RemoteException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.xml.parsers.ParserConfigurationException;

public class GameController implements Observer {

	private HashMap<String, Communicator> subscribers = new HashMap<String, Communicator>();
	
	public GameState getState() {
		return state;
	}

	public void setState(GameState state) {
		this.state = state;
	}
	
	private ConcurrentLinkedQueue<NotifyEvent> buffer;

	private GameModel gameModel;

	private Timer timer;

	private GameState state = GameState.STARTING;
	
    private String topic;
    
	public String getTopic() {
		return topic;
	}

	private Boolean canAcceptOtherPlayers = true;

	public GameController(String type, String topic) throws ParserConfigurationException, Exception {
		
    	this.initGame(type, topic);
    	
    }
	
	/**
	 * quando viene aggiungo un evento alla coda di questo topic viene fired questo metodo che invia il messaggio.
	 **/
	public void update(Observable o, Object arg) {
		if(((String)arg).equals(this.getTopic())) {
			try {
				this.sendNotifyEvent();
			} catch (RemoteException e) {
				System.err.println("Error in sending the message !");
			}
		}
	}
	
	public HashMap<String, Communicator> getSubscribers() {
		return subscribers;
	}

	public void publish(NotifyEvent evt) throws RemoteException {
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
	public void addEventToTheQueue(NotifyEvent msg) {
		buffer.add(msg);
		synchronized(buffer) {
			buffer.notify();
		}
	}
	
	public NotifyEvent performUserCommands(GameAction action) throws RemoteException {
		NotifyEvent notifyEvent = null;
		if(action.isPossible(this.getGameModel())) {
			notifyEvent = action.perform(this.getGameModel());
    		this.publish(notifyEvent);
    	}
		return notifyEvent; 
	}

	public void sendNotifyEvent() throws RemoteException {
		NotifyEvent msg = this.getBuffer().poll();
		if(msg != null) {
			this.publish(msg);
		} else {
			try {
				synchronized(this.getBuffer()) {
					this.getBuffer().wait();
				}
			} catch (InterruptedException e) {
				System.err.println("Cannot wait on the queue!");
			}
		}
	}

	public ConcurrentLinkedQueue<NotifyEvent> getBuffer() {
		return buffer;
	}

	public void setBuffer(ConcurrentLinkedQueue<NotifyEvent> buffer) {
		this.buffer = buffer;
	}

	public void initGame(String type, String topic) throws ParserConfigurationException, Exception {
		this.setTopic(topic);
	    this.setGameModel(new GameModel(type));
	    this.setTimer(new Timer());
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}
	
	public void closeGame() {
	    this.setState(GameState.FINISHED);
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

	public void setTimer(Timer timer) {
		this.timer = timer;
	}

	public void setGameModel(GameModel gameModel) {
		this.gameModel = gameModel;
	}
}
