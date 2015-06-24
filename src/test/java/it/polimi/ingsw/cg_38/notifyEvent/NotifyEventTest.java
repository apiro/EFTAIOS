package it.polimi.ingsw.cg_38.notifyEvent;

import static org.junit.Assert.*;

import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;

import it.polimi.ingsw.cg_38.controller.action.Action;
import it.polimi.ingsw.cg_38.controller.action.Attack;
import it.polimi.ingsw.cg_38.controller.event.NotifyEventType;
import it.polimi.ingsw.cg_38.controller.gameEvent.EventAttack;
import it.polimi.ingsw.cg_38.controller.notifyEvent.EventAddedToGame;
import it.polimi.ingsw.cg_38.controller.notifyEvent.EventAttacked;
import it.polimi.ingsw.cg_38.controller.notifyEvent.EventCardUsed;
import it.polimi.ingsw.cg_38.controller.notifyEvent.EventClosingGame;
import it.polimi.ingsw.cg_38.controller.notifyEvent.EventDeclarePosition;
import it.polimi.ingsw.cg_38.controller.notifyEvent.EventDrown;
import it.polimi.ingsw.cg_38.controller.notifyEvent.EventFinishedTurn;
import it.polimi.ingsw.cg_38.controller.notifyEvent.EventHatchBlocked;
import it.polimi.ingsw.cg_38.controller.notifyEvent.EventMoved;
import it.polimi.ingsw.cg_38.controller.notifyEvent.EventNotYourTurn;
import it.polimi.ingsw.cg_38.controller.notifyEvent.EventNotifyAliensWin;
import it.polimi.ingsw.cg_38.controller.notifyEvent.EventNotifyChatMessage;
import it.polimi.ingsw.cg_38.controller.notifyEvent.EventNotifyEnvironment;
import it.polimi.ingsw.cg_38.controller.notifyEvent.EventNotifyError;
import it.polimi.ingsw.cg_38.controller.notifyEvent.EventNotifyHumanWin;
import it.polimi.ingsw.cg_38.controller.notifyEvent.EventNotifyPlayerState;
import it.polimi.ingsw.cg_38.controller.notifyEvent.EventNotifyRetired;
import it.polimi.ingsw.cg_38.controller.notifyEvent.EventNotifyTopics;
import it.polimi.ingsw.cg_38.controller.notifyEvent.EventNotifyTurn;
import it.polimi.ingsw.cg_38.controller.notifyEvent.EventRejectCardAlien;
import it.polimi.ingsw.cg_38.controller.notifyEvent.EventRejectCardHuman;
import it.polimi.ingsw.cg_38.controller.notifyEvent.EventSufferAttack;
import it.polimi.ingsw.cg_38.controller.notifyEvent.EventUseDefense;
import it.polimi.ingsw.cg_38.model.Alien;
import it.polimi.ingsw.cg_38.model.Avatar;
import it.polimi.ingsw.cg_38.model.Name;
import it.polimi.ingsw.cg_38.model.Player;
import it.polimi.ingsw.cg_38.model.deck.Card;
import it.polimi.ingsw.cg_38.model.deck.HatchCard;
import it.polimi.ingsw.cg_38.model.deck.HatchCardType;
import it.polimi.ingsw.cg_38.model.deck.ObjectCard;
import it.polimi.ingsw.cg_38.model.deck.ObjectCardType;
import it.polimi.ingsw.cg_38.model.map.Hatch;
import it.polimi.ingsw.cg_38.model.map.Map;
import it.polimi.ingsw.cg_38.model.map.Sector;

import org.junit.Before;
import org.junit.Test;

public class NotifyEventTest {
	
	EventAddedToGame addedToGame;
	EventAttacked attacked;
	EventCardUsed cardUsed;
	EventClosingGame closingGame;
	EventDeclarePosition declarePosition;
	EventDrown drown;
	EventFinishedTurn finishedTurn;
	EventMoved moved;
	EventNotifyEnvironment notifyEnvironment;
	EventNotifyError notifyError;
	EventNotifyPlayerState notifyPlayerState;
	EventNotifyTurn notifyTurn;
	EventNotYourTurn notYourTurn;
	EventSufferAttack sufferAttack;
	EventUseDefense useDefense;
	EventNotifyAliensWin notifyAliens;
	EventNotifyTopics notifyTopics;
	EventHatchBlocked hatchBlocked;
	EventNotifyHumanWin humanWin;
	Action action;
	EventRejectCardAlien rejectCardAlien;
	EventRejectCardHuman rejectCardHuman;
	EventNotifyChatMessage notifyChatMessage;
	EventNotifyRetired notifyRetired;
	/*EventShowTopics showTopics;*/
	
	Player player1;
	Player player2;
	
	Avatar avatar1;
	
	Name name1;
	
	Sector sector1;
	Hatch sector2;
	
	ArrayList<Player> toDeclare2;
	
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
	boolean finished;
	
	@Before
	public void init() throws ParserConfigurationException, Exception{
		
		player1 = new Player("scimmiu");
		player2 = new Player("albi");
		

		name1 = Name.Alien1;
		
		avatar1 = new Alien(name1 , sector1);
		avatar1.setIsPowered(true);
		
		player2.setAvatar(avatar1);
		
		sector2 = new Hatch();
		
		card1 = new HatchCard(HatchCardType.Red);
		card2 = new ObjectCard(ObjectCardType.Adrenaline);
		
		action = new Attack(new EventAttack(player1 , sector1));
		
		map = new Map();
				
		added = true;
		broadcast = true;
		performed = true;
		winner = true;
		finished = true;
		
		toDeclare2 = new ArrayList<Player>();
		
		toDeclare = new ArrayList<Player>();
		topics = new ArrayList<String>();
		
		addedToGame =  new EventAddedToGame(player1 , added , broadcast);
		attacked = new EventAttacked(player2, false);
		cardUsed = new EventCardUsed(player1 , performed , ObjectCardType.AttackCard);
		closingGame = new EventClosingGame(player1 , broadcast);
		declarePosition = new EventDeclarePosition(player1 , toDeclare);
		declarePosition.setToDeclare(toDeclare2);
		drown = new EventDrown(player1 , card2 , card1);
		finishedTurn = new EventFinishedTurn(player1 , finished);
		moved = new EventMoved(player1 , moved1);
		notifyEnvironment = new EventNotifyEnvironment(toDeclare, map);
		notifyError = new EventNotifyError(player1 , action);
		notifyPlayerState = new EventNotifyPlayerState(player1, winner);
		notifyTurn = new EventNotifyTurn(player1);
		notYourTurn = new EventNotYourTurn(player1);
		sufferAttack = new EventSufferAttack(player1 , toDeclare);
		useDefense = new EventUseDefense(player1 , true , ObjectCardType.Defense);
		notifyAliens = new EventNotifyAliensWin(player1 , toDeclare , true);
		notifyTopics = new EventNotifyTopics(player1 , false , topics);
		hatchBlocked = new EventHatchBlocked(player1 , sector2);
		humanWin = new EventNotifyHumanWin(player1);
		rejectCardAlien = new EventRejectCardAlien(player1);
		rejectCardHuman = new EventRejectCardHuman(player1 , card2);
		notifyChatMessage = new EventNotifyChatMessage(player1 , "Welcome");
		notifyRetired = new EventNotifyRetired(player1);
		
	}
	
	
	@Test
	public void test() {
		
		assertEquals(notifyRetired.getGenerator() , player1);
		assertTrue(closingGame.getAreThereOtherHumans());
		assertEquals(notifyChatMessage.getMessage() , "Welcome");
		assertEquals(addedToGame.getGenerator() , player1);
		assertEquals(addedToGame.getType() , NotifyEventType.Added);
		assertEquals(addedToGame.getAdded() , added);
		assertEquals(addedToGame.isBroadcast() , broadcast);
		assertEquals(addedToGame.toString() , "EventAddedToGame [added=" + added + " player= " + player1.getName() + "]");	
	
		assertEquals(attacked.getAreYouPowered() , avatar1.getIsPowered());
		assertEquals(attacked.getAreThereOtherHumans() , false);
		
		assertEquals(cardUsed.getPerformed() , performed);
		assertEquals(cardUsed.toString() , "EventCardUsed [" + ObjectCardType.AttackCard.toString() + "]");
		
		assertEquals(declarePosition.getToDeclare() , toDeclare2);
		
		assertEquals(drown.getDrown() , card1);
		assertEquals(drown.getAdded() , card2);
		
		assertEquals(finishedTurn.getFinished() , finished);
		
		assertEquals(moved.getMoved() , moved1);
		
		assertEquals(notifyEnvironment.getMap() , map);
		assertEquals(notifyEnvironment.getMappingPlayerAvatar() , toDeclare);
		assertEquals(notifyEnvironment.toString() , "EventNotifyEnvironment [ ]");
		
		assertEquals(notifyPlayerState.getPlayer() , player1);
		assertEquals(notifyPlayerState.getWinner() , winner);
		
		assertEquals(notifyTurn.getPlayerOfTurn() , player1);
		assertEquals(notifyTurn.toString() , "EventNotifyTurn [playerOfTurn= " + player1.getName() + "]");
		
		assertEquals(notYourTurn.getMessage() , "This is not your turn !");
		
		assertEquals(moved.toString() , "NotifyEvent [type=" + NotifyEventType.Moved + "]");
		
		assertEquals(notifyError.getRelatedAction() , action);
		
		assertEquals(sufferAttack.getKilled() , toDeclare);
		sufferAttack.setKilled(toDeclare2);
		assertEquals(sufferAttack.getKilled() , toDeclare2);
		
		assertEquals(useDefense.getTypeCard() , ObjectCardType.Defense);
		
		assertEquals(notifyAliens.getWinners() , toDeclare);
		
		assertEquals(notifyTopics.getTopics() , topics);
		
		assertEquals(hatchBlocked.getHatch() , sector2);
		
		assertEquals(rejectCardAlien.getGenerator() , player1);
		
		assertEquals(rejectCardHuman.getCard() , card2);
		/*assertEquals(showTopics.getTopics() , topics);*/		
	}

}
