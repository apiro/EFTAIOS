package it.polimi.ingsw.cg_38.controller.action;

import it.polimi.ingsw.cg_38.controller.GameState;
import it.polimi.ingsw.cg_38.controller.event.Event;
import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.controller.notifyEvent.EventClosingGame;
import it.polimi.ingsw.cg_38.controller.notifyEvent.EventNotifyClosingTopic;
import it.polimi.ingsw.cg_38.controller.notifyEvent.EventNotifyHumanWin;
import it.polimi.ingsw.cg_38.model.EndState;
import it.polimi.ingsw.cg_38.model.GameModel;
import it.polimi.ingsw.cg_38.model.map.Hatch;

import java.util.ArrayList;
import java.util.List;

/** identifica l'azione di vittoria di un avatar di tipo umano */
public class HumanWin extends GameAction {

	private static final long serialVersionUID = 1L;

	/** invoca il costruttore della superclasse 
	 * 
	 * @param evt evento che ha generato l'azione
	 */
	public HumanWin(Event evt) {
		super(evt.getGenerator());
	}

	/** viene settato a false l'attributo setIsOpen del settore scialuppa sul quale si trova l'avatar
	 * e viene modifica lo stato dell'avatar. Inoltre se non ci sono altri umani in gioco vengono generati
	 * i relativi eventi di notifica
	 * 
	 * @param model gameModel sul quale performare l'azione
	 * @return lista di eventi di notifica generati
	 */
	@Override
	public List<NotifyEvent> perform(GameModel model) {
		
		List<NotifyEvent> callbackEvent = new ArrayList<NotifyEvent>();
		model.getActualTurn().getCurrentPlayer().getAvatar().setIsWinner(EndState.WINNER);
		Hatch htc = ((Hatch)model.getActualTurn().getCurrentPlayer().getAvatar().getCurrentSector());
		
		((Hatch)model.getGameMap().searchSectorByCoordinates(htc.getRow(), htc.getCol())).setIsOpen(false);
		callbackEvent.add(new EventNotifyHumanWin(model.getActualTurn().getCurrentPlayer()));
		if(!model.areThereOtherHumans()) {
			callbackEvent.add(new EventNotifyClosingTopic(model.getActualTurn().getCurrentPlayer()));
			callbackEvent.add(new EventClosingGame(model.getActualTurn().getCurrentPlayer(), model.areThereOtherHumans()));
			model.setGameState(GameState.CLOSING);
		}
		
		return callbackEvent;
	}
}
