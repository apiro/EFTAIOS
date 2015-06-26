package it.polimi.ingsw.cg_38.client.notifyAction;

import it.polimi.ingsw.cg_38.client.PlayerClient;
import it.polimi.ingsw.cg_38.client.PlayerClientState;
import it.polimi.ingsw.cg_38.controller.event.GameEvent;
import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.controller.notifyEvent.EventAddedToGame;

/** rappresenta l'azione di avvenura aggiunta al gioco */
public class AddedToGame extends NotifyAction {

	private static final long serialVersionUID = 1L;
	
	private NotifyEvent evt;
	
	/** invoca il costruttore della superclasse
	 * 
	 * @param evt2 evento di notifica che ha generato l'azione
	 */
	public AddedToGame(NotifyEvent evt2) {
		super(evt2.getGenerator() , evt2);
	}
	
	@Override
	public NotifyEvent getEvt() {
		return evt;
	}

	@Override
	public void setEvt(NotifyEvent evt) {
		this.evt = evt;
	}

	/** il client viene settato a connesso e viene bloccata la sua interfaccia in attesta che inizi
	 * la partita
	 * 
	 * @param client client che ha richiesto l'azione
	 */
	@Override
	public GameEvent render(PlayerClient client) {
		client.getLogger().print(this.getEvt().getGenerator().getName() + " has been added ? " + ((EventAddedToGame)evt).getAdded());
		if(((EventAddedToGame)evt).getGenerator().getName().equals(client.getPlayer().getName())) {
			client.setPlayerClientState(PlayerClientState.CONNECTED);
		}
		client.setIsInterfaceBlocked(true);
		return null;		
	}

	/** è sempre possibile performare questa azione */
	@Override
	public Boolean isPossible(PlayerClient client) {
		return true;
	}
}