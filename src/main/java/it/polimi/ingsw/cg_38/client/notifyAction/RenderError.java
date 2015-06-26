package it.polimi.ingsw.cg_38.client.notifyAction;

import it.polimi.ingsw.cg_38.client.PlayerClient;
import it.polimi.ingsw.cg_38.controller.event.GameEvent;
import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.controller.notifyEvent.EventNotifyError;

/** rappresenta il rendimento dell'azione di errore */
public class RenderError extends NotifyAction {
	
	private static final long serialVersionUID = 1L;

	/** invoca il costruttore della superclasse
	 * 
	 * @param evt evento di notifica che ha generato l'azione
	 */
	public RenderError(NotifyEvent evt){
		
		super(evt.getGenerator() , evt);
	}

	/** è sempre possibile performare l'azione sul client
	 * 
	 * @param client client sul quale fare la verifica
	 * @return true
	 */
	@Override
	public Boolean isPossible(PlayerClient client) {
		return true;
	}

	/** viene notifica il client che ha generato l'azione che c'è stato un errore nell'eseguirla
	 * e viene settata, di conseguenza, l'interfaccia
	 * 
	 * @param client client sul quale performare l'azione
	 * @return ritorna l'evento di gioco
	 */
	@Override
	public GameEvent render(PlayerClient client) {
		if(client.getPlayer().getName().equals(evt.getGenerator().getName())){
			client.setPlayer(evt.getGenerator());
			client.updateCards();
			client.getLogger().print("There was an error in processing " + 
			((EventNotifyError)evt).getRelatedAction().getClass().toString().substring(46) + 
			" action related to your previous GameEvent ... RETRY !");
			client.setIsInterfaceBlocked(false);
		} else {
			client.setIsInterfaceBlocked(true);
		}
		return null;
	}
}
