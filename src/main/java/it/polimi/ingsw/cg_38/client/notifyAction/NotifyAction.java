package it.polimi.ingsw.cg_38.client.notifyAction;

import it.polimi.ingsw.cg_38.client.PlayerClient;
import it.polimi.ingsw.cg_38.controller.action.Action;
import it.polimi.ingsw.cg_38.controller.event.GameEvent;
import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.model.EndState;
import it.polimi.ingsw.cg_38.model.LifeState;
import it.polimi.ingsw.cg_38.model.Player;

/** rapprsenta una generica azione di notifica */
public abstract class NotifyAction extends Action {
	
	private static final long serialVersionUID = 1L;
	
	public NotifyEvent evt;

	/** il client viene settato a connesso e viene bloccata la sua interfaccia in attesta che inizi
	 * la partita. Infine setta i dati
	 * 
	 * @param player giocatore sul quale performare l'azione
	 * @param evt evento di gioco che ha generato l'azione
	 */
	public NotifyAction(Player player , NotifyEvent evt) {
		super(player);
		this.setEvt(evt);
	}
	
	public NotifyEvent getEvt() {
		return evt;
	}

	public void setEvt(NotifyEvent evt) {
		this.evt = evt;
	}

	public abstract Boolean isPossible(PlayerClient client);
	
	/** verifica se l'avatar del giocatore è ancora vivo e non ha perso
	 * 
	 * @param client client sul quale fare la verifica
	 * @return true se il giocatore è vivo ed è in gioco
	 */
	public Boolean check(PlayerClient client) {
		if(client.getPlayer().getAvatar().getIsAlive().equals(LifeState.DEAD) &&
				client.getPlayer().getAvatar().getIsWinner().equals(EndState.LOOSER)) {
			return false;
		}
		return true;
	};
	
	public abstract GameEvent render(PlayerClient client);	
}
