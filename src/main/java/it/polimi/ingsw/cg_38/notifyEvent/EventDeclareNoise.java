package it.polimi.ingsw.cg_38.notifyEvent;

import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.model.Player;
import it.polimi.ingsw.cg_38.model.Sector;

public class EventDeclareNoise extends NotifyEvent {
	
	private static final long serialVersionUID = 1L;
	
	private Sector sectorToNoise;
	
	public EventDeclareNoise(Player generator , Sector sector){
		super(generator , true);
		this.setSectorToNoise(sector);
		
	}

	public Sector getSectorToNoise() {
		return sectorToNoise;
	}

	public void setSectorToNoise(Sector sectorToNoise) {
		this.sectorToNoise = sectorToNoise;
	}

}
