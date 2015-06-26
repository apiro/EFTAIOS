package it.polimi.ingsw.cg_38.client.notifyAction;

import it.polimi.ingsw.cg_38.client.PlayerClient;
import it.polimi.ingsw.cg_38.controller.event.GameEvent;
import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.controller.notifyEvent.EventNotifyAliensWin;

/** rappresneta l'azione rendimento della vittoria degli alieni */
public class RenderAliensWin extends NotifyAction {

	private static final long serialVersionUID = 1L;
	
	private Boolean areWinner;

	/** invoca il costruttore della superclasse
	 * 
	 * @param evt evento di notifica che ha generato l'azione
	 */
	public RenderAliensWin(NotifyEvent evt) {
		super(evt.getGenerator(), evt);
		
		this.areWinner = ((EventNotifyAliensWin)evt).getAreWinner();
	}

	/** è sempre possibile performare questa azione
	 * 
	 * @param client client sul quale performare l'azione
	 */
	@Override
	public Boolean isPossible(PlayerClient client) {
		return true;
	}

	/** se i giocatori hanno vinto viene segnalata a tutti i giocatori la vittoria degli alieni e viene 
	 * bloccata l'interfacciaù
	 * 
	 * @param client client sul quale performare l'azione
	 */
	@Override
	public GameEvent render(PlayerClient client) {
		if(areWinner) {
			client.getLogger().print("ALIENS WIN !!!");
			client.setIsInterfaceBlocked(true);
			client.setClientAlive(false);
			/*client.closeClient();*/
		} else {
			client.setIsInterfaceBlocked(false);
		}
		return null;
	}
}