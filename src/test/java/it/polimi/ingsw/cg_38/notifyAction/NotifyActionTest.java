package it.polimi.ingsw.cg_38.notifyAction;

import static org.junit.Assert.*;

import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;

import it.polimi.ingsw.cg_38.controller.PlayerClient;
import it.polimi.ingsw.cg_38.controller.PlayerClientState;
import it.polimi.ingsw.cg_38.gameEvent.EventAliensWinner;
import it.polimi.ingsw.cg_38.gameEvent.EventNoiseMySect;
import it.polimi.ingsw.cg_38.gameEvent.EventNoiseRandSect;
import it.polimi.ingsw.cg_38.gameEvent.EventPlayerLooser;
import it.polimi.ingsw.cg_38.gameEvent.EventPlayerWinner;
import it.polimi.ingsw.cg_38.gameEvent.EventSubscribe;
import it.polimi.ingsw.cg_38.model.Alien;
import it.polimi.ingsw.cg_38.model.Avatar;
import it.polimi.ingsw.cg_38.model.Card;
import it.polimi.ingsw.cg_38.model.Dangerous;
import it.polimi.ingsw.cg_38.model.EndState;
import it.polimi.ingsw.cg_38.model.HatchCard;
import it.polimi.ingsw.cg_38.model.HatchCardType;
import it.polimi.ingsw.cg_38.model.Human;
import it.polimi.ingsw.cg_38.model.LifeState;
import it.polimi.ingsw.cg_38.model.Name;
import it.polimi.ingsw.cg_38.model.ObjectCard;
import it.polimi.ingsw.cg_38.model.ObjectCardType;
import it.polimi.ingsw.cg_38.model.Player;
import it.polimi.ingsw.cg_38.model.Sector;
import it.polimi.ingsw.cg_38.model.SectorCard;
import it.polimi.ingsw.cg_38.model.SectorCardType;
import it.polimi.ingsw.cg_38.notifyEvent.EventAddedToGame;
import it.polimi.ingsw.cg_38.notifyEvent.EventAttacked;
import it.polimi.ingsw.cg_38.notifyEvent.EventDrown;
import it.polimi.ingsw.cg_38.notifyEvent.EventNotifyAliensWin;

import org.junit.Before;
import org.junit.Test;

public class NotifyActionTest {
	
	AddedToGame addedToGame;
	RenderAliensWin renderAliensWin;
	RenderAttacked renderAttacked;
	RenderAttacked renderAttacked2;
	RenderDrown renderDrown;
	EventNotifyAliensWin evtNotifyAliensWin;
	EventAttacked evtAttacked;
	EventAttacked evtAttacked2;
	EventDrown evtDrown;
	EventDrown evtDrown2;
	EventSubscribe evtSubscribe;
	EventAddedToGame evtAddedToGame;
	EventNoiseMySect evtNoiseMySect;
	EventNoiseMySect evtNoiseMySect2;
	EventPlayerWinner evtPlayerWinner;
	EventAliensWinner evtAliensWinner;
	EventPlayerLooser evtPlayerLooser;
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
		evtNotifyAliensWin = new EventNotifyAliensWin(player1 , true , winners);
		evtSubscribe = new EventSubscribe(player1 , "room1" , "Galilei");
		evtAttacked = new EventAttacked(player1 , killed , true);
		evtAttacked2 = new EventAttacked(player1 , killed2 , true);
		evtDrown = new EventDrown(player1 , null , card);
		evtDrown2 = new EventDrown(player1 , null , card2);
		evtPlayerLooser = new EventPlayerLooser(player1);
		evtPlayerWinner = new EventPlayerWinner(player1);
		evtAliensWinner = new EventAliensWinner(player1);
		evtNoiseMySect = new EventNoiseMySect(player1);
		client = new PlayerClient("RMI" , evtSubscribe);
		client.setPlayer(player1);
		client2 = new PlayerClient("Socket" , evtSubscribe);
		client2.setPlayer(player2);
		renderAttacked = new RenderAttacked(evtAttacked);
		renderAttacked2 = new RenderAttacked(evtAttacked2);
		renderAliensWin = new RenderAliensWin(evtNotifyAliensWin);
		renderDrown = new RenderDrown(evtDrown);
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
		
		assertEquals(renderAliensWin.isPossible(client) , true);
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
		assertEquals(renderAttacked.render(client).getGenerator() , evtPlayerLooser.getGenerator());
		assertEquals(renderAttacked.render(client2) , null);
		assertEquals(renderAttacked2.render(client) , null);
		assertEquals(client.getIsInterfaceBlocked() , false);
		evtAttacked2.setAreThereOtherHumans(false);
		assertEquals(renderAttacked2.render(client).getGenerator() , evtAliensWinner.getGenerator());
		evtAttacked2.getGenerator().setAvatar(avatar2);
		assertEquals(renderAttacked2.render(client) , null);
		assertEquals(client.getIsInterfaceBlocked() , false);
		evtAttacked2.getKilled().remove(player2);
		evtAttacked2.getGenerator().setAvatar(avatar1);
		assertEquals(renderAttacked2.render(client) , null);
		
		assertEquals(renderDrown.isPossible(client) , false);
		client.setPlayerClientState(PlayerClientState.isTurn);
		assertEquals(renderDrown.isPossible(client) , true);
		assertEquals(renderDrown.render(client).getGenerator() , evtNoiseMySect.getGenerator());
		card = new SectorCard(SectorCardType.Silence , false);
		evtDrown = new EventDrown(player1 , null , card);
		renderDrown = new RenderDrown(evtDrown);
		assertEquals(renderDrown.render(client) , null);
		assertEquals(client.getIsInterfaceBlocked() , false);
		renderDrown = new RenderDrown(evtDrown2);
		assertEquals(renderDrown.render(client).getGenerator() , evtPlayerWinner.getGenerator());
		card2 = new HatchCard(HatchCardType.Red);
		evtDrown2 = new EventDrown(player1 , null , card2);
		renderDrown = new RenderDrown(evtDrown2);
		assertEquals(renderDrown.render(client) , null);
		assertEquals(client.getIsInterfaceBlocked() , false);
		card3 = new ObjectCard(ObjectCardType.Adrenaline);
		evtDrown2 = new EventDrown(player1 , null , card3);
		assertEquals(renderDrown.render(client) , null);
	}

}
