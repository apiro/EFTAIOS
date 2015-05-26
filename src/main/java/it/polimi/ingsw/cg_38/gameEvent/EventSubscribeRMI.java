package it.polimi.ingsw.cg_38.gameEvent;

import it.polimi.ingsw.cg_38.controller.event.GameEvent;
import it.polimi.ingsw.cg_38.model.Player;

import java.io.Serializable;

public class EventSubscribeRMI extends EventSubscribe implements Serializable{
	
	public String RMI_ID;

	public EventSubscribeRMI(Player generator,  String room, String map, String RMI_ID){
		
		super(generator, room, map);
		this.setRMI_ID(RMI_ID);
		
		
	}

	@Override
	public String toString() {
		return "EventSubscribeRMI [RMI_ID=" + RMI_ID + "]";
	}

	public String getRMI_ID() {
		return RMI_ID;
	}

	public void setRMI_ID(String rMI_ID) {
		RMI_ID = rMI_ID;
	}
}
