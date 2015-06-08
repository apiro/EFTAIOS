package it.polimi.ingsw.cg_38.notifyAction;

import it.polimi.ingsw.cg_38.controller.Client;
import it.polimi.ingsw.cg_38.controller.ClientInterface;
import it.polimi.ingsw.cg_38.controller.GameState;
import it.polimi.ingsw.cg_38.controller.event.GameEvent;
import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.notifyEvent.EventAddedToGame;
import it.polimi.ingsw.cg_38.notifyEvent.EventNotifyEnvironment;

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
	public GameEvent render(Client client) {
		
		return null;		
	}

	public Boolean isPossible(Client client) {
		if(((EventAddedToGame)evt).getAdded()) {
			return true;
		}
		else return false;
	}
}
