package it.polimi.ingsw.cg_38.client.notifyAction;

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
    	if(type.equals(null))
    		action = new RenderError((NotifyEvent) evt);
    	if(type.equals(NotifyEventType.ADDED)) 
    		action = new AddedToGame((NotifyEvent) evt);
    	if(type.equals(NotifyEventType.ENVIRONMENT))
    		action = new RenderEnvironment((NotifyEvent) evt);
    	if(type.equals(NotifyEventType.ATTACKED))
    		action = new RenderAttacked((NotifyEvent) evt);
    	if(type.equals(NotifyEventType.NOTIFYTURN))
    		action = new RenderNotifyTurn((NotifyEvent) evt);
    	if(type.equals(NotifyEventType.ERROR))
    		action = new RenderError((NotifyEvent) evt);
    	if(type.equals(NotifyEventType.MOVED))
    		action = new RenderMoved((NotifyEvent) evt);
    	if(type.equals(NotifyEventType.DROWN))
    		action = new RenderDrown((NotifyEvent) evt);
    	if(type.equals(NotifyEventType.NOISE)) 
    		action = new RenderNoise((NotifyEvent) evt);
    	if(type.equals(NotifyEventType.ALIENSWIN))
    		action = new RenderAliensWin((NotifyEvent)evt);
    	if(type.equals(NotifyEventType.CARDUSED))
    		action = new RenderNoSideEffectCard((NotifyEvent)evt);
    	if(type.equals(NotifyEventType.DECLAREPOSITION))
    		action = new RenderSpotlight((NotifyEvent)evt);
    	if(type.equals(NotifyEventType.SUFFERATTACK)) 
    		action = new RenderAttackDamage((NotifyEvent)evt);
		if(type.equals(NotifyEventType.USEDEFENSECARD))
			action = new RenderUseDefenseCard((NotifyEvent)evt);
		if(type.equals(NotifyEventType.NOTTELEPORT)) 
			action = new RenderTeleport((NotifyEvent)evt);
		if(type.equals(NotifyEventType.HUMANWIN)) 
			action = new RenderHumanWin((NotifyEvent)evt);
		if(type.equals(NotifyEventType.HATCHBLOCKED)) 
			action = new RenderHatchBlocked((NotifyEvent)evt);
		if(type.equals(NotifyEventType.REJECTCARD)) 
			action = new RenderRejectCard((NotifyEvent)evt);
		if(type.equals(NotifyEventType.CARDPERFORMED)) 
			action = new RenderCardPerformed((NotifyEvent)evt);
		if(type.equals(NotifyEventType.REJECTCARDHUMAN))
			action = new RenderRejectHumanCard((NotifyEvent)evt);
		if(type.equals(NotifyEventType.CLOSINGGAME))
			action = new RenderClosingGame((NotifyEvent)evt);
		if(type.equals(NotifyEventType.CHAT))
			action = new RenderChatMessage((NotifyEvent)evt);
		if(type.equals(NotifyEventType.RETIRED))
			action = new RenderRetired((NotifyEvent)evt);
    	return action;
	}
	
}
