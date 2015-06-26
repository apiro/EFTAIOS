package it.polimi.ingsw.cg_38.client.notifyAction;

import it.polimi.ingsw.cg_38.client.PlayerClient;
import it.polimi.ingsw.cg_38.client.PlayerClientState;
import it.polimi.ingsw.cg_38.controller.event.GameEvent;
import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.controller.gameEvent.EventContinue;
import it.polimi.ingsw.cg_38.controller.notifyEvent.EventSufferAttack;
import it.polimi.ingsw.cg_38.model.Human;
import it.polimi.ingsw.cg_38.model.Player;
 
/** rappresenta il rendimento dell'azione di attacco */
public class RenderAttackDamage extends NotifyAction {

	private static final long serialVersionUID = 1L;

	/** invoca il costruttore della superclasse
	 * 
	 * @param evt evento di notifica che ha generato l'azione
	 */
	public RenderAttackDamage(NotifyEvent evt) {
		super(evt.getGenerator(), evt);
	}

	/** verifica se è possibile performare l'azione
	 * 
	 * @param client client sul quale fare la verifica
	 * @return true se il l'avatar del client è vivo
	 */
	@Override
	public Boolean isPossible(PlayerClient client) {
		return super.check(client);
	}

	/** viene notificata a tutti i giocatori l'avvenuta dell'attacco 
	 * 
	 * @param client client sul quale performare l'azione
	 * @return ritorna un evento di continua l'azione
	 */
	@Override
	public GameEvent render(PlayerClient client) {
		
		client.getLogger().print(evt.getGenerator().getName() + " has attacked in sector: row:" +
				evt.getGenerator().getAvatar().getCurrentSector().getRow() + " , col: " +
						evt.getGenerator().getAvatar().getCurrentSector().getCol() + " ...");
		
		for(Player pl:((EventSufferAttack)evt).getKilled()){
			client.getLogger().print(pl.getName() + " has been killed in sector: row: " + 
					evt.getGenerator().getAvatar().getCurrentSector().getRow() + " col: " + 
					evt.getGenerator().getAvatar().getCurrentSector().getCol() );
			if(pl.getAvatar() instanceof Human)
				client.getLogger().print("He was a Human");
			else
				client.getLogger().print("He was an Alien");
			
			if(pl.getName().equals(client.getPlayer().getName())) {
				this.renderLoose(client);
			}
		}
		
		return new EventContinue();
	}

	/** notifica la sconfitta al giocatore e blocca l'interfaccia
	 * 
	 * @param client client sul quale performare la sconfitta
	 */
	public void renderLoose(PlayerClient client) {
		
		client.getLogger().print("YOU LOOSE !");
		client.setIsInterfaceBlocked(true);
		client.setClientAlive(false);
		client.setPlayerClientState(PlayerClientState.LOOSER);
	}
}
