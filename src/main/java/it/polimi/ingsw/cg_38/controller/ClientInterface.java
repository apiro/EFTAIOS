package it.polimi.ingsw.cg_38.controller;

import it.polimi.ingsw.cg_38.controller.event.Event;
import it.polimi.ingsw.cg_38.notifyEvent.EventNotifyEnvironment;

public interface ClientInterface {

	public void renderSplashView(EventNotifyEnvironment evt);
	
	public Event askForCoordinates();
	
	public void trasmit(Event evt);

	public void renderEvent();
	
}
