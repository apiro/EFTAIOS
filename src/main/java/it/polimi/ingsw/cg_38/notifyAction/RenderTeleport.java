package it.polimi.ingsw.cg_38.notifyAction;

import it.polimi.ingsw.cg_38.controller.PlayerClient;
import it.polimi.ingsw.cg_38.controller.PlayerClientCLI;
import it.polimi.ingsw.cg_38.controller.event.GameEvent;
import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.gameEvent.EventContinue;


public class RenderTeleport extends RenderMoved {

	private static final long serialVersionUID = 1L;

	public RenderTeleport(NotifyEvent evt) {
		super(evt);
	}

	@Override
	public GameEvent render(PlayerClient client) {
		client.getLogger().print("* Teleported in HumanStartingPoint ! *");
		return new EventContinue();
	}
}
