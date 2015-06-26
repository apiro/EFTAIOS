package it.polimi.ingsw.cg_38.controller.action;

import java.util.ArrayList;
import java.util.List;

import it.polimi.ingsw.cg_38.controller.GameState;
import it.polimi.ingsw.cg_38.controller.TurnTimerController;
import it.polimi.ingsw.cg_38.controller.event.GameEvent;
import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.controller.notifyEvent.EventClosingGame;
import it.polimi.ingsw.cg_38.controller.notifyEvent.EventNotifyAliensWin;
import it.polimi.ingsw.cg_38.controller.notifyEvent.EventNotifyClosingTopic;
import it.polimi.ingsw.cg_38.controller.notifyEvent.EventNotifyTurn;
import it.polimi.ingsw.cg_38.model.Alien;
import it.polimi.ingsw.cg_38.model.EndState;
import it.polimi.ingsw.cg_38.model.GameModel;
import it.polimi.ingsw.cg_38.model.Human;
import it.polimi.ingsw.cg_38.model.Player;
import it.polimi.ingsw.cg_38.model.Turn;

/** identifica l'azione di richiesa di fine del turno */
public class FinishTurn extends GameAction {

	private static final long serialVersionUID = 1L;

	/** invoca il costruttore della superclasse
	 * 
	 * @param evt evento di gioco che ha generato l'azione
	 */
	public FinishTurn(GameEvent evt) {
		super(evt.getGenerator());
		
	}

	/** verifica se è possibile performare l'azione sul model
	 * 
	 * @param model gameModel sul quale viene fatta la verifica
	 * @return true se è possibile performare l'azione sul model
	 */
	@Override
	public Boolean isPossible(GameModel model){
		super.isPossible(model);
		if(super.isPossible(model) && model.getActualTurn().getHasMoved()) {
			return true;
		} return false;
	}
	
	/** viene verificato lo stato del turno e del model. Se non ci sono umani ancora in gioco vengono 
	 * generati gli eventi di notifica di vittoria degli alieni, altrimenti viene creato un nuovo turno 
	 * del giocatore successivo e vengono generati i relativi eventi di notifica
	 * 
	 * @param model gameModel sul quale performare l'azione
	 * @return ritorna la lista di eventi di notifica generati
	 */
	@Override
	public List<NotifyEvent> perform(GameModel model) {
		List<NotifyEvent> callbackEvent = new ArrayList<NotifyEvent>();
		if(model.getActualTurn().getHasMoved()) {
			if(model.getActualTurn().getCurrentPlayer().getAvatar() instanceof Human 
					&& model.getActualTurn().getCurrentPlayer().getAvatar().getIsPowered()) {
				model.getActualTurn().getCurrentPlayer().getAvatar().setIsPowered(false);
			}
			model.getActualTurn().getCurrentPlayer().finishTurn();
			
			/*if(model.getActualTurn().getCurrentPlayer().getNumTurniGiocati() == 39 &&
					model.getNextPlayer().getNumTurniGiocati()+1 == 40) {
				model.setGameState(GameState.CLOSING);
				callbackEvent.add(new EventNotifyAliensWin(model.getActualTurn().getCurrentPlayer(), winners, true));
				callbackEvent.add(new EventNotifyClosingTopic(model.getActualTurn().getCurrentPlayer()));
				callbackEvent.add(new EventClosingGame(model.getActualTurn().getCurrentPlayer(), model.areThereOtherHumans()));
				return callbackEvent;
			}*/
			if(model.getGamePlayers().size() == 0) {
				callbackEvent.add(new EventClosingGame(model.getActualTurn().getCurrentPlayer(), model.areThereOtherHumans()));
				return callbackEvent;
			}
			if(!model.areThereOtherHumans() || 
					(model.getActualTurn().getCurrentPlayer().getNumTurniGiocati() == 39 &&
					model.getNextPlayer().getNumTurniGiocati()+1 == 40)) {
				List<Player> winners = new ArrayList<Player>();
				model.setGameState(GameState.CLOSING);
				for(Player pl:model.getGamePlayers()) {
					if(pl.getAvatar() instanceof Alien) {
						pl.getAvatar().setIsWinner(EndState.WINNER);
						winners.add(pl);
					}
				}
				callbackEvent.add(new EventNotifyAliensWin(model.getActualTurn().getCurrentPlayer(), winners, true));
				callbackEvent.add(new EventNotifyClosingTopic(model.getActualTurn().getCurrentPlayer()));
				callbackEvent.add(new EventClosingGame(model.getActualTurn().getCurrentPlayer(), model.areThereOtherHumans()));
				return callbackEvent;
			}
			Turn newTurn = new Turn(model.getNextPlayer());
	    	model.setActualTurn(newTurn);
	    	
	    	callbackEvent.add(new EventNotifyTurn(model.getActualTurn().getCurrentPlayer()));
	    	System.out.println(model.getActualTurn().getCurrentPlayer().getName() + " " + model.getActualTurn().getCurrentPlayer().getNumTurniGiocati());
		} else {
			callbackEvent.add(new EventNotifyTurn(model.getActualTurn().getCurrentPlayer()));
		}
		return callbackEvent;
	}
}
