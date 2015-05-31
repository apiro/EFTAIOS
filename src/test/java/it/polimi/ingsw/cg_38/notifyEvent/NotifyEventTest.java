package it.polimi.ingsw.cg_38.notifyEvent;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.ParserConfigurationException;

import it.polimi.ingsw.cg_38.controller.event.NotifyEventType;
import it.polimi.ingsw.cg_38.model.Alien;
import it.polimi.ingsw.cg_38.model.Avatar;
import it.polimi.ingsw.cg_38.model.Card;
import it.polimi.ingsw.cg_38.model.HatchCard;
import it.polimi.ingsw.cg_38.model.HatchCardType;
import it.polimi.ingsw.cg_38.model.Map;
import it.polimi.ingsw.cg_38.model.Name;
import it.polimi.ingsw.cg_38.model.ObjectCard;
import it.polimi.ingsw.cg_38.model.ObjectCardType;
import it.polimi.ingsw.cg_38.model.Player;
import it.polimi.ingsw.cg_38.model.Sector;

import org.junit.Before;
import org.junit.Test;

public class NotifyEventTest {
	
	EventAddedToGame addedToGame;
	EventAttacked attacked;
	EventCardUsed cardUsed;
	EventDeclarePosition declarePosition;
	EventDrown drown;
	EventMoved moved;
	EventNotifyEnvironment notifyEnvironment;
	EventNotifyPlayerState notifyPlayerState;
	EventNotifyTurn notifyTurn;
	EventNotYourTurn notYourTurn;
	EventShowTopics showTopics;
	
	Player player1;
	Player player2;
	
	Avatar avatar1;
	
	Name name1;
	
	Sector sector1;
	
	HashMap<String, Sector> toDeclare2;
	
	ArrayList<Player> toDeclare;
	ArrayList<String> topics;
	
	Map map;
	
	Card card1;
	ObjectCard card2;
	
	String moved1;
	
	boolean added;
	boolean broadcast;
	boolean performed;
	boolean winner;
	
	@Before
	public void init() throws ParserConfigurationException, Exception{
		
		player1 = new Player("scimmiu");
		player2 = new Player("albi");
		

		name1 = Name.Alien1;
		
		avatar1 = new Alien(name1 , sector1);
		avatar1.setIsPowered(true);
		
		player2.setAvatar(avatar1);
		
		card1 = new HatchCard(HatchCardType.Red);
		card2 = new ObjectCard(ObjectCardType.Adrenaline);
		
		map = new Map();
				
		added = true;
		broadcast = true;
		performed = true;
		winner = true;
		
		toDeclare2 = new HashMap<String, Sector>();
		
		toDeclare = new ArrayList<Player>();
		topics = new ArrayList<String>();
		
		addedToGame =  new EventAddedToGame(player1 , added , broadcast);
		attacked = new EventAttacked(player2);
		cardUsed = new EventCardUsed(player1 , performed);
		declarePosition = new EventDeclarePosition(player1 , toDeclare);
		declarePosition.setToDeclare(toDeclare2);
		drown = new EventDrown(player1 , card2 , card1);
		moved = new EventMoved(player1 , moved1);
		notifyEnvironment = new EventNotifyEnvironment(toDeclare, map);
		notifyPlayerState = new EventNotifyPlayerState(player1, winner);
		notifyTurn = new EventNotifyTurn(player1);
		notYourTurn = new EventNotYourTurn(player1);
		showTopics = new EventShowTopics(topics);
		
	}
	
	
	@Test
	public void test() {
		
		assertEquals(addedToGame.getGenerator() , player1);
		assertEquals(addedToGame.getType() , NotifyEventType.Added);
		assertEquals(addedToGame.getAdded() , added);
		assertEquals(addedToGame.isBroadcast() , broadcast);
		assertEquals(addedToGame.toString() , "EventAddedToGame [added=" + added + " player= " + player1.getName() + "]");	
	
		assertEquals(attacked.getAreYouPowered() , avatar1.getIsPowered());
		
		assertEquals(cardUsed.getPerformed() , performed);
		
		assertEquals(declarePosition.getToDeclare() , toDeclare2);
		
		assertEquals(drown.getDrown() , card1);
		assertEquals(drown.getAdded() , card2);
		
		assertEquals(moved.getMoved() , moved1);
		
		assertEquals(notifyEnvironment.getMap() , map);
		assertEquals(notifyEnvironment.getMappingPlayerAvatar() , toDeclare);
		assertEquals(notifyEnvironment.toString() , "EventNotifyEnvironment [ ]");
		
		assertEquals(notifyPlayerState.getPlayer() , player1);
		assertEquals(notifyPlayerState.getWinner() , winner);
		
		assertEquals(notifyTurn.getPlayerOfTurn() , player1);
		assertEquals(notifyTurn.toString() , "EventNotifyTurn [playerOfTurn= " + player1.getName() + "]");
		
		assertEquals(notYourTurn.getMessage() , "This is not your turn !");
		
		assertEquals(showTopics.getTopics() , topics);		
	}

}
