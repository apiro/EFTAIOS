package it.polimi.ingsw.cg_38.controller.action;

import it.polimi.ingsw.cg_38.controller.event.GameEvent;
import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.controller.notifyEvent.EventCardUsed;
import it.polimi.ingsw.cg_38.controller.notifyEvent.EventRejectCardAlien;
import it.polimi.ingsw.cg_38.model.GameModel;
import it.polimi.ingsw.cg_38.model.deck.ObjectCard;
import it.polimi.ingsw.cg_38.model.deck.ObjectCardType;

import java.util.ArrayList;
import java.util.List;

/** identifica l'azione di utilizzo dell carta difesa da parte di un giocatore */
public class Defense extends GameAction {

	private static final long serialVersionUID = 1L;

	/** invoca il costruttore della superclasse
	 * 
	 * @param evt evento di gioco che ha generato l'azione
	 */
	public Defense(GameEvent evt) {
		super(evt.getGenerator());
	}

	/** elimina la carta difesa dalle carte dell'avatar e la aggiunge alla lista di carte del rejectedDeck
	 * 
	 * @param model gameModel sul quale performare l'azione
	 * @return lista di eventi di notifica generati
	 */
	@Override
	public List<NotifyEvent> perform(GameModel model) {
		List<NotifyEvent> callbackEvent = new ArrayList<NotifyEvent>();
		int i = 0;
		for(ObjectCard c:model.getActualTurn().getCurrentPlayer().getAvatar().getMyCards()) {
			if(c.getType().equals(ObjectCardType.DEFENSE)) {
				break;
			}
			i++;
		}
		ObjectCard cardToRemove = model.getActualTurn().getCurrentPlayer().getAvatar().getMyCards().get(i);
		model.handleRejectedCard(model.getActualTurn().getCurrentPlayer().getAvatar().eliminateFromMyCards(cardToRemove));
		callbackEvent.add(new EventRejectCardAlien(model.getActualTurn().getCurrentPlayer()));
		callbackEvent.add(new EventCardUsed(model.getActualTurn().getCurrentPlayer(), false, ObjectCardType.DEFENSE));
		return callbackEvent;
	}
}
