package it.polimi.ingsw.cg_38.client.notifyAction;

import it.polimi.ingsw.cg_38.client.Client;
import it.polimi.ingsw.cg_38.client.PlayerClient;
import it.polimi.ingsw.cg_38.client.PlayerClientState;
import it.polimi.ingsw.cg_38.controller.event.GameEvent;
import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.controller.notifyEvent.EventNotifyEnvironment;
import it.polimi.ingsw.cg_38.model.Player;

/** rappresenta il rendimento dell'azione di notifica di inizio gioco */
public class RenderEnvironment extends NotifyAction {
	
	private static final long serialVersionUID = 1L;
	
	Client client;
	
	/** invoca il costruttore della superclasse
	 * 
	 * @param evt evento di notifica che ha generato l'azione
	 */
	public RenderEnvironment(NotifyEvent evt){
		
		super(evt.getGenerator() , evt);
	}

	/** verifica se è possibile performare l'azione
	 * 
	 * @client client sul quale fare la verifica
	 * @return true se al client non è associata nessuna mappa
	 */
	@Override
	public Boolean isPossible(PlayerClient client) {
		if(client.getMap() == null) {
			return true;
		}
		return false;
	}

	/** notifica al client quale avatar gli è stato assegnato e che il gioco sta iniziando e setta
	 * i vari dati
	 * 
	 * @param client client sul quale performare l'azione
	 * @return ritorna null
	 */
	@Override
	public GameEvent render(PlayerClient client) {

		for(Player pl:((EventNotifyEnvironment)super.getEvt()).getMappingPlayerAvatar()) {
			if(pl.getName().equals(client.getPlayer().getName())) {
				client.setPlayer(pl);
				client.getLogger().print("The server assigned you : " + client.getPlayer().getAvatar().getName() + "...");
			}
		}
		client.setClientAlive(true);
		client.setMap(((EventNotifyEnvironment)evt).getMap());
		client.setPlayerClientState(PlayerClientState.PLAYING);
		client.getLogger().print("----------------------------------------------------------------------");
		client.getLogger().print("Starting Game : Waiting for the first turn message ...");
		client.setIsInterfaceBlocked(true);
		return null;
	}
}
