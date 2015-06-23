package it.polimi.ingsw.cg_38.controller.action;

import java.util.ArrayList;

import it.polimi.ingsw.cg_38.controller.GameState;
import it.polimi.ingsw.cg_38.controller.event.Event;
import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.controller.gameEvent.EventAliensWinner;
import it.polimi.ingsw.cg_38.controller.notifyEvent.EventClosingGame;
import it.polimi.ingsw.cg_38.controller.notifyEvent.EventNotifyAliensWin;
import it.polimi.ingsw.cg_38.controller.notifyEvent.EventNotifyClosingTopic;
import it.polimi.ingsw.cg_38.model.Alien;
import it.polimi.ingsw.cg_38.model.EndState;
import it.polimi.ingsw.cg_38.model.GameModel;
import it.polimi.ingsw.cg_38.model.Player;

public class AliensWin extends GameAction {

	private static final long serialVersionUID = 1L;
	private Boolean areWinner;

	public AliensWin(Event evt) {
		super(evt.getGenerator());
		areWinner = ((EventAliensWinner)evt).getAreWinner();
	}

	@Override
	public ArrayList<NotifyEvent> perform(GameModel model) {
		ArrayList<Player> winners = new ArrayList<Player>();
		ArrayList<NotifyEvent> callbackEvent = new ArrayList<NotifyEvent>();
		
		for(Player pl:model.getGamePlayers()) {
			if(pl.getAvatar() instanceof Alien) {
				pl.getAvatar().setIsWinner(EndState.WINNER);
				winners.add(pl);
			}
		}
		
		model.setGameState(GameState.CLOSING);
		callbackEvent.add(new EventNotifyAliensWin(model.getActualTurn().getCurrentPlayer(), winners, areWinner));
		callbackEvent.add(new EventNotifyClosingTopic(model.getActualTurn().getCurrentPlayer()));
		callbackEvent.add(new EventClosingGame(model.getActualTurn().getCurrentPlayer(), model.areThereOtherHumans()));
		return callbackEvent;
	}
}
