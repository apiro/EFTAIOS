package it.polimi.ingsw.cg_38.controller.action;

import it.polimi.ingsw.cg_38.controller.event.GameEvent;
import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.controller.gameEvent.EventRejectCard;
import it.polimi.ingsw.cg_38.controller.notifyEvent.EventRejectCardHuman;
import it.polimi.ingsw.cg_38.model.GameModel;
import it.polimi.ingsw.cg_38.model.deck.ObjectCard;

import java.util.ArrayList;
import java.util.List;

/** identifica l'azione di scarto di una carta oggetto */
public class Reject extends GameAction {

	private static final long serialVersionUID = 1L;
	
	/** contiene la carta oggetto da scartare */
	private ObjectCard card;

	/** invoca il costruttore della superclasse e setta i dati
	 * 
	 * @param evt evento di gioco che ha generato l'azione
	 */
	public Reject(GameEvent evt) {
		super(evt.getGenerator());
		this.setCard(((EventRejectCard)evt).getCard());
		
	}

	/** elimina la carta oggetto dalla lista di carte possedute dall'avatar e la aggiunge alla lista
	 *  di carte del rejectedDeck relativo
	 * 
	 * @param model gameModel sul quale performare l'azione
	 * @return ritorna la lista di eventi di notifica generati
	 */
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

	/** verifica se l'avatar relativo al giocatore possiede realmente la carta
	 * 
	 * @param model gameModel sul quale fare la verifica
	 * @return true se è possibile perfomare l'azione
	 */
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
