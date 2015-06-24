package it.polimi.ingsw.cg_38.controller.action;

import it.polimi.ingsw.cg_38.controller.event.GameEvent;
import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.controller.gameEvent.EventChat;
import it.polimi.ingsw.cg_38.controller.notifyEvent.EventNotifyChatMessage;
import it.polimi.ingsw.cg_38.model.GameModel;

import java.util.ArrayList;
import java.util.List;

public class Chat extends GameAction {

	private static final long serialVersionUID = 1L;
	private String message;

	public Chat(GameEvent evt) {
		super(evt.getGenerator());
		this.message = ((EventChat)evt).getMessage();
	}

	@Override
	public ArrayList<NotifyEvent> perform(GameModel model) {
		ArrayList<NotifyEvent> callbackEvent = new ArrayList<NotifyEvent>();
		callbackEvent.add(new EventNotifyChatMessage(model.getActualTurn().getCurrentPlayer(), message));
		return callbackEvent;
	}
}
