package it.polimi.ingsw.cg_38.notifyAction;

import it.polimi.ingsw.cg_38.controller.PlayerClient;
import it.polimi.ingsw.cg_38.controller.event.GameEvent;
import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;

public class RenderNoSideEffectCard extends NotifyAction {

	private static final long serialVersionUID = 1L;

	public RenderNoSideEffectCard(NotifyEvent evt) {
		super(evt.getGenerator(), evt);
	}

	@Override
	public Boolean isPossible(PlayerClient client) {
		return super.check(client);
	}

	@Override
	public GameEvent render(PlayerClient client) {
		client.setPlayer(evt.getGenerator());
		System.out.println("Card used by " + evt.getGenerator().getName() + " in sector: row: " +
				evt.getGenerator().getAvatar().getCurrentSector().getRow() + " col:" + 
				evt.getGenerator().getAvatar().getCurrentSector().getCol());
		return null;
	}

}
