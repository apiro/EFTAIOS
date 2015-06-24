package it.polimi.ingsw.cg_38.controller.action;

import it.polimi.ingsw.cg_38.controller.event.GameEvent;
import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.controller.notifyEvent.EventNotifyRetired;
import it.polimi.ingsw.cg_38.model.GameModel;

import java.util.ArrayList;

public class Retire extends GameAction {

	private static final long serialVersionUID = 1L;

	public Retire(GameEvent evt) {
		super(evt.getGenerator());
	}

	@Override
	public ArrayList<NotifyEvent> perform(GameModel model) {
		ArrayList<NotifyEvent> callbackEvent = new ArrayList<NotifyEvent>();
		if(model.getActualTurn().getHasMoved()) {
			player.getAvatar().attacked();
		}
		callbackEvent.add(new EventNotifyRetired(model.getActualTurn().getCurrentPlayer()));
		return callbackEvent;
	}
}
