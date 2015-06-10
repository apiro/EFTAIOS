package it.polimi.ingsw.cg_38.notifyAction;

import it.polimi.ingsw.cg_38.controller.PlayerClient;
import it.polimi.ingsw.cg_38.controller.event.GameEvent;
import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.notifyEvent.EventDeclareNoise;

public class RenderNoise extends NotifyAction {

	public RenderNoise(NotifyEvent evt) {
		super(evt.getGenerator(), evt);
	}

	@Override
	public Boolean isPossible(PlayerClient client) {
		return true;
	}

	@Override
	public GameEvent render(PlayerClient client) {
		System.out.println("Noise declared in sector: row:" + ((EventDeclareNoise)evt).getSectorToNoise().getRow() + " col: " + ((EventDeclareNoise)evt).getSectorToNoise().getCol() + " ...");
		return null;
	}

}
