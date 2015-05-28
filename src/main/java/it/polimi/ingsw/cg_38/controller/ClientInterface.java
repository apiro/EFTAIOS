package it.polimi.ingsw.cg_38.controller;

import it.polimi.ingsw.cg_38.controller.event.Event;
import it.polimi.ingsw.cg_38.controller.event.SplashEvent;

public interface ClientInterface {

	public void renderSplashView();
	
	public void renderEvent(Event evt);
	
	public Event askUserCommands();
	
	public void trasmit(Event evt);

	public String askForProtocol();
	
	public SplashEvent askForSplashEvent();
	
	public int askForPort();
	
}
