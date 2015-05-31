package it.polimi.ingsw.cg_38.notifyAction;

import it.polimi.ingsw.cg_38.controller.ClientInterface;
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
		super(evt2.getGenerator());
		this.setEvt(evt2);
	}

	@Override
	public void render(ClientInterface view) {
	}

	public Boolean isPossible() {
		if(((EventAddedToGame)evt).getAdded()) {
			return true;
		}
		else return false;
	}
}
