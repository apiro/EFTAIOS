package it.polimi.ingsw.cg_38.notifyEvent;

import java.util.ArrayList;

import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.controller.event.NotifyEventType;
import it.polimi.ingsw.cg_38.model.Player;

public class EventAttacked extends NotifyEvent {

	private static final long serialVersionUID = 1L;
	
	private Boolean areYouPowered;
	private Boolean areThereOtherHumans;
	public Boolean getAreThereOtherHumans() {
		return areThereOtherHumans;
	}

	public void setAreThereOtherHumans(Boolean areThereOtherHumans) {
		this.areThereOtherHumans = areThereOtherHumans;
	}

	private ArrayList<Player> killed = new ArrayList<Player>();
	
	public Boolean getAreYouPowered() {
		return areYouPowered;
	}

	public void setAreYouPowered(Boolean areYouPowered) {
		this.areYouPowered = areYouPowered;
	}

	public EventAttacked(Player generator, ArrayList<Player> killed, Boolean areThereOtherHumans) {
		super(generator, true);
		this.setAreThereOtherHumans(areThereOtherHumans);
		super.setType(NotifyEventType.Attacked);
		this.setKilled(killed);
		this.setAreYouPowered(generator.getAvatar().getIsPowered());
	}

	public ArrayList<Player> getKilled() {
		return killed;
	}

	public void setKilled(ArrayList<Player> killed) {
		this.killed = killed;
	}
}
