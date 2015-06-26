package it.polimi.ingsw.cg_38.controller.notifyEvent;

import java.util.List;

import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.controller.event.NotifyEventType;
import it.polimi.ingsw.cg_38.model.Player;

/** rappresenta l'evento di subita di un attaco */
public class EventSufferAttack extends NotifyEvent {

	private static final long serialVersionUID = 1L;

	/** lista dei giocatori uccisi */
	private List<Player> killed;

	/** invoca il costruttore della superclasse e setta i vari dati
	 * 
	 * @param generator giocatore che ha generato l'azione
	 * @param killed lista di giocatori uccisi
	 */
	public EventSufferAttack(Player generator, List<Player> killed) {
		super(generator, true);
		this.killed = killed;
		super.setType(NotifyEventType.SUFFERATTACK);
	}

	public List<Player> getKilled() {
		return killed;
	}

	public void setKilled(List<Player> killed) {
		this.killed = killed;
	}

}
