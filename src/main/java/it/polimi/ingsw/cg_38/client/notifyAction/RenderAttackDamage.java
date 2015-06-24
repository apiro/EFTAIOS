package it.polimi.ingsw.cg_38.client.notifyAction;

import it.polimi.ingsw.cg_38.client.PlayerClient;
import it.polimi.ingsw.cg_38.client.PlayerClientState;
import it.polimi.ingsw.cg_38.controller.event.GameEvent;
import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.controller.gameEvent.EventContinue;
import it.polimi.ingsw.cg_38.controller.notifyEvent.EventSufferAttack;
import it.polimi.ingsw.cg_38.model.Human;
import it.polimi.ingsw.cg_38.model.Player;

public class RenderAttackDamage extends NotifyAction {

	public RenderAttackDamage(NotifyEvent evt) {
		super(evt.getGenerator(), evt);
	}

	private static final long serialVersionUID = 1L;

	@Override
	public Boolean isPossible(PlayerClient client) {
		return super.check(client);
	}

	@Override
	public GameEvent render(PlayerClient client) {
		
		client.getLogger().print(evt.getGenerator().getName() + " has attacked in sector: row:" +
				evt.getGenerator().getAvatar().getCurrentSector().getRow() + " , col: " +
						evt.getGenerator().getAvatar().getCurrentSector().getCol() + " ...");
		
		for(Player pl:((EventSufferAttack)evt).getKilled()){
			client.getLogger().print(pl.getName() + " has been killed in sector: row: " + 
					evt.getGenerator().getAvatar().getCurrentSector().getRow() + " col: " + 
					evt.getGenerator().getAvatar().getCurrentSector().getCol() );
			if(pl.getAvatar() instanceof Human)
				client.getLogger().print("He was a Human");
			else
				client.getLogger().print("He was an Alien");
			
			if(pl.getName().equals(client.getPlayer().getName())) {
				this.renderLoose(client);
			}
		}
		
		return new EventContinue();
	}

	public void renderLoose(PlayerClient client) {
		
		client.getLogger().print("YOU LOOSE !");
		client.setIsInterfaceBlocked(true);
		client.setClientAlive(false);
		client.setPlayerClientState(PlayerClientState.looser);
	}
}
