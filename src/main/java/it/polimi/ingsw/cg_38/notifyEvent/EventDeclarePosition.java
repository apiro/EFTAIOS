package it.polimi.ingsw.cg_38.notifyEvent;

import java.util.ArrayList;
import java.util.HashMap;

import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.controller.event.NotifyEventType;
import it.polimi.ingsw.cg_38.model.Player;
import it.polimi.ingsw.cg_38.model.Sector;

public class EventDeclarePosition extends NotifyEvent {

	private static final long serialVersionUID = 1L;
	
	private ArrayList<Player> toDeclare = new ArrayList<Player>();
	
	public EventDeclarePosition(Player generator, ArrayList<Player> toDeclare) {
		super(generator/*potrei metterlo a null tanto è broadcast !*/, true);
		super.setType(NotifyEventType.DeclarePosition);
		this.setToDeclare(toDeclare);
		/*this.buildMap(toDeclare);*/
	}

	public ArrayList<Player> getToDeclare() {
		return toDeclare;
	}

	public void setToDeclare(ArrayList<Player> toDeclare) {
		this.toDeclare = toDeclare;
	}

	/*private void buildMap(ArrayList<Player> toDeclare2) {
		for(Player pl:toDeclare2){
			this.getToDeclare().put(pl.getName(), pl.getAvatar().getCurrentSector());
		}
	}*/

}
