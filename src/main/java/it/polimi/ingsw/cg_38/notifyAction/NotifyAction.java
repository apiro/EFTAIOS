package it.polimi.ingsw.cg_38.notifyAction;

import it.polimi.ingsw.cg_38.controller.PlayerClient;
import it.polimi.ingsw.cg_38.controller.action.Action;
import it.polimi.ingsw.cg_38.controller.event.GameEvent;
import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.model.EndState;
import it.polimi.ingsw.cg_38.model.LifeState;
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
