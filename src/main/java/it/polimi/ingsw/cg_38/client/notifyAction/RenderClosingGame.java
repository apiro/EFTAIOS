package it.polimi.ingsw.cg_38.client.notifyAction;

import it.polimi.ingsw.cg_38.client.PlayerClient;
import it.polimi.ingsw.cg_38.controller.event.GameEvent;
import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;

public class RenderClosingGame extends NotifyAction {

	private static final long serialVersionUID = 1L;

	public RenderClosingGame(NotifyEvent evt) {
		super(evt.getGenerator(), evt);
	}

	@Override
	public Boolean isPossible(PlayerClient client) {
		return super.check(client);
	}

	@Override
	public GameEvent render(PlayerClient client) {
		client.getLogger().print("REACHED THE MAX NUMBER OF TURNS:\n->ALIENS WON !!!");
		client.setIsInterfaceBlocked(true);
		client.setClientAlive(false);
		client.closeClient();
		return null;
	}
}

