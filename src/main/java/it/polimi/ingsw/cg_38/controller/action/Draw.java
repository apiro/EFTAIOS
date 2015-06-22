package it.polimi.ingsw.cg_38.controller.action;
import java.util.ArrayList;

import it.polimi.ingsw.cg_38.controller.event.GameEvent;
import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import  it.polimi.ingsw.cg_38.model.*;
import it.polimi.ingsw.cg_38.notifyEvent.EventDrown;

/**
 * 
 * se il giocatore ha pescato una carta settore questa viene ritornata e se la carta settore pescata aveva l'icona, al model
 * del giocatore viene aggiunta una carta oggetto random, dopodiche
 * viene ritornata al giocatore la carta settore pescata cosicchè nella view possa venire generata una azione di rumore,
 * la quale viene ritornata al controller(a seconda del tipo di carta settore ricevuta dal controller la view genera una 
 * azione rumore piuttosto che l'altra) che infine chiama perform().
 * 
 * giocatore-> manda azione di draw()-> al controller che chiama perform ritorna la carta pescata-> invia alla vista la carta
 * pescata e aggiunge al giocatore l'eventuale carta oggetto che gli spetta-> ritorna l'azione e il controller chiama perform
 * sulla azione rumore
 * 
 */
public class Draw extends GameAction {

	private static final long serialVersionUID = 1L;

    public Draw(GameEvent evt) {
    	super(evt.getGenerator());
    }

    /**
     * @return
     */
    public ArrayList<NotifyEvent> perform(GameModel model) {
    	ArrayList<NotifyEvent> callbackEvent = new ArrayList<NotifyEvent>();
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
   			if(drown1.getColor().equals(HatchCardType.Red)) {
   				((Hatch)model.getActualTurn().getCurrentPlayer().getAvatar().getCurrentSector()).setIsOpen(false);
   			}
   			callbackEvent.add(new EventDrown(model.getActualTurn().getCurrentPlayer(), null, drown1));
   		} 
    	return callbackEvent;
    }

    /**
     * @return
     */
    public Boolean isPossible(GameModel model) {
       
    	// TODO implement here
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