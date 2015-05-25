package it.polimi.ingsw.cg_38.controller.action;

import javax.xml.parsers.ParserConfigurationException;

import it.polimi.ingsw.cg_38.controller.event.GameEvent;
import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.gameEvent.EventSubscribe;
import it.polimi.ingsw.cg_38.notifyEvent.EventAddedToGame;
import it.polimi.ingsw.cg_38.controller.GameController;
import it.polimi.ingsw.cg_38.controller.ServerController;

public class Subscribe extends InitGameAction {

	private String room;
	private String typeMap;
	
	public String getTypeMap() {
		return typeMap;
	}

	public void setTypeMap(String typeMap) {
		this.typeMap = typeMap;
	}

	public String getRoom() {
		return room;
	}

	public void setRoom(String room) {
		this.room = room;
	}

	public Subscribe(GameEvent evt) {
		super(evt.getGenerator());
		this.setRoom(((EventSubscribe)evt).getRoom());
		this.setTypeMap(((EventSubscribe)evt).getMap());
    }
	
	@Override
	public NotifyEvent perform(ServerController server) throws ParserConfigurationException, Exception {
		
		for(GameController gc:server.getTopics().values()) {
			if(this.isPossible(gc)) {
				gc.getGameModel().getGamePlayers().add(super.getPlayer());
				server.getTopics().put(super.getPlayer().getName(), gc);
				return new EventAddedToGame(super.getPlayer(), true);
			} else {
				return new EventAddedToGame(super.getPlayer(), false);
			}
		}
		
		GameController newGc = server.initAndStartANewGame(this.getTypeMap(), this.getRoom());
		server.addObserver(newGc);
		server.getTopics().put(super.getPlayer().getName(), newGc);
		return new EventAddedToGame(super.getPlayer(), true);
	}

	@Override
	public Boolean isPossible(GameController gc) {
		if(gc.getRoom().equals(this.getRoom()) && gc.getGameModel().getGamePlayers().size()<8 &&
				gc.getCanAcceptOtherPlayers()) {
			return true;
		}
		else return false;
	}

}
