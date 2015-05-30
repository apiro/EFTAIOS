package it.polimi.ingsw.cg_38.controller.action;
import it.polimi.ingsw.cg_38.controller.GameState;
import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import  it.polimi.ingsw.cg_38.model.*;

/**
 * 
 * il playercontroller riceve azioni e le performa. anche se il playercontroller non ha riferimento al modello su cui
 * performare puo performare azioni lo stesso perche le azioni hanno un riferimento al gamemodel
 * 
 * le action sono sempre riferite al currentTurn
 * 
 */
public abstract class GameAction extends Action {

    public GameAction(Player player) {
    	super(player);
    }
    
    public abstract NotifyEvent perform(GameModel model);

    public Boolean isPossible(GameModel model) {
    	if(model.getGameState().equals(GameState.RUNNING)) {
    		return true;
    	} else return false;
    }

	public String currentAvatarType(GameModel model){
		if(model.getActualTurn().getCurrentPlayer().getAvatar() instanceof Alien) {
			return "Alien";
		} else {
			return "Human";
		}
	}
}