package it.polimi.ingsw.cg_38.controller.action;
import java.util.ArrayList;
import java.util.List;

import it.polimi.ingsw.cg_38.controller.event.GameEvent;
import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.controller.gameEvent.EventAttack;
import it.polimi.ingsw.cg_38.controller.gameEvent.EventATTACKCARD;
import it.polimi.ingsw.cg_38.controller.notifyEvent.EventCardUsed;
import it.polimi.ingsw.cg_38.controller.notifyEvent.EventRejectCardAlien;
import  it.polimi.ingsw.cg_38.model.*;
import it.polimi.ingsw.cg_38.model.deck.ObjectCard;
import it.polimi.ingsw.cg_38.model.map.Sector;

/**identifica l'azione di utilizzo della carta attacoo */
public class UseAttackCard extends GameAction {

	private static final long serialVersionUID = 1L;
	
	/** contiene il giocatore che ha generato l'azione */
	private Player generator;
	
	private ObjectCard card;
	
	/** contiene il settore nel quale effettuare l'attacco */
    private Sector sectorToAttack;
    

	/** invoca il costruttore della superclasse e setta i vari dati
	 * 
	 * @param evt evento di gioco che ha generato l'azione
	 */
	public UseAttackCard(GameEvent evt) {
		super(evt.getGenerator());
		this.generator = evt.getGenerator();
		this.setCard(((ObjectCard)((EventATTACKCARD)evt).getToUse()));
    	this.setSectorToAttack(((EventATTACKCARD)evt).getTarget());
    }

    public Sector getSectorToAttack() {
		return sectorToAttack;
	}

	public void setSectorToAttack(Sector sectorToAttack) {
		this.sectorToAttack = sectorToAttack;
	}

    public ObjectCard getCard() {
		return card;
	}

	public void setCard(ObjectCard card) {
		this.card = card;
	}

	/** se l'avatar del giocatore che ha generato l'azione è di tipo alien la carta viene scartata, 
	 * altrimenti viene performata un azione di attacco normale
	 * 
	 * @param model gameModel sul quale performare l'azione
	 * @return lista di eventi di notifica generati
	 */
	@Override
    public List<NotifyEvent> perform(GameModel model) {
    	List<NotifyEvent> callbackEvent = new ArrayList<NotifyEvent>();
    	
    	if(("Alien").equals(this.currentAvatarType(model))){
    		model.getActualTurn().getCurrentPlayer().getAvatar().eliminateFromMyCards(card);
    		model.handleRejectedCard(card);
    		model.getActualTurn().setHasUsedObjectCard(true);
    		callbackEvent.add(new EventRejectCardAlien(model.getActualTurn().getCurrentPlayer()));
    		callbackEvent.add(new EventCardUsed(model.getActualTurn().getCurrentPlayer(), false, card.getType()));
    		return callbackEvent;
    	}
    	
    	((Human)model.getActualTurn().getCurrentPlayer().getAvatar()).setCanAttack(true);
    	model.getActualTurn().getCurrentPlayer().getAvatar().eliminateFromMyCards(card);
    	model.handleRejectedCard(card);
    	GameAction humanAttackAction = new Attack(new EventAttack(generator, this.getSectorToAttack()));
    	
    	if(humanAttackAction.isPossible(model)) {
    		callbackEvent = humanAttackAction.perform(model);
    	}
    	model.getActualTurn().setHasUsedObjectCard(true);
    	callbackEvent.add(new EventCardUsed(model.getActualTurn().getCurrentPlayer(), true, card.getType()));
    	return callbackEvent;
    }

	/** verifica se il giocatore possiede la carta /*
	 * 
	 * @param model gameModel sul quale fare la verifica
	 * @return true se è possibile performare l'azione sul model
	 */
    @Override
    public Boolean isPossible(GameModel model) {
	    if(model.getActualTurn().getCurrentPlayer().getAvatar().getMyCards().contains(this.getCard()) && 
	    		!model.getActualTurn().getHasUsedObjectCard() &&
	    		super.isPossible(model)) {
	    	return true;
	    }
	    return false;
	}
}
