package it.polimi.ingsw.cg_38.controller.action;

import java.util.ArrayList;

import it.polimi.ingsw.cg_38.controller.event.Event;
import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.model.Alien;
import it.polimi.ingsw.cg_38.model.EndState;
import it.polimi.ingsw.cg_38.model.GameModel;
import it.polimi.ingsw.cg_38.model.Player;
import it.polimi.ingsw.cg_38.notifyEvent.EventNotifyAliensWin;

public class AliensWin extends GameAction {

	public AliensWin(Event evt) {
		super(evt.getGenerator());
	}

	@Override
	public NotifyEvent perform(GameModel model) {
		ArrayList<Player> winners = new ArrayList<Player>();
		for(Player pl:model.getGamePlayers()) {
			if(pl.getAvatar() instanceof Alien) {
				pl.getAvatar().setIsWinner(EndState.WINNER);
				winners.add(pl);
			}
		}
		return new EventNotifyAliensWin(model.getActualTurn().getCurrentPlayer(), true, winners);
	}

}
