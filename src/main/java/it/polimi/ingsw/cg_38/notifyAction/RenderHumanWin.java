package it.polimi.ingsw.cg_38.notifyAction;

import it.polimi.ingsw.cg_38.controller.PlayerClient;
import it.polimi.ingsw.cg_38.controller.event.GameEvent;
import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;

public class RenderHumanWin extends NotifyAction {

	private static final long serialVersionUID = 1L;

	public RenderHumanWin(NotifyEvent evt) {
		super(evt.getGenerator(), evt);
	}

	@Override
	public Boolean isPossible(PlayerClient client) {
		return super.check(client);
	}

	@Override
	public GameEvent render(PlayerClient client) {
		client.getLogger().print("Player " + super.player.getName() + " HAS LEFT THE SPACESHIP ! ");
		if(client.getPlayer().getName().equals(super.player.getName())) {
			try {
				Thread.sleep(15000);
			} catch (InterruptedException e) {
				client.getLogger().print("Interrupted Exception !");
			}
			client.setIsInterfaceBlocked(true);
		} else {
			client.setIsInterfaceBlocked(false);
		}
		return null;
	}

}
