package it.polimi.ingsw.cg_38.controller.action;
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
    public static Action createAction(GameEvent evt) {
    	GameEventType type = evt.getType();
    	GameAction action = null;
    	InitGameAction action2 = null;
    	if(type.equals(GameEventType.Attack)) {
    		action = new Attack(evt);
    	} else if (type.equals(GameEventType.Draw)) {
    		action = new Draw(evt);
    	} else if (type.equals(GameEventType.Move)) {
    		action = new Move(evt);
    	}  else if (type.equals(GameEventType.Adrenaline)) {
    		action = new UseAdrenalineCard(evt);
    	} else if (type.equals(GameEventType.AttackCard)) {
    		action = new UseAttackCard(evt);
    	} else if (type.equals(GameEventType.Lights)) {
    		action = new UseLightsCard(evt);
    	} else if (type.equals(GameEventType.Sedatives)) {
    		action = new UseSedativesCard(evt);
    	} else if (type.equals(GameEventType.NoiseRandSect)) {
    		action = new UseRandomSectorNoise(evt);
    	} else if (type.equals(GameEventType.NoiseMySect)) {
    		action = new UseMySectorNoise(evt);
    	} else if (type.equals(GameEventType.Teleport)) {
    		action = new UseTeleportCard(evt);
    	} else if (type.equals(GameEventType.subscribeRMI)) {
    		action2 = new SubscribeRMI(evt);
    		return action2;
    	} else if (type.equals(GameEventType.subscribeSocket)) {
    		action2 = new SubscribeSocket(evt);
    		return action2;
    	}
		return action;
    }
}