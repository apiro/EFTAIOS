package it.polimi.ingsw.cg_38.controller.action;

import static org.junit.Assert.*;
import it.polimi.ingsw.cg_38.gameEvent.EventAdren;
import it.polimi.ingsw.cg_38.gameEvent.EventAliensWinner;
import it.polimi.ingsw.cg_38.gameEvent.EventAttack;
import it.polimi.ingsw.cg_38.gameEvent.EventAttackCard;
import it.polimi.ingsw.cg_38.gameEvent.EventDraw;
import it.polimi.ingsw.cg_38.gameEvent.EventFinishTurn;
import it.polimi.ingsw.cg_38.gameEvent.EventLights;
import it.polimi.ingsw.cg_38.gameEvent.EventMove;
import it.polimi.ingsw.cg_38.gameEvent.EventNoiseMySect;
import it.polimi.ingsw.cg_38.gameEvent.EventNoiseRandSect;
import it.polimi.ingsw.cg_38.gameEvent.EventSedat;
import it.polimi.ingsw.cg_38.gameEvent.EventSubscribe;
import it.polimi.ingsw.cg_38.gameEvent.EventTeleport;
import it.polimi.ingsw.cg_38.model.Avatar;
import it.polimi.ingsw.cg_38.model.Card;
import it.polimi.ingsw.cg_38.model.Human;
import it.polimi.ingsw.cg_38.model.Name;
import it.polimi.ingsw.cg_38.model.Player;
import it.polimi.ingsw.cg_38.model.Sector;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.Before;
import org.junit.Test;

public class GameActionCreatorTest {
	
	EventAdren evtAdren;
	EventAttack evtAttack;
	EventAttackCard evtAttackCard;
	EventDraw evtDraw;
	EventFinishTurn evtFinishTurn;
	EventLights evtLights;
	EventMove evtMove;
	EventNoiseMySect evtNoiseMySect;
	EventNoiseRandSect evtNoiseRandSect;
	EventSedat evtSedat;
	EventTeleport evtTeleport;
	EventSubscribe evtSubscribe;
	EventAliensWinner evtAliensWinner;
	
	Attack attack1;
	Draw draw;
	FinishTurn finishTurn;
	Looser looser;
	Move move;
	Subscribe subscribe;
	UseAdrenalineCard adrenalineCard;
	UseAttackCard attackCard;
	UseLightsCard lightsCard;
	UseMySectorNoise mySectorNoise;
	UseRandomSectorNoise randomSectorNoise;
	UseSedativesCard sedativesCard;
	UseSilenceCard silenceCard;
	UseTeleportCard teleportCard;
	Winner winner;
	
	Player player1;
	
	Avatar avatar1;
	
	String room;
	String map;
	
	Card card1;
	
	Sector sector1;
	
	GameActionCreator gameActionCreator;
	
	
	
	@Before
	public void init() throws ParserConfigurationException, Exception{
		
		gameActionCreator = new GameActionCreator();
		
		room = "room2";
		map = "Galvani";
		
		avatar1 = new Human(Name.Human1 , sector1);
		
		player1 = new Player("scimmiu");
		player1.setAvatar(avatar1);
		
		evtAdren = new EventAdren(player1 , card1);
		evtAttack = new EventAttack(player1 , sector1);
		evtAttackCard = new EventAttackCard(player1 , card1);
		evtDraw = new EventDraw(player1);
		evtFinishTurn = new EventFinishTurn(player1);
		evtLights = new EventLights(player1 , sector1 , card1);
		evtMove = new EventMove(player1 , sector1);
		evtNoiseMySect = new EventNoiseMySect(player1);
		evtNoiseRandSect = new EventNoiseRandSect(player1 , sector1);
		evtSedat = new EventSedat(player1 , card1);
		evtSubscribe = new EventSubscribe(player1 , room , map);
		evtTeleport = new EventTeleport(player1 , card1);
		evtAliensWinner = new EventAliensWinner(player1);
				
	}

	@Test
	public void test() {
		
		assertTrue(GameActionCreator.createGameAction(evtAdren) instanceof UseAdrenalineCard);
		assertTrue(GameActionCreator.createGameAction(evtAttack) instanceof Attack);
		assertTrue(GameActionCreator.createGameAction(evtAttackCard) instanceof UseAttackCard);
		assertTrue(GameActionCreator.createGameAction(evtDraw)instanceof Draw);
		assertTrue(GameActionCreator.createGameAction(evtFinishTurn) instanceof FinishTurn);
		assertTrue(GameActionCreator.createGameAction(evtLights) instanceof UseLightsCard);
		assertTrue(GameActionCreator.createGameAction(evtMove) instanceof Move);
		assertTrue(GameActionCreator.createGameAction(evtNoiseMySect) instanceof UseMySectorNoise);
		assertTrue(GameActionCreator.createGameAction(evtNoiseRandSect) instanceof UseRandomSectorNoise);
		assertTrue(GameActionCreator.createGameAction(evtSedat) instanceof UseSedativesCard);
		assertTrue(GameActionCreator.createGameAction(evtSubscribe) instanceof Subscribe);
		assertTrue(GameActionCreator.createGameAction(evtTeleport) instanceof UseTeleportCard);
		assertTrue(GameActionCreator.createGameAction(evtAliensWinner) instanceof AliensWin);
		
	}

}