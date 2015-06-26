package it.polimi.ingsw.cg_38.controller.action;

import it.polimi.ingsw.cg_38.controller.event.GameEvent;
import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.controller.notifyEvent.EventNotifyRetired;
import it.polimi.ingsw.cg_38.model.GameModel;

import java.util.ArrayList;
import java.util.List;

/** identifica l'azione di ritiro di un giocatore dalla partita*/
public class Retire extends GameAction {

	private static final long serialVersionUID = 1L;

	/** invocal il costruttore della superclasse
	 * 
	 * @param evt evento di gioco che ha generato l'azione
	 */
	public Retire(GameEvent evt) {
		super(evt.getGenerator());
	}

	/** verifica se il giocatore ha già effettuato il movimento durante il turno
	 * 
	 * @param model gameModel sul quale fare la verifica
	 * @return true se è possibile performare l'azione
	 */
	@Override
	public Boolean isPossible(GameModel model) {
		return model.getActualTurn().getHasMoved();
	}
	
	/** modifica lo stato di gioco dell'avatar
	 * 
	 * @param model gameModel sul quale performare l'azione
	 * @return ritorna la lista di eventi di notifica generati
	 */
	@Override
	public List<NotifyEvent> perform(GameModel model) {
		List<NotifyEvent> callbackEvent = new ArrayList<NotifyEvent>();
		model.getActualTurn().getCurrentPlayer().getAvatar().attacked();
		callbackEvent.add(new EventNotifyRetired(model.getActualTurn().getCurrentPlayer()));
		return callbackEvent;
	}
}
