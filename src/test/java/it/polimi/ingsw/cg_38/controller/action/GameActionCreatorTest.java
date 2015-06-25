package it.polimi.ingsw.cg_38.controller.action;

import static org.junit.Assert.*;
import it.polimi.ingsw.cg_38.controller.gameEvent.EventADRENALINE;
import it.polimi.ingsw.cg_38.controller.gameEvent.EventAliensWinner;
import it.polimi.ingsw.cg_38.controller.gameEvent.EventAttack;
import it.polimi.ingsw.cg_38.controller.gameEvent.EventATTACKCARD;
import it.polimi.ingsw.cg_38.controller.gameEvent.EventChat;
import it.polimi.ingsw.cg_38.controller.gameEvent.EventDEFENSE;
import it.polimi.ingsw.cg_38.controller.gameEvent.EventDraw;
import it.polimi.ingsw.cg_38.controller.gameEvent.EventFinishTurn;
import it.polimi.ingsw.cg_38.controller.gameEvent.EventHatchBlocked;
import it.polimi.ingsw.cg_38.controller.gameEvent.EventHumanWin;
import it.polimi.ingsw.cg_38.controller.gameEvent.EventMove;
import it.polimi.ingsw.cg_38.controller.gameEvent.EventNoiseMySect;
import it.polimi.ingsw.cg_38.controller.gameEvent.EventNoiseRandSect;
import it.polimi.ingsw.cg_38.controller.gameEvent.EventRejectCard;
import it.polimi.ingsw.cg_38.controller.gameEvent.EventRetired;
import it.polimi.ingsw.cg_38.controller.gameEvent.EventSEDATIVES;
import it.polimi.ingsw.cg_38.controller.gameEvent.EventSPOTLIGHT;
import it.polimi.ingsw.cg_38.controller.gameEvent.EventSubscribe;
import it.polimi.ingsw.cg_38.controller.gameEvent.EventTELEPORT;
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

public class GameActionCreatorTest {
	
	EventADRENALINE evtAdren;
	EventAttack evtAttack;
	EventATTACKCARD evtAttackCard;
	EventDraw evtDraw;
	EventFinishTurn evtFinishTurn;
	EventSPOTLIGHT evtLights;
	EventMove evtMove;
	EventNoiseMySect evtNoiseMySect;
	EventNoiseRandSect evtNoiseRandSect;
	EventSEDATIVES evtSedat;
	EventTELEPORT evtTeleport;
	EventSubscribe evtSubscribe;
	EventAliensWinner evtAliensWinner;
	EventHumanWin humanWin;
	EventHatchBlocked hatchBlocked;
	EventDEFENSE evtDefense;
	EventRejectCard evtRejectCard;
	EventChat evtChat;
	EventRetired evtRetired;
	
	
	Attack attack1;
	AliensWin aliensWin;
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
	HumanWin human;
	
	
	Player player1;
	Boolean bool;
	Avatar avatar1;
	
	String room;
	String map;
	String message;
	
	Card card1;
	
	Sector sector1;
	
	GameActionCreator gameActionCreator;
	
	
	
	@Before
	public void init() throws ParserConfigurationException, Exception{
		
		gameActionCreator = new GameActionCreator();
		
		room = "room2";
		map = "Galvani";
		message = "Welcome";
		
		card1 = new ObjectCard(ObjectCardType.ADRENALINE);
		
		avatar1 = new Human(Name.Human1 , sector1);
		
		player1 = new Player("scimmiu");
		player1.setAvatar(avatar1);
		
		evtAdren = new EventADRENALINE(player1 , card1);
		evtAttack = new EventAttack(player1 , sector1);
		evtAttackCard = new EventATTACKCARD(player1 , card1);
		evtDraw = new EventDraw(player1);
		evtFinishTurn = new EventFinishTurn(player1);
		evtLights = new EventSPOTLIGHT(player1 , sector1 , card1);
		evtMove = new EventMove(player1 , sector1);
		evtNoiseMySect = new EventNoiseMySect(player1);
		evtNoiseRandSect = new EventNoiseRandSect(player1 , sector1);
		evtSedat = new EventSEDATIVES(player1 , card1);
		evtSubscribe = new EventSubscribe(player1 , room , map);
		evtTeleport = new EventTELEPORT(player1 , card1);
		evtAliensWinner = new EventAliensWinner(player1, bool);
		humanWin = new EventHumanWin(player1);
		hatchBlocked = new EventHatchBlocked(player1);
		evtDefense = new EventDEFENSE(player1);
		evtRejectCard = new EventRejectCard(player1 , (ObjectCard)card1);
		evtChat = new EventChat(player1 , message);
		evtRetired = new EventRetired(player1);
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
		assertTrue(GameActionCreator.createGameAction(humanWin) instanceof HumanWin);
		assertTrue(GameActionCreator.createGameAction(hatchBlocked) instanceof HatchBlocked);
		assertTrue(GameActionCreator.createGameAction(evtDefense) instanceof Defense);
		assertTrue(GameActionCreator.createGameAction(evtRejectCard) instanceof Reject);
		assertTrue(GameActionCreator.createGameAction(evtChat) instanceof Chat);
		assertTrue(GameActionCreator.createGameAction(evtRetired) instanceof Retire);
		
	}

}