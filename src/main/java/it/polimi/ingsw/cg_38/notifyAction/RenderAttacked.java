package it.polimi.ingsw.cg_38.notifyAction;

import it.polimi.ingsw.cg_38.controller.PlayerClient;
import it.polimi.ingsw.cg_38.controller.event.GameEvent;
import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.gameEvent.EventAliensWinner;
import it.polimi.ingsw.cg_38.gameEvent.EventPlayerLooser;
import it.polimi.ingsw.cg_38.model.Alien;
import it.polimi.ingsw.cg_38.model.Player;
import it.polimi.ingsw.cg_38.notifyEvent.EventAttacked;

public class RenderAttacked extends NotifyAction {
	
	public RenderAttacked(NotifyEvent evt){
		
		super(evt.getGenerator() , evt);
	}

	@Override
	public Boolean isPossible(PlayerClient client) {
		//deve sempre ritornare true perchè l'evento di attaccato deve essere processato anche
		//da chi non è del turno(anche chi è stato attaccato deve fare la render)
		return true;
	}

	@Override
	public GameEvent render(PlayerClient client) {	
		
		/*client.setPlayer(evt.getGenerator());*/
		for(Player pl:((EventAttacked)evt).getKilled()){
			if(pl.getName().equals(client.getPlayer().getName())) {
				return new EventPlayerLooser(client.getPlayer());
			}
		}
		if(((EventAttacked)evt).getGenerator().getName().equals(client.getPlayer().getName()) &&
				((EventAttacked)evt).getKilled().size() >= 1) {
			System.out.println("You killed someone !");
			if(((EventAttacked)evt).getGenerator().getAvatar() instanceof Alien) {
				if(((EventAttacked)evt).getAreThereOtherHumans()) {
					client.setIsInterfaceBlocked(false);
					return null;
				} else {
					return new EventAliensWinner(client.getPlayer());
				}
			} else {
				client.setIsInterfaceBlocked(false);
				return null;
			}
		} else if (((EventAttacked)evt).getGenerator().getName().equals(client.getPlayer().getName()) &&
				!(((EventAttacked)evt).getKilled().size() >= 1)) {
			System.out.println("There are no Players in the sector you have choosen !");
		}
		return null;
	}
}
