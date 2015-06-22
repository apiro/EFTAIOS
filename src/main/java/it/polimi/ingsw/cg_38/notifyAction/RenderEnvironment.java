package it.polimi.ingsw.cg_38.notifyAction;

import it.polimi.ingsw.cg_38.controller.Client;
import it.polimi.ingsw.cg_38.controller.PlayerClient;
import it.polimi.ingsw.cg_38.controller.PlayerClientState;
import it.polimi.ingsw.cg_38.controller.event.GameEvent;
import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.model.Player;
import it.polimi.ingsw.cg_38.notifyEvent.EventNotifyEnvironment;

public class RenderEnvironment extends NotifyAction {
	
	private static final long serialVersionUID = 1L;
	Client client;
	
	public RenderEnvironment(NotifyEvent evt){
		
		super(evt.getGenerator() , evt);
	}

	@Override
	public Boolean isPossible(PlayerClient client) {
		if(client.getMap() == null) {
			return true;
		}
		return false;
	}

	@Override
	public GameEvent render(PlayerClient client) {
		/**
		 * PERFORM NOTIFYEVENT. NO TOKEN-ACTION
		 * */
		for(Player pl:((EventNotifyEnvironment)super.getEvt()).getMappingPlayerAvatar()) {
			if(pl.getName().equals(client.getPlayer().getName())) {
				client.setPlayer(pl);
				client.getLogger().print("The server assigned you : " + client.getPlayer().getAvatar().getName() + "...");
			}
		}
		client.setClientAlive(true);
		client.setMap(((EventNotifyEnvironment)evt).getMap());
		client.setPlayerClientState(PlayerClientState.playing);
		client.getLogger().print("----------------------------------------------------------------------");
		client.getLogger().print("Starting Game : Waiting for the first turn message ...");
		client.setIsInterfaceBlocked(true);
		return null;
	}
}
