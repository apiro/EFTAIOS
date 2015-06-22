package it.polimi.ingsw.cg_38.controller;

import it.polimi.ingsw.cg_38.controller.GameState;
import it.polimi.ingsw.cg_38.controller.action.FinishTurn;
import it.polimi.ingsw.cg_38.controller.action.GameAction;
import it.polimi.ingsw.cg_38.controller.connection.Communicator;
import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.controller.logger.Logger;
import it.polimi.ingsw.cg_38.controller.logger.LoggerCLI;
import it.polimi.ingsw.cg_38.controller.notifyEvent.EventNotifyError;
import it.polimi.ingsw.cg_38.model.Alien;
import it.polimi.ingsw.cg_38.model.GameModel;
import it.polimi.ingsw.cg_38.model.Human;
import it.polimi.ingsw.cg_38.model.Name;
import it.polimi.ingsw.cg_38.model.Turn;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.xml.parsers.ParserConfigurationException;

public class GameController implements Observer {

	private ArrayList<Communicator> subscribers = new ArrayList<Communicator>();
	
	private ConcurrentLinkedQueue<NotifyEvent> buffer;

	private GameModel gameModel;
	
    private String topic;
    
    private Logger logger = new LoggerCLI();
	
    public String getTopic() {
		return topic;
	}

	private Boolean canAcceptOtherPlayers = true;

	public GameController(String type, String topic) throws ParserConfigurationException, Exception {
		
    	this.initGame(type, topic);
    	this.setBuffer(new ConcurrentLinkedQueue<NotifyEvent>());
    }
	
	/**
	 * quando viene aggiungo un evento alla coda di questo topic viene fired questo metodo che invia il messaggio.
	 **/
	public void update(Observable o, Object arg) {
		if(((String)arg).equals(this.getTopic())) {
			try {
				this.sendNotifyEvent();
			} catch (RemoteException e) {
				logger.print("Problems in sending broadcast message ...");
			}
		} 
	}
	
	public ArrayList<Communicator> getSubscribers() {
		return subscribers;
	}

	public void publish(NotifyEvent evt) throws RemoteException {
		for(Communicator comm: this.getSubscribers()) {
			comm.send(evt);
		}
	}

	public void addEventToTheQueue(NotifyEvent msg) {
		buffer.add(msg);
		synchronized(buffer) {
			buffer.notify();
		}
	}
	
	public ArrayList<NotifyEvent> performUserCommands(GameAction action) throws RemoteException {
		ArrayList<NotifyEvent> notifyEvent = new ArrayList<NotifyEvent>();
		if(action instanceof FinishTurn) {
			//l'oggetto action viene gestito diversamente dalle altre action. ritorno l'evento di 
			//callback in ogni caso !
			notifyEvent = action.perform(this.getGameModel());
			return notifyEvent; 
		}
		if(action.isPossible(this.getGameModel())) {
			notifyEvent = action.perform(this.getGameModel());
    	} else {
    		NotifyEvent e = new EventNotifyError(action.getPlayer(), action);
    		notifyEvent.add(e);
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
				logger.print("Cannot wait on the queue!");
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
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}
	
	public void closeGame() {
	    this.getGameModel().setGameState(GameState.CLOSING);
	}
	
	public void setFirstTurn() {
    	Turn actualTurn = new Turn(this.getGameModel().getGamePlayers().get(0));
    	this.getGameModel().setActualTurn(actualTurn);
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
	    }
	   	Collections.shuffle(getGameModel().getGamePlayers());
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
	
	public void setGameModel(GameModel gameModel) {
		this.gameModel = gameModel;
	}
}
