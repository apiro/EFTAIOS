package it.polimi.ingsw.cg_38.notifyAction;

import it.polimi.ingsw.cg_38.controller.PlayerClient;
import it.polimi.ingsw.cg_38.controller.PlayerClientCLI;
import it.polimi.ingsw.cg_38.controller.PlayerClientState;
import it.polimi.ingsw.cg_38.controller.event.GameEvent;
import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.notifyEvent.EventAddedToGame;

public class AddedToGame extends NotifyAction {

	private NotifyEvent evt;
	
	public NotifyEvent getEvt() {
		return evt;
	}

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
			client.setPlayerClientState(PlayerClientState.connected);
			client.setIsInterfaceBlocked(true);
		}
		return null;		
	}

	public Boolean isPossible(PlayerClient client) {
		return true;
	}
}