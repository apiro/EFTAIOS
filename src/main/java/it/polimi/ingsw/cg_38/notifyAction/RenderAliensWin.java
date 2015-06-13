package it.polimi.ingsw.cg_38.notifyAction;

import it.polimi.ingsw.cg_38.controller.PlayerClient;
import it.polimi.ingsw.cg_38.controller.event.GameEvent;
import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.model.Alien;

public class RenderAliensWin extends NotifyAction {

	public RenderAliensWin(NotifyEvent evt) {
		super(evt.getGenerator(), evt);
	}

	@Override
	public Boolean isPossible(PlayerClient client) {
		if(client.getPlayer().getAvatar() instanceof Alien &&
				super.check(client)) {
			return true;
		}
		return false;
	}

	@Override
	public GameEvent render(PlayerClient client) {
		System.out.println("YOU KILLED ALL THE HUMANS, YOU WIN !");
		return null;
	}

}
