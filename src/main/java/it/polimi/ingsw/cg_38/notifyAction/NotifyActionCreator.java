package it.polimi.ingsw.cg_38.notifyAction;

import it.polimi.ingsw.cg_38.controller.action.Action;
import it.polimi.ingsw.cg_38.controller.event.Event;
import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.controller.event.NotifyEventType;

public class NotifyActionCreator {

	public NotifyActionCreator() {
    }
	
	public static Action createNotifyAction(Event evt) {
		NotifyEventType type = ((NotifyEvent)evt).getType();
    	Action action = null;
    	if(type.equals(NotifyEventType.Added)) {
    		action = new AddedToGame((NotifyEvent) evt);
    	} /*else if (type.equals(NotifyEventType.))*/
		return action;
		
	}
	
}
