package it.polimi.ingsw.cg_38.notifyAction;

import it.polimi.ingsw.cg_38.controller.PlayerClient;
import it.polimi.ingsw.cg_38.controller.PlayerClientState;
import it.polimi.ingsw.cg_38.controller.event.GameEvent;
import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;

public class RenderLoose extends NotifyAction {

	private static final long serialVersionUID = 1L;

	public RenderLoose(NotifyEvent evt) {
		super(evt.getGenerator(), evt);
	}

	@Override
	public Boolean isPossible(PlayerClient client) {
		return true;
	}

	@Override
	public GameEvent render(PlayerClient client) {
		
		if(evt.getGenerator().getName().equals(client.getPlayer().getName())) {
			
			System.out.println("YOU LOOSE !");
			client.setIsInterfaceBlocked(true);
			client.setPlayerClientState(PlayerClientState.looser);
			client.closeClient();
		}
		System.out.println("Player killed in sector: row: " + 
				evt.getGenerator().getAvatar().getCurrentSector().getRow() + " col: " + 
				evt.getGenerator().getAvatar().getCurrentSector().getCol());
		return null;
	}

}
