package it.polimi.ingsw.cg_38.client.notifyAction;

import it.polimi.ingsw.cg_38.client.PlayerClient;
import it.polimi.ingsw.cg_38.client.PlayerClientState;
import it.polimi.ingsw.cg_38.controller.event.GameEvent;
import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.controller.notifyEvent.EventAddedToGame;

public class AddedToGame extends NotifyAction {

	private static final long serialVersionUID = 1L;
	private NotifyEvent evt;
	
	@Override
	public NotifyEvent getEvt() {
		return evt;
	}

	@Override
	public void setEvt(NotifyEvent evt) {
		this.evt = evt;
	}

	public AddedToGame(NotifyEvent evt2) {
		super(evt2.getGenerator() , evt2);
	}

	@Override
	public GameEvent render(PlayerClient client) {
		client.getLogger().print(this.getEvt().getGenerator().getName() + " has been added ? " + ((EventAddedToGame)evt).getAdded());
		if(((EventAddedToGame)evt).getAdded()) {
			client.setPlayerClientState(PlayerClientState.CONNECTED);
			client.setIsInterfaceBlocked(true);
		}
		client.setIsInterfaceBlocked(true);
		return null;		
	}

	@Override
	public Boolean isPossible(PlayerClient client) {
		return true;
	}
}