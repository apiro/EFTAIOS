package it.polimi.ingsw.cg_38.notifyAction;

import it.polimi.ingsw.cg_38.controller.PlayerClient;
import it.polimi.ingsw.cg_38.controller.PlayerClientCLI;
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
			client.getLogger().print("IS YOUR TURN !");
			client.getLogger().print("YOU ARE " + client.getPlayer().getName() + "AND YOU ARE AN " + client.getPlayer().getAvatar().getName());
			client.setPlayerClientState(PlayerClientState.isTurn);
			client.setIsMyTurn(true);
			client.setIsInterfaceBlocked(false);
		} else {
			client.getLogger().print("NOT YOUR TURN !");
			client.setPlayerClientState(PlayerClientState.playing);
			client.setIsMyTurn(false);
			client.setIsInterfaceBlocked(true);
		}
		return null;
	}
}
