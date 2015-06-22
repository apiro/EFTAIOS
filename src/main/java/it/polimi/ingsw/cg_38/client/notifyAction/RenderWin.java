package it.polimi.ingsw.cg_38.client.notifyAction;

import it.polimi.ingsw.cg_38.client.PlayerClient;
import it.polimi.ingsw.cg_38.client.PlayerClientState;
import it.polimi.ingsw.cg_38.controller.event.GameEvent;
import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.controller.gameEvent.EventFinishTurn;

public class RenderWin extends NotifyAction {

	private static final long serialVersionUID = 1L;

	public RenderWin(NotifyEvent evt) {
		super(evt.getGenerator(), evt);
	}

	@Override
	public Boolean isPossible(PlayerClient client) {
		return super.check(client);
	}

	@Override
	public GameEvent render(PlayerClient client) {
		client.getLogger().print("YOU WIN !");
		client.setIsInterfaceBlocked(true);
		client.setPlayerClientState(PlayerClientState.winner);
		client.paintHatch(true, evt.getGenerator().getAvatar().getCurrentSector());
		client.closeClient();
		return new EventFinishTurn(super.evt.getGenerator());
	}
}
