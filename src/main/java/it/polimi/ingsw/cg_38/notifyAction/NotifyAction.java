package it.polimi.ingsw.cg_38.notifyAction;

import it.polimi.ingsw.cg_38.controller.ClientInterface;
import it.polimi.ingsw.cg_38.controller.action.Action;
import it.polimi.ingsw.cg_38.model.Player;

public abstract class NotifyAction extends Action {

	public NotifyAction(Player player) {
		super(player);
	}
	
	public abstract Boolean isPossible();
	
	public abstract void render(ClientInterface view);	
}
