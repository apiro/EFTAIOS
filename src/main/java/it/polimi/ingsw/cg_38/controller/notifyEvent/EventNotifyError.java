package it.polimi.ingsw.cg_38.controller.notifyEvent;

import it.polimi.ingsw.cg_38.controller.action.Action;
import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.controller.event.NotifyEventType;
import it.polimi.ingsw.cg_38.model.Player;

/** rappresenta l'evento di notifica di errore */
public class EventNotifyError extends NotifyEvent {

	private static final long serialVersionUID = 1L;
	
	/** contiene l'azione che ha generato l'errore */
	private Action relatedAction;
	
	/** invoca il costruttore della superclasse e setta i vari dati 
	 * 
	 * @param generator giocatore che ha generato l'evento
	 * @param action azione che ha generato l'errore
	 */
	public EventNotifyError(Player generator, Action action) {
		super(generator, false);
		this.relatedAction = action;
		this.setType(NotifyEventType.ERROR);
	}

	public Action getRelatedAction() {
		return relatedAction;
	}

}
