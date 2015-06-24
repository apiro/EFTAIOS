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
		return true;
	}

	@Override
	public GameEvent render(PlayerClient client) {
		if(areWinner) {
			client.getLogger().print("ALIENS WIN !!!");
			client.setIsInterfaceBlocked(true);
			client.setClientAlive(false);
			/*client.closeClient();*/
		} else {
			client.setIsInterfaceBlocked(false);
		}
		return null;
	}
}