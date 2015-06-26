package it.polimi.ingsw.cg_38.controller.action;
import java.util.ArrayList;
import java.util.List;

import it.polimi.ingsw.cg_38.controller.event.GameEvent;
import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.controller.notifyEvent.EventDeclareNoise;
import  it.polimi.ingsw.cg_38.model.*;

/** identifica l'azione della carta rumore nel mio settore */
public class UseMySectorNoise extends GameAction {

	private static final long serialVersionUID = 1L;

	/** invoca il costruttore della superclasse
	 * 
	 * @param evt evento di gioco che ha generato l'azione
	 */
	public UseMySectorNoise(GameEvent evt) {
    	super(evt.getGenerator());
    }

	/** genera un evento di dichiarazione di un rumore
	 * 
	 * @param model gameModel sul quale viene performata l'azione
	 * @return lista di eventi di notifica generati
	 */
	@Override
    public List<NotifyEvent> perform(GameModel model) {
    	List<NotifyEvent> callbackEvent = new ArrayList<NotifyEvent>();
    	callbackEvent.add(new EventDeclareNoise(model.getActualTurn().getCurrentPlayer() , model.getActualTurn().getCurrentPlayer().getAvatar().getCurrentSector()));
    	return callbackEvent;
    }
    
	/** verifica se è possibile effettuare l'azione
	 * 
	 * @param model gameModel sul quale viene fatta la verifica
	 * @return true se il gioco è in esecuzione
	 */
    @Override
    public Boolean isPossible(GameModel model) {
    	return super.isPossible(model);
    }

}
