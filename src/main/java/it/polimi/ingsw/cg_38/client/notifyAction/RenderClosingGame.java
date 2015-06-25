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
		return true;
	}

	@Override
	public GameEvent render(PlayerClient client) {
		client.getLogger().print("Game closing in 30 secs ... ");
		/*client.setIsInterfaceBlocked(true);
		client.setClientAlive(false);*/
		try {
			Thread.sleep(30000);
		} catch (InterruptedException e) {
			client.getLogger().print("EXCEPTION IN SLEEPING THE THREAD !");
		}
		client.closeClient();
		return null;
	}
}
