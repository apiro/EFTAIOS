package it.polimi.ingsw.cg_38.controller.action;
import it.polimi.ingsw.cg_38.controller.event.Event;
import it.polimi.ingsw.cg_38.controller.event.GameEvent;
import it.polimi.ingsw.cg_38.controller.event.GameEventType;

/**
 * 
 */
public class GameActionCreator {

    /**
     * 
     */
    public GameActionCreator() {
    }

    /**
     * @param String type
     */
    public static Action createGameAction(Event evt) {
    	GameEventType type = ((GameEvent)evt).getType();
    	Action action = null;
    	/*InitGameAction action2 = null;*/
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
    	} else if (type.equals(GameEventType.finishTurn)) {
    		action = new FinishTurn((GameEvent)evt);
    		return action;
    	} else if (type.equals(GameEventType.subscribe)) {
    		action = new Subscribe((GameEvent)evt);
    	} else if (type.equals(GameEventType.winner)) {
    		action = new Winner((GameEvent)evt);
    	} else if (type.equals(GameEventType.looser)) {
    		action = new Looser((GameEvent)evt);
    	} else if (type.equals(GameEventType.aliensWin)) {
    		action = new AliensWin((GameEvent)evt);
    	} else if (type.equals(GameEventType.humanWin)) {
    		action = new HumanWin((GameEvent)evt);
    	} else if (type.equals(GameEventType.blockHatch)) {
    		action = new HatchBlocked((GameEvent)evt);
    	}
		return action;
    }
}
