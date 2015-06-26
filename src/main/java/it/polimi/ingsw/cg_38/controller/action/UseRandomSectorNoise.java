package it.polimi.ingsw.cg_38.controller.action;


import java.util.ArrayList;
import java.util.List;

import it.polimi.ingsw.cg_38.controller.event.GameEvent;
import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.controller.gameEvent.EventNoiseRandSect;
import it.polimi.ingsw.cg_38.controller.notifyEvent.EventDeclareNoise;
import it.polimi.ingsw.cg_38.model.GameModel;
import it.polimi.ingsw.cg_38.model.map.Sector;

/** identifica l'utilizzo della carta di rumore in un settore qualsiasi */
public class UseRandomSectorNoise extends GameAction {

	private static final long serialVersionUID = 1L;

    private Sector toDeclare;
    
	public UseRandomSectorNoise(GameEvent evt) {
    	super(evt.getGenerator());
    	this.setToDeclare(((EventNoiseRandSect)evt).getToNoise());
    }
	
	/** viene generato un evento di dichiarazione di un ruore
	 * 
	 * @param model gameModel sul quale viene performata l'azione
	 * @return lista di eventi di notifica generati
	 */
	@Override
    public List<NotifyEvent> perform(GameModel model) {
    	List<NotifyEvent> callbackEvent = new ArrayList<NotifyEvent>();
    	callbackEvent.add(new EventDeclareNoise(model.getActualTurn().getCurrentPlayer(), this.getToDeclare()));
    	return callbackEvent;
    }

    public Sector getToDeclare() {
		return toDeclare;
	}

	public void setToDeclare(Sector toDeclare) {
		this.toDeclare = toDeclare;
	}

	/** verifica il gioco è in esecuzione
	 * 
	 * @param model gameModel sul quale fare la verifica
	 * @return true se è possibile performare l'azione sul model
	 */
	@Override
    public Boolean isPossible(GameModel model) {
        return super.isPossible(model);
    }

}
