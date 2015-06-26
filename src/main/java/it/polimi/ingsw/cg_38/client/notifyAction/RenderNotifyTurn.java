package it.polimi.ingsw.cg_38.client.notifyAction;

import it.polimi.ingsw.cg_38.client.PlayerClient;
import it.polimi.ingsw.cg_38.client.PlayerClientState;
import it.polimi.ingsw.cg_38.controller.event.GameEvent;
import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.controller.notifyEvent.EventNotifyTurn;

/** rappresenta il rendimento dell'azione di notifica del turno */
public class RenderNotifyTurn extends NotifyAction {
	
	private static final long serialVersionUID = 1L;

	/** invoca il costruttore della superclasse
	 * 
	 * @param evt evento di notifica che ha generato l'azione
	 */
	public RenderNotifyTurn(NotifyEvent evt){
		super(evt.getGenerator() , evt);		
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

	/** se il giocatore associato al client è colui che deve effettuare il turno glielo comunica,
	 * altrimenti gli comunica che non è il suo turni. In entrambi i casi vengono settate le interfacce
	 * 
	 * @param client client sul quale performare l'azione
	 * @return null
	 */
	@Override
	public GameEvent render(PlayerClient client) {
		
		if(((EventNotifyTurn)evt).getPlayerOfTurn().getName().equals(client.getPlayer().getName())) {
			client.getLogger().print("------------------------\n");
			client.getLogger().print("IS YOUR TURN !");
			client.setIsMyTurn(true);
			client.setPlayerClientState(PlayerClientState.ISTURN);
			client.getLogger().print("[T] You are " + client.getPlayer().getName() + " and you are " + client.getPlayer().getAvatar().getName());
			client.setIsInterfaceBlocked(false);
		} else {
			client.getLogger().print("------------------------\n");
			client.getLogger().print("NOT YOUR TURN !");
			client.setPlayerClientState(PlayerClientState.PLAYING);
			client.setIsMyTurn(false);
			client.setIsInterfaceBlocked(true);
		}
		return null;
	}
}
