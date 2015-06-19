package it.polimi.ingsw.cg_38.gameEvent;


import static org.junit.Assert.*;
import it.polimi.ingsw.cg_38.controller.event.GameEventType;
import it.polimi.ingsw.cg_38.model.Avatar;
import it.polimi.ingsw.cg_38.model.Card;
import it.polimi.ingsw.cg_38.model.Human;
import it.polimi.ingsw.cg_38.model.Name;
import it.polimi.ingsw.cg_38.model.ObjectCard;
import it.polimi.ingsw.cg_38.model.ObjectCardType;
import it.polimi.ingsw.cg_38.model.Player;
import it.polimi.ingsw.cg_38.model.Sector;

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
	
	Player player1;
	
	Avatar avatar1;
	
	Sector sector1;
	
	String room;
	String RMI_ID;
	
	int PORT;

	Card card1;
	
	String map;
	
	
	@Before
	public void init() throws ParserConfigurationException, Exception {
		
		player1 = new Player("Albi");
				
		sector1 = new Sector();
		
		avatar1 = new Human(Name.Human1 , sector1);
		
		player1.setAvatar(avatar1);
		
		room = "room1";
		
		map = "Galvani";
		
		card1 = new ObjectCard(ObjectCardType.Adrenaline);
			
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
		
				
	}
	
	@Test
	public void test(){
		
		assertEquals(adrenaline.getToUse() , card1);
		assertEquals(adrenaline.getType() , GameEventType.Adrenaline);
		assertEquals(adrenaline.getGenerator() , player1);
		
		assertEquals(attack.getTarget() , sector1);
		
		assertEquals(attackCard.getToUse() , card1);
		assertEquals(attackCard.getTarget() , sector1);
		
		assertEquals(lights.getCard() , card1);
		assertEquals(lights.getTarget() , sector1);
		
		assertEquals(move.getToMove() , sector1);
		
		assertEquals(newGame.getCreatingRoomName() , room);
		
		assertEquals(noiseRandSect.getToNoise() , sector1);
		
		assertEquals(sedat.getToUse() , card1);
		
		assertEquals(subscribe.getMap() , map);
		assertEquals(subscribe.getRoom() , room);
		assertEquals(subscribe.toString() , "EventSubscribe [map=" + map + ", room=" + room + "]");
		
		assertEquals(teleport.getToUse() , card1);
	}
	
}
