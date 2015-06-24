package it.polimi.ingsw.cg_38.controller.notifyEvent;

import java.util.List;

import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.controller.event.NotifyEventType;
import it.polimi.ingsw.cg_38.model.Player;

public class EventNotifyAliensWin extends NotifyEvent {

	private static final long serialVersionUID = 1L;
	private List<Player> winners;
	private Boolean areWinner;

	public EventNotifyAliensWin(Player generator, List<Player> winners, Boolean areWinner) {
		super(generator, true);
		this.setWinners(winners);
		this.setAreWinner(areWinner);
		super.setType(NotifyEventType.ALIENSWIN);
	}

	public List<Player> getWinners() {
		return winners;
	}

	public void setWinners(List<Player> winners) {
		this.winners = winners;
	}

	public Boolean getAreWinner() {
		return areWinner;
	}

	public void setAreWinner(Boolean areWinner) {
		this.areWinner = areWinner;
	}
}
