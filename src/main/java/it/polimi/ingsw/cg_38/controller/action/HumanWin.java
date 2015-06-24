package it.polimi.ingsw.cg_38.controller.action;

import it.polimi.ingsw.cg_38.controller.GameState;
import it.polimi.ingsw.cg_38.controller.event.Event;
import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.controller.notifyEvent.EventClosingGame;
import it.polimi.ingsw.cg_38.controller.notifyEvent.EventNotifyClosingTopic;
import it.polimi.ingsw.cg_38.controller.notifyEvent.EventNotifyHumanWin;
import it.polimi.ingsw.cg_38.model.EndState;
import it.polimi.ingsw.cg_38.model.GameModel;
import it.polimi.ingsw.cg_38.model.map.Hatch;

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
		((Hatch)model.getGameMap().searchSectorByCoordinates(model.getActualTurn().getCurrentPlayer().getAvatar().getCurrentSector().getRow(), model.getActualTurn().getCurrentPlayer().getAvatar().getCurrentSector().getCol())).setIsOpen(false);
		
		callbackEvent.add(new EventNotifyHumanWin(model.getActualTurn().getCurrentPlayer()));
		if(!model.areThereOtherHumans()) {
			callbackEvent.add(new EventNotifyClosingTopic(model.getActualTurn().getCurrentPlayer()));
			callbackEvent.add(new EventClosingGame(model.getActualTurn().getCurrentPlayer(), model.areThereOtherHumans()));
			model.setGameState(GameState.CLOSING);
		}
		
		return callbackEvent;
	}
}
