package it.polimi.ingsw.cg_38.notifyAction;

import it.polimi.ingsw.cg_38.controller.PlayerClient;
import it.polimi.ingsw.cg_38.controller.PlayerClientCLI;
import it.polimi.ingsw.cg_38.controller.event.GameEvent;
import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.gameEvent.EventAliensWinner;
import it.polimi.ingsw.cg_38.gameEvent.EventContinue;
import it.polimi.ingsw.cg_38.model.Alien;
import it.polimi.ingsw.cg_38.notifyEvent.EventAttacked;

public class RenderAttacked extends NotifyAction {
	
	private static final long serialVersionUID = 1L;

	public RenderAttacked(NotifyEvent evt){
		
		super(evt.getGenerator() , evt);
	}

	@Override
	public Boolean isPossible(PlayerClient client) {
		//deve sempre ritornare true perchè l'evento di attaccato deve essere processato anche
		//da chi non è del turno(anche chi è stato attaccato deve fare la render)
		return super.check(client);
	}

	@Override
	public GameEvent render(PlayerClient client) {	
		
		
		/*if(((EventAttacked)evt).getGenerator().getName().equals(client.getPlayer().getName())) {*/
			
			client.setPlayer(evt.getGenerator());
			
			if(((EventAttacked)evt).getAreYouPowered()) {
				client.getLogger().print("You killed someone !");
				//verifico chi è stato a fare l'attacco
				if(((EventAttacked)evt).getGenerator().getAvatar() instanceof Alien) {
					
					if(((EventAttacked)evt).getAreThereOtherHumans()) {
						
						client.setIsInterfaceBlocked(false);
						return null;
					} else {
						
						return new EventAliensWinner(client.getPlayer());
					}
				} else {
					
					client.setIsInterfaceBlocked(true);
					return new EventContinue();
				}
			} else {
				
				client.getLogger().print("There are no players in the sector you have choosen !");
				client.setIsInterfaceBlocked(false);
				return null;
			}
		/*}
		client.setIsInterfaceBlocked(true);
		return null;*/
	}
}
