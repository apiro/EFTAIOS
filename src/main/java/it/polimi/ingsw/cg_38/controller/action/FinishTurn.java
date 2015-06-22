package it.polimi.ingsw.cg_38.controller.action;

import java.util.ArrayList;

import it.polimi.ingsw.cg_38.controller.event.GameEvent;
import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.controller.notifyEvent.EventNotifyTurn;
import it.polimi.ingsw.cg_38.model.GameModel;
import it.polimi.ingsw.cg_38.model.Human;
import it.polimi.ingsw.cg_38.model.Turn;

public class FinishTurn extends GameAction {

	private static final long serialVersionUID = 1L;

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
	public ArrayList<NotifyEvent> perform(GameModel model) {
		ArrayList<NotifyEvent> callbackEvent = new ArrayList<NotifyEvent>();
		if(model.getActualTurn().getHasMoved()) {
			if(model.getActualTurn().getCurrentPlayer().getAvatar() instanceof Human 
					&& model.getActualTurn().getCurrentPlayer().getAvatar().getIsPowered()) {
				model.getActualTurn().getCurrentPlayer().getAvatar().setIsPowered(false);
			}
			model.getActualTurn().getCurrentPlayer().finishTurn();
			Turn newTurn = new Turn(model.getNextPlayer());
	    	model.setActualTurn(newTurn);
	    	callbackEvent.add(new EventNotifyTurn(newTurn.getCurrentPlayer()));
		} else {
			callbackEvent.add(new EventNotifyTurn(model.getActualTurn().getCurrentPlayer()));
		}
		return callbackEvent;
	}
}
