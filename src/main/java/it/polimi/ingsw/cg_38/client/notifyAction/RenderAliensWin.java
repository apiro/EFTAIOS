package it.polimi.ingsw.cg_38.client.notifyAction;

import it.polimi.ingsw.cg_38.client.PlayerClient;
import it.polimi.ingsw.cg_38.controller.event.GameEvent;
import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.controller.notifyEvent.EventNotifyAliensWin;

public class RenderAliensWin extends NotifyAction {

	private static final long serialVersionUID = 1L;
	private Boolean areWinner;

	public RenderAliensWin(NotifyEvent evt) {
		super(evt.getGenerator(), evt);
		this.areWinner = ((EventNotifyAliensWin)evt).getAreWinner();
	}

	@Override
	public Boolean isPossible(PlayerClient client) {
		return super.check(client);
	}

	@Override
	public GameEvent render(PlayerClient client) {
		if(areWinner) {
			client.getLogger().print("ALIENS WON !!!");
			client.setIsInterfaceBlocked(true);
			client.setClientAlive(false);
		} else {
			client.setIsInterfaceBlocked(false);
		}
		return null;
	}
}