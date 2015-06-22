package it.polimi.ingsw.cg_38.controller.notifyEvent;

import java.util.ArrayList;

import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.controller.event.NotifyEventType;
import it.polimi.ingsw.cg_38.model.Player;

public class EventDeclarePosition extends NotifyEvent {

	private static final long serialVersionUID = 1L;
	
	private ArrayList<Player> toDeclare = new ArrayList<Player>();
	
	public EventDeclarePosition(Player generator, ArrayList<Player> toDeclare) {
		super(generator, true);
		super.setType(NotifyEventType.DeclarePosition);
		this.setToDeclare(toDeclare);
	}

	public ArrayList<Player> getToDeclare() {
		return toDeclare;
	}

	public void setToDeclare(ArrayList<Player> toDeclare) {
		this.toDeclare = toDeclare;
	}
}
