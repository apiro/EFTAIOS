package it.polimi.ingsw.cg_38.client.notifyAction;

import it.polimi.ingsw.cg_38.client.PlayerClient;
import it.polimi.ingsw.cg_38.client.PlayerClientState;
import it.polimi.ingsw.cg_38.controller.event.GameEvent;
import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.controller.gameEvent.EventAttack;
import it.polimi.ingsw.cg_38.controller.gameEvent.EventDraw;
import it.polimi.ingsw.cg_38.controller.notifyEvent.EventMoved;

public class RenderMoved extends NotifyAction {
	
	private static final long serialVersionUID = 1L;

	public RenderMoved(NotifyEvent evt){
		
		super(evt.getGenerator() , evt);
	}

	@Override
	public Boolean isPossible(PlayerClient client) {
		if(client.getPlayerClientState().equals(PlayerClientState.ISTURN) && 
				super.check(client)){
			return true;
		}
		return false;
	}

	@Override
	public GameEvent render(PlayerClient client) {
		
		GameEvent evt1 = null;
		
		client.setPlayer(evt.getGenerator());
		if(("Safe").equals(((EventMoved)evt).getMoved())) {
			String com = client.getLogger().showAndRead("You are in a SAFE sector ! Type attack or continue: [A] | [C]", "Game Message");
			while(!com.equals("C") && !com.equals("A")) {
				client.getLogger().print("Command not valid retry !");
				com = client.getLogger().showAndRead("You are in a SAFE sector ! Type attack or continue: [A] | [C]", "Game Message");
			}
			if(("C").equals(com)) {
				client.setIsInterfaceBlocked(false);
				client.updateMovements();
				return null;
			} else if (("A").equals(com)) {
				evt1 = new EventAttack(client.getPlayer(), client.getPlayer().getAvatar().getCurrentSector());
				client.updateMovements();
				return evt1;
			}
		} else if(("Dangerous").equals(((EventMoved)evt).getMoved())) {
			String com = client.getLogger().showAndRead("You are in a DANGEROUS sector ! Type draw or attack :[D] | [A] ?", "Game Message");
			client.getLogger().print("(If you activated the sedative card Type [D] to continue without drowing !");
			while(!com.equals("D") && !com.equals("A")) {
				client.getLogger().print("Command not valid retry !");
				com = client.getLogger().showAndRead("You are in a DANGEROUS sector ! Type draw or attack :[D] | [A] ?", "Game Message");
			}
			if(("D").equals(com)) {
				evt1 = new EventDraw(client.getPlayer());
			} else if (com.equals("A")) {
				evt1 = new EventAttack(client.getPlayer(), client.getPlayer().getAvatar().getCurrentSector());
			}
		} else if(("Hatch").equals(((EventMoved)evt).getMoved())) {
			String com = client.getLogger().showAndRead("You are in a HATCH sector ! Type [D]", "Game Message");
			while(!com.equals("D")){}
			evt1 = new EventDraw(client.getPlayer());
		}
		client.updateMovements();
		return evt1;
	}
}
