package it.polimi.ingsw.cg_38.controller.action;

import it.polimi.ingsw.cg_38.controller.event.GameEvent;
import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.controller.gameEvent.EventRejectCard;
import it.polimi.ingsw.cg_38.controller.notifyEvent.EventRejectCardHuman;
import it.polimi.ingsw.cg_38.model.GameModel;
import it.polimi.ingsw.cg_38.model.deck.ObjectCard;

import java.util.ArrayList;
import java.util.List;

public class Reject extends GameAction {

	private static final long serialVersionUID = 1L;
	private ObjectCard card;

	public Reject(GameEvent evt) {
		super(evt.getGenerator());
		this.setCard(((EventRejectCard)evt).getCard());
		
	}

	@Override
	public List<NotifyEvent> perform(GameModel model) {
		List<NotifyEvent> callbackEvent = new ArrayList<NotifyEvent>();
		int i=0;
		for(ObjectCard c:model.getActualTurn().getCurrentPlayer().getAvatar().getMyCards()) {
			if(c.getType().equals(card.getType())) {
				break;
			}
			i++;
		}
		ObjectCard cardToRemove = model.getActualTurn().getCurrentPlayer().getAvatar().getMyCards().get(i);
		model.handleRejectedCard(model.getActualTurn().getCurrentPlayer().getAvatar().eliminateFromMyCards(cardToRemove));
		callbackEvent.add(new EventRejectCardHuman(model.getActualTurn().getCurrentPlayer(), card));
		return callbackEvent;
	}

	@Override
	public Boolean isPossible(GameModel model) {
	    if(model.getActualTurn().getCurrentPlayer().getAvatar().getMyCards().contains(this.getCard()) && 
	    		super.isPossible(model)) {
	    	return true;
	    }
	     return false;
	}

	public ObjectCard getCard() {
		return card;
	}

	public void setCard(ObjectCard card) {
		this.card = card;
	}
	
}
