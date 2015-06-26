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

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.xml.parsers.ParserConfigurationException;

/** utilizzato per la gestione di una partita e dei giocatori che vi partecipano
 * . Si occupa di inizializzare una partita, di assegnare gli avatar e di inviare gli eventi
 *  di notifica ai giocatori generati in conseguenza di azioni performate
 *  
 * @author Marco
 *
 */
public class GameController implements Observer {

	/** lista di comunicatori di tutti i giocatori sottoscritti al topic */
	private List<Communicator> subscribers = new ArrayList<Communicator>();
	
	/** buffer degli eventi di notifica da inviare */
	private Queue<NotifyEvent> buffer;

	/** modello del gioco */
	private GameModel gameModel;
	
    private String topic;
    
    private Logger logger = new LoggerCLI();

    /** true se è possibile ancora accettare altri giocatori alla partita */
	private Boolean canAcceptOtherPlayers = true;

	/** il costruttore inizializza il gioco e setta il buffer con una lista di eventi di notifica 
	 * 
	 * @param type mappa da utilizzare nella partita
	 * @param topic nome del topic della partita
	 */
	public GameController(String type, String topic) throws ParserConfigurationException, Exception {
		
    	this.initGame(type, topic);
    	this.setBuffer(new ConcurrentLinkedQueue<NotifyEvent>());
    }
	
	@Override
	public void update(Observable o, Object arg) {
		if(((String)arg).equals(this.getTopic())) {
			try {
				this.sendNotifyEvent();
			} catch (IOException e) {
				logger.print("Problems in sending broadcast message ...");
			}
		} 
	}
	
    public String getTopic() {
		return topic;
	}

	public List<Communicator> getSubscribers() {
		return subscribers;
	}

	/** manda l'evento di notifica al giocatore 
	 * 
	 * @param evt evento di notifica generato
	 */
	public void publish(NotifyEvent evt) throws IOException {
		for(Communicator comm: this.getSubscribers()) {
			comm.send(evt);
		}
	}

	/** aggiunge un evento di notifica al buffer 
	 * 
	 * @param msg evento di notifica da aggiungere
	 */
	public void addEventToTheQueue(NotifyEvent msg) {
		buffer.add(msg);
		synchronized(buffer) {
			buffer.notify();
		}
	}
	
	/** viene verificata la possibilità di performare l'azione e in caso positivo vengono generati 
	 * gli eventi di notifica relativi l'azione. se non è possibile performare l'azione
	 * viene generato un evento di notifica di errore
	 * 
	 * @param action azione da performare
	 * @return ritorna la lista di eventi di notifica da inviare al giocatore
	 * @throws RemoteException
	 */
	public List<NotifyEvent> performUserCommands(GameAction action) throws RemoteException {
		List<NotifyEvent> notifyEvent = new ArrayList<NotifyEvent>();
		if(action instanceof FinishTurn) {
			String oldPlayer = this.getGameModel().getActualTurn().getCurrentPlayer().getName();
			notifyEvent = action.perform(this.getGameModel());
			String newPlayer = this.getGameModel().getActualTurn().getCurrentPlayer().getName();
			
			if(!newPlayer.equals(oldPlayer) && this.getGameModel().areThereOtherHumans()) {
				Thread tH = new Thread(new TurnTimerController(this), "TurnHandler");
				tH.start();
				logger.print("Starting new Turn and starting a new Turn Handler !");
			} else {
				logger.print("Error ! This turn can't finish now ...");
			}
			
			return notifyEvent; 
		}
		if(action.isPossible(this.getGameModel())) {
			synchronized(this) {
				notifyEvent = action.perform(this.getGameModel());
			}
    	} else {
    		NotifyEvent e = new EventNotifyError(action.getPlayer(), action);
    		notifyEvent.add(e);
    	}
		return notifyEvent; 
	}

	public void sendNotifyEvent() throws IOException {
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

	public Queue<NotifyEvent> getBuffer() {
		return buffer;
	}

	public void setBuffer(Queue<NotifyEvent> buffer) {
		this.buffer = buffer;
	}

	/** setta il nome del topic e crea un nuovo modell con la mappa scelta dal giocatore che ha creato
	 * la partita
	 * 
	 * @param type tipo di mappa da creare
	 * @param topic nome del topic della partita
	 * @throws ParserConfigurationException
	 * @throws Exception
	 */
	public void initGame(String type, String topic) throws ParserConfigurationException, Exception {
		this.setTopic(topic);
	    this.setGameModel(new GameModel(type));
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}
	
	/** viene modificato lo stato di gioco del modello */
	public void closeGame() {
	    this.getGameModel().setGameState(GameState.CLOSING);
	}
	
	/** viene settato il primo turno con il primo giocatore nella lista presente nel modello */
	public void setFirstTurn() {
    	Turn actualTurn = new Turn(this.getGameModel().getGamePlayers().get(0));
    	this.getGameModel().setActualTurn(actualTurn);
    }
	
	/** viene mischiata la lista dei giocatori della partita e in base al loro numero 
	 *  viene assegnato un avatar ad ognuno di essi
	 */
	 public void assignAvatars() {
	    Collections.shuffle(getGameModel().getGamePlayers());
	   	for(int i =0; i<this.getGameModel().getGamePlayers().size(); i++) {
	   		int floor = this.getGameModel().getGamePlayers().size()/2;
	   		if(i<floor) {
	   			this.getGameModel().getGamePlayers().get(i).setAvatar(new Human(Name.valueOf("HUMAN"+(i+1)), this.getGameModel().getGameMap().searchSectorByCoordinates(1, 1)/*.searchSectorByName("HumanStartingPoint")*/));
	    	} else {
	    		this.getGameModel().getGamePlayers().get(i).setAvatar(new Alien(Name.valueOf("ALIEN"+(i-floor+1)), this.getGameModel().getGameMap().searchSectorByCoordinates(1, 1)/*.searchSectorByName("AlienStartingPoint")*/));
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
