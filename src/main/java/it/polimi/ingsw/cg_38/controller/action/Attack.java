package it.polimi.ingsw.cg_38.controller.action;
import java.util.ArrayList;
import java.util.List;

import it.polimi.ingsw.cg_38.controller.event.GameEvent;
import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.controller.gameEvent.EventAttack;
import it.polimi.ingsw.cg_38.controller.notifyEvent.EventAttacked;
import it.polimi.ingsw.cg_38.controller.notifyEvent.EventSufferAttack;
import it.polimi.ingsw.cg_38.controller.notifyEvent.EventUseDefense;
import it.polimi.ingsw.cg_38.model.*;
import it.polimi.ingsw.cg_38.model.deck.ObjectCardType;
import it.polimi.ingsw.cg_38.model.map.Sector;

/**
 * rappresenta l'azione di attacco base, sia quello degli umani sia degli alieni senza disitinzioni.
 *  l'azione attacco per gli alieni viene generata con un'istanza di questa classe, per
 *  gli umani viene generata l'azione useAttackCard(Card card, Sector sector) che genera a sua volta
 *  una azione attacco 
 */
public class Attack extends GameAction {
	
	private static final long serialVersionUID = 1L;
	
	/** contiene il settore sul quale performare l'attacco */
    public Sector sectorToAttack;

    /** invoca il costruttore della superclasse e setta i dati
     * 
     * @param evt evento di gioco che ha generato l'azione
     */
    public Attack(GameEvent evt) {
    	super(evt.getGenerator());
    	this.setSectorToAttack(((EventAttack)evt).getTarget());
    }


    /** verifica quali sono i giocatori che risultano effettivamente attaccati cambiandone lo stato
     * e nel caso in cui l'azione è generata da un alieno esso viene settato potenziato
     * 
     * @param model gameModel sul quale performare l'azione
     * @return lista degli eventi di notifica generati
     */
    @Override
    public List<NotifyEvent> perform(GameModel model) { 
    	List<NotifyEvent> callbackEvent = new ArrayList<NotifyEvent>();
    	List<Player> killed = model.getDesiredPlayers(this.getSectorToAttack());
    	Player p = null;
    	List<Player> hasDefense = new ArrayList<Player>();
    	
    	//tolgo il giocatore che ha attaccato che è nel target sector
    	for(Player pl:killed) {
    		if((pl.getName().equals(super.getPlayer().getName())) || 
    				(pl.getAvatar().getIsAlive().equals(LifeState.DEAD))) {
    			hasDefense.add(pl);
    		} else if (pl.getAvatar().hasDefenseCard()) {
    			hasDefense.add(pl);
    			callbackEvent.add(new EventUseDefense(pl, true, ObjectCardType.DEFENSE));
    		} else {
    			//qui setto il giocatore ucciso a dead e looser
    			pl.getAvatar().attacked();
    			if(("Alien").equals(this.currentAvatarType(model))) {
       				model.getActualTurn().getCurrentPlayer().getAvatar().setIsPowered(true);
       			}
    		}
    	}
    	for(Player pl:hasDefense) {
    		killed.remove(pl);
    	}
    	callbackEvent.add(new EventSufferAttack(model.getActualTurn().getCurrentPlayer(), killed));
    	
    	model.getActualTurn().setHasAttacked(true);
        
        Boolean areThereOtherHumans = false;
        for(Player pl:model.getGamePlayers()) {
        	if((pl.getAvatar() instanceof Human) && pl.getAvatar().getIsAlive().equals(LifeState.ALIVE)) {
        		areThereOtherHumans = true;
        	}
        }
        
        callbackEvent.add(new EventAttacked(model.getActualTurn().getCurrentPlayer(), areThereOtherHumans));
        return callbackEvent;
    }

    public Sector getSectorToAttack() {
		return sectorToAttack;
	}

	public void setSectorToAttack(Sector sectorToAttack) {
		this.sectorToAttack = sectorToAttack;
	}
	
	/** verifica se è possibile performare l'azione in base allo stato del game model
	 * 
	 * @param model gameModel sul quale deve essere performata l'azione
	 * @return true se è possibile performare l'azione sul modello
	 */
	@Override
    public Boolean isPossible(GameModel model) {
        if(("Alien").equals(this.currentAvatarType(model)) &&
        		model.getActualTurn().getHasMoved() &&
        		!model.getActualTurn().getHasAttacked() && 
        		!model.getActualTurn().getHasDraw() && super.isPossible(model)) {
        	return true;
        } else if (("Human").equals(this.currentAvatarType(model)) && 
        		((Human)model.getActualTurn().getCurrentPlayer().getAvatar()).getCanAttack() && 
        		/*model.getActualTurn().getHasMoved() && non c'è questa condizione perchè l'umano puo giocare la carta attacco anche prima di aver mosso !*/
        		!model.getActualTurn().getHasAttacked() && 
        		/*!model.getActualTurn().getHasDraw() && non c'è questa condizione perchè l'umano puo giocare la carta attacco anche prima di aver pescato !*/ super.isPossible(model)) {
        	return true;
        }
        return false;
    }

}