package it.polimi.ingsw.cg_38.client.notifyAction;

import it.polimi.ingsw.cg_38.client.PlayerClient;
import it.polimi.ingsw.cg_38.controller.event.GameEvent;
import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.controller.gameEvent.EventContinue;

/** rappresenta il rendimento dell'utilizzo della carta di teletrasporto */
public class RenderTeleport extends RenderMoved {

	private static final long serialVersionUID = 1L;

	/** invoca il costruttore della superclasse
	 * 
	 * @param evt evento di notifica che ha generato l'azione
	 */
	public RenderTeleport(NotifyEvent evt) {
		super(evt);
	}

	/** viene comunicato al client che il giocatore è tornato nel settore di partenza degli umani
	 * e vengono aggiornate le carte possedute e i movimenti effettuati
	 * 
	 * @param client client sul quale performare l'azione
	 * @return evento di gioco di continuare l'azione
	 */
	@Override
	public GameEvent render(PlayerClient client) {
		client.getLogger().print("* Teleported in HumanStartingPoint ! *");
		client.setPlayer(evt.getGenerator());
		client.updateCards();
		client.updateMovements();
		return new EventContinue();
	}
}
