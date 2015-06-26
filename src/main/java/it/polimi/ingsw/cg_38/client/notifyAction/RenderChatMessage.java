package it.polimi.ingsw.cg_38.client.notifyAction;

import it.polimi.ingsw.cg_38.client.PlayerClient;
import it.polimi.ingsw.cg_38.controller.event.GameEvent;
import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.controller.notifyEvent.EventNotifyChatMessage;

/** raprresneta il rendimento dell'azione di invio di un messaggio di chat */
public class RenderChatMessage extends NotifyAction {

	private static final long serialVersionUID = 1L;
	
	/** contiene il messaggio di chat da inviare */
	private String message;

	/** invoca il costruttore della superclasse e setta i dati
	 * 
	 * @param evt evento di notifica che ha generato l'azione
	 */
	public RenderChatMessage(NotifyEvent evt) {
		super(evt.getGenerator(), evt);
		this.message = ((EventNotifyChatMessage)evt).getMessage();
	}

	/** è sempre possibile performare questo tipo di azione
	 * 
	 * @param client client sul quale fare la verifica
	 * @return true
	 */
	@Override
	public Boolean isPossible(PlayerClient client) {
		return true;
	}

	/** invia ai giocatori il messaggio di chat
	 * 
	 * @param client client che ha generato l'invio di un messaggio di chat
	 * @return ritorna null
	 */
	@Override
	public GameEvent render(PlayerClient client) {
		client.getLoggerChat().print(super.getPlayer().getName() + " says: " + message );
		return null;
	}
}
