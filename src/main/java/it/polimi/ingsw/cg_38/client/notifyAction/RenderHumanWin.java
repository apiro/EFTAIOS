package it.polimi.ingsw.cg_38.client.notifyAction;

import it.polimi.ingsw.cg_38.client.PlayerClient;
import it.polimi.ingsw.cg_38.controller.event.GameEvent;
import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.controller.gameEvent.EventFinishTurn;
import it.polimi.ingsw.cg_38.model.map.Hatch;

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
		client.getLogger().print("PLAYER " + super.player.getName() + " HAS LEFT THE SPACESHIP ! ");
		if(client.getPlayer().getName().equals(super.player.getName())) {
			client.paintHatch(true, evt.getGenerator().getAvatar().getCurrentSector());
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				client.getLogger().print("EXCEPTION IN CLOSING GAME...");
			}
			client.setIsInterfaceBlocked(true);
			return new EventFinishTurn(client.getPlayer());
		} else {
			client.paintHatch(false, evt.getGenerator().getAvatar().getCurrentSector());
			client.setIsInterfaceBlocked(false);
		}
		return null;
	}
}
