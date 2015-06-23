package it.polimi.ingsw.cg_38.client.notifyAction;

import it.polimi.ingsw.cg_38.client.PlayerClient;
import it.polimi.ingsw.cg_38.controller.event.GameEvent;
import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;

public class RenderHatchBlocked extends NotifyAction {

	private static final long serialVersionUID = 1L;

	public RenderHatchBlocked(NotifyEvent evt) {
		super(evt.getGenerator(), evt);
	}

	@Override
	public Boolean isPossible(PlayerClient client) {
		return super.check(client);
	}

	@Override
	public GameEvent render(PlayerClient client) {
		client.paintHatch(false, evt.getGenerator().getAvatar().getCurrentSector());
		client.getLogger().print("HATCH BLOCKED !");
		return null;
	}
}