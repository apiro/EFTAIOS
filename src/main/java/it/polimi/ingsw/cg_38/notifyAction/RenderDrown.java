package it.polimi.ingsw.cg_38.notifyAction;

import java.util.Scanner;

import it.polimi.ingsw.cg_38.controller.PlayerClient;
import it.polimi.ingsw.cg_38.controller.PlayerClientState;
import it.polimi.ingsw.cg_38.controller.event.GameEvent;
import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.gameEvent.EventNoiseMySect;
import it.polimi.ingsw.cg_38.gameEvent.EventNoiseRandSect;
import it.polimi.ingsw.cg_38.gameEvent.EventPlayerWinner;
import it.polimi.ingsw.cg_38.model.HatchCard;
import it.polimi.ingsw.cg_38.model.HatchCardType;
import it.polimi.ingsw.cg_38.model.SectorCard;
import it.polimi.ingsw.cg_38.model.SectorCardType;
import it.polimi.ingsw.cg_38.notifyEvent.EventDrown;

public class RenderDrown extends NotifyAction {
	
	public RenderDrown(NotifyEvent evt){
		
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
		if(((EventDrown)evt).getDrown() instanceof SectorCard) {
			SectorCard card = ((SectorCard)((EventDrown)evt).getDrown());
			if(card.getType().equals(SectorCardType.MySectorNoise)) {
				evt1 = new EventNoiseMySect(client.getPlayer());
				
			} else if (card.getType().equals(SectorCardType.RandomSectorNoise)) {
				evt1 = new EventNoiseRandSect(client.getPlayer(), client.askForMoveCoordinates(in));
				
			} else if (card.getType().equals(SectorCardType.Silence)) {
				client.setIsInterfaceBlocked(false);
				in.close();
				return null;
			}
			
		} else if (((EventDrown)evt).getDrown() instanceof HatchCard) {
			HatchCard card = ((HatchCard)((EventDrown)evt).getDrown());
			if(card.getColor().equals(HatchCardType.Green)) {
				evt1 = new EventPlayerWinner(client.getPlayer());
			} else if(card.getColor().equals(HatchCardType.Red)) {
				client.setIsInterfaceBlocked(false);
				System.out.println("You can't escape frome the SpaceShip !");
				in.close();
				return null;
			}
		}
		return evt1;
	}
}
