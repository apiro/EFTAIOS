package it.polimi.ingsw.cg_38.controller.action;

import it.polimi.ingsw.cg_38.controller.event.GameEvent;
import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.controller.notifyEvent.EventNotifyRetired;
import it.polimi.ingsw.cg_38.model.GameModel;

import java.util.ArrayList;
import java.util.List;

public class Retire extends GameAction {

	private static final long serialVersionUID = 1L;

	public Retire(GameEvent evt) {
		super(evt.getGenerator());
	}

	@Override
	public Boolean isPossible(GameModel model) {
		return model.getActualTurn().getHasMoved();
	}
	
	@Override
	public List<NotifyEvent> perform(GameModel model) {
		List<NotifyEvent> callbackEvent = new ArrayList<NotifyEvent>();
		model.getActualTurn().getCurrentPlayer().getAvatar().attacked();
		callbackEvent.add(new EventNotifyRetired(model.getActualTurn().getCurrentPlayer()));
		return callbackEvent;
	}
}
