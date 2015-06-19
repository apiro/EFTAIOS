package it.polimi.ingsw.cg_38.notifyAction;

import static org.junit.Assert.*;

import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;

import it.polimi.ingsw.cg_38.controller.PlayerClient;
import it.polimi.ingsw.cg_38.controller.PlayerClientState;
import it.polimi.ingsw.cg_38.controller.action.Draw;
import it.polimi.ingsw.cg_38.gameEvent.EventAliensWinner;
import it.polimi.ingsw.cg_38.gameEvent.EventDraw;
import it.polimi.ingsw.cg_38.gameEvent.EventNoiseMySect;
import it.polimi.ingsw.cg_38.gameEvent.EventNoiseRandSect;
import it.polimi.ingsw.cg_38.gameEvent.EventSubscribe;
import it.polimi.ingsw.cg_38.gameEvent.EventContinue;
import it.polimi.ingsw.cg_38.model.Alien;
import it.polimi.ingsw.cg_38.model.Avatar;
import it.polimi.ingsw.cg_38.model.Card;
import it.polimi.ingsw.cg_38.model.Dangerous;
import it.polimi.ingsw.cg_38.model.EndState;
import it.polimi.ingsw.cg_38.model.HatchCard;
import it.polimi.ingsw.cg_38.model.HatchCardType;
import it.polimi.ingsw.cg_38.model.Human;
import it.polimi.ingsw.cg_38.model.LifeState;
import it.polimi.ingsw.cg_38.model.Galvani;
import it.polimi.ingsw.cg_38.model.Name;
import it.polimi.ingsw.cg_38.model.ObjectCard;
import it.polimi.ingsw.cg_38.model.ObjectCardType;
import it.polimi.ingsw.cg_38.model.Player;
import it.polimi.ingsw.cg_38.model.Sector;
import it.polimi.ingsw.cg_38.model.SectorCard;
import it.polimi.ingsw.cg_38.model.SectorCardType;
import it.polimi.ingsw.cg_38.notifyEvent.EventAddedToGame;
import it.polimi.ingsw.cg_38.notifyEvent.EventAttacked;
import it.polimi.ingsw.cg_38.notifyEvent.EventCardUsed;
import it.polimi.ingsw.cg_38.notifyEvent.EventDeclareNoise;
import it.polimi.ingsw.cg_38.notifyEvent.EventDeclarePosition;
import it.polimi.ingsw.cg_38.notifyEvent.EventDrown;
import it.polimi.ingsw.cg_38.notifyEvent.EventNotifyAliensWin;
import it.polimi.ingsw.cg_38.notifyEvent.EventNotifyEnvironment;
import it.polimi.ingsw.cg_38.notifyEvent.EventNotifyError;
import it.polimi.ingsw.cg_38.notifyEvent.EventNotifyTeleport;
import it.polimi.ingsw.cg_38.notifyEvent.EventNotifyTurn;
import it.polimi.ingsw.cg_38.notifyEvent.EventUseDefense;

import org.junit.Before;
import org.junit.Test;

public class NotifyActionTest {
	
	AddedToGame addedToGame;
	RenderAliensWin renderAliensWin;
	RenderAttacked renderAttacked;
	RenderAttacked renderAttacked2;
	RenderDrown renderDrown;
	RenderTeleport renderTeleport;
	RenderNoise renderNoise;
	RenderNoSideEffectCard renderEffectCard;
	RenderUseDefenseCard renderUseDefenseCard;
	RenderError renderError;
	RenderNotifyTurn renderTurn;
	RenderSpotlight renderSpotlight;
	RenderEnvironment renderEnvironment;
	
	EventNotifyAliensWin evtNotifyAliensWin;
	EventAttacked evtAttacked;
	EventAttacked evtAttacked2;
	EventDrown evtDrown;
	EventDrown evtDrown2;
	EventSubscribe evtSubscribe;
	EventAddedToGame evtAddedToGame;
	EventNoiseMySect evtNoiseMySect;
	EventNoiseMySect evtNoiseMySect2;
	EventAliensWinner evtAliensWinner;
	EventNotifyTeleport evtNotifyTeleport;
	EventDeclareNoise evtDeclareNoise;
	EventCardUsed cardUsed;
	EventUseDefense useDefense;
	EventNotifyError notifyError;
	EventNotifyTurn notifyTurn;
	EventDeclarePosition declarePosition;
	EventNotifyEnvironment environment;
	
	EventDraw evtDraw;
	Draw draw;
	
	PlayerClient client;
	PlayerClient client2;
	Player player1;
	Player player2;
	Player player3;
	
	Avatar avatar1;
	Avatar avatar2;
	Sector sector;
	Sector sector2;
	SectorCard card;
	HatchCard card2;
	ObjectCard card3;
	
	ArrayList<Player> winners;
	ArrayList<Player> killed;
	ArrayList<Player> killed2;
	
	@Before
	public void init() throws ParserConfigurationException, Exception {

		winners = new ArrayList<Player>();
		killed = new ArrayList<Player>();
		killed2 = new ArrayList<Player>();
		player1 = new Player("albi");
		player2 = new Player("scimmiu");
		player3 = new Player("reda");
		sector = new Sector();
		sector2 = new Dangerous();
		card = new SectorCard(SectorCardType.MySectorNoise , false);
		card2 = new HatchCard(HatchCardType.Green);
		avatar1 = new Alien(Name.Alien1 , sector);
		avatar2 = new Human(Name.Human1 , sector);
		player1.setAvatar(avatar1);
		winners.add(player1);
		winners.add(player2);
		killed.add(player1);
		killed.add(player3);
		killed2.add(player2);
		
		evtDraw = new EventDraw(player1);
		draw = new Draw(evtDraw);
		evtNotifyAliensWin = new EventNotifyAliensWin(player1 , winners);
		evtSubscribe = new EventSubscribe(player1 , "room1" , "Galilei");
		evtAttacked = new EventAttacked(player1 , true);
		evtAttacked2 = new EventAttacked(player1 , true);
		evtDrown = new EventDrown(player1 , null , card);
		evtDrown2 = new EventDrown(player1 , null , card2);
		evtAliensWinner = new EventAliensWinner(player1);
		evtNoiseMySect = new EventNoiseMySect(player1);
		evtNotifyTeleport = new EventNotifyTeleport(player1 , "moved");
		evtDeclareNoise = new EventDeclareNoise(player1 , sector);
		cardUsed = new EventCardUsed(player1 , true , ObjectCardType.Adrenaline);
		useDefense = new EventUseDefense(player1 , true , ObjectCardType.Defense);
		notifyError = new EventNotifyError(player1 , draw);
		notifyTurn = new EventNotifyTurn(player1);
		declarePosition = new EventDeclarePosition(player1 , killed);
		environment = new EventNotifyEnvironment(killed , new Galvani());
		
		client = new PlayerClient("RMI" , evtSubscribe);
		client.setPlayer(player1);
		client2 = new PlayerClient("Socket" , evtSubscribe);
		client2.setPlayer(player2);
		
		renderAttacked = new RenderAttacked(evtAttacked);
		renderAttacked2 = new RenderAttacked(evtAttacked2);
		renderAliensWin = new RenderAliensWin(evtNotifyAliensWin);
		renderDrown = new RenderDrown(evtDrown);
		renderTeleport = new RenderTeleport(evtNotifyTeleport);
		renderNoise = new RenderNoise(evtDeclareNoise);
		renderEffectCard = new RenderNoSideEffectCard(cardUsed);
		renderUseDefenseCard = new RenderUseDefenseCard(useDefense);
		renderError = new RenderError(notifyError);
		renderTurn = new RenderNotifyTurn(notifyTurn);
		renderSpotlight = new RenderSpotlight(declarePosition);
		renderEnvironment = new RenderEnvironment(environment);
		
		evtAddedToGame = new EventAddedToGame(player1 , true , true);
		addedToGame = new AddedToGame(evtAddedToGame);
	}

	@Test
	public void test() {
		
		assertEquals(addedToGame.getEvt() , evtAddedToGame);
		assertEquals(addedToGame.isPossible(client) , true);
		System.out.println(addedToGame.getEvt().getGenerator().getName());
		assertEquals(addedToGame.render(client) , null);
		assertEquals(client.getIsInterfaceBlocked() , true);
		assertEquals(client.getPlayerClientState() , PlayerClientState.connected);
		
		client.setMap(new Galvani());
		assertEquals(renderEnvironment.isPossible(client) , false);
		client.setMap(null);
		assertTrue(renderEnvironment.isPossible(client));
		assertEquals(renderEnvironment.render(client) , null);
		assertEquals(client.getIsInterfaceBlocked() , true);
		assertTrue(renderSpotlight.isPossible(client));
		assertTrue(renderSpotlight.render(client) instanceof EventContinue);
		assertTrue(renderTurn.isPossible(client));
		assertEquals(renderTurn.render(client) , null);
		assertEquals(client.getIsInterfaceBlocked() , false);
		assertEquals(renderTurn.render(client2) , null);
		assertEquals(client2.getIsInterfaceBlocked() , true);
		assertTrue(renderError.isPossible(client));
		assertEquals(renderError.render(client) , null);
		assertEquals(client.getIsInterfaceBlocked() , false);
		assertTrue(renderUseDefenseCard.render(client) instanceof EventContinue);
		assertEquals(renderEffectCard.isPossible(client) , true);
		assertEquals(renderEffectCard.render(client) , null);
		assertEquals(renderNoise.isPossible(client) , true);
		assertEquals(renderNoise.render(client) , null);
		client.getPlayer().getAvatar().setIsAlive(LifeState.DEAD);
		client.getPlayer().getAvatar().setIsWinner(EndState.LOOSER);
		assertEquals(renderSpotlight.isPossible(client) , false);
		assertEquals(renderNoise.isPossible(client) , false);
		assertEquals(renderEffectCard.isPossible(client) , false);
		assertEquals(renderTurn.isPossible(client) , false);
		client.getPlayer().getAvatar().setIsAlive(LifeState.ALIVE);
		client.getPlayer().getAvatar().setIsWinner(EndState.PLAYING);
		assertEquals(renderAliensWin.isPossible(client) , true);
		assertEquals(renderAliensWin.getEvt() , evtNotifyAliensWin);
		player1.setAvatar(avatar2);
		assertEquals(renderAliensWin.isPossible(client) , false);
		player1.setAvatar(avatar1);
		client.getPlayer().getAvatar().setIsAlive(LifeState.DEAD);
		client.getPlayer().getAvatar().setIsWinner(EndState.LOOSER);
		assertEquals(renderAliensWin.isPossible(client) , false);
		assertEquals(renderAliensWin.render(client) , null);
		client.getPlayer().getAvatar().setIsAlive(LifeState.ALIVE);
		client.getPlayer().getAvatar().setIsWinner(EndState.PLAYING);
		
		assertEquals(renderAttacked.isPossible(client) , true);
		evtAttacked.setAreYouPowered(true);
		evtAttacked.setAreYouPowered(true);
		renderAttacked = new RenderAttacked(evtAttacked);
		assertEquals(renderAttacked.render(client2) , null);
		assertEquals(renderAttacked2.render(client) , null);
		assertEquals(client.getIsInterfaceBlocked() , false);
		evtAttacked.setAreThereOtherHumans(false);
		renderAttacked = new RenderAttacked(evtAttacked);
		assertTrue(renderAttacked.render(client) instanceof EventAliensWinner);
		evtAttacked2.setAreThereOtherHumans(false);
		evtAttacked2.getGenerator().setAvatar(avatar2);
		assertEquals(renderAttacked2.render(client) , null);
		assertEquals(client.getIsInterfaceBlocked() , false);
		evtAttacked2.getGenerator().setAvatar(avatar1);
		assertEquals(renderAttacked2.render(client) , null);
		evtAttacked2.getGenerator().setAvatar(avatar2);
		evtAttacked2.setAreYouPowered(true);
		renderAttacked2 = new RenderAttacked(evtAttacked2);
		assertTrue(renderAttacked2.render(client2) instanceof EventContinue);
		assertEquals(client2.getIsInterfaceBlocked() , true);
		
		client.setPlayerClientState(PlayerClientState.playing);
		assertTrue(renderTeleport.render(client) instanceof EventContinue);
		assertEquals(renderDrown.isPossible(client) , false);
		client.setPlayerClientState(PlayerClientState.isTurn);
		client.getPlayer().getAvatar().setIsAlive(LifeState.DEAD);
		client.getPlayer().getAvatar().setIsWinner(EndState.LOOSER);		
		assertEquals(renderDrown.isPossible(client) , false);
		client.getPlayer().getAvatar().setIsAlive(LifeState.ALIVE);
		client.getPlayer().getAvatar().setIsWinner(EndState.WINNER);	
		assertEquals(renderDrown.isPossible(client) , true);		
		assertEquals(renderDrown.render(client).getGenerator() , evtNoiseMySect.getGenerator());
		card = new SectorCard(SectorCardType.Silence , false);
		evtDrown = new EventDrown(player1 , null , card);
		renderDrown = new RenderDrown(evtDrown);
		assertEquals(renderDrown.render(client) , null);
		assertEquals(client.getIsInterfaceBlocked() , false);
		renderDrown = new RenderDrown(evtDrown2);
		card2 = new HatchCard(HatchCardType.Red);
		evtDrown2 = new EventDrown(player1 , null , card2);
		renderDrown = new RenderDrown(evtDrown2);
		assertEquals(renderDrown.render(client) , null);
		assertEquals(client.getIsInterfaceBlocked() , false);
		card3 = new ObjectCard(ObjectCardType.Adrenaline);
		evtDrown2 = new EventDrown(player1 , null , card3);
		assertEquals(renderDrown.render(client) , null);
		client.getPlayer().getAvatar().setIsWinner(EndState.WINNER);
		client.getPlayer().getAvatar().setIsAlive(LifeState.DEAD);
		assertEquals(renderDrown.check(client) , true);
		assertTrue(addedToGame.getEvt() instanceof EventAddedToGame);
	
	}

}
