package it.polimi.ingsw.cg_38.controller.action;
import java.util.List;

import it.polimi.ingsw.cg_38.controller.GameState;
import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import  it.polimi.ingsw.cg_38.model.*;

/** corrisponde ad una azione che il giocatore vuole effettuare in un determinato turno performata
 *  nel gameModel relativo al giocatore */
public abstract class GameAction extends Action {

	private static final long serialVersionUID = 1L;
    
    public abstract List<NotifyEvent> perform(GameModel model);

	/** invoca il costruttore della superclasse
	 * 
	 * @param player giocatore che vuole effettuare l'azione
	 */
	public GameAction(Player player) {
    	super(player);
    }

	/** verifica che il model sia nello stato di gioco in esecuzione
	 * 
	 * @param model gameModel sul quale viene verificata la possibilita di eseguire l'azione
	 * @return true se è il model è in esecuzione
	 */
    public Boolean isPossible(GameModel model) {
    	if(model.getGameState().equals(GameState.RUNNING)) {
    		return true;
    	} else return false;
    }

    /** indica il tipo di avatar del giocatore che ha generato l'azione
     * 
     * @param model gameModel riferito al giocatore
     * @return nome del tipo di avatar
     */
	public String currentAvatarType(GameModel model){
		if(model.getActualTurn().getCurrentPlayer().getAvatar() instanceof Alien) {
			return "Alien";
		} else {
			return "Human";
		}
	}
}