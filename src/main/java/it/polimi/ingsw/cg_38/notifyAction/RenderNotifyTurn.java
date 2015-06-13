package it.polimi.ingsw.cg_38.notifyAction;

import it.polimi.ingsw.cg_38.controller.PlayerClient;
import it.polimi.ingsw.cg_38.controller.PlayerClientState;
import it.polimi.ingsw.cg_38.controller.event.GameEvent;
import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.notifyEvent.EventNotifyTurn;

public class RenderNotifyTurn extends NotifyAction {
	
	public RenderNotifyTurn(NotifyEvent evt){
		
		super(evt.getGenerator() , evt);		
	}

	@Override
	public Boolean isPossible(PlayerClient client) {
		return super.check(client);
	}

	@Override
	public GameEvent render(PlayerClient client) {
		
		if(((EventNotifyTurn)evt).getPlayerOfTurn().getName().equals(client.getPlayer().getName())) {
			System.out.println("IS YOUR TURN !");
			client.setPlayerClientState(PlayerClientState.isTurn);
			/*client.setIsMyTurn(true);*/
			client.setIsInterfaceBlocked(false);
		} else {
			System.out.println("NOT YOUR TURN !");
			client.setPlayerClientState(PlayerClientState.playing);
			/*client.setIsMyTurn(false);*/
			client.setIsInterfaceBlocked(true);
		}
		return null;
	}
}
