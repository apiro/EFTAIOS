package it.polimi.ingsw.cg_38.notifyAction;

import java.util.Scanner;

import it.polimi.ingsw.cg_38.controller.PlayerClient;
import it.polimi.ingsw.cg_38.controller.PlayerClientState;
import it.polimi.ingsw.cg_38.controller.event.GameEvent;
import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.gameEvent.EventAttack;
import it.polimi.ingsw.cg_38.gameEvent.EventDraw;
import it.polimi.ingsw.cg_38.notifyEvent.EventMoved;

public class RenderMoved extends NotifyAction {
	
	public RenderMoved(NotifyEvent evt){
		
		super(evt.getGenerator() , evt);
	}

	@Override
	public Boolean isPossible(PlayerClient client) {
		if(client.getPlayerClientState().equals(PlayerClientState.isTurn)){
			return true;
		}
		return false;
	}

	@Override
	public GameEvent render(PlayerClient client) {
		
		GameEvent evt1 = null;
		
		Scanner in = new Scanner(System.in);
		client.setPlayer(evt.getGenerator());
		if(((EventMoved)evt).getMoved().equals("Safe")) {
			System.out.println("You are in a SAFE sector ! Type attack or continue: [A] | [C]");
			String com = in.nextLine();
			while(!com.equals("C") && !com.equals("A")) {
				System.out.println("Command not valid retry !");
				System.out.println("You are in a DANGEROUS sector ! Type draw or attack :[A] | [C] ?");
				com = in.nextLine();
			}
			if(com.equals("C")) {
				client.setIsInterfaceBlocked(false);
				return null;
			} else if (com.equals("A")) {
				evt1 = new EventAttack(client.getPlayer(), client.getPlayer().getAvatar().getCurrentSector());
				return evt1;
			}
		} else if(((EventMoved)evt).getMoved().equals("Dangerous")) {
			System.out.println("You are in a DANGEROUS sector ! Type draw or attack :[D] | [A] ?");
			String com = in.nextLine();
			while(!com.equals("D") && !com.equals("A")) {
				System.out.println("Command not valid retry !");
				System.out.println("You are in a DANGEROUS sector ! Type draw or attack :[D] | [A] ?");
				com = in.nextLine();
			}
			if(com.equals("D")) {
				evt1 = new EventDraw(client.getPlayer());
			} else if (com.equals("A")) {
				evt1 = new EventAttack(client.getPlayer(), client.getPlayer().getAvatar().getCurrentSector());
			}
		} else if(((EventMoved)evt).getMoved().equals("Hatch")) {
			System.out.println("You are in a HATCH sector ! Type [D]");
			while(!in.nextLine().equals("D")){}
			evt1 = new EventDraw(client.getPlayer());
		}
		return evt1;
	}
}
