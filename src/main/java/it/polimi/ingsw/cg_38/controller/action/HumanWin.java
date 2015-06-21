package it.polimi.ingsw.cg_38.controller.action;

import it.polimi.ingsw.cg_38.controller.event.Event;
import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.model.GameModel;
import it.polimi.ingsw.cg_38.notifyEvent.EventNotifyHumanWin;

import java.util.ArrayList;

public class HumanWin extends GameAction {

	private static final long serialVersionUID = 1L;

	public HumanWin(Event evt) {
		super(evt.getGenerator());
	}

	@Override
	public ArrayList<NotifyEvent> perform(GameModel model) {
		ArrayList<NotifyEvent> callbackEvent = new ArrayList<NotifyEvent>();
		callbackEvent.add(new EventNotifyHumanWin(model.getActualTurn().getCurrentPlayer()));
		return callbackEvent;
	}
}
