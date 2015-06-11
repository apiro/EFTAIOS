package it.polimi.ingsw.cg_38.notifyAction;

import it.polimi.ingsw.cg_38.controller.PlayerClient;
import it.polimi.ingsw.cg_38.controller.PlayerClientState;
import it.polimi.ingsw.cg_38.controller.event.GameEvent;
import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;

public class RenderLoose extends NotifyAction {

	public RenderLoose(NotifyEvent evt) {
		super(evt.getGenerator(), evt);
	}

	@Override
	public Boolean isPossible(PlayerClient client) {
		return null;
	}

	@Override
	public GameEvent render(PlayerClient client) {
		System.out.println("YOU LOOSE !");
		client.setIsInterfaceBlocked(true);
		client.setPlayerClientState(PlayerClientState.winner);
		client.closeClient();
		return null;
	}

}
