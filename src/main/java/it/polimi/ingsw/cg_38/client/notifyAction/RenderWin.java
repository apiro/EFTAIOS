package it.polimi.ingsw.cg_38.client.notifyAction;

import it.polimi.ingsw.cg_38.client.PlayerClient;
import it.polimi.ingsw.cg_38.client.PlayerClientState;
import it.polimi.ingsw.cg_38.controller.event.GameEvent;
import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.controller.gameEvent.EventFinishTurn;

/** rappresenta il rendimento della vittoria di un giocatore */
public class RenderWin extends NotifyAction {

	private static final long serialVersionUID = 1L;

	/** invoca il costruttore della superclasse
	 * 
	 * @param evt evento di notifica che ha generato l'azione
	 */
	public RenderWin(NotifyEvent evt) {
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

	/** comunica al giocatore che ha vinto, modifica lo stato del client che infine viene chiuso
	 * 
	 * @param client client sul quale performare l'azione
	 * @return ritorna l'evento di fine turno
	 */
	@Override
	public GameEvent render(PlayerClient client) {
		client.getLogger().print("YOU WIN !");
		client.setIsInterfaceBlocked(true);
		client.setPlayerClientState(PlayerClientState.WINNER);
		client.paintHatch(true, evt.getGenerator().getAvatar().getCurrentSector());
		client.closeClient();
		return new EventFinishTurn(super.evt.getGenerator());
	}
}
