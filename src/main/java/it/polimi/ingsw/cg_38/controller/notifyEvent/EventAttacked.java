package it.polimi.ingsw.cg_38.controller.notifyEvent;

import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.controller.event.NotifyEventType;
import it.polimi.ingsw.cg_38.model.Player;

/** rappresenta l'evento di avvenuta dell'attacco */
public class EventAttacked extends NotifyEvent {

	private static final long serialVersionUID = 1L;
	
	/** indica se il giocatore é potenziato */
	private Boolean areYouPowered;
	
	/** indica se ci sono ancora umani in gioco */
	private Boolean areThereOtherHumans;
	
	/** viene invocato il costrutto della superclasse e vengo settati i vari dati
	 * 
	 * @param generator giocatore che ha generato l'evento
	 * @param areThereOtherHumans true se ci sono altri umani in gioco
	 */
	public EventAttacked(Player generator, Boolean areThereOtherHumans) {
		super(generator, false);
		this.setAreThereOtherHumans(areThereOtherHumans);
		super.setType(NotifyEventType.ATTACKED);
		this.setAreYouPowered(generator.getAvatar().getIsPowered());
	}
	
	public Boolean getAreThereOtherHumans() {
		return areThereOtherHumans;
	}

	public void setAreThereOtherHumans(Boolean areThereOtherHumans) {
		this.areThereOtherHumans = areThereOtherHumans;
	}
	
	public Boolean getAreYouPowered() {
		return areYouPowered;
	}

	public void setAreYouPowered(Boolean areYouPowered) {
		this.areYouPowered = areYouPowered;
	}



}
