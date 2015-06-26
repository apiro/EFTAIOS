package it.polimi.ingsw.cg_38.client.notifyAction;

import it.polimi.ingsw.cg_38.client.PlayerClient;
import it.polimi.ingsw.cg_38.controller.event.GameEvent;
import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.controller.gameEvent.EventFinishTurn;

/** rappresenta il rendimento dell'azione di ritiro da parte di un giocatore */
public class RenderRetired extends NotifyAction {

	private static final long serialVersionUID = 1L;

	/** invoca il costruttore della superclasse
	 * 
	 * @param evt evento di notifica che ha generato l'azione
	 */
	public RenderRetired(NotifyEvent evt) {
		super(evt.getGenerator(), evt);
	}
	
	/** verifica se è possibile performare l'azione sul client
	 * 
	 * @param client client sul quale fare la verifica
	 * @return true se l'avatar associato al client è vivo ed è in gioco
	 */
	@Override
	public Boolean isPossible(PlayerClient client) {
		return super.check(client);
	}

	/** comunica al giocatore che ha lasciato la stanza
	 * 
	 * @param client client sul quale performare l'azione
	 * @return ritorna un evento di fine di turno se il giocatore associato al client ha generato
	 * l'azione di abbandono, null altrimenti
	 */
	@Override
	public GameEvent render(PlayerClient client) {
		client.getLogger().print("Player: " + super.getPlayer().getName() + " left the Room...");
		if(client.getPlayer().getName().equals(super.getPlayer().getName())) {
			client.setPlayer(evt.getGenerator());
			client.setIsInterfaceBlocked(true);
			client.getLogger().print("GoodBye !");
			return new EventFinishTurn(client.getPlayer());
		}
		return null;
	}
}
