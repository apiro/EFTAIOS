package it.polimi.ingsw.cg_38.notifyAction;

import java.util.Scanner;

import it.polimi.ingsw.cg_38.controller.PlayerClient;
import it.polimi.ingsw.cg_38.controller.PlayerClientCLI;
import it.polimi.ingsw.cg_38.controller.PlayerClientState;
import it.polimi.ingsw.cg_38.controller.event.GameEvent;
import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.gameEvent.EventAttack;
import it.polimi.ingsw.cg_38.gameEvent.EventDraw;
import it.polimi.ingsw.cg_38.notifyEvent.EventMoved;

public class RenderMoved extends NotifyAction {
	
	private static final long serialVersionUID = 1L;

	public RenderMoved(NotifyEvent evt){
		
		super(evt.getGenerator() , evt);
	}

	@Override
	public Boolean isPossible(PlayerClient client) {
		if(client.getPlayerClientState().equals(PlayerClientState.isTurn) && 
				super.check(client)){
			return true;
		}
		return false;
	}

	@Override
	public GameEvent render(PlayerClient client) {
		
		GameEvent evt1 = null;
		
		client.setPlayer(evt.getGenerator());
		if(((EventMoved)evt).getMoved().equals("Safe")) {
			String com = client.getLogger().showAndRead("You are in a SAFE sector ! Type attack or continue: [A] | [C]");
			while(!com.equals("C") && !com.equals("A")) {
				client.getLogger().print("Command not valid retry !");
				com = client.getLogger().showAndRead("You are in a SAFE sector ! Type attack or continue: [A] | [C]");
			}
			if(com.equals("C")) {
				client.setIsInterfaceBlocked(false);
				return null;
			} else if (com.equals("A")) {
				evt1 = new EventAttack(client.getPlayer(), client.getPlayer().getAvatar().getCurrentSector());
				return evt1;
			}
		} else if(((EventMoved)evt).getMoved().equals("Dangerous")) {
			
			String com = client.getLogger().showAndRead("You are in a DANGEROUS sector ! Type draw or attack :[D] | [A] ?");
			client.getLogger().print("(If you activated the sedative card Type [D] to continue without drowing !");
			while(!com.equals("D") && !com.equals("A")) {
				client.getLogger().print("Command not valid retry !");
				com = client.getLogger().showAndRead("You are in a DANGEROUS sector ! Type draw or attack :[D] | [A] ?");
			}
			if(com.equals("D")) {
				evt1 = new EventDraw(client.getPlayer());
			} else if (com.equals("A")) {
				evt1 = new EventAttack(client.getPlayer(), client.getPlayer().getAvatar().getCurrentSector());
			}
		} else if(((EventMoved)evt).getMoved().equals("Hatch")) {
			String com = client.getLogger().showAndRead("You are in a HATCH sector ! Type [D]");
			while(!com.equals("D")){}
			evt1 = new EventDraw(client.getPlayer());
		}
		client.updateMovements();
		return evt1;
	}
}
