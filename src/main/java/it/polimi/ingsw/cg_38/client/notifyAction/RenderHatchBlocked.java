package it.polimi.ingsw.cg_38.client.notifyAction;

import it.polimi.ingsw.cg_38.client.PlayerClient;
import it.polimi.ingsw.cg_38.controller.event.GameEvent;
import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;

/** rappresenta il rendimento dell'azione di bloccaggio del settore scialuppa */
public class RenderHatchBlocked extends NotifyAction {

	private static final long serialVersionUID = 1L;

	/** invoca il costruttore della superclasse
	 * 
	 * @param evt evento di notifica che ha generato l'azione
	 */
	public RenderHatchBlocked(NotifyEvent evt) {
		super(evt.getGenerator(), evt);
	}

	/** verifica se è possibile effettuare performare l'azione sul client
	 * 
	 * @param client client sul quale fare la verifica
	 * @return true se l'avatar associato al client è vivo ed è in gioco
	 */
	@Override
	public Boolean isPossible(PlayerClient client) {
		return super.check(client);
	}

	/** comunica al client che il settore scialuppa è bloccato
	 * 
	 * @param client client sul quale performare l'azione
	 * @return null
	 */
	@Override
	public GameEvent render(PlayerClient client) {
		client.paintHatch(false, evt.getGenerator().getAvatar().getCurrentSector());
		client.getLogger().print("HATCH BLOCKED !");
		return null;
	}
}