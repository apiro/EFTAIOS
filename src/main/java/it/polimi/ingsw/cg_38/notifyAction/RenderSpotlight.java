package it.polimi.ingsw.cg_38.notifyAction;

import it.polimi.ingsw.cg_38.controller.PlayerClient;
import it.polimi.ingsw.cg_38.controller.PlayerClientCLI;
import it.polimi.ingsw.cg_38.controller.event.GameEvent;
import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.gameEvent.EventContinue;
import it.polimi.ingsw.cg_38.model.Player;
import it.polimi.ingsw.cg_38.notifyEvent.EventDeclarePosition;

public class RenderSpotlight extends NotifyAction {

	private static final long serialVersionUID = 1L;

	public RenderSpotlight(NotifyEvent evt) {
		super(evt.getGenerator(), evt);
	}

	@Override
	public Boolean isPossible(PlayerClient client) {
		return super.check(client);
	}

	@Override
	public GameEvent render(PlayerClient client) {
		
		/*client.setPlayer(evt.getGenerator());*/
		if(((EventDeclarePosition)evt).getToDeclare().size() >= 1) {
			client.getLogger().print("Declared Light in sector: row: " + ((EventDeclarePosition)evt).getToDeclare().get(0).getAvatar().getCurrentSector().getRow() + 
				"col: " + ((EventDeclarePosition)evt).getToDeclare().get(0).getAvatar().getCurrentSector().getCol());
			int i = 0;
			for(Player pl:((EventDeclarePosition)evt).getToDeclare()) {
				client.getLogger().print(i + ") -> " + pl.getName());
				i++;
			}
		}
		if(client.getPlayer().getName().equals(evt.getGenerator().getName())) {
			client.setPlayer(evt.getGenerator());
			client.getLogger().print("Card used !");
		}
		return new EventContinue();
	}
}