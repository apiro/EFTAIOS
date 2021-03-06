package it.polimi.ingsw.cg_38.controller.gameEvent;

import it.polimi.ingsw.cg_38.controller.event.GameEvent;
import it.polimi.ingsw.cg_38.controller.event.GameEventType;
import it.polimi.ingsw.cg_38.model.Player;
import it.polimi.ingsw.cg_38.model.map.Sector;

/** rappresenta l'evento di ruomore in un qualsiasi settore */
public class EventNoiseRandSect extends GameEvent {

	private static final long serialVersionUID = 1L;
	
	/** contiene il settore sul quale il giocatore ha scelto di fare rumore */
	private Sector toNoise;
	
	/** invoca il costruttore della superclasse e setta i vari dati
	 * 
	 * @param generator giocatore che ha generato l'evento
	 * @param toNoise settore nel quale fare rumore
	 */
	public EventNoiseRandSect(Player generator, Sector toNoise) {
		super(generator, true);
		super.setType(GameEventType.NOISERANDSECT);
		this.setToNoise(toNoise);
	}
	public Sector getToNoise() {
		return toNoise;
	}

	public void setToNoise(Sector toNoise) {
		this.toNoise = toNoise;
	}

}
