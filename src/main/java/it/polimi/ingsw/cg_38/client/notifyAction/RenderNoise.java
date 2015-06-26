package it.polimi.ingsw.cg_38.client.notifyAction;

import it.polimi.ingsw.cg_38.client.PlayerClient;
import it.polimi.ingsw.cg_38.controller.event.GameEvent;
import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.controller.notifyEvent.EventDeclareNoise;

/** rappresenta il rendimento dell'azione di rumore in un settore */
public class RenderNoise extends NotifyAction {

	private static final long serialVersionUID = 1L;

	/** invoca il costruttore della superclasse
	 * 
	 * @param evt evento di notifica che ha generato l'azione
	 */
	public RenderNoise(NotifyEvent evt) {
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

	/** comunica al giocatore associato al client l'evento di rumore in un settore
	 * 
	 * @param client client sul quale performare l'azione
	 * @return null
	 */
	@Override
	public GameEvent render(PlayerClient client) {
		client.getLogger().print("Noise declared in sector: row:" + ((EventDeclareNoise)evt).getSectorToNoise().getRow() + " col: " + ((EventDeclareNoise)evt).getSectorToNoise().getCol() + " ...");
		return null;
	}

}
