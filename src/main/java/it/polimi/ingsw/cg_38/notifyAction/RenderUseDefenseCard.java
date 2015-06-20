package it.polimi.ingsw.cg_38.notifyAction;

import it.polimi.ingsw.cg_38.controller.PlayerClient;
import it.polimi.ingsw.cg_38.controller.PlayerClientCLI;
import it.polimi.ingsw.cg_38.controller.event.GameEvent;
import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.gameEvent.EventContinue;
import it.polimi.ingsw.cg_38.notifyEvent.EventCardUsed;

public class RenderUseDefenseCard extends RenderNoSideEffectCard {

	private static final long serialVersionUID = 1L;

	public RenderUseDefenseCard(NotifyEvent evt) {
		super(evt);
	}

	@Override
	public GameEvent render(PlayerClient client) {
		if(client.getPlayer().getName().equals(evt.getGenerator().getName())) client.setPlayer(evt.getGenerator());
		client.getLogger().print(((EventCardUsed)this.getEvt()).getTypeCard().toString() +
				"card used by " + evt.getGenerator().getName() + " in sector: row: " +
				evt.getGenerator().getAvatar().getCurrentSector().getRow() + " col:" + 
				evt.getGenerator().getAvatar().getCurrentSector().getCol());
		return new EventContinue();
	}
}
