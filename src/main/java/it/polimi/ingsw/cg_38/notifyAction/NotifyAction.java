package it.polimi.ingsw.cg_38.notifyAction;

import it.polimi.ingsw.cg_38.controller.Client;
import it.polimi.ingsw.cg_38.controller.ClientInterface;
import it.polimi.ingsw.cg_38.controller.action.Action;
import it.polimi.ingsw.cg_38.controller.event.GameEvent;
import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.model.Player;

public abstract class NotifyAction extends Action {
	
	public NotifyEvent evt;

	public NotifyEvent getEvt() {
		return evt;
	}

	public void setEvt(NotifyEvent evt) {
		this.evt = evt;
	}

	public NotifyAction(Player player , NotifyEvent evt) {
		super(player);
		this.evt = evt;
	}
	
	public abstract Boolean isPossible(Client client);
	
	public abstract GameEvent render(Client client);	
}
