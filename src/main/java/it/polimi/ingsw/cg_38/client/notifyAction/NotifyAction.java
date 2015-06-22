package it.polimi.ingsw.cg_38.client.notifyAction;

import it.polimi.ingsw.cg_38.client.PlayerClient;
import it.polimi.ingsw.cg_38.controller.action.Action;
import it.polimi.ingsw.cg_38.controller.event.GameEvent;
import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.model.EndState;
import it.polimi.ingsw.cg_38.model.LifeState;
import it.polimi.ingsw.cg_38.model.Player;

public abstract class NotifyAction extends Action {
	
	private static final long serialVersionUID = 1L;
	public NotifyEvent evt;

	public NotifyEvent getEvt() {
		return evt;
	}

	public void setEvt(NotifyEvent evt) {
		this.evt = evt;
	}

	public NotifyAction(Player player , NotifyEvent evt) {
		super(player);
		this.setEvt(evt);
	}
	
	public abstract Boolean isPossible(PlayerClient client);
	
	public Boolean check(PlayerClient client) {
		if(client.getPlayer().getAvatar().getIsAlive().equals(LifeState.DEAD) &&
				client.getPlayer().getAvatar().getIsWinner().equals(EndState.LOOSER)) {
			return false;
		}
		return true;
	};
	
	public abstract GameEvent render(PlayerClient client);	
}