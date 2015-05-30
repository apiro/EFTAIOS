package it.polimi.ingsw.cg_38.controller;

import it.polimi.ingsw.cg_38.controller.event.Event;
import it.polimi.ingsw.cg_38.controller.event.SplashEvent;
import it.polimi.ingsw.cg_38.model.Map;
import it.polimi.ingsw.cg_38.notifyEvent.EventAddedToGame;
import it.polimi.ingsw.cg_38.notifyEvent.EventNotifyEnvironment;

public interface ClientInterface {

	public void renderSplashView(EventNotifyEnvironment evt);
	
	public Event askUserCommands();
	
	public void trasmit(Event evt);

	public String askForProtocol();
	
	public SplashEvent askForSplashEvent();
	
	public int askForPort();
	
	public void renderErrorMessage();
	
}
