package it.polimi.ingsw.cg_38.notifyAction;

import it.polimi.ingsw.cg_38.controller.PlayerClient;
import it.polimi.ingsw.cg_38.controller.PlayerClientCLI;
import it.polimi.ingsw.cg_38.controller.event.GameEvent;
import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.gameEvent.EventContinue;
import it.polimi.ingsw.cg_38.model.ObjectCardType;
import it.polimi.ingsw.cg_38.notifyEvent.EventCardUsed;

public class RenderNoSideEffectCard extends NotifyAction {

	private static final long serialVersionUID = 1L;
	private ObjectCardType cardType;

	public RenderNoSideEffectCard(NotifyEvent evt) {
		super(evt.getGenerator(), evt);
		this.cardType = ((EventCardUsed)evt).getTypeCard();
	}

	@Override
	public Boolean isPossible(PlayerClient client) {
		return super.check(client);
	}

	@Override
	public GameEvent render(PlayerClient client) {
		if(client.getPlayer().getName().equals(evt.getGenerator().getName())) client.setPlayer(evt.getGenerator());
		client.getLogger().print(((EventCardUsed)this.getEvt()).getTypeCard().toString() +
				" card used by " + evt.getGenerator().getName() + " in sector: row: " +
				evt.getGenerator().getAvatar().getCurrentSector().getRow() + " col:" + 
				evt.getGenerator().getAvatar().getCurrentSector().getCol());
		return null;
	}
}
