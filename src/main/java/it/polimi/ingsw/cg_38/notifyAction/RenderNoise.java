package it.polimi.ingsw.cg_38.notifyAction;

import it.polimi.ingsw.cg_38.controller.PlayerClient;
import it.polimi.ingsw.cg_38.controller.PlayerClientCLI;
import it.polimi.ingsw.cg_38.controller.event.GameEvent;
import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.notifyEvent.EventDeclareNoise;

public class RenderNoise extends NotifyAction {

	public RenderNoise(NotifyEvent evt) {
		super(evt.getGenerator(), evt);
	}

	@Override
	public Boolean isPossible(PlayerClient client) {
		return super.check(client);
	}

	@Override
	public GameEvent render(PlayerClient client) {
		client.getLogger().print("Noise declared in sector: row:" + ((EventDeclareNoise)evt).getSectorToNoise().getRow() + " col: " + ((EventDeclareNoise)evt).getSectorToNoise().getCol() + " ...");
		return null;
	}

}
