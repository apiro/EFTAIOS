package it.polimi.ingsw.cg_38.client.notifyAction;

import it.polimi.ingsw.cg_38.client.PlayerClient;
import it.polimi.ingsw.cg_38.client.PlayerClientState;
import it.polimi.ingsw.cg_38.controller.event.GameEvent;
import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.controller.notifyEvent.EventNotifyTurn;

public class RenderNotifyTurn extends NotifyAction {
	
	private static final long serialVersionUID = 1L;

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
			client.getLogger().print("------------------------\n");
			client.getLogger().print("IS YOUR TURN !");
			client.getLogger().print("[T] You are " + client.getPlayer().getName() + " and you are " + client.getPlayer().getAvatar().getName());
			client.setPlayerClientState(PlayerClientState.ISTURN);
			client.setIsMyTurn(true);
			client.setIsInterfaceBlocked(false);
		} else {
			client.getLogger().print("------------------------\n");
			client.getLogger().print("NOT YOUR TURN !");
			client.setPlayerClientState(PlayerClientState.PLAYING);
			client.setIsMyTurn(false);
			client.setIsInterfaceBlocked(true);
		}
		return null;
	}
}
