package it.polimi.ingsw.cg_38.gameEvent;


import static org.junit.Assert.*;
import it.polimi.ingsw.cg_38.controller.event.GameEventType;
import it.polimi.ingsw.cg_38.controller.gameEvent.EventAdrenaline;
import it.polimi.ingsw.cg_38.controller.gameEvent.EventAttack;
import it.polimi.ingsw.cg_38.controller.gameEvent.EventAttackCard;
import it.polimi.ingsw.cg_38.controller.gameEvent.EventChat;
import it.polimi.ingsw.cg_38.controller.gameEvent.EventContinue;
import it.polimi.ingsw.cg_38.controller.gameEvent.EventDefense;
import it.polimi.ingsw.cg_38.controller.gameEvent.EventDraw;
import it.polimi.ingsw.cg_38.controller.gameEvent.EventFinishTurn;
import it.polimi.ingsw.cg_38.controller.gameEvent.EventHumanWin;
import it.polimi.ingsw.cg_38.controller.gameEvent.EventMove;
import it.polimi.ingsw.cg_38.controller.gameEvent.EventNewGame;
import it.polimi.ingsw.cg_38.controller.gameEvent.EventNoiseMySect;
import it.polimi.ingsw.cg_38.controller.gameEvent.EventNoiseRandSect;
import it.polimi.ingsw.cg_38.controller.gameEvent.EventRejectCard;
import it.polimi.ingsw.cg_38.controller.gameEvent.EventRequestTopic;
import it.polimi.ingsw.cg_38.controller.gameEvent.EventRetired;
import it.polimi.ingsw.cg_38.controller.gameEvent.EventSedatives;
import it.polimi.ingsw.cg_38.controller.gameEvent.EventSpotLight;
import it.polimi.ingsw.cg_38.controller.gameEvent.EventSubscribe;
import it.polimi.ingsw.cg_38.controller.gameEvent.EventTeleport;
import it.polimi.ingsw.cg_38.model.Avatar;
import it.polimi.ingsw.cg_38.model.Human;
import it.polimi.ingsw.cg_38.model.Name;
import it.polimi.ingsw.cg_38.model.Player;
import it.polimi.ingsw.cg_38.model.deck.Card;
import it.polimi.ingsw.cg_38.model.deck.ObjectCard;
import it.polimi.ingsw.cg_38.model.deck.ObjectCardType;
import it.polimi.ingsw.cg_38.model.map.Sector;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.Before;
import org.junit.Test;

public class GameEventTest {
	
	EventAdrenaline adrenaline;
	EventAttack attack;
	EventAttackCard attackCard;
	EventSpotLight lights;
	EventMove move;
	EventNewGame newGame;
	EventNoiseRandSect noiseRandSect;
	EventSedatives sedat;
	EventSubscribe subscribe;
	EventTeleport teleport;
	EventDraw draw;
	EventNoiseMySect noiseMySect;
	EventFinishTurn finishTurn;
	EventRequestTopic requestTopic;
	EventContinue continue1;
	EventHumanWin humanWin;
	EventDefense defense;
	EventRejectCard rejectCard;
	EventChat chat;
	EventRetired retired;
	
	Player player1;
	
	Avatar avatar1;
	
	Sector sector1;
	
	String room;
	String RMI_ID;
	String message;
	
	int PORT;

	Card card1;
	
	String map;
	
	
	@Before
	public void init() throws ParserConfigurationException, Exception {
		
		player1 = new Player("Albi");
				
		sector1 = new Sector();
		
		avatar1 = new Human(Name.HUMAN1 , sector1);
		
		player1.setAvatar(avatar1);
		
		room = "room1";
		message = "Welcome";
		
		map = "Galvani";
		
		card1 = new ObjectCard(ObjectCardType.ADRENALINE);
			
		adrenaline = new EventAdrenaline(player1 , card1 );
		attack = new EventAttack(player1 , sector1);
		attackCard = new EventAttackCard(player1 , card1);	
		lights = new EventSpotLight(player1 , sector1 , card1);
		move = new EventMove(player1 , sector1);
		newGame = new EventNewGame(player1 , room);
		noiseRandSect = new EventNoiseRandSect(player1 , sector1);
		sedat = new EventSedatives(player1 , card1);
		subscribe = new EventSubscribe(player1 , room , map);
		teleport = new EventTeleport(player1 , card1);
		draw = new EventDraw(player1);
		noiseMySect = new EventNoiseMySect(player1);
		finishTurn = new EventFinishTurn(player1);
		requestTopic = new EventRequestTopic(player1 , true);
		continue1 = new EventContinue();
		humanWin = new EventHumanWin(player1);
		defense = new EventDefense(player1);
		rejectCard = new EventRejectCard(player1 , (ObjectCard)card1);
		chat = new EventChat(player1 , message);
		retired = new EventRetired(player1);
		
				
	}
	
	@Test
	public void test(){
		
		assertTrue(retired.getType().equals(GameEventType.RETIRED));
		assertTrue(chat.getMessage().equals(message));
		
		assertEquals(adrenaline.getToUse() , card1);
		assertEquals(adrenaline.getType() , GameEventType.ADRENALINE);
		assertEquals(adrenaline.getGenerator() , player1);
		
		assertEquals(attack.getTarget() , sector1);
		
		assertEquals(attackCard.getToUse() , card1);
		assertEquals(attackCard.getTarget() , sector1);
		
		assertEquals(lights.getTarget() , sector1);
		
		assertEquals(move.getToMove() , sector1);
		
		assertEquals(newGame.getCreatingRoomName() , room);
		
		assertEquals(noiseRandSect.getToNoise() , sector1);
		
		assertEquals(sedat.getToUse() , card1);
		
		assertEquals(subscribe.getMap() , map);
		assertEquals(subscribe.getRoom() , room);
		assertEquals(subscribe.toString() , "EventSubscribe [map=" + map + ", room=" + room + "]");
		
		assertEquals(teleport.getToUse() , card1);
		
		assertEquals(attack.toString() , "GameEvent [type=" + GameEventType.ATTACK + "]");
		
		assertEquals(humanWin.getType() , GameEventType.HUMANWIN);
		
		assertEquals(defense.getGenerator() , player1);
		
		assertEquals(rejectCard.getCard() , card1);
	}
	
}
