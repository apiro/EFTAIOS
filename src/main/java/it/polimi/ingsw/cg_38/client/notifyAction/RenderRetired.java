package it.polimi.ingsw.cg_38.client.notifyAction;

import it.polimi.ingsw.cg_38.client.PlayerClient;
import it.polimi.ingsw.cg_38.controller.event.GameEvent;
import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.controller.gameEvent.EventFinishTurn;

public class RenderRetired extends NotifyAction {

	private static final long serialVersionUID = 1L;

	public RenderRetired(NotifyEvent evt) {
		super(evt.getGenerator(), evt);
	}

	@Override
	public Boolean isPossible(PlayerClient client) {
		return super.check(client);
	}

	@Override
	public GameEvent render(PlayerClient client) {
		client.getLogger().print("Player: " + super.getPlayer().getName() + " left the Room...");
		if(client.getPlayer().getName().equals(super.getPlayer().getName())) {
			client.setPlayer(evt.getGenerator());
			client.setIsInterfaceBlocked(true);
			client.getLogger().print("GoodBye !");
			return new EventFinishTurn(client.getPlayer());
		}
		return null;
	}
}
