package it.polimi.ingsw.cg_38.controller.action;

import java.util.ArrayList;

import it.polimi.ingsw.cg_38.controller.event.GameEvent;
import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.model.EndState;
import it.polimi.ingsw.cg_38.model.GameModel;
import it.polimi.ingsw.cg_38.model.LifeState;
import it.polimi.ingsw.cg_38.model.Player;
import it.polimi.ingsw.cg_38.notifyEvent.EventNotifyWin;

public class Winner extends GameAction {

	private static final long serialVersionUID = 1L;

	public Winner(GameEvent evt) {
		super(evt.getGenerator());
	}

	@Override
	public ArrayList<NotifyEvent> perform(GameModel model) {
		ArrayList<NotifyEvent> callbackEvent = new ArrayList<NotifyEvent>();
		for(Player pl:model.getGamePlayers()) {
			if(pl.getName().equals(super.getPlayer().getName())) {
				pl.getAvatar().setIsAlive(LifeState.ALIVE);
				pl.getAvatar().setIsWinner(EndState.WINNER);
			}
		}
		callbackEvent.add(new EventNotifyWin(model.getActualTurn().getCurrentPlayer()));
		return callbackEvent;
	}

}
