package it.polimi.ingsw.cg_38.controller.action;

import javax.xml.parsers.ParserConfigurationException;

import it.polimi.ingsw.cg_38.controller.GameController;
import it.polimi.ingsw.cg_38.controller.GameState;
import it.polimi.ingsw.cg_38.controller.ServerController;
import it.polimi.ingsw.cg_38.controller.WaitingRoomController;
import it.polimi.ingsw.cg_38.controller.connection.Communicator;
import it.polimi.ingsw.cg_38.controller.event.GameEvent;
import it.polimi.ingsw.cg_38.controller.gameEvent.EventSubscribe;
import it.polimi.ingsw.cg_38.controller.notifyEvent.EventAddedToGame;

/** identifica l'azione di sottoscrizione al topic */
public class Subscribe extends Action {

	private static final long serialVersionUID = 1L;
	
	/** contiene il nome del topic */
	private String topic;
	
	/** contiene il tipo della mappa relativo al topic*/
	private String typeMap;

	/** invoca il costruttore della superclasse e setta i vari dati
	 * 
	 * @param evt evento di gioco che ha generato l'azione
	 */
	public Subscribe(GameEvent evt) {
		super(evt.getGenerator());
		this.setTopic(((EventSubscribe)evt).getRoom());
		this.setTypeMap(((EventSubscribe)evt).getMap());
    }

	public String getTypeMap() {
		return typeMap;
	}

	public void setTypeMap(String typeMap) {
		this.typeMap = typeMap;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}
	
	
	/** genera un evento di notifica di aggiunta al gioco in base alla situazione del server.
	 *Se il topic non esiste ne crea un nuovo e crea la partita relativa
	 *
	 *@param c communicator relativo al giocatore
	 *@param server server del gioco
	 *@return ritorna un evento di notifica di aggiunta al gioco
	 *@exception ParserConfigurationException
	 *@exception Exception
	 */
	public EventAddedToGame generalEventGenerator(Communicator c, ServerController server) throws ParserConfigurationException, Exception {
		

		for(GameController gc:server.getTopics().values()) {
			if(gc.getGameModel().getGamePlayers().contains(super.getPlayer())) {
				synchronized(gc) {
					gc.getSubscribers().add(c);
					gc.addEventToTheQueue(new EventAddedToGame(super.getPlayer(), false, true));
					gc.sendNotifyEvent();
					gc.getSubscribers().remove(c);
					return null;
				}
			}
		}
		
		if(this.isPossible(server)) {
			//il topic proposto è tra i topic già presenti
			//E' LA FASE DI ACCEPTING
			for(GameController gc:server.getTopics().values()) {
				if(gc.getTopic().equals(this.getTopic())) {
					synchronized(gc) {
						if(gc.getGameModel().getGameState().equals(GameState.ACCEPTING) &&
								gc.getGameModel().getGamePlayers().size() < 8) {
							gc.getSubscribers().add(c);
							gc.getGameModel().getGamePlayers().add(super.getPlayer());
							server.getTopics().put(super.getPlayer().getName(), gc);
							return new EventAddedToGame(super.getPlayer(), true, true);
						}  else {
							gc.getSubscribers().add(c);
							gc.addEventToTheQueue(new EventAddedToGame(super.getPlayer(), false, true));
							gc.sendNotifyEvent();
							gc.getSubscribers().remove(c);
							return null;	
						}
					}
				}
			}
		}
		
		GameController newGc = null;
		synchronized(server.getTopics()) {
			newGc = server.initAndStartANewGame(this.getTypeMap(), this.getTopic());
			server.addObserver(newGc);
			newGc.getSubscribers().add(c);
			newGc.getGameModel().getGamePlayers().add(super.getPlayer());
			server.getTopics().put(super.getPlayer().getName(), newGc);
			newGc.getGameModel().setGameState(GameState.ACCEPTING);
			server.getTopics().notify();
		}
		
		Thread waitingRoomController = new Thread(new WaitingRoomController(newGc), "WaitingRoomControllerThread");
		waitingRoomController.start();
		
		return new EventAddedToGame(super.getPlayer(), true, true);
	}
	
	/** verifica l'esistenza del topic al quale il giocatore vuole sottoscriversi
	 * 
	 * @param server del gioco
	 * @return true se è stato trovato il topic all'interno del server
	 */
	public Boolean isPossible(ServerController server) {
		//true -> giocatore chiede room gia presente false->giocatore chiede room da istanziare
		Boolean found = false;
		
		for(GameController gc:server.getTopics().values()) {
			if(gc.getTopic().equals(this.getTopic())) {
				found = true;
			}
		}
		return found;
	}
}
