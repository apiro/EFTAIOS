package it.polimi.ingsw.cg_38.controller.action;

import it.polimi.ingsw.cg_38.controller.event.GameEvent;
import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.model.GameModel;

import java.util.ArrayList;
import java.util.List;

/** identifica l'azione di forzatura di fine turno */
public class ForceFinishTurn extends FinishTurn {

	private static final long serialVersionUID = 1L;

	/** invoca il costruttore della superclasse
	 * 
	 * @param evt evento di gioco che ha generato l'azione
	 */
	public ForceFinishTurn(GameEvent evt) {
		super(evt);
	}
	
	/** sempre true in quanto è sempe possibile performare questo tipo di azione */
	public Boolean isPossible(GameModel model) {
		return true;
	}

	/** viene modifica lo stato del turno in modo da permetterne la fine
	 * 
	 *  @param model gameModel sul quale performare l'azione
	 *  @return ritorna la lista degli eventi di notifica generati dalla perform della superclasse
	 */
	@Override
	public List<NotifyEvent> perform(GameModel model) {
		model.getActualTurn().setHasMoved(true);
		model.getActualTurn().setHasDraw(true);
		model.getActualTurn().setHasAttacked(true);
		model.getActualTurn().setHasUsedObjectCard(true);
		
		List<NotifyEvent> callbackEvent = new ArrayList<NotifyEvent>();
		
		callbackEvent = super.perform(model);
		
		return callbackEvent;
	}

}
