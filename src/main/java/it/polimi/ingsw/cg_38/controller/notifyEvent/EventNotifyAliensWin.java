package it.polimi.ingsw.cg_38.controller.notifyEvent;

import java.util.List;

import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.controller.event.NotifyEventType;
import it.polimi.ingsw.cg_38.model.Player;

/** rappresenta l'evento di notifica di vittoria degli alieni */
public class EventNotifyAliensWin extends NotifyEvent {

	private static final long serialVersionUID = 1L;
	
	/** lista di tutti i giocatori alieni che hanno vinto */
	private List<Player> winners;
	
	/** indica se i giocatori hanno effettivamente vinto */
	private Boolean areWinner;

	/** invoca il costruttore della superclasse e setta i vari dati
	 * 
	 * @param generator giocatore che ha generato l'evento
	 * @param winners lista dei giocatori che hanno vinto
	 * @param areWinner true se i giocatori hanno effettivamente vinto
	 */
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
