package it.polimi.ingsw.cg_38.controller.notifyEvent;

import java.util.ArrayList;
import java.util.List;

import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.controller.event.NotifyEventType;
import it.polimi.ingsw.cg_38.model.Player;
import it.polimi.ingsw.cg_38.model.map.Map;

/** rappresenta l'evento di notifica dei dati di inizio gioco */
public class EventNotifyEnvironment extends NotifyEvent {

	
	private static final long serialVersionUID = 1L;
	
	/** lista dei giocatori da notificare */
	private List<Player> mappingPlayerAvatar = new ArrayList<Player>();
	
	/** contiene la mappa di gioco */
	private Map map;
	
	/** invoca il costruttore della superclasse e setta i vari dati
	 * 
	 * @param mapping lista dei giocatori da notificare
	 * @param map mappa di gioco
	 */
	public EventNotifyEnvironment(List<Player> mapping, Map map) {
		super(null, true);
		this.setMappingPlayerAvatar(mapping);
		this.setMap(map);
		this.setType(NotifyEventType.ENVIRONMENT);
	}
	
	@Override
	public String toString() {
		return "EventNotifyEnvironment [ ]";
	}
		
	public Map getMap() {
		return map;
	}
	
	public void setMap(Map map) {
		this.map = map;
	}
	
	public List<Player> getMappingPlayerAvatar() {
		return mappingPlayerAvatar;
	}
	
	public void setMappingPlayerAvatar(List<Player> mappingPlayerAvatar) {
		this.mappingPlayerAvatar = mappingPlayerAvatar;
	}
	
	
}
