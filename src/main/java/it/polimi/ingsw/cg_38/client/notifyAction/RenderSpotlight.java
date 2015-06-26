package it.polimi.ingsw.cg_38.client.notifyAction;

import it.polimi.ingsw.cg_38.client.PlayerClient;
import it.polimi.ingsw.cg_38.controller.event.GameEvent;
import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.controller.gameEvent.EventContinue;
import it.polimi.ingsw.cg_38.controller.notifyEvent.EventDeclarePosition;
import it.polimi.ingsw.cg_38.model.Player;

/** rappresenta il rendimento dell'utilizzo della carta luci in un settore */
public class RenderSpotlight extends NotifyAction {

	private static final long serialVersionUID = 1L;

	/** invoca il costruttore della superclasse
	 * 
	 * @param evt evento di notifica che ha generato l'azione
	 */
	public RenderSpotlight(NotifyEvent evt) {
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

	/** se la lista di giocatori da dichiarare non è nulla essa viene mostrata al client
	 * 
	 * @param client client sul quale performare l'azione
	 * @return ritorna un azione di gioco di continuare l'azione
	 */
	@Override
	public GameEvent render(PlayerClient client) {
		
		/*client.setPlayer(evt.getGenerator());*/
		if(!((EventDeclarePosition)evt).getToDeclare().isEmpty()) {
			client.getLogger().print("Declared Light in sector: row: " + ((EventDeclarePosition)evt).getToDeclare().get(0).getAvatar().getCurrentSector().getRow() + 
				"col: " + ((EventDeclarePosition)evt).getToDeclare().get(0).getAvatar().getCurrentSector().getCol());
			int i = 0;
			for(Player pl:((EventDeclarePosition)evt).getToDeclare()) {
				client.getLogger().print(i + ") -> " + pl.getName());
				i++;
			}
		}
		if(client.getPlayer().getName().equals(evt.getGenerator().getName())) {
			client.setPlayer(evt.getGenerator());
			client.updateCards();
			client.getLogger().print("Card used !");
		}
		return new EventContinue();
	}
}