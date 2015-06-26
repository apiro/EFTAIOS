package it.polimi.ingsw.cg_38.controller.action;

import java.util.ArrayList;
import java.util.List;

import it.polimi.ingsw.cg_38.controller.GameState;
import it.polimi.ingsw.cg_38.controller.event.Event;
import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.controller.gameEvent.EventAliensWinner;
import it.polimi.ingsw.cg_38.controller.notifyEvent.EventClosingGame;
import it.polimi.ingsw.cg_38.controller.notifyEvent.EventNotifyAliensWin;
import it.polimi.ingsw.cg_38.controller.notifyEvent.EventNotifyClosingTopic;
import it.polimi.ingsw.cg_38.model.Alien;
import it.polimi.ingsw.cg_38.model.EndState;
import it.polimi.ingsw.cg_38.model.GameModel;
import it.polimi.ingsw.cg_38.model.LifeState;
import it.polimi.ingsw.cg_38.model.Player;

/** rappresenta l'azione di vittoria degli alieni */
public class AliensWin extends GameAction {

	private static final long serialVersionUID = 1L;
	
	/** true se gli alieni hanno effettivamente vinto */
	private Boolean areWinner;

	/** invoca il costruttore della superclasse e setta i dati
	 * 
	 * @param evt evento di gioco che ha generato l'azione
	 */
	public AliensWin(Event evt) {
		super(evt.getGenerator());
		areWinner = ((EventAliensWinner)evt).getAreWinner();
	}

	/** performa l'azione aggiungendo alla lista di giocatori vincenti tutti gli alieni ancora vivi 
	 * 
	 * @param model gameModel sul quale performare l'azione
	 * @return lista degli eventi di notifica generati 
	 *
	 */
	@Override
	public List<NotifyEvent> perform(GameModel model) {
		List<Player> winners = new ArrayList<Player>();
		List<NotifyEvent> callbackEvent = new ArrayList<NotifyEvent>();
		
		for(Player pl:model.getGamePlayers()) {
			if(pl.getAvatar() instanceof Alien && 
					pl.getAvatar().getIsAlive().equals(LifeState.ALIVE)) {
				pl.getAvatar().setIsWinner(EndState.WINNER);
				winners.add(pl);
			}
		}
		
		model.setGameState(GameState.CLOSING);
		callbackEvent.add(new EventNotifyAliensWin(model.getActualTurn().getCurrentPlayer(), winners, areWinner));
		callbackEvent.add(new EventNotifyClosingTopic(model.getActualTurn().getCurrentPlayer()));
		callbackEvent.add(new EventClosingGame(model.getActualTurn().getCurrentPlayer(), model.areThereOtherHumans()));
		return callbackEvent;
	}
}
