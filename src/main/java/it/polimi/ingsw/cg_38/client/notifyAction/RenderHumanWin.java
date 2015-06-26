package it.polimi.ingsw.cg_38.client.notifyAction;

import it.polimi.ingsw.cg_38.client.PlayerClient;
import it.polimi.ingsw.cg_38.controller.event.GameEvent;
import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.controller.gameEvent.EventFinishTurn;

/** rappresenta l'azione di rendimento della vittoria di un umano */
public class RenderHumanWin extends NotifyAction {

	private static final long serialVersionUID = 1L;

	/** invoca il costruttore della superclasse
	 * 
	 * @param evt evento di notifica che ha generato l'azione
	 */
	public RenderHumanWin(NotifyEvent evt) {
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

	/** comunica al giocatore che ha lasciato la nave spaziale e che ha vinto 
	 * e setta l'interfaccia
	 * 
	 * @param client client sul quale performale l'azione
	 * @return ritorna un evento di gioco di fine turno se il client corrisponde al giocatore
	 * che ha vinto, null altrimenti
	 */
	@Override
	public GameEvent render(PlayerClient client) {
		client.getLogger().print("PLAYER " + super.player.getName() + " HAS LEFT THE SPACESHIP ! ");
		if(client.getPlayer().getName().equals(super.player.getName())) {
			client.paintHatch(true, evt.getGenerator().getAvatar().getCurrentSector());
			client.setIsInterfaceBlocked(true);
			return new EventFinishTurn(client.getPlayer());
		} else {
			client.paintHatch(false, evt.getGenerator().getAvatar().getCurrentSector());
			client.setIsInterfaceBlocked(true);
		}
		return null;
	}
}
