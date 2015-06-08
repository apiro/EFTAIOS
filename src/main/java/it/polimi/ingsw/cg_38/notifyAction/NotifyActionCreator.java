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
    	if(type.equals(NotifyEventType.environment))
    		action = new RenderEnvironment((NotifyEvent) evt);
    	if(type.equals(NotifyEventType.Attacked))
    		action = new RenderAttacked((NotifyEvent) evt);
    	if(type.equals(NotifyEventType.notifyTurn))
    		action = new RenderNotifyTurn((NotifyEvent) evt);
    	if(type.equals(NotifyEventType.Error))
    		action = new RenderError((NotifyEvent) evt);
    	if(type.equals(NotifyEventType.Moved))
    		action = new RenderMoved((NotifyEvent) evt);
    	if(type.equals(NotifyEventType.Drown))
    			action = new RenderDrown((NotifyEvent) evt);
		return action;
		
	}
	
}
