package it.polimi.ingsw.cg_38.controller.notifyEvent;

import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.controller.event.NotifyEventType;
import it.polimi.ingsw.cg_38.model.Player;
import it.polimi.ingsw.cg_38.model.map.Hatch;

/** rappresenta l'evento scialuppa bloccato*/
public class EventHatchBlocked extends NotifyEvent {

	private static final long serialVersionUID = 1L;
	
	private Hatch hatch;

	/** invoca il costruttore della superclasse e setta i dati
	 * 
	 * @param generator giocatore che ha generato l'evento
	 * @param hatch settore scialuppa da bloccare
	 */
	public EventHatchBlocked(Player generator, Hatch hatch) {
		super(generator, true);
		this.setType(NotifyEventType.HATCHBLOCKED);
		this.setHatch(hatch);
	}

	public Hatch getHatch() {
		return hatch;
	}

	public void setHatch(Hatch hatch) {
		this.hatch = hatch;
	}

}
