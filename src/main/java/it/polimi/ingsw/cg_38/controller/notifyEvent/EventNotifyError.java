package it.polimi.ingsw.cg_38.controller.notifyEvent;

import it.polimi.ingsw.cg_38.controller.action.Action;
import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.controller.event.NotifyEventType;
import it.polimi.ingsw.cg_38.model.Player;

public class EventNotifyError extends NotifyEvent {

	public EventNotifyError(Player generator, Action action) {
		super(generator, false);
		this.relatedAction = action;
		this.setType(NotifyEventType.ERROR);
	}

	public Action getRelatedAction() {
		return relatedAction;
	}

	/** contiene l'azione che ha generato l'errore */
	private Action relatedAction;
	
	private static final long serialVersionUID = 1L;

}
