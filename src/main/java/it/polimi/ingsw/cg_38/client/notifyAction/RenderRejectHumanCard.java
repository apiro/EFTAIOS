package it.polimi.ingsw.cg_38.client.notifyAction;

import it.polimi.ingsw.cg_38.client.PlayerClient;
import it.polimi.ingsw.cg_38.controller.event.GameEvent;
import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.controller.notifyEvent.EventRejectCardHuman;
import it.polimi.ingsw.cg_38.model.deck.ObjectCard;

public class RenderRejectHumanCard extends NotifyAction {

	private static final long serialVersionUID = 1L;
	private ObjectCard card;

	public RenderRejectHumanCard(NotifyEvent evt) {
		super(evt.getGenerator(), evt);
		this.card = ((EventRejectCardHuman)evt).getCard();
	}

	@Override
	public Boolean isPossible(PlayerClient client) {
		return super.check(client);
	}

	@Override
	public GameEvent render(PlayerClient client) {
		if(client.getPlayer().getName().equals(super.player.getName())) client.setPlayer(evt.getGenerator());
		client.getLogger().print(super.getPlayer().getName() + " has rejected a: " + card.getType().toString() + " card !");
		return null;
	}
}
