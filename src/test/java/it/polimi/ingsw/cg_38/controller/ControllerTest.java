package it.polimi.ingsw.cg_38.controller;

import static org.junit.Assert.*;
import it.polimi.ingsw.cg_38.client.ClientView;
import it.polimi.ingsw.cg_38.controller.connection.RMIRemoteObjectDetails;
import it.polimi.ingsw.cg_38.controller.connection.RegistrationView;
import it.polimi.ingsw.cg_38.controller.event.Event;
import it.polimi.ingsw.cg_38.controller.gameEvent.EventDraw;
import it.polimi.ingsw.cg_38.model.Player;

import java.util.concurrent.ConcurrentLinkedQueue;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.Before;
import org.junit.Test;

public class ControllerTest {
	
	ConcurrentLinkedQueue<Event> queue;
	ClientView clientView;
	GameController gc;
	GameController gc2;
	WaitingRoomController waitingRoom;
	ServerController serverController;
	RegistrationView registrationView;
	RMIRemoteObjectDetails details;
	
	Boolean boolean1;
	Boolean boolean2;
	
	String type;
	String topic;
	String RMI_ID;
	
	EventDraw draw;
	
	Player player1;

	@Before
	public void init() throws ParserConfigurationException, Exception {

		type = "Galilei";
		topic = "room1"; 
		RMI_ID = "test";
		gc = new GameController(type , topic);
		player1 = new Player("Robben");
		draw = new EventDraw(player1);
		queue = new ConcurrentLinkedQueue<Event>();
		queue.add(draw);
		clientView = new ClientView(queue);
		waitingRoom = new WaitingRoomController(gc);
		serverController = new ServerController();
		gc2 = serverController.initAndStartANewGame(type, topic);
		registrationView = new RegistrationView(serverController);
		boolean1 = registrationView.isLoginValid("test");
		boolean2 = registrationView.isLoginValid("albi");
		details = new RMIRemoteObjectDetails(RMI_ID);
		
	}

	@Test
	public void test() {
		
		assertEquals(clientView.getQueue().size() , 1);
		assertEquals(waitingRoom.getGc() , gc);
		assertEquals(gc2.getTopic() , topic);
		serverController.setToDispatch(queue);
		assertEquals(serverController.getToDispatch() , queue);
		assertEquals(boolean1 , true);
		assertEquals(boolean2 , false);
		assertEquals(details.getRMI_ID() , RMI_ID);
		
		
		
	}

}