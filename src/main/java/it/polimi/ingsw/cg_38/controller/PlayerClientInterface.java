package it.polimi.ingsw.cg_38.controller;

import it.polimi.ingsw.cg_38.controller.event.Event;
import it.polimi.ingsw.cg_38.gameEvent.EventSubscribe;

public interface PlayerClientInterface {

	public void initPlayer();
	
	public String askForTypeOfConnection();
	
	public EventSubscribe askForEventSubscribe();
	
	public void process(Event msg);
	
	public void run();
	
}
