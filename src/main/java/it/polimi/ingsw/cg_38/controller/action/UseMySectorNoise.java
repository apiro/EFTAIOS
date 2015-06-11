package it.polimi.ingsw.cg_38.controller.action;
import it.polimi.ingsw.cg_38.controller.event.GameEvent;
import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import  it.polimi.ingsw.cg_38.model.*;
import it.polimi.ingsw.cg_38.notifyEvent.EventDeclareNoise;
<<<<<<< HEAD
=======
import it.polimi.ingsw.cg_38.notifyEvent.EventDeclarePosition;
>>>>>>> branch 'master' of https://albypiro@bitbucket.org/albypiro/cg_38.git


public class UseMySectorNoise extends GameAction {

    public UseMySectorNoise(GameEvent evt) {
    	super(evt.getGenerator());
    }

    /**
     * @return
     */
    public NotifyEvent perform(GameModel model) {
    	return new EventDeclareNoise(model.getActualTurn().getCurrentPlayer() , model.getActualTurn().getCurrentPlayer().getAvatar().getCurrentSector());
    }

    /**
     * @return
     */
    public Boolean isPossible(GameModel model) {
    	return super.isPossible(model);
    }

}
