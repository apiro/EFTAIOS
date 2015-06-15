package it.polimi.ingsw.cg_38.notifyAction;

import it.polimi.ingsw.cg_38.controller.PlayerClient;
import it.polimi.ingsw.cg_38.controller.event.GameEvent;
import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.gameEvent.EventPlayerLooser;
import it.polimi.ingsw.cg_38.model.Player;
import it.polimi.ingsw.cg_38.notifyEvent.EventSufferAttack;

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
		
		System.out.println(evt.getGenerator().getName() + " has attacked in sector: row:" +
				evt.getGenerator().getAvatar().getCurrentSector().getRow() + " , col: " +
						evt.getGenerator().getAvatar().getCurrentSector().getCol() + " ...");
		
		for(Player pl:((EventSufferAttack)evt).getKilled()){
			if(pl.getName().equals(client.getPlayer().getName())) {
				return new EventPlayerLooser(client.getPlayer());
			}
		}
		
		return null;
	}

}
