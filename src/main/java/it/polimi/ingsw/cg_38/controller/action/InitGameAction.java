package it.polimi.ingsw.cg_38.controller.action;

import javax.xml.parsers.ParserConfigurationException;

import it.polimi.ingsw.cg_38.controller.ServerController;
import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.model.Player;

public abstract class InitGameAction extends Action {
	
	public InitGameAction(Player player) {
		super(player);
    }
	
	public abstract NotifyEvent perform(ServerController serverc) throws ParserConfigurationException, Exception;
	
	public abstract Boolean isPossible(ServerController server);
}
