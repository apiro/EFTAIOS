package it.polimi.ingsw.cg_38.controller.action;

import it.polimi.ingsw.cg_38.controller.event.GameEvent;
import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.model.GameModel;

import java.util.ArrayList;
import java.util.List;

public class ForceFinishTurn extends FinishTurn {

	private static final long serialVersionUID = 1L;

	public ForceFinishTurn(GameEvent evt) {
		super(evt);
	}
	
	public Boolean isPossible(GameModel model) {
		return true;
	}

	@Override
	public List<NotifyEvent> perform(GameModel model) {
		model.getActualTurn().setHasMoved(true);
		model.getActualTurn().setHasDraw(true);
		model.getActualTurn().setHasAttacked(true);
		model.getActualTurn().setHasUsedObjectCard(true);
		
		List<NotifyEvent> callbackEvent = new ArrayList<NotifyEvent>();
		
		callbackEvent = super.perform(model);
		
		return callbackEvent;
	}

}
