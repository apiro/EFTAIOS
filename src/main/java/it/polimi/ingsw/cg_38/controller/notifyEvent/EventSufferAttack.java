package it.polimi.ingsw.cg_38.controller.notifyEvent;

import java.util.List;

import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.controller.event.NotifyEventType;
import it.polimi.ingsw.cg_38.model.Player;

public class EventSufferAttack extends NotifyEvent {

	private List<Player> killed;

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

	private static final long serialVersionUID = 1L;

}
