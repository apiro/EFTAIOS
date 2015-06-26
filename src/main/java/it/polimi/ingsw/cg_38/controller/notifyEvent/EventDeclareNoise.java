package it.polimi.ingsw.cg_38.controller.notifyEvent;

import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.controller.event.NotifyEventType;
import it.polimi.ingsw.cg_38.model.Player;
import it.polimi.ingsw.cg_38.model.map.Sector;

/** rappresenta l'evento di dichiarazione del rumore */
public class EventDeclareNoise extends NotifyEvent {
	
	private static final long serialVersionUID = 1L;
	
	/** contiene il settore sul quale performare il rumore */
	private Sector sectorToNoise;
	
	/** invoca il costruttore della superclasse e setta i vari dati
	 * 
	 * @param generator giocatore che ha generato l'evento
	 * @param sector settore sul quale fare rumore
	 */
	public EventDeclareNoise(Player generator , Sector sector){
		super(generator , true);
		this.setSectorToNoise(sector);
		super.setType(NotifyEventType.NOISE);
		
	}

	public Sector getSectorToNoise() {
		return sectorToNoise;
	}

	public void setSectorToNoise(Sector sectorToNoise) {
		this.sectorToNoise = sectorToNoise;
	}
}

