package it.polimi.ingsw.cg_38.notifyAction;

import java.util.Scanner;

import it.polimi.ingsw.cg_38.controller.Client;
import it.polimi.ingsw.cg_38.controller.event.GameEvent;
import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.gameEvent.EventNoiseMySect;
import it.polimi.ingsw.cg_38.gameEvent.EventNoiseRandSect;
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
	public Boolean isPossible(Client client) {
		if(client.getIsMyTurn())
			return true;
		return false;
	}

	@Override
	public GameEvent render(Client client) {
		
		GameEvent evt1 = null;
		Scanner in = new Scanner(System.in);
		
		client.setPlayer(evt.getGenerator());
		if(((EventDrown)evt).getDrown() instanceof SectorCard) {
			SectorCard card = ((SectorCard)((EventDrown)evt).getDrown());
			if(card.getType().equals(SectorCardType.MySectorNoise)) {
				evt1 = new EventNoiseMySect(client.getPlayer());
				
			} else if (card.getType().equals(SectorCardType.RandomSectorNoise)) {
				evt1 = new EventNoiseRandSect(client.getPlayer(), client.askForMoveCoordinates(in));
				
			} else if (card.getType().equals(SectorCardType.Silence)) 
				return null;
			
		} else if (((EventDrown)evt).getDrown() instanceof HatchCard) {
			HatchCard card = ((HatchCard)((EventDrown)evt).getDrown());
			if(card.getColor().equals(HatchCardType.Green)) {
				//se sono umano
				System.out.println("WINNER");
				//send event of "player generator wins"
				//se sono alieno niente
			} else if(card.getColor().equals(HatchCardType.Red)) {
				//niente
				return null;
			}
		}
		
		return evt1;
	}

}
