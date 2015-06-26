package it.polimi.ingsw.cg_38.controller.event;

import static org.junit.Assert.*;

import javax.xml.parsers.ParserConfigurationException;

import it.polimi.ingsw.cg_38.controller.gameEvent.EventMove;
import it.polimi.ingsw.cg_38.controller.notifyEvent.EventMoved;
import it.polimi.ingsw.cg_38.model.Player;
import it.polimi.ingsw.cg_38.model.map.Dangerous;
import it.polimi.ingsw.cg_38.model.map.Sector;

import org.junit.Before;
import org.junit.Test;

/** contiene i test degli eventi */
public class ControllerEventTest {
	
	EventMove move;
	
	EventMoved evtMoved;
	
	Player player1;
	
	Sector sector1;
	
	String moved;

	@Before
	public void init() throws ParserConfigurationException, Exception {
		
		player1 = new Player("scimmiu");
		
		sector1 = new Dangerous();
		
		moved = "yes";
		
		move = new EventMove(player1 , sector1);
		
		evtMoved = new EventMoved(player1 , moved);
	}
	
	@Test
	public void test() {
		
		assertEquals(move.getNotifyEventIsBroadcast() , false);
		assertEquals(evtMoved.getType() , NotifyEventType.MOVED);	

	}
}
