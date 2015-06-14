package it.polimi.ingsw.cg_38.notifyAction;

import it.polimi.ingsw.cg_38.controller.PlayerClient;
import it.polimi.ingsw.cg_38.controller.event.GameEvent;
import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.model.Player;
import it.polimi.ingsw.cg_38.notifyEvent.EventDeclarePosition;

public class RenderSpotlight extends NotifyAction {

	public RenderSpotlight(NotifyEvent evt) {
		super(evt.getGenerator(), evt);
	}

	@Override
	public Boolean isPossible(PlayerClient client) {
		return super.check(client);
	}

	@Override
	public GameEvent render(PlayerClient client) {
		
		client.setPlayer(evt.getGenerator());
		if(((EventDeclarePosition)evt).getToDeclare().size() >= 1) {
			System.out.println("Declared Light in sector: row: " + ((EventDeclarePosition)evt).getToDeclare().get(0).getAvatar().getCurrentSector().getRow() + 
				"col: " + ((EventDeclarePosition)evt).getToDeclare().get(0).getAvatar().getCurrentSector().getCol());
			for(Player pl:((EventDeclarePosition)evt).getToDeclare()) {
				System.out.println("/t -> " + pl.getName());
			}
		}
		System.out.println("Card used !");
		return null;
	}

}