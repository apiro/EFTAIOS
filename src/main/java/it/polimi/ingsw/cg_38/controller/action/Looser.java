package it.polimi.ingsw.cg_38.controller.action;

import it.polimi.ingsw.cg_38.controller.event.GameEvent;
import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.model.EndState;
import it.polimi.ingsw.cg_38.model.GameModel;
import it.polimi.ingsw.cg_38.model.LifeState;
import it.polimi.ingsw.cg_38.model.Player;
import it.polimi.ingsw.cg_38.notifyEvent.EventNotifyLoose;

public class Looser extends GameAction {

	public Looser(GameEvent evt) {
		super(evt.getGenerator());
	}

	@Override
	public NotifyEvent perform(GameModel model) {
		
		for(Player pl:model.getGamePlayers()) {
			if(pl.getName().equals(super.getPlayer().getName())) {
				pl.getAvatar().setIsAlive(LifeState.DEAD);
				pl.getAvatar().setIsWinner(EndState.LOOSER);
			}
		}
		//DOVREI  togliere riferimenti al giocatore nel server e nel topic
		return new EventNotifyLoose(model.getActualTurn().getCurrentPlayer());
	}

}
