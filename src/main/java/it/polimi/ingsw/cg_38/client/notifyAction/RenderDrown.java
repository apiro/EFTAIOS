package it.polimi.ingsw.cg_38.client.notifyAction;


import it.polimi.ingsw.cg_38.client.PlayerClient;
import it.polimi.ingsw.cg_38.client.PlayerClientState;
import it.polimi.ingsw.cg_38.controller.event.GameEvent;
import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.controller.gameEvent.EventHatchBlocked;
import it.polimi.ingsw.cg_38.controller.gameEvent.EventHumanWin;
import it.polimi.ingsw.cg_38.controller.gameEvent.EventNoiseMySect;
import it.polimi.ingsw.cg_38.controller.gameEvent.EventNoiseRandSect;
import it.polimi.ingsw.cg_38.controller.notifyEvent.EventDrown;
import it.polimi.ingsw.cg_38.model.deck.HatchCard;
import it.polimi.ingsw.cg_38.model.deck.HatchCardType;
import it.polimi.ingsw.cg_38.model.deck.SectorCard;
import it.polimi.ingsw.cg_38.model.deck.SectorCardType;
import it.polimi.ingsw.cg_38.model.map.Hatch;

public class RenderDrown extends NotifyAction {
	
	private static final long serialVersionUID = 1L;

	public RenderDrown(NotifyEvent evt){
		
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
		client.updateCards();
		
		if(((EventDrown)evt).getDrown() instanceof SectorCard) {
			
			SectorCard card = ((SectorCard)((EventDrown)evt).getDrown());
			if(card.getType().equals(SectorCardType.MYSECTORNOISE)) {
				evt1 = new EventNoiseMySect(client.getPlayer());
				
			} else if (card.getType().equals(SectorCardType.RANDOMSECTORNOISE)) {
				evt1 = new EventNoiseRandSect(client.getPlayer(), client.askForMoveCoordinates());
				
			} else if (card.getType().equals(SectorCardType.SILENCE)) {
				client.setIsInterfaceBlocked(false);
				return null;
			}
			
		} else if (((EventDrown)evt).getDrown() instanceof HatchCard) {
			HatchCard card = ((HatchCard)((EventDrown)evt).getDrown());
			if(card.getColor().equals(HatchCardType.GREEN) && 
					((Hatch)client.getPlayer().getAvatar().getCurrentSector()).getIsOpen()) {
				client.paintHatch(true, evt.getGenerator().getAvatar().getCurrentSector());
				this.renderWin(client);
				evt1 = new EventHumanWin(client.getPlayer());
			} else if(card.getColor().equals(HatchCardType.RED)) {
				client.paintHatch(false, evt.getGenerator().getAvatar().getCurrentSector());
				client.setIsInterfaceBlocked(false);
				client.getLogger().print("You can't escape frome the SpaceShip !");
				evt1 = new EventHatchBlocked(client.getPlayer());
			} else if (card.getColor().equals(HatchCardType.GREEN) && 
					!((Hatch)client.getPlayer().getAvatar().getCurrentSector()).getIsOpen()) {
				client.getLogger().print("You can't escape frome the SpaceShip !");
			}
		}
		return evt1;
	}

	private void renderWin(PlayerClient client) {
		client.getLogger().print("YOU WIN !");
		client.setIsInterfaceBlocked(true);
		client.setPlayerClientState(PlayerClientState.WINNER);
	}
}
