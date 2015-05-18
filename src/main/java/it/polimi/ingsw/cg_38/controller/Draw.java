package it.polimi.ingsw.cg_38.controller;
import  it.polimi.ingsw.cg_38.model.*;

import java.util.*;

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

    /**
     * 
     */
    public Draw(GameModel gameModel) {
    	super(gameModel);
    }

    /**
     * @return
     */
    public Card perform() {
        // TODO implement here
    	if(this.getGameModel().getActualTurn().getCurrentPlayer().getAvatar().getCurrentSector() instanceof Dangerous ) {
    		SectorCard drown = (SectorCard)this.getGameModel().getDeckSector().draw();
    		
    		if(drown.getHasObjectIcon()) {
    			this.getGameModel().getActualTurn().getCurrentPlayer().getAvatar().addCard(this.getGameModel().getDeckObject().draw());
    		}
    		this.getGameModel().getActualTurn().setHasDraw(true);
   			return drown;
   		} else if (this.getGameModel().getActualTurn().getCurrentPlayer().getAvatar().getCurrentSector() instanceof Hatch ) {
   			this.getGameModel().getActualTurn().setHasDraw(true);
   			return this.getGameModel().getDeckHatch().draw();
   		} else {
    		return null;
    	}
    }

    /**
     * @return
     */
    public Boolean isPossible() {
       
    	// TODO implement here
    	if( this.getGameModel().getActualTurn().getHasMoved() &&
    		!this.getGameModel().getActualTurn().getHasAttacked() &&
    		!this.getGameModel().getActualTurn().getHasDraw() && 
    		!(this.getGameModel().getActualTurn().getCurrentPlayer().getAvatar().getCurrentSector() instanceof Safe)) {
    		return true;
    	}
    	return false;
    }
    

}