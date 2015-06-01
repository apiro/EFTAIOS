package it.polimi.ingsw.cg_38.controller.action;

import it.polimi.ingsw.cg_38.controller.event.GameEvent;
import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.model.GameModel;
import it.polimi.ingsw.cg_38.model.Player;
import it.polimi.ingsw.cg_38.model.Turn;
import it.polimi.ingsw.cg_38.notifyEvent.EventNotifyTurn;

public class FinishTurn extends GameAction {

	public FinishTurn(GameEvent evt) {
		super(evt.getGenerator());
		
	}

	public Boolean isPossible(GameModel model){
		super.isPossible(model);
		if(super.isPossible(model) && model.getActualTurn().getHasMoved()) {
			return true;
		} return false;
	}
	
	@Override
	public NotifyEvent perform(GameModel model) {
		Player previousTurnPlayer = model.getActualTurn().getCurrentPlayer();
		model.getActualTurn().getCurrentPlayer().finishTurn();
		Turn newTurn = new Turn(model.getNextPlayer());
    	model.setActualTurn(newTurn);
		return new EventNotifyTurn(super.getPlayer());
	}

}