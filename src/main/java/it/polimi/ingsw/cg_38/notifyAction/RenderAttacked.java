package it.polimi.ingsw.cg_38.notifyAction;

import it.polimi.ingsw.cg_38.controller.PlayerClient;
import it.polimi.ingsw.cg_38.controller.PlayerClientState;
import it.polimi.ingsw.cg_38.controller.event.GameEvent;
import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.model.Player;
import it.polimi.ingsw.cg_38.notifyEvent.EventAttacked;

public class RenderAttacked extends NotifyAction {
	
	public RenderAttacked(NotifyEvent evt){
		
		super(evt.getGenerator() , evt);
	}

	@Override
	public Boolean isPossible(PlayerClient client) {
		if(client.getPlayerClientState().equals(PlayerClientState.isTurn)){
			return true;
		}
		return false;
	}

	@Override
	public GameEvent render(PlayerClient client) {	
		
		/*client.setPlayer(evt.getGenerator());*/
		for(Player pl:((EventAttacked)evt).getKilled()){
			if(pl.getName().equals(client.getPlayer().getName())) {
				System.out.println("AN ALIEN KILLED YOU ! YOU LOOSE !");
				client.setIsInterfaceBlocked(true);
			}
		}
		if(((EventAttacked)evt).getGenerator().getName().equals(client.getPlayer().getName()) &&
				((EventAttacked)evt).getKilled().size() >= 1) {
			System.out.println("You killed an Human ! You are powered!");
			client.setIsInterfaceBlocked(false);
		} else {
			System.out.println("There are no Players in the sector you have choosen !");
		}
		return null;
	}
}
