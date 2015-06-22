package it.polimi.ingsw.cg_38.controller.action;

import it.polimi.ingsw.cg_38.controller.event.GameEvent;
import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.controller.notifyEvent.EventCardUsed;
import it.polimi.ingsw.cg_38.controller.notifyEvent.EventRejectCard;
import it.polimi.ingsw.cg_38.controller.notifyEvent.EventUseDefense;
import it.polimi.ingsw.cg_38.model.GameModel;
import it.polimi.ingsw.cg_38.model.Player;
import it.polimi.ingsw.cg_38.model.deck.ObjectCardType;

import java.util.ArrayList;

public class Defense extends GameAction {

	private static final long serialVersionUID = 1L;

	public Defense(GameEvent evt) {
		super(evt.getGenerator());
	}

	@Override
	public ArrayList<NotifyEvent> perform(GameModel model) {
		ArrayList<NotifyEvent> callbackEvent = new ArrayList<NotifyEvent>();
		callbackEvent.add(new EventRejectCard(model.getActualTurn().getCurrentPlayer()));
		callbackEvent.add(new EventCardUsed(model.getActualTurn().getCurrentPlayer(), false, ObjectCardType.Defense));
		return null;
	}

}
