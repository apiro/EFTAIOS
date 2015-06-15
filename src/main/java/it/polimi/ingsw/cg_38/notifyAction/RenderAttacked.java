package it.polimi.ingsw.cg_38.notifyAction;

import it.polimi.ingsw.cg_38.controller.PlayerClient;
import it.polimi.ingsw.cg_38.controller.event.GameEvent;
import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.gameEvent.EventAliensWinner;
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
		
		
		//se il giocatore a cui arriva questo evento è il generator dell'evento cioè chi ha attaccato
		if(((EventAttacked)evt).getGenerator().getName().equals(client.getPlayer().getName())) {
			
			client.setPlayer(evt.getGenerator());
			//ha ucciso almeno un giocatore
			if(((EventAttacked)evt).getAreYouPowered()) {
				System.out.println("You killed someone !");
				//se ha attaccato un alieno verifico se ci sono altri human nella mappa
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
			} else {
				
				System.out.println("There are no players in the sector you have choosen !");
			}
		}
		
		return null;
	}
}
