package it.polimi.ingsw.cg_38.controller.notifyEvent;

import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.controller.event.NotifyEventType;
import it.polimi.ingsw.cg_38.model.Player;

/** rappresenta l'evento di aggiunta al gioco */
public class EventAddedToGame extends NotifyEvent {

	
	private static final long serialVersionUID = 1L;
	
	/** indica se il giocatore è statto correttamente aggiunto al gioco */
	private Boolean added;
	
	private NotifyEventType type;

	public EventAddedToGame(Player generator, Boolean added, Boolean broadcast) {
		super(generator, broadcast);
		this.setAdded(added);
		this.setType(NotifyEventType.ADDED);
	}
	
	@Override
	public NotifyEventType getType() {
		return type;
	}

	@Override
	public void setType(NotifyEventType type) {
		this.type = type;
	}
	
	/** viene invocato il costruttore della superclasse e vengono settati i vari dati
	 * @param generator player che ha generato l'evento
	 * @param added true se il giocatore è stato correttamente aggiunto
	 * @param broadcast true se l'evento è broadcast */

	@Override
	public String toString() {
		return "EventAddedToGame [added=" + added + " player= " + super.getGenerator().getName() + "]";
	}

	private void setAdded(Boolean added) {
		this.added = added;
	}

	public Boolean getAdded() {
		return added;
	}

}
