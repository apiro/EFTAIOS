package it.polimi.ingsw.cg_38.controller.action;
import it.polimi.ingsw.cg_38.controller.event.Event;
import it.polimi.ingsw.cg_38.controller.event.GameEvent;
import it.polimi.ingsw.cg_38.controller.event.GameEventType;

/** classe utilizzata per creare azioni in base agli eventi ricevuti dal server */
public class GameActionCreator {

    public GameActionCreator() {
    }

    /** crea un azione in base al evento passato come parametro
     * @param evt evento di gioco che genera l'azione
     * @return ritorna l'azione generata
     */
    public static Action createGameAction(Event evt) {
    	GameEventType type = ((GameEvent)evt).getType();
    	Action action = null;
    	/*InitGameAction action2 = null;*/
    	if(type.equals(GameEventType.ATTACK)) {
    		action = new Attack(((GameEvent)evt));
    	} else if (type.equals(GameEventType.DRAW)) {
    		action = new Draw(((GameEvent)evt));
    	} else if (type.equals(GameEventType.MOVE)) {
    		action = new Move(((GameEvent)evt));
    	}  else if (type.equals(GameEventType.ADRENALINE)) {
    		action = new UseAdrenalineCard(((GameEvent)evt));
    	} else if (type.equals(GameEventType.ATTACKCARD)) {
    		action = new UseAttackCard(((GameEvent)evt));
    	} else if (type.equals(GameEventType.LIGHTS)) {
    		action = new UseLightsCard(((GameEvent)evt));
    	} else if (type.equals(GameEventType.SEDATIVES)) {
    		action = new UseSedativesCard(((GameEvent)evt));
    	} else if (type.equals(GameEventType.NOISERANDSECT)) {
    		action = new UseRandomSectorNoise(((GameEvent)evt));
    	} else if (type.equals(GameEventType.NOISEMYSECT)) {
    		action = new UseMySectorNoise(((GameEvent)evt));
    	} else if (type.equals(GameEventType.TELEPORT)) {
    		action = new UseTeleportCard(((GameEvent)evt));
    	} else if (type.equals(GameEventType.FINISHTURN)) {
    		action = new FinishTurn((GameEvent)evt);
    		return action;
    	} else if (type.equals(GameEventType.SUBSCRIBE)) {
    		action = new Subscribe((GameEvent)evt);
    	} else if (type.equals(GameEventType.WINNER)) {
    		action = new Winner((GameEvent)evt);
    	} else if (type.equals(GameEventType.LOOSER)) {
    		action = new Looser((GameEvent)evt);
    	} else if (type.equals(GameEventType.ALIENSWIN)) {
    		action = new AliensWin((GameEvent)evt);
    	} else if (type.equals(GameEventType.HUMANWIN)) {
    		action = new HumanWin((GameEvent)evt);
    	} else if (type.equals(GameEventType.BLOCKHATCH)) {
    		action = new HatchBlocked((GameEvent)evt);
    	} else if (type.equals(GameEventType.DEFENSE)) {
    		action = new Defense((GameEvent)evt);
    	} else if (type.equals(GameEventType.REJECTCARD)) {
    		action = new Reject((GameEvent)evt);
    	} else if (type.equals(GameEventType.CHAT)) {
    		action = new Chat((GameEvent)evt);
    	} else if (type.equals(GameEventType.RETIRED)) {
    		action = new Retire((GameEvent)evt);
    	} else if (type.equals(GameEventType.FORCEDFINISH)) {
    		action = new ForceFinishTurn((GameEvent)evt);
    	}
		return action;
    }
}
