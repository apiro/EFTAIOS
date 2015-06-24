package it.polimi.ingsw.cg_38.controller.action;
import java.util.ArrayList;
import java.util.List;

import it.polimi.ingsw.cg_38.controller.event.GameEvent;
import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.controller.gameEvent.EventMove;
import it.polimi.ingsw.cg_38.controller.notifyEvent.EventMoved;
import  it.polimi.ingsw.cg_38.model.*;
import it.polimi.ingsw.cg_38.model.map.Sector;

public class Move extends GameAction {

	private static final long serialVersionUID = 1L;

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

    public Sector sectorToMove;

    public List<NotifyEvent> perform(GameModel model) {
    	
    	List<NotifyEvent> callbackEvent = new ArrayList<NotifyEvent>();
    	String toReturn = model.getActualTurn().getCurrentPlayer().getAvatar().move(getSectorToMove(), model.getActualTurn().getCurrentPlayer().getNumTurniGiocati()+1);
    	model.getActualTurn().setHasMoved(true);
    	callbackEvent.add(new EventMoved(model.getActualTurn().getCurrentPlayer(), toReturn));
    	return callbackEvent;
    }

    public Boolean isPossible(GameModel model) {
        if(!model.getActualTurn().getHasMoved() &&
        		model.getActualTurn().getCurrentPlayer().getAvatar().canMove(getSectorToMove()) && 
        		super.isPossible(model)) {
        	return true;
        }
        return false;
    }
}