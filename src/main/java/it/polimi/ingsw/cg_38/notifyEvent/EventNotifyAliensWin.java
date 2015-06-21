package it.polimi.ingsw.cg_38.notifyEvent;

import java.util.ArrayList;

import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.controller.event.NotifyEventType;
import it.polimi.ingsw.cg_38.model.Player;

public class EventNotifyAliensWin extends NotifyEvent {

	private static final long serialVersionUID = 1L;
	private ArrayList<Player> winners;

	public EventNotifyAliensWin(Player generator, ArrayList<Player> winners) {
		super(generator, true);
		this.setWinners(winners);
		super.setType(NotifyEventType.aliensWin);
	}

	public ArrayList<Player> getWinners() {
		return winners;
	}

	public void setWinners(ArrayList<Player> winners) {
		this.winners = winners;
	}
}
