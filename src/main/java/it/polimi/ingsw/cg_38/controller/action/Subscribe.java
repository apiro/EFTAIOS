package it.polimi.ingsw.cg_38.controller.action;


import javax.xml.parsers.ParserConfigurationException;

import it.polimi.ingsw.cg_38.controller.Communicator;
import it.polimi.ingsw.cg_38.controller.GameController;
import it.polimi.ingsw.cg_38.controller.ServerController;
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
				return new EventAddedToGame(super.getPlayer(), false);
			}
		}
		if(this.isPossible(server)) {
			//il topic proposto è tra le topic già presenti
			for(GameController gc:server.getTopics().values()) {
				if(gc.getTopic().equals(this.getTopic()) && gc.getGameModel().getGamePlayers().size()<8 &&
						gc.getCanAcceptOtherPlayers()) {
					gc.getSubscribers().put(super.getPlayer().getName(), c);
					gc.getGameModel().getGamePlayers().add(super.getPlayer());
					gc.setFirstTurn();
					gc.assignAvatars();
					server.getTopics().put(super.getPlayer().getName(), gc);
					return new EventAddedToGame(super.getPlayer(), true);
				}
			}
			return new EventAddedToGame(super.getPlayer(), false);	
		} else {
			//il topic proposto NON è tra le topic già presenti
			GameController newGc = server.initAndStartANewGame(this.getTypeMap(), this.getTopic());
			server.addObserver(newGc);
			newGc.getSubscribers().put(super.getPlayer().getName(), c);
			newGc.getGameModel().getGamePlayers().add(super.getPlayer());
			newGc.setFirstTurn();
			newGc.assignAvatars();
			server.getTopics().put(super.getPlayer().getName(), newGc);
			return new EventAddedToGame(super.getPlayer(), true);
		}
	}
	
	public Boolean isPossible(ServerController server) {
		Boolean found = false;
		
		for(GameController gc:server.getTopics().values()) {
			if(gc.getTopic().equals(this.getTopic())) {
				found = true;
			}
		}
		return found;
	}
}
