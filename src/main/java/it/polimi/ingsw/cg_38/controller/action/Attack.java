package it.polimi.ingsw.cg_38.controller.action;
import java.util.ArrayList;

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
    	ArrayList<Player> killed = model.getDesiredPlayers(this.getSectorToAttack());
    	Player p = null;
    	//tolgo il giocatore che ha attaccato che è nel target sector
    	for(Player pl:killed) {
    		if(pl.getName().equals(super.getPlayer().getName())) {
    			p = pl;
    		}
    	}
    	killed.remove(p);
    	
       	for(Player pl:killed) {
       		
       		if(pl.getAvatar() instanceof Human && pl.getAvatar().attacked()) {
       		
       			if(this.currentAvatarType(model).equals("Alien")) {
       				model.getActualTurn().getCurrentPlayer().getAvatar().setIsPowered(true);
       			} 
       			
       		} else {
       			p = pl;
       		}
        }
       	killed.remove(p);
       	p = null;
        model.getActualTurn().setHasAttacked(true);
        
        Boolean areAllAliens = true;
        for(Player pl:model.getGamePlayers()) {
        	if(pl.getAvatar() instanceof Human) {
        		areAllAliens = false;
        	}
        }
        if(areAllAliens) {
        	return new EventAttacked(model.getActualTurn().getCurrentPlayer(), killed, false);
        }
        return new EventAttacked(model.getActualTurn().getCurrentPlayer(), killed, true);
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
        		/*model.getActualTurn().getHasMoved() && non c'è questa condizione perchè l'umano puo giocare la carta attacco anche prima di aver mosso !*/
        		!model.getActualTurn().getHasAttacked() && 
        		/*!model.getActualTurn().getHasDraw() && non c'è questa condizione perchè l'umano puo giocare la carta attacco anche prima di aver pescato !*/ super.isPossible(model)) {
        	return true;
        }
        return false;
    }

}