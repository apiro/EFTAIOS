package it.polimi.ingsw.cg_38.notifyEvent;

import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.controller.event.NotifyEventType;
import it.polimi.ingsw.cg_38.model.Hatch;
import it.polimi.ingsw.cg_38.model.Player;

public class EventHatchBlocked extends NotifyEvent {

	private static final long serialVersionUID = 1L;
	private Hatch hatch;

	public EventHatchBlocked(Player generator, Hatch hatch) {
		super(generator, true);
		this.setType(NotifyEventType.hatchBlocked);
		this.setHatch(hatch);
	}

	public Hatch getHatch() {
		return hatch;
	}

	public void setHatch(Hatch hatch) {
		this.hatch = hatch;
	}

}
