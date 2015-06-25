package it.polimi.ingsw.cg_38.client.gui;

import java.lang.reflect.InvocationTargetException;
import java.util.Queue;

import it.polimi.ingsw.cg_38.controller.event.Event;
import it.polimi.ingsw.cg_38.controller.logger.Logger;

import javax.swing.SwingUtilities;

public class UXStarter {

	private UX ux;
	private Logger myLogger;
	private Queue<Event> toSend;
	
	public UXStarter(final Object[] params) {
		
		this.myLogger = (Logger)params[0];
		this.toSend = ((Queue<Event>)params[1]);
		
		try {
			SwingUtilities.invokeAndWait(new Runnable() 
			{
				public void run() 
				{
					ux = new UX(myLogger, toSend);
					myLogger.print("Graphic UX loaded ...");
				}
			});
		} catch (InvocationTargetException e) {
			myLogger.print("InvocationTargetException in UXStarter ...");
		} catch (InterruptedException e) {
			myLogger.print("Interrupted Exception in UXStarter ...");
		}
	}

	public UX getUx() {
		return ux;
	}	
}
