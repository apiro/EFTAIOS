package it.polimi.ingsw.cg_38.controller.action;

import it.polimi.ingsw.cg_38.controller.event.Event;
import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.controller.notifyEvent.EventNotifyClosingTopic;
import it.polimi.ingsw.cg_38.controller.notifyEvent.EventNotifyHumanWin;
import it.polimi.ingsw.cg_38.model.EndState;
import it.polimi.ingsw.cg_38.model.GameModel;
import it.polimi.ingsw.cg_38.model.Human;
import it.polimi.ingsw.cg_38.model.Player;

import java.util.ArrayList;

public class HumanWin extends GameAction {

	private static final long serialVersionUID = 1L;

	public HumanWin(Event evt) {
		super(evt.getGenerator());
	}

	@Override
	public ArrayList<NotifyEvent> perform(GameModel model) {
		ArrayList<NotifyEvent> callbackEvent = new ArrayList<NotifyEvent>();
		model.getActualTurn().getCurrentPlayer().getAvatar().setIsWinner(EndState.WINNER);
		
		Boolean areThereOtherHumans = false;
		for(Player pl:model.getGamePlayers()) {
			if((pl.getAvatar() instanceof Human) && pl.getAvatar().getIsAlive().equals(EndState.PLAYING)) {
				areThereOtherHumans = true;
			}
		}
		callbackEvent.add(new EventNotifyHumanWin(model.getActualTurn().getCurrentPlayer(), areThereOtherHumans));
		if(!areThereOtherHumans) callbackEvent.add(new EventNotifyClosingTopic(model.getActualTurn().getCurrentPlayer()));
		return callbackEvent;
	}
}