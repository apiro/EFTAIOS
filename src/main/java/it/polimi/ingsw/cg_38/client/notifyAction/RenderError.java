package it.polimi.ingsw.cg_38.client.notifyAction;

import it.polimi.ingsw.cg_38.client.PlayerClient;
import it.polimi.ingsw.cg_38.controller.event.GameEvent;
import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.controller.notifyEvent.EventNotifyError;

public class RenderError extends NotifyAction {
	
	private static final long serialVersionUID = 1L;

	public RenderError(NotifyEvent evt){
		
		super(evt.getGenerator() , evt);
	}

	@Override
	public Boolean isPossible(PlayerClient client) {
		return true;
	}

	@Override
	public GameEvent render(PlayerClient client) {
		if(client.getPlayer().getName().equals(evt.getGenerator().getName())){
			client.setPlayer(evt.getGenerator());
			client.updateCards();
			client.getLogger().print("There was an error in processing " + 
			((EventNotifyError)evt).getRelatedAction().getClass().toString().substring(46) + 
			" action related to your previous GameEvent ... RETRY !");
			client.setIsInterfaceBlocked(false);
		} else {
			client.setIsInterfaceBlocked(true);
		}
		return null;
	}
}