package it.polimi.ingsw.cg_38.notifyAction;

import it.polimi.ingsw.cg_38.controller.Client;
import it.polimi.ingsw.cg_38.controller.event.GameEvent;
import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.notifyEvent.EventNotifyTurn;

public class RenderNotifyTurn extends NotifyAction {
	
	public RenderNotifyTurn(NotifyEvent evt){
		
		super(evt.getGenerator() , evt);		
	}

	@Override
	public Boolean isPossible(Client client) {
		return null;
	}

	@Override
	public GameEvent render(Client client) {
		
		if(((EventNotifyTurn)evt).getPlayerOfTurn().getName().equals(client.getPlayer().getName())) {
			client.setIsMyTurn(true);
		} else {
			System.out.println("NOT YOUR TURN !");
			client.setIsMyTurn(false);
			
		}
		return null;
		

	}

}
