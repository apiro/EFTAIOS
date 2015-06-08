package it.polimi.ingsw.cg_38.notifyAction;

import it.polimi.ingsw.cg_38.controller.Client;
import it.polimi.ingsw.cg_38.controller.event.GameEvent;
import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.model.Player;
import it.polimi.ingsw.cg_38.notifyEvent.EventAttacked;

public class RenderAttacked extends NotifyAction {
	
	public RenderAttacked(NotifyEvent evt){
		
		super(evt.getGenerator() , evt);
	}

	@Override
	public Boolean isPossible(Client client) {
		if(client.getIsMyTurn())
			return true;
		return false;
	}

	@Override
	public GameEvent render(Client client) {	
		
		
		for(Player pl:((EventAttacked)evt).getKilled()){
			if(pl.getName().equals(client.getPlayer().getName())) {
				System.out.println("AN ALIEN KILLED YOU ! YOU LOOSE !");
				
			}
		}
		if(((EventAttacked)evt).getGenerator().getName().equals(client.getPlayer().getName())) {
			System.out.println("You killed an Human ! You are powered!");
		}
		
		return null;

	}

}
