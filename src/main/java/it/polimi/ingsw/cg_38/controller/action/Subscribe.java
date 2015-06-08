package it.polimi.ingsw.cg_38.controller.action;


import javax.xml.parsers.ParserConfigurationException;

import it.polimi.ingsw.cg_38.controller.Communicator;
import it.polimi.ingsw.cg_38.controller.GameController;
import it.polimi.ingsw.cg_38.controller.GameState;
import it.polimi.ingsw.cg_38.controller.ServerController;
import it.polimi.ingsw.cg_38.controller.WaitingRoomController;
import it.polimi.ingsw.cg_38.controller.event.GameEvent;
import it.polimi.ingsw.cg_38.gameEvent.EventSubscribe;
import it.polimi.ingsw.cg_38.notifyEvent.EventAddedToGame;

public abstract class Subscribe extends InitGameAction {

	private String topic;
	private String typeMap;
	
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

	public Subscribe(GameEvent evt) {
		super(evt.getGenerator());
		this.setTopic(((EventSubscribe)evt).getRoom());
		this.setTypeMap(((EventSubscribe)evt).getMap());
    }
	
	public EventAddedToGame generalEventGenerator(Communicator c, ServerController server) throws ParserConfigurationException, Exception {
		
		for(GameController gc:server.getTopics().values()) {
			if(gc.getGameModel().getGamePlayers().contains(super.getPlayer())) {
				gc.getSubscribers().add(c);
				server.getTopics().put(super.getPlayer().getName(), gc);
				return new EventAddedToGame(super.getPlayer(), false, false);
			}
		}
		if(this.isPossible(server)) {
			//il topic proposto è tra i topic già presenti
			//E' LA FASE DI ACCEPTING
			for(GameController gc:server.getTopics().values()) {
				if(gc.getTopic().equals(this.getTopic())) {
					if(/*gc.getGameModel().getGamePlayers().size()<8 &&
							gc.getCanAcceptOtherPlayers()*/ gc.getGameModel().getGameState().equals(GameState.ACCEPTING) ) {
						gc.getSubscribers().add(c);
						gc.getGameModel().getGamePlayers().add(super.getPlayer());
						server.getTopics().put(super.getPlayer().getName(), gc);
						return new EventAddedToGame(super.getPlayer(), true, true);
					}  else {
						gc.getSubscribers().add(c);
						server.getTopics().put(super.getPlayer().getName(), gc);
						return new EventAddedToGame(super.getPlayer(), false, false);	
					}
				}
			}
		}
		//il topic proposto NON è tra le topic già presenti
		//E' LA FASE DI INIT DEL GIOCO ! STATO 0 DEL GIOCO, QUANDO UN GIOCATORE RICHIEDE DI GIOCARE IN UNA ROOM NON PRESENTE
		GameController newGc = server.initAndStartANewGame(this.getTypeMap(), this.getTopic());
		server.addObserver(newGc);
		newGc.getSubscribers().add(c);
		newGc.getGameModel().getGamePlayers().add(super.getPlayer());
		server.getTopics().put(super.getPlayer().getName(), newGc);
		newGc.getGameModel().setGameState(GameState.ACCEPTING);
		
		Thread waitingRoomController = new Thread(new WaitingRoomController(newGc), "WaitingRoomControllerThread");
		waitingRoomController.start();
		
		return new EventAddedToGame(super.getPlayer(), true, true);
	}
	
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
