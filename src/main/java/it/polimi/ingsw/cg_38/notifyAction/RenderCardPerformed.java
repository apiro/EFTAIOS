package it.polimi.ingsw.cg_38.notifyAction;

import it.polimi.ingsw.cg_38.controller.PlayerClient;
import it.polimi.ingsw.cg_38.controller.event.GameEvent;
import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.gameEvent.EventContinue;

public class RenderCardPerformed extends NotifyAction {

	private static final long serialVersionUID = 1L;

	public RenderCardPerformed(NotifyEvent evt) {
		super(evt.getGenerator(), evt);
	}

	@Override
	public Boolean isPossible(PlayerClient client) {
		return super.check(client);
	}

	@Override
	public GameEvent render(PlayerClient client) {
		client.getLogger().print("The card's effect has been performed !");
		client.setPlayer(evt.getGenerator());
		client.updateCards();
		return new EventContinue();
	}
}
