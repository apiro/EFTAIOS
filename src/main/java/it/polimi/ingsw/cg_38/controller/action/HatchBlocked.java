package it.polimi.ingsw.cg_38.controller.action;

import it.polimi.ingsw.cg_38.controller.event.Event;
import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.controller.notifyEvent.EventHatchBlocked;
import it.polimi.ingsw.cg_38.model.GameModel;
import it.polimi.ingsw.cg_38.model.map.Hatch;

import java.util.ArrayList;
import java.util.List;

/** identifica l'azione di bloccaggio del settore scialuppa */
public class HatchBlocked extends GameAction {

	private static final long serialVersionUID = 1L;

	/** invoca il costruttore della superclasse */
	public HatchBlocked(Event evt) {
		super(evt.getGenerator());
	}

	/** viene modificata a false il valore setIsOpen relativo al settore scialuppa
	 * 
	 * @param model gameModel sul quale performare l'azione
	 * @return lista di eventi di notifica generati
	 */
	@Override
	public List<NotifyEvent> perform(GameModel model) {
		List<NotifyEvent> callbackEvent = new ArrayList<NotifyEvent>();
		Hatch htc = ((Hatch)model.getActualTurn().getCurrentPlayer().getAvatar().getCurrentSector());
		
		((Hatch)model.getGameMap().searchSectorByCoordinates(htc.getRow(), htc.getCol())).setIsOpen(false);
		callbackEvent.add(new EventHatchBlocked(model.getActualTurn().getCurrentPlayer(), 
   						(Hatch)model.getActualTurn().getCurrentPlayer().getAvatar().getCurrentSector()));
		return callbackEvent;
	}

}
