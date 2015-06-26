package it.polimi.ingsw.cg_38.controller.action;
import java.util.ArrayList;
import java.util.List;

import it.polimi.ingsw.cg_38.controller.event.GameEvent;
import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.controller.gameEvent.EventMove;
import it.polimi.ingsw.cg_38.controller.notifyEvent.EventMoved;
import  it.polimi.ingsw.cg_38.model.*;
import it.polimi.ingsw.cg_38.model.map.Sector;

/** identifica l'azione di movimento */
public class Move extends GameAction {

	private static final long serialVersionUID = 1L;

    public Sector sectorToMove;
   
	/** invoca il costruttore della superclasse e setta i vari dati
	 * 
	 * @param evt evento di gioco che ha generato l'azione
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
 
	/** modifica il settore attuale dell'avatar e lo stato del turno
	 * 
	 * @param model gameModel sul quale performare l'azione
	 * @return lista degli eventi di notifica generati
	 */
    @Override
    public List<NotifyEvent> perform(GameModel model) {
    	
    	List<NotifyEvent> callbackEvent = new ArrayList<NotifyEvent>();
    	
    	Sector sec = model.getGameMap().searchSectorByCoordinates(getSectorToMove().getRow(), getSectorToMove().getCol());
    	String toReturn = model.getActualTurn().getCurrentPlayer().getAvatar().move(sec, model.getActualTurn().getCurrentPlayer().getNumTurniGiocati()+1);
    	
    	model.getActualTurn().setHasMoved(true);
    	callbackEvent.add(new EventMoved(model.getActualTurn().getCurrentPlayer(), toReturn));
    	return callbackEvent;
    }

    /** verifica se è possibile performare l'azione sul model
     * 
     * @param model gameModel sul quale fare la verifica
     * @return true se è possibile performare l'azione sul model
     */
    @Override
    public Boolean isPossible(GameModel model) {
        if(!model.getActualTurn().getHasMoved() &&
        		model.getActualTurn().getCurrentPlayer().getAvatar().canMove(getSectorToMove()) && 
        		super.isPossible(model)) {
        	return true;
        }
        return false;
    }
}
