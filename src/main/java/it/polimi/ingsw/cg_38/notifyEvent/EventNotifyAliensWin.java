package it.polimi.ingsw.cg_38.notifyEvent;

import java.util.ArrayList;

import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.controller.event.NotifyEventType;
import it.polimi.ingsw.cg_38.model.Player;

public class EventNotifyAliensWin extends NotifyEvent {

	private static final long serialVersionUID = 1L;
	private ArrayList<Player> winners;
	private Boolean areWinner;

	public EventNotifyAliensWin(Player generator, ArrayList<Player> winners, Boolean areWinner) {
		super(generator, true);
		this.setWinners(winners);
		this.setAreWinner(areWinner);
		super.setType(NotifyEventType.aliensWin);
	}

	public ArrayList<Player> getWinners() {
		return winners;
	}

	public void setWinners(ArrayList<Player> winners) {
		this.winners = winners;
	}

	public Boolean getAreWinner() {
		return areWinner;
	}

	public void setAreWinner(Boolean areWinner) {
		this.areWinner = areWinner;
	}
}
