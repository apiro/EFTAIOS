package it.polimi.ingsw.cg_38.controller.gameEvent;

import it.polimi.ingsw.cg_38.controller.event.GameEvent;
import it.polimi.ingsw.cg_38.controller.event.GameEventType;
import it.polimi.ingsw.cg_38.model.Player;
import it.polimi.ingsw.cg_38.model.map.Sector;

/** rappresenta l'evento di attacco */
public class EventAttack extends GameEvent {

	private static final long serialVersionUID = 1L;
	
	/** contiene il settore sul quale il giocatore ha scelto di attaccare */
	private Sector target;
	
	/** invoca il costruttore della superclasse e setta i vari dati
	 * 
	 * @param generator giocatore che ha generato l'evento
	 * @param toAttack settore sul quale è stato effettuato l'attacco
	 */
	public EventAttack(Player generator, Sector toAttack) {
		super(generator, false);
		super.setType(GameEventType.ATTACK);
		this.setTarget(toAttack);
	}
	
	public Sector getTarget() {
		return target;
	}

	public void setTarget(Sector target) {
		this.target = target;
	}

}
