package it.polimi.ingsw.cg_38.notifyAction;

import it.polimi.ingsw.cg_38.controller.Client;
import it.polimi.ingsw.cg_38.controller.event.GameEvent;
import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;

public class RenderError extends NotifyAction {
	
	public RenderError(NotifyEvent evt){
		
		super(evt.getGenerator() , evt);
	}

	@Override
	public Boolean isPossible(Client client) {
		return null;
	}

	@Override
	public GameEvent render(Client client) {
		
		System.out.println("There was an error in processing your previous GameEvent ... RETRY !");
		return null;
	}

}
