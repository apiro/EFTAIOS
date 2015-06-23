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
    	if(type.equals(NotifyEventType.Added)) 
    		action = new AddedToGame((NotifyEvent) evt);
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
    	if(type.equals(NotifyEventType.Noise)) 
    		action = new RenderNoise((NotifyEvent) evt);
    	if(type.equals(NotifyEventType.aliensWin))
    		action = new RenderAliensWin((NotifyEvent)evt);
    	if(type.equals(NotifyEventType.CardUsed))
    		action = new RenderNoSideEffectCard((NotifyEvent)evt);
    	if(type.equals(NotifyEventType.DeclarePosition))
    		action = new RenderSpotlight((NotifyEvent)evt);
    	if(type.equals(NotifyEventType.sufferAttack)) 
    		action = new RenderAttackDamage((NotifyEvent)evt);
		if(type.equals(NotifyEventType.useDefenseCard))
			action = new RenderUseDefenseCard((NotifyEvent)evt);
		if(type.equals(NotifyEventType.notTeleport)) 
			action = new RenderTeleport((NotifyEvent)evt);
		if(type.equals(NotifyEventType.humanWin)) 
			action = new RenderHumanWin((NotifyEvent)evt);
		if(type.equals(NotifyEventType.hatchBlocked)) 
			action = new RenderHatchBlocked((NotifyEvent)evt);
		if(type.equals(NotifyEventType.rejectCard)) 
			action = new RenderRejectCard((NotifyEvent)evt);
		if(type.equals(NotifyEventType.cardPerformed)) 
			action = new RenderCardPerformed((NotifyEvent)evt);
		if(type.equals(NotifyEventType.rejectCardHuman))
			action = new RenderRejectHumanCard((NotifyEvent)evt);
		if(type.equals(NotifyEventType.closingGame))
			action = new RenderClosingGame((NotifyEvent)evt);
    	return action;
	}
	
}
