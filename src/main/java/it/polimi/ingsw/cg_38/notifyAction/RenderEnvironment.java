package it.polimi.ingsw.cg_38.notifyAction;

import it.polimi.ingsw.cg_38.controller.Client;
import it.polimi.ingsw.cg_38.controller.event.GameEvent;
import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.model.Player;
import it.polimi.ingsw.cg_38.notifyEvent.EventNotifyEnvironment;

public class RenderEnvironment extends NotifyAction {
	
	Client client;
	
	public RenderEnvironment(NotifyEvent evt){
		
		super(evt.getGenerator() , evt);
	}

	@Override
	public Boolean isPossible(Client client) {
		if(client.getMap() != null) {
			return true;
		}
		return false;
	}

	@Override
	public GameEvent render(Client client) {
		
		for(Player pl:((EventNotifyEnvironment)evt).getMappingPlayerAvatar()) {
			if(pl.getName().equals(client.getPlayer().getName())) {
				client.setPlayer(pl);
				System.out.println("The server assigned you : " + client.getPlayer().getAvatar().getName() + "...");
			}
		}
		client.setMap(((EventNotifyEnvironment)evt).getMap());
		System.out.println("----------------------------------------------------------------------");
		System.out.println("Starting Game : Waiting for the first turn message ...");
		return null;

	}

}
