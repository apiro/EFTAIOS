package it.polimi.ingsw.cg_38.controller.action;
import it.polimi.ingsw.cg_38.controller.event.GameEvent;
import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.gameEvent.EventAttack;
import it.polimi.ingsw.cg_38.model.*;
import it.polimi.ingsw.cg_38.notifyEvent.EventAttacked;

/**
 * 
 * anche questa perform è void in realta
 * 
 * 
 * è l'azione di attacco base.attacco sia di umani che di alieni senza disitinzioni.
 *  l'azione attacco per gli alieni viene generata con un'istanza di questa classe, per
 *  gli umani viene generata l'azione useAttackCard(Card card, Sector sector) che genera una azione attacco e la performa
 * 
 */
public class Attack extends GameAction {
	
    /**
     * 
     */
    public Sector sectorToAttack;

    /**
     * @return
     */
    public NotifyEvent perform(GameModel model) { 	
        	for(Player pl:model.getDesiredPlayers(this.getSectorToAttack())) {
        		//nella condizione di if oltre a verificare se l'attacco è andato a buon fine modifico anche il modello
        		//del gicatore attaccato
        		if(pl.getAvatar().attacked()) {
        		//se l'attacco è andato a buon fine sul giocatore i che si trovava nel settore che hai attaccato
        			if(this.currentAvatarType(model).equals("Alien")) {
        				model.getActualTurn().getCurrentPlayer().getAvatar().setIsPowered(true);
        			} else {
        				//se il giocatore che attacca è umano non so cosa succede all'umano che uccide qualcuno,
        				//se diventa potenziato allora non serve questo ultimo ramo if-else e il setIsPowered è 
        				//sia per umani che per alieni
        			}
        		}
        	}
        	model.getActualTurn().setHasAttacked(true);
        return new EventAttacked(model.getActualTurn().getCurrentPlayer());
    }

    /**
     * @param Sector sector
     */
    public Attack(GameEvent evt) {
    	super(evt.getGenerator());
    	this.setSectorToAttack(((EventAttack)evt).getTarget());
    }

    public Sector getSectorToAttack() {
		return sectorToAttack;
	}

	public void setSectorToAttack(Sector sectorToAttack) {
		this.sectorToAttack = sectorToAttack;
	}
	
	/**
     * @return
     */
    public Boolean isPossible(GameModel model) {
        if(this.currentAvatarType(model).equals("Alien") &&
        		model.getActualTurn().getHasMoved() &&
        		!model.getActualTurn().getHasAttacked() && 
        		!model.getActualTurn().getHasDraw() && super.isPossible(model)) {
        	return true;
        } else if (this.currentAvatarType(model).equals("Human") && 
        		((Human)model.getActualTurn().getCurrentPlayer().getAvatar()).getCanAttack() && 
        		model.getActualTurn().getHasMoved() &&
        		!model.getActualTurn().getHasAttacked() && 
        		!model.getActualTurn().getHasDraw()) {
        	return true;
        }
        return false;
    }

}