package it.polimi.ingsw.cg_38.controller.notifyEvent;

import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.model.Player;

/** rappresenta l'evento di notifica dello stato del giocatore */
public class EventNotifyPlayerState extends NotifyEvent {


	private static final long serialVersionUID = 1L;

	private Player player;
	
	/** indica se il giocatore ha vinto */
	private Boolean winner;
	
	/** invoca il costruttore della superclasse e setta i vari dati
	 * 
	 * @param player giocatore che ha generato l'evento
	 * @param isWinner true se il giocatore ha vinto
	 */
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

	public Boolean getWinner() {
		return winner;
	}
	public void setIsWinner(Boolean winner) {
		this.winner = winner;
	}

}
