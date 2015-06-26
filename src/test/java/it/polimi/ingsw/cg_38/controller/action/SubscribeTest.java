package it.polimi.ingsw.cg_38.controller.action;

import static org.junit.Assert.*;
import it.polimi.ingsw.cg_38.controller.GameController;
import it.polimi.ingsw.cg_38.controller.ServerController;
import it.polimi.ingsw.cg_38.controller.connection.Communicator;
import it.polimi.ingsw.cg_38.controller.connection.SocketCommunicator;
import it.polimi.ingsw.cg_38.controller.gameEvent.EventSubscribe;
import it.polimi.ingsw.cg_38.controller.notifyEvent.EventAddedToGame;
import it.polimi.ingsw.cg_38.controller.notifyEvent.EventNotifyTopics;
import it.polimi.ingsw.cg_38.model.Alien;
import it.polimi.ingsw.cg_38.model.Avatar;
import it.polimi.ingsw.cg_38.model.GameModel;
import it.polimi.ingsw.cg_38.model.Human;
import it.polimi.ingsw.cg_38.model.Name;
import it.polimi.ingsw.cg_38.model.Player;
import it.polimi.ingsw.cg_38.model.Turn;
import it.polimi.ingsw.cg_38.model.map.Dangerous;
import it.polimi.ingsw.cg_38.model.map.Hatch;
import it.polimi.ingsw.cg_38.model.map.HumanStartingPoint;
import it.polimi.ingsw.cg_38.model.map.Safe;
import it.polimi.ingsw.cg_38.model.map.Sector;

import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import org.junit.Before;
import org.junit.Test;

/** contiene alcuni test della classe Subscribe */
public class SubscribeTest {
	
	Subscribe subscribe;
	Subscribe subscribe2;
	
	EventSubscribe evtSubscribe;
	EventSubscribe evtSubscribe2;
	
	EventNotifyTopics evtNotifyTopics;
	EventAddedToGame evtAddedToGame;
	
	Player player1;
	Player player2;
	Player player3;
	Player player4;
	Player player5;
	Player player6;
	Player player7;
	
	Turn turn1;
	Turn turn2;
	Turn turn3;
	Turn turn4;
	Turn turn5;
	Turn turn6;
	Turn turn7;
	
	List<Player> addPlayers;
	List<String> topics;
	
	Sector sector1;
	Sector sector2;
	Sector sector3;
	Sector sector4;
	Sector sector5;
	Sector sector6;
	Hatch sector7;
	
	Avatar avatar1;
	Avatar avatar2;
	Avatar avatar3;
	Avatar avatar4;
	Avatar avatar5;
	Avatar avatar6;
	Avatar avatar7;
	
	GameModel model1;
	
	ServerController server;
	GameController gc;
	Communicator c;
	

	@Before
	public void init() throws ParserConfigurationException, Exception {
		
		player1 = new Player("albi");
		player2 = new Player("scimmiu");
		player2.getAvatar();
		player3 = new Player("shane");
		player4 = new Player("diffi");
		player5 = new Player("reda");
		player6 = new Player("piccio");
		player7 = new Player("poldo");
		
		model1 = new GameModel("Galvani");
		
		gc = new GameController("Galilei" , "room1");
		server = new ServerController();
		server.getTopics().put("room1", gc);
		c = new SocketCommunicator(null);
		
		turn1 = new Turn(player1);
		turn1.setHasMoved(true);
		turn2 = new Turn(player2);
		turn2.setHasMoved(true);
		turn3 = new Turn(player3);
		turn4 = new Turn(player4);
		turn5 = new Turn(player5);
		turn6 = new Turn(player6);
		turn7 = new Turn(player7);
		
		sector1 = new Dangerous();
		sector1.setCol(5);
		sector1.setRow(11);
		sector2 = model1.getGameMap().getTable().get(3).get(6);
		sector3 = model1.getGameMap().getTable().get(3).get(5);
		sector4 = new Hatch();
		sector5 = new HumanStartingPoint();
		sector6 = new Safe();
		sector7 = new Hatch();
		
		avatar1 = new Alien(Name.ALIEN1 , sector2);
		avatar2 = new Alien(Name.ALIEN2 , sector2);
		avatar3 = new Human(Name.HUMAN1 , sector2);
		avatar4 = new Human(Name.HUMAN2 , sector4);
		avatar5 = new Human(Name.HUMAN3 , sector5);
		avatar6 = new Alien(Name.ALIEN3 , sector4);
		avatar7 = new Human(Name.HUMAN4 , sector7);

		addPlayers = new ArrayList<Player>();
		
		topics = new ArrayList<String>();
		topics.add("room1");
		topics.add("room2");
		
		evtSubscribe = new EventSubscribe(player1 , "room1" , "Galilei");
		evtSubscribe2 = new EventSubscribe(player1 , "room2" , "Galvani");
		
		subscribe = new Subscribe(evtSubscribe);
		subscribe2 = new Subscribe(evtSubscribe2);
		
		evtNotifyTopics = new EventNotifyTopics(player1 , true , topics);
		
		player1.setAvatar(avatar1);
		player2.setAvatar(avatar2);
		player3.setAvatar(avatar3);
		player4.setAvatar(avatar4);
		player5.setAvatar(avatar5);
		player6.setAvatar(avatar6);
		player7.setAvatar(avatar7);
		addPlayers.add(player1);
		addPlayers.add(player2);
		addPlayers.add(player3);
		addPlayers.add(player4);
		addPlayers.add(player5);
		addPlayers.add(player6);
		addPlayers.add(player7);
		model1.setGamePlayers(addPlayers);
	}
	
	@Test
	public void test() {

		/* verifica il corretto funzionamento dell'azione di subscribe */
		assertEquals(subscribe.getTopic() , "room1");
		assertEquals(subscribe.getTypeMap() , "Galilei");
		assertTrue(subscribe.isPossible(server));
		assertTrue(!subscribe2.isPossible(server));
	}

}
