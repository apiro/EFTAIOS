package it.polimi.ingsw.cg_38.controller.action;
import it.polimi.ingsw.cg_38.controller.event.GameEvent;
import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.gameEvent.EventMove;
import  it.polimi.ingsw.cg_38.model.*;
import it.polimi.ingsw.cg_38.notifyEvent.EventMoved;

/**
 * 
 */
public class Move extends GameAction {

    /**
     * 
     */
    public Move(GameEvent evt) {
    	super(evt.getGenerator());
    	this.setSectorToMove(((EventMove)evt).getToMove());
    }

    public Sector getSectorToMove() {
		return sectorToMove;
	}

	public void setSectorToMove(Sector sectorToMove) {
		this.sectorToMove = sectorToMove;
	}

	/**
     * 
     */
    public Sector sectorToMove;

    /**
     * @return
     */
    public NotifyEvent perform(GameModel model) {
        // TODO implement here
    	String toReturn = model.getActualTurn().getCurrentPlayer().getAvatar().move(getSectorToMove(), model.getActualTurn().getCurrentPlayer().getNumTurniGiocati()+1);
    	model.getActualTurn().getCurrentPlayer().setNumTurniGiocati(model.getActualTurn().getCurrentPlayer().getNumTurniGiocati()+1);
    	model.getActualTurn().setHasMoved(true);
    	return new EventMoved(model.getActualTurn().getCurrentPlayer(), toReturn);
    }

    /**
     * @return
     */
    public Boolean isPossible(GameModel model) {
        if(!model.getActualTurn().getHasMoved() &&
        		model.getActualTurn().getCurrentPlayer().getAvatar().canMove(getSectorToMove()) && 
        		super.isPossible(model)) {
        	return true;
        }
        return false;
    }
}