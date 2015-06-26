package it.polimi.ingsw.cg_38.controller.action;

import it.polimi.ingsw.cg_38.controller.event.GameEvent;
import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.controller.gameEvent.EventChat;
import it.polimi.ingsw.cg_38.controller.notifyEvent.EventNotifyChatMessage;
import it.polimi.ingsw.cg_38.model.GameModel;

import java.util.ArrayList;
import java.util.List;

/** identifica l'azione di invio di un messaggio di chat da parte di un giocatore */
public class Chat extends GameAction {

	private static final long serialVersionUID = 1L;
	
	/** contiene il messagio che si desidera inviare */
	private String message;

	/** invoca il costruttore della superclasse e setta i dati
	 * 
	 * @param evt evento di gioco che ha generato l'azione
	 */
	public Chat(GameEvent evt) {
		super(evt.getGenerator());
		this.message = ((EventChat)evt).getMessage();
	}

	/** ha il solo compito di generare eventi di notifica
	 * 
	 * @param model gameModel sul quale performare l'azione
	 * @return lista di eventi di notifica generati
	 */
	@Override
	public List<NotifyEvent> perform(GameModel model) {
		List<NotifyEvent> callbackEvent = new ArrayList<NotifyEvent>();
		callbackEvent.add(new EventNotifyChatMessage(model.getActualTurn().getCurrentPlayer(), message));
		return callbackEvent;
	}
}
