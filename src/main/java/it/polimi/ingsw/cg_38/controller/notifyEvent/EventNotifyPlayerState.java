package it.polimi.ingsw.cg_38.controller.notifyEvent;

import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.model.Player;

public class EventNotifyPlayerState extends NotifyEvent {

	public EventNotifyPlayerState(Player player, Boolean isWinner) {
		super(null, true);
		this.setPlayer(player);
		this.setIsWinner(isWinner);
	}
	
	public Player getPlayer() {
		return player;
	}
	public void setPlayer(Player player) {
		this.player = player;
	}

	private Player player;
	
	/** è settato a true se il giocatore ha vinto */
	private Boolean winner;
	
	public Boolean getWinner() {
		return winner;
	}
	public void setIsWinner(Boolean winner) {
		this.winner = winner;
	}

	private static final long serialVersionUID = 1L;
}
