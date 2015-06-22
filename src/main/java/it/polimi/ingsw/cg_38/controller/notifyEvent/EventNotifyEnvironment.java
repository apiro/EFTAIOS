package it.polimi.ingsw.cg_38.controller.notifyEvent;

import java.util.ArrayList;

import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.controller.event.NotifyEventType;
import it.polimi.ingsw.cg_38.model.Player;
import it.polimi.ingsw.cg_38.model.map.Map;

public class EventNotifyEnvironment extends NotifyEvent {

	@Override
	public String toString() {
		return "EventNotifyEnvironment [ ]";
	}
	public EventNotifyEnvironment(ArrayList<Player> mapping, Map map) {
		super(null, true);
		this.setMappingPlayerAvatar(mapping);
		this.setMap(map);
		this.setType(NotifyEventType.environment);
	}
	
	private static final long serialVersionUID = 1L;
	private ArrayList<Player> mappingPlayerAvatar = new ArrayList<Player>();
	private Map map;
	
	public Map getMap() {
		return map;
	}
	public void setMap(Map map) {
		this.map = map;
	}
	public ArrayList<Player> getMappingPlayerAvatar() {
		return mappingPlayerAvatar;
	}
	public void setMappingPlayerAvatar(ArrayList<Player> mappingPlayerAvatar) {
		this.mappingPlayerAvatar = mappingPlayerAvatar;
	}
	
	
}