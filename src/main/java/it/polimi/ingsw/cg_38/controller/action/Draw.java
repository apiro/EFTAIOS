package it.polimi.ingsw.cg_38.controller.action;
import java.util.ArrayList;
import java.util.List;

import it.polimi.ingsw.cg_38.controller.event.GameEvent;
import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.controller.notifyEvent.EventDrown;
import  it.polimi.ingsw.cg_38.model.*;
import it.polimi.ingsw.cg_38.model.deck.HatchCard;
import it.polimi.ingsw.cg_38.model.deck.HatchCardType;
import it.polimi.ingsw.cg_38.model.deck.ObjectCard;
import it.polimi.ingsw.cg_38.model.deck.SectorCard;
import it.polimi.ingsw.cg_38.model.map.Dangerous;
import it.polimi.ingsw.cg_38.model.map.Hatch;
import it.polimi.ingsw.cg_38.model.map.Safe;

/** identifica l'azione di pescaggio di una carta dal mazzo */
public class Draw extends GameAction {

	private static final long serialVersionUID = 1L;

	/** invoca il costruttore della superclasse
	 * 
	 * @param evt evento di gioco che ha generato l'azione
	 */
    public Draw(GameEvent evt) {
    	super(evt.getGenerator());
    }

    /** in base al nome del settore su cui si trova l'avatar viene pescata una carta nel relativo deck.
     * Se si tratta di una carta settore dotata di un icona viene anche aggiunta alle carte dell'avatar una
     * carta oggetto pescata dal relativo mazzo
     * 
     * @param model gameModel sul quale performare l'azione
     * @return ritorna gli eventi di notifica generati
     */
    @Override
    public List<NotifyEvent> perform(GameModel model) {
    	List<NotifyEvent> callbackEvent = new ArrayList<NotifyEvent>();
    	if(model.getActualTurn().getCurrentPlayer().getAvatar().getCurrentSector() instanceof Dangerous ) {
    		SectorCard drown1 = (SectorCard)model.getDeckSector().draw();
    		ObjectCard drown2 = null;
    		if(drown1.getHasObjectIcon()) {
    			drown2 = (ObjectCard)model.getDeckObject().draw();
    			model.getActualTurn().getCurrentPlayer().getAvatar().addCard(drown2);
    		}
    		model.getActualTurn().setHasDraw(true);
   			callbackEvent.add(new EventDrown(model.getActualTurn().getCurrentPlayer(), drown2, drown1));
   		} else if (model.getActualTurn().getCurrentPlayer().getAvatar().getCurrentSector() instanceof Hatch ) {
   			model.getActualTurn().setHasDraw(true);
   			HatchCard drown1 =  (HatchCard)model.getDeckHatch().draw();
   			if(drown1.getColor().equals(HatchCardType.RED)) {
   				((Hatch)model.getActualTurn().getCurrentPlayer().getAvatar().getCurrentSector()).setIsOpen(false);
   			}
   			callbackEvent.add(new EventDrown(model.getActualTurn().getCurrentPlayer(), null, drown1));
   		} 
    	return callbackEvent;
    }

    /** verifica se è possibile per formazione l'azione sul modello
     * 
     * @param model gameModel sul quale viene effettuata la verifica
     * @return true se è possibile performare l'azione
     */
    @Override
    public Boolean isPossible(GameModel model) {
       
    	if( model.getActualTurn().getHasMoved() &&
    		!model.getActualTurn().getHasAttacked() &&
    		!model.getActualTurn().getHasDraw() && 
    		!(model.getActualTurn().getCurrentPlayer().getAvatar().getCurrentSector() instanceof Safe) && 
    		super.isPossible(model)) {
    		return true;
    	}
    	return false;
    }
    
}