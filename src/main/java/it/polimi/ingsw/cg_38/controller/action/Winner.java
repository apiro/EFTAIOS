package it.polimi.ingsw.cg_38.controller.action;

import it.polimi.ingsw.cg_38.controller.event.GameEvent;
import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.model.GameModel;
import it.polimi.ingsw.cg_38.model.Player;
import it.polimi.ingsw.cg_38.notifyEvent.EventNotifyWin;

public class Winner extends GameAction {

	public Winner(GameEvent evt) {
		super(evt.getGenerator());
	}

	@Override
	public NotifyEvent perform(GameModel model) {
		model.getGamePlayers().remove(super.getPlayer());
		//DOVREI  togliere riferimenti al giocatore nel server e nel topic
		return new EventNotifyWin(model.getActualTurn().getCurrentPlayer());
	}

}
