package it.polimi.ingsw.cg_38.controller.action;

import java.util.ArrayList;
import java.util.List;

import it.polimi.ingsw.cg_38.controller.event.GameEvent;
import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.controller.notifyEvent.EventDeclareNoise;
import it.polimi.ingsw.cg_38.model.GameModel;

/** identifica l'utilizzo della carta di silenzio */
public class UseSilenceCard extends GameAction {

	private static final long serialVersionUID = 1L;

	/** invoca il costruttore della superclasse
	 * 
	 * @param evt evento di gioco che ha generato l'azione
	 */
    public UseSilenceCard(GameEvent evt) {
    	super(evt.getGenerator());
    }
    
    /** genera un evento di dichiarazione di un rumore
     * 
     * @param model gameModel sul quale performare l'azione
     * @return lista di eventid i notifica
     */
    @Override
    public List<NotifyEvent> perform(GameModel model) {
    	List<NotifyEvent> callbackEvent = new ArrayList<NotifyEvent>();
        callbackEvent.add(new EventDeclareNoise(model.getActualTurn().getCurrentPlayer(), null));
        return callbackEvent;
    }

    /** verifica se il gioco è in esecuzione
     * 
     * @param model gameModel sul quale fare la verifica
     * @return true se il gioco è in esecuzione
     */
    @Override
    public Boolean isPossible(GameModel model) {
         return super.isPossible(model);
    }

}