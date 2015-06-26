package it.polimi.ingsw.cg_38.client.notifyAction;

import it.polimi.ingsw.cg_38.client.PlayerClient;
import it.polimi.ingsw.cg_38.client.PlayerClientState;
import it.polimi.ingsw.cg_38.controller.event.GameEvent;
import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.controller.gameEvent.EventAttack;
import it.polimi.ingsw.cg_38.controller.gameEvent.EventDraw;
import it.polimi.ingsw.cg_38.controller.notifyEvent.EventMoved;
import it.polimi.ingsw.cg_38.model.Alien;

/** rappresenta l'azione di rendimento dell'azione di mossa */
public class RenderMoved extends NotifyAction {
	
	private static final long serialVersionUID = 1L;

	/** invoca il costruttore della superclasse
	 * 
	 * @param evt evento di notifica che ha generato l'azione
	 */
	public RenderMoved(NotifyEvent evt){
		super(evt.getGenerator() , evt);
	}

	/** verifica se è possibile performare l'azione sul client
	 * 
	 * @param client client sul quale fare la verifica
	 * @return true se è il turno del giocatore associato al client e se l'avatar del giocatore
	 * è vivo ed è in gioco
	 */
	@Override
	public Boolean isPossible(PlayerClient client) {
		if(client.getPlayerClientState().equals(PlayerClientState.ISTURN) && 
				super.check(client)){
			return true;
		}
		return false;
	}

	/** fa scegliere al client quale azione generare dopo la mossa e genera, di conseguenza,
	 * un azione di gioco 
	 * 
	 * @param client client sul quale performare l'azione
	 * @return evento di gioco generato
	 */
	@Override
	public GameEvent render(PlayerClient client) {
		
		GameEvent evt1 = null;
		
		client.setPlayer(evt.getGenerator());
		if(("Safe").equals(((EventMoved)evt).getMoved())) {
			String com = client.getLogger().showAndRead("You are in a SAFE sector ! Type attack or continue: [A] | [C]", "Game Message");
			while(!("C").equals(com) && !("A").equals(com)) {
				client.getLogger().print("Command not valid retry !");
				com = client.getLogger().showAndRead("You are in a SAFE sector ! Type attack or continue: [A] | [C]", "Game Message");
			}
			if(("C").equals(com)) {
				client.setIsInterfaceBlocked(false);
				client.updateMovements();
				return null;
			} else if (("A").equals(com) && client.getPlayer().getAvatar() instanceof Alien) {
				evt1 = new EventAttack(client.getPlayer(), client.getPlayer().getAvatar().getCurrentSector());
				client.updateMovements();
				return evt1;
			} else {
				client.setIsInterfaceBlocked(false);
				client.updateMovements();
				return null;
			}
		} else if(("Dangerous").equals(((EventMoved)evt).getMoved())) {
			String com = client.getLogger().showAndRead("You are in a DANGEROUS sector ! Type draw or attack :[D] | [A] ?", "Game Message");
			client.getLogger().print("(If you activated the sedative card Type [D] to continue without drowing !");
			while(!("D").equals(com) && !("A").equals(com)) {
				client.getLogger().print("Command not valid retry !");
				com = client.getLogger().showAndRead("You are in a DANGEROUS sector ! Type draw or attack :[D] | [A] ?", "Game Message");
			}
			if(("D").equals(com)) {
				evt1 = new EventDraw(client.getPlayer());
			} else if (("A").equals(com) && client.getPlayer().getAvatar() instanceof Alien) {
				evt1 = new EventAttack(client.getPlayer(), client.getPlayer().getAvatar().getCurrentSector());
			} else {
				evt1 = new EventDraw(client.getPlayer());
			}
		} else if(("Hatch").equals(((EventMoved)evt).getMoved())) {
			String com = client.getLogger().showAndRead("You are in a HATCH sector ! Type [D]", "Game Message");
			while(!("D").equals(com)){}
			evt1 = new EventDraw(client.getPlayer());
		}
		client.updateMovements();
		return evt1;
	}
}
