package it.polimi.ingsw.cg_38.controller.action;

import it.polimi.ingsw.cg_38.controller.event.Event;
import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.controller.notifyEvent.EventHatchBlocked;
import it.polimi.ingsw.cg_38.model.GameModel;
import it.polimi.ingsw.cg_38.model.map.Hatch;

import java.util.ArrayList;

public class HatchBlocked extends GameAction {

	private static final long serialVersionUID = 1L;

	public HatchBlocked(Event evt) {
		super(evt.getGenerator());
	}

	@Override
	public ArrayList<NotifyEvent> perform(GameModel model) {
		ArrayList<NotifyEvent> callbackEvent = new ArrayList<NotifyEvent>();
		((Hatch)model.getActualTurn().getCurrentPlayer().getAvatar().getCurrentSector()).setIsOpen(false);
		callbackEvent.add(new EventHatchBlocked(model.getActualTurn().getCurrentPlayer(), 
   						(Hatch)model.getActualTurn().getCurrentPlayer().getAvatar().getCurrentSector()));
		return callbackEvent;
	}

}
