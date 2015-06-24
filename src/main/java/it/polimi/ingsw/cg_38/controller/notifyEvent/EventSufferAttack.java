package it.polimi.ingsw.cg_38.controller.notifyEvent;

import java.util.ArrayList;

import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.controller.event.NotifyEventType;
import it.polimi.ingsw.cg_38.model.Player;

public class EventSufferAttack extends NotifyEvent {

	private ArrayList<Player> killed;

	public EventSufferAttack(Player generator, ArrayList<Player> killed) {
		super(generator, true);
		this.killed = killed;
		super.setType(NotifyEventType.SUFFERATTACK);
	}

	public ArrayList<Player> getKilled() {
		return killed;
	}

	public void setKilled(ArrayList<Player> killed) {
		this.killed = killed;
	}

	private static final long serialVersionUID = 1L;

}
