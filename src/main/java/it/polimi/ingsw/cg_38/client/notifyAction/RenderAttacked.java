package it.polimi.ingsw.cg_38.client.notifyAction;

import it.polimi.ingsw.cg_38.client.PlayerClient;
import it.polimi.ingsw.cg_38.controller.event.GameEvent;
import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.controller.gameEvent.EventAliensWinner;
import it.polimi.ingsw.cg_38.controller.gameEvent.EventContinue;
import it.polimi.ingsw.cg_38.controller.notifyEvent.EventAttacked;
import it.polimi.ingsw.cg_38.model.Alien;

/** rappresenta il rendimento dell'azione di essere attaccato */
public class RenderAttacked extends NotifyAction {
	
	private static final long serialVersionUID = 1L;

	/** invoca il costruttore della superclasse
	 * 
	 * @param evt evento di notifica che ha generato l'azione
	 */
	public RenderAttacked(NotifyEvent evt){
		
		super(evt.getGenerator() , evt);
	}

	/** verifica se è possibile performare l'azione
	 * 
	 * @param client client sul quale fare la verifica
	 * @return true se l'avatar del client e vivo ed è in gioco
	 */
	@Override
	public Boolean isPossible(PlayerClient client) {
		return super.check(client);
	}

	/** verifica il tipo di avatar che ha effettuato l'attacco e se ci sono altri umani in gioco e,
	 * di conseguenza, genera eventi di gioco
	 * 
	 * @param client client sul quale performare l'azione
	 * @return ritorna un evento di gioco in base alla situazione del gioco
	 */
	@Override
	public GameEvent render(PlayerClient client) {	
		
		
		/*if(((EventAttacked)evt).getGenerator().getName().equals(client.getPlayer().getName())) {*/
			
			client.setPlayer(evt.getGenerator());
			
			if(((EventAttacked)evt).getAreYouPowered()) {
				//verifico chi è stato a fare l'attacco
				if(((EventAttacked)evt).getGenerator().getAvatar() instanceof Alien) {
					
					if(((EventAttacked)evt).getAreThereOtherHumans()) {
						
						client.setIsInterfaceBlocked(false);
						return null;
					} else {
						
						return new EventAliensWinner(client.getPlayer(), true);
					}
				} else {
					
					client.setIsInterfaceBlocked(true);
					return new EventContinue();
				}
			} else {
				
				client.getLogger().print("There are no players in the sector you have choosen !");
				client.setIsInterfaceBlocked(false);
				return null;
			}
		/*}
		client.setIsInterfaceBlocked(true);
		return null;*/
	}
}
