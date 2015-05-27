package it.polimi.ingsw.cg_38.controller.action;
import it.polimi.ingsw.cg_38.controller.event.Event;
import it.polimi.ingsw.cg_38.controller.event.GameEvent;
import it.polimi.ingsw.cg_38.controller.event.GameEventType;

/**
 * 
 */
public class ActionCreator {

    /**
     * 
     */
    public ActionCreator() {
    }

    /**
     * @param String type
     */
    public static Action createAction(Event evt) {
    	GameEventType type = ((GameEvent)evt).getType();
    	Action action = null;
    	InitGameAction action2 = null;
    	if(type.equals(GameEventType.Attack)) {
    		action = new Attack(((GameEvent)evt));
    	} else if (type.equals(GameEventType.Draw)) {
    		action = new Draw(((GameEvent)evt));
    	} else if (type.equals(GameEventType.Move)) {
    		action = new Move(((GameEvent)evt));
    	}  else if (type.equals(GameEventType.Adrenaline)) {
    		action = new UseAdrenalineCard(((GameEvent)evt));
    	} else if (type.equals(GameEventType.AttackCard)) {
    		action = new UseAttackCard(((GameEvent)evt));
    	} else if (type.equals(GameEventType.Lights)) {
    		action = new UseLightsCard(((GameEvent)evt));
    	} else if (type.equals(GameEventType.Sedatives)) {
    		action = new UseSedativesCard(((GameEvent)evt));
    	} else if (type.equals(GameEventType.NoiseRandSect)) {
    		action = new UseRandomSectorNoise(((GameEvent)evt));
    	} else if (type.equals(GameEventType.NoiseMySect)) {
    		action = new UseMySectorNoise(((GameEvent)evt));
    	} else if (type.equals(GameEventType.Teleport)) {
    		action = new UseTeleportCard(((GameEvent)evt));
    	} else if (type.equals(GameEventType.subscribeRMI)) {
    		action2 = new SubscribeRMI(((GameEvent)evt));
    		return action2;
    	} else if (type.equals(GameEventType.subscribeSocket)) {
    		action2 = new SubscribeSocket(((GameEvent)evt));
    		return action2;
    	}
		return action;
    }
}