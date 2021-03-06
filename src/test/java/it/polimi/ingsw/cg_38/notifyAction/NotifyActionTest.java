package it.polimi.ingsw.cg_38.notifyAction;

import static org.junit.Assert.*;

import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;

import it.polimi.ingsw.cg_38.client.PlayerClientState;
import it.polimi.ingsw.cg_38.client.cli.PlayerClientCLI;
import it.polimi.ingsw.cg_38.client.notifyAction.AddedToGame;
import it.polimi.ingsw.cg_38.client.notifyAction.RenderAliensWin;
import it.polimi.ingsw.cg_38.client.notifyAction.RenderAttackDamage;
import it.polimi.ingsw.cg_38.client.notifyAction.RenderAttacked;
import it.polimi.ingsw.cg_38.client.notifyAction.RenderCardPerformed;
import it.polimi.ingsw.cg_38.client.notifyAction.RenderChatMessage;
import it.polimi.ingsw.cg_38.client.notifyAction.RenderClosingGame;
import it.polimi.ingsw.cg_38.client.notifyAction.RenderDrown;
import it.polimi.ingsw.cg_38.client.notifyAction.RenderEnvironment;
import it.polimi.ingsw.cg_38.client.notifyAction.RenderError;
import it.polimi.ingsw.cg_38.client.notifyAction.RenderHatchBlocked;
import it.polimi.ingsw.cg_38.client.notifyAction.RenderHumanWin;
import it.polimi.ingsw.cg_38.client.notifyAction.RenderMoved;
import it.polimi.ingsw.cg_38.client.notifyAction.RenderNoSideEffectCard;
import it.polimi.ingsw.cg_38.client.notifyAction.RenderNoise;
import it.polimi.ingsw.cg_38.client.notifyAction.RenderNotifyTurn;
import it.polimi.ingsw.cg_38.client.notifyAction.RenderRejectCard;
import it.polimi.ingsw.cg_38.client.notifyAction.RenderRejectHumanCard;
import it.polimi.ingsw.cg_38.client.notifyAction.RenderRetired;
import it.polimi.ingsw.cg_38.client.notifyAction.RenderSpotlight;
import it.polimi.ingsw.cg_38.client.notifyAction.RenderTeleport;
import it.polimi.ingsw.cg_38.client.notifyAction.RenderUseDefenseCard;
import it.polimi.ingsw.cg_38.client.notifyAction.RenderWin;
import it.polimi.ingsw.cg_38.controller.action.Draw;
import it.polimi.ingsw.cg_38.controller.gameEvent.EventAliensWinner;
import it.polimi.ingsw.cg_38.controller.gameEvent.EventContinue;
import it.polimi.ingsw.cg_38.controller.gameEvent.EventDraw;
import it.polimi.ingsw.cg_38.controller.gameEvent.EventFinishTurn;
import it.polimi.ingsw.cg_38.controller.gameEvent.EventHumanWin;
import it.polimi.ingsw.cg_38.controller.gameEvent.EventNoiseMySect;
import it.polimi.ingsw.cg_38.controller.gameEvent.EventSubscribe;
import it.polimi.ingsw.cg_38.controller.notifyEvent.EventAddedToGame;
import it.polimi.ingsw.cg_38.controller.notifyEvent.EventAttacked;
import it.polimi.ingsw.cg_38.controller.notifyEvent.EventCardUsed;
import it.polimi.ingsw.cg_38.controller.notifyEvent.EventClosingGame;
import it.polimi.ingsw.cg_38.controller.notifyEvent.EventDeclareNoise;
import it.polimi.ingsw.cg_38.controller.notifyEvent.EventDeclarePosition;
import it.polimi.ingsw.cg_38.controller.notifyEvent.EventDrown;
import it.polimi.ingsw.cg_38.controller.notifyEvent.EventMoved;
import it.polimi.ingsw.cg_38.controller.notifyEvent.EventNotifyAliensWin;
import it.polimi.ingsw.cg_38.controller.notifyEvent.EventNotifyCardPerformed;
import it.polimi.ingsw.cg_38.controller.notifyEvent.EventNotifyChatMessage;
import it.polimi.ingsw.cg_38.controller.notifyEvent.EventNotifyEnvironment;
import it.polimi.ingsw.cg_38.controller.notifyEvent.EventNotifyError;
import it.polimi.ingsw.cg_38.controller.notifyEvent.EventNotifyHumanWin;
import it.polimi.ingsw.cg_38.controller.notifyEvent.EventNotifyRetired;
import it.polimi.ingsw.cg_38.controller.notifyEvent.EventNotifyTeleport;
import it.polimi.ingsw.cg_38.controller.notifyEvent.EventNotifyTurn;
import it.polimi.ingsw.cg_38.controller.notifyEvent.EventNotifyWin;
import it.polimi.ingsw.cg_38.controller.notifyEvent.EventRejectCardAlien;
import it.polimi.ingsw.cg_38.controller.notifyEvent.EventRejectCardHuman;
import it.polimi.ingsw.cg_38.controller.notifyEvent.EventSufferAttack;
import it.polimi.ingsw.cg_38.controller.notifyEvent.EventUseDefense;
import it.polimi.ingsw.cg_38.controller.notifyEvent.EventHatchBlocked;
import it.polimi.ingsw.cg_38.model.Alien;
import it.polimi.ingsw.cg_38.model.Avatar;
import it.polimi.ingsw.cg_38.model.EndState;
import it.polimi.ingsw.cg_38.model.Human;
import it.polimi.ingsw.cg_38.model.LifeState;
import it.polimi.ingsw.cg_38.model.Name;
import it.polimi.ingsw.cg_38.model.Player;
import it.polimi.ingsw.cg_38.model.deck.Card;
import it.polimi.ingsw.cg_38.model.deck.HatchCard;
import it.polimi.ingsw.cg_38.model.deck.HatchCardType;
import it.polimi.ingsw.cg_38.model.deck.ObjectCard;
import it.polimi.ingsw.cg_38.model.deck.ObjectCardType;
import it.polimi.ingsw.cg_38.model.deck.SectorCard;
import it.polimi.ingsw.cg_38.model.deck.SectorCardType;
import it.polimi.ingsw.cg_38.model.map.Dangerous;
import it.polimi.ingsw.cg_38.model.map.Galvani;
import it.polimi.ingsw.cg_38.model.map.Hatch;
import it.polimi.ingsw.cg_38.model.map.Sector;

import org.junit.Before;
import org.junit.Test;

/** contiene i test delle azioni di notifica */
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
	RenderAttackDamage renderAttackDamage;
	RenderMoved renderMoved;
	RenderWin renderWin;
	RenderCardPerformed renderCardPerformed;
	RenderRejectCard renderRejectCard;
	RenderHatchBlocked renderHatchBlocked;
	RenderHumanWin renderHumanWin;
	RenderRejectHumanCard renderRejectHuman; 
	RenderChatMessage renderChatMessage;
	RenderRetired renderRetired;
	RenderClosingGame renderClosingGame;
	
	EventNotifyAliensWin evtNotifyAliensWin;
	EventNotifyCardPerformed evtNotifyCardPerformed;
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
	EventSufferAttack sufferAttack;
	EventMoved moved;
	EventNotifyWin win;
	EventRejectCardAlien evtRejectCard;
	EventHatchBlocked evtHatchBlocked;
	EventNotifyHumanWin evtHumanWin;
	EventRejectCardHuman evtRejectCardHuman;
	EventNotifyChatMessage evtChatMessage;
	EventNotifyRetired evtRetired;
	EventClosingGame evtClosingGame;
	
	EventDraw evtDraw;
	Draw draw;
	
	PlayerClientCLI client;
	PlayerClientCLI client2;
	Player player1;
	Player player2;
	Player player3;
	
	Avatar avatar1;
	Avatar avatar2;
	Sector sector;
	Sector sector2;
	Hatch sector3;
	SectorCard card;
	HatchCard card2;
	ObjectCard card3;
	Card card4;
	ObjectCard card5;
	
	ArrayList<Player> winners;
	ArrayList<Player> declared;
	ArrayList<Player> killed;
	ArrayList<Player> killed2;
	
	@Before
	public void init() throws ParserConfigurationException, Exception {

		winners = new ArrayList<Player>();
		killed = new ArrayList<Player>();
		killed2 = new ArrayList<Player>();
		declared = new ArrayList<Player>();
		player1 = new Player("albi");
		player2 = new Player("scimmiu");
		player3 = new Player("reda");
		sector = new Sector();
		sector2 = new Dangerous();
		sector3 = new Hatch();
		card = new SectorCard(SectorCardType.MYSECTORNOISE , false);
		card2 = new HatchCard(HatchCardType.GREEN);
		card5 = new ObjectCard(ObjectCardType.SEDATIVES);
		avatar1 = new Alien(Name.ALIEN1 , sector);
		avatar2 = new Human(Name.HUMAN1 , sector);
		player1.setAvatar(avatar1);
		player2.setAvatar(avatar2);
		winners.add(player1);
		winners.add(player2);
		killed.add(player1);
		killed.add(player3);
		killed2.add(player2);
		
		evtDraw = new EventDraw(player1);
		draw = new Draw(evtDraw);
		evtNotifyAliensWin = new EventNotifyAliensWin(player1 , winners , true);
		evtSubscribe = new EventSubscribe(player1 , "room1" , "Galilei");
		evtAttacked = new EventAttacked(player1 , true);
		evtAttacked2 = new EventAttacked(player1 , true);
		evtDrown = new EventDrown(player1 , null , card);
		evtDrown2 = new EventDrown(player1 , null , card2);
		evtAliensWinner = new EventAliensWinner(player1 , true);
		evtNoiseMySect = new EventNoiseMySect(player1);
		evtNotifyTeleport = new EventNotifyTeleport(player1 , "moved");
		evtDeclareNoise = new EventDeclareNoise(player1 , sector);
		cardUsed = new EventCardUsed(player1 , true , ObjectCardType.ADRENALINE);
		useDefense = new EventUseDefense(player1 , true , ObjectCardType.DEFENSE);
		notifyError = new EventNotifyError(player1 , draw);
		notifyTurn = new EventNotifyTurn(player1);
		declarePosition = new EventDeclarePosition(player1 , killed);
		environment = new EventNotifyEnvironment(killed , new Galvani());
		sufferAttack = new EventSufferAttack(player1 , killed2);
		moved = new EventMoved(player1 , "moved");
		win = new EventNotifyWin(player1);
		evtNotifyCardPerformed = new EventNotifyCardPerformed(player2);
		evtRejectCard = new EventRejectCardAlien(player1);
		evtHatchBlocked = new EventHatchBlocked(player1 , sector3);
		evtHumanWin = new EventNotifyHumanWin(player1);
		evtRejectCardHuman = new EventRejectCardHuman(player1 , card5);
		evtChatMessage = new EventNotifyChatMessage(player1 , "Welcome");
		evtRetired = new EventNotifyRetired(player1);
		evtClosingGame = new EventClosingGame(player1 , true);
		
		client = new PlayerClientCLI("RMI" , evtSubscribe);
		client.setPlayer(player1);
		client2 = new PlayerClientCLI("Socket" , evtSubscribe);
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
		renderAttackDamage = new RenderAttackDamage(sufferAttack);
		renderMoved = new RenderMoved(moved);
		renderWin = new RenderWin(win);
		evtAddedToGame = new EventAddedToGame(player1 , true , true);
		addedToGame = new AddedToGame(evtAddedToGame);
		renderCardPerformed = new RenderCardPerformed(evtNotifyCardPerformed);
		renderRejectCard = new RenderRejectCard(evtRejectCard);
		renderHatchBlocked = new RenderHatchBlocked(evtHatchBlocked);
		renderHumanWin = new RenderHumanWin(evtHumanWin);
		renderRejectHuman = new RenderRejectHumanCard(evtRejectCardHuman);
		renderChatMessage = new RenderChatMessage(evtChatMessage);
		renderRetired = new RenderRetired(evtRetired);
		renderClosingGame = new RenderClosingGame(evtClosingGame);
		
	}

	@Test
	public void test() {
		
		/* verifica il corretto funzionamento della classe addedToGame */
		assertEquals(addedToGame.getEvt() , evtAddedToGame);
		assertEquals(addedToGame.isPossible(client) , true);
		System.out.println(addedToGame.getEvt().getGenerator().getName());
		assertEquals(addedToGame.render(client) , null);
		assertEquals(client.getPlayerClientState() , PlayerClientState.CONNECTED);
		evtAddedToGame = new EventAddedToGame(player1 , false , false);
		addedToGame = new AddedToGame(evtAddedToGame);
		assertEquals(addedToGame.render(client) , null);
		assertEquals(client.getIsInterfaceBlocked() , true);
		
		/* verifica il corretto funzionamento della classe renderEnvironment */
		client.setMap(new Galvani());
		assertEquals(renderEnvironment.isPossible(client) , false);
		client.setMap(null);
		assertTrue(renderEnvironment.isPossible(client));
		assertEquals(renderEnvironment.render(client) , null);
		assertEquals(client.getIsInterfaceBlocked() , true);
		
		/* verifica il metodo isPossible delle azioni di notifica */
		assertTrue(renderWin.isPossible(client));
		assertTrue(!renderMoved.isPossible(client));
		client.setPlayerClientState(PlayerClientState.ISTURN);
		client.getPlayer().getAvatar().setIsAlive(LifeState.DEAD);
		client.getPlayer().getAvatar().setIsWinner(EndState.LOOSER);
		assertTrue(!renderMoved.isPossible(client));
		assertTrue(!renderRejectHuman.isPossible(client));
		client.getPlayer().getAvatar().setIsAlive(LifeState.ALIVE);
		client.getPlayer().getAvatar().setIsWinner(EndState.PLAYING);
		assertTrue(renderRejectHuman.isPossible(client));
		assertTrue(renderMoved.isPossible(client));
		
		assertEquals(renderMoved.render(client) , null);
		
		/* verifica il corretto funzionamente dei metodi del renderAttackDamage */
		assertTrue(renderAttackDamage.isPossible(client));
		assertTrue(renderAttackDamage.render(client) instanceof EventContinue);
		killed2.add(player1);
		sufferAttack = new EventSufferAttack(player1 , killed2);
		renderAttackDamage = new RenderAttackDamage(sufferAttack);
		
		assertTrue(renderSpotlight.isPossible(client));
		assertTrue(renderSpotlight.render(client) instanceof EventContinue);
		assertTrue(renderSpotlight.render(client2) instanceof EventContinue);
		
		assertTrue(renderTurn.isPossible(client));
		assertEquals(renderTurn.render(client) , null);
		assertEquals(client.getIsInterfaceBlocked() , false);
		assertEquals(renderTurn.render(client2) , null);
		assertEquals(client2.getIsInterfaceBlocked() , true);
		
		assertTrue(renderError.isPossible(client));
		assertEquals(renderError.render(client2) , null);
		assertEquals(renderError.render(client) , null);
		assertEquals(client.getIsInterfaceBlocked() , false);
		
		assertTrue(renderUseDefenseCard.render(client) instanceof EventContinue);
		assertTrue(renderUseDefenseCard.render(client2) instanceof EventContinue);
		
		assertEquals(renderEffectCard.isPossible(client) , true);
		assertEquals(renderEffectCard.render(client) , null);
		assertEquals(renderEffectCard.render(client2) , null);
		
		assertEquals(renderNoise.isPossible(client) , true);
		assertEquals(renderNoise.render(client) , null);
		
		client.getPlayer().getAvatar().setIsAlive(LifeState.DEAD);
		client.getPlayer().getAvatar().setIsWinner(EndState.LOOSER);
		assertTrue(!renderHatchBlocked.isPossible(client));
		assertTrue(!renderWin.isPossible(client));
		assertEquals(renderAttackDamage.isPossible(client) , false);
		assertEquals(renderSpotlight.isPossible(client) , false);
		assertEquals(renderNoise.isPossible(client) , false);
		assertEquals(renderEffectCard.isPossible(client) , false);
		assertEquals(renderTurn.isPossible(client) , false);
		client.getPlayer().getAvatar().setIsAlive(LifeState.ALIVE);
		client.getPlayer().getAvatar().setIsWinner(EndState.PLAYING);
		assertTrue(renderHatchBlocked.isPossible(client));
		
		declarePosition = new EventDeclarePosition(player1 , declared);
		renderSpotlight = new RenderSpotlight(declarePosition);
		assertTrue(renderSpotlight.render(client) instanceof EventContinue);
		
		/* verifica il corretto funzionamento del metodo isPossible delle varie azioni */
		assertEquals(renderAliensWin.isPossible(client) , true);
		assertEquals(renderAliensWin.getEvt() , evtNotifyAliensWin);
		player1.setAvatar(avatar2);
		player1.setAvatar(avatar1);
		client.getPlayer().getAvatar().setIsAlive(LifeState.DEAD);
		client.getPlayer().getAvatar().setIsWinner(EndState.LOOSER);
		assertTrue(!renderRejectCard.isPossible(client));
		assertTrue(!renderCardPerformed.isPossible(client));
		evtNotifyAliensWin = new EventNotifyAliensWin(player1 , winners , false);
		renderAliensWin = new RenderAliensWin(evtNotifyAliensWin);
		assertEquals(renderAliensWin.render(client) , null);
		assertTrue(!client.getIsInterfaceBlocked());
		client.getPlayer().getAvatar().setIsAlive(LifeState.ALIVE);
		client.getPlayer().getAvatar().setIsWinner(EndState.PLAYING);
		assertTrue(renderRejectCard.isPossible(client));
		assertTrue(renderCardPerformed.isPossible(client));
		assertEquals(renderAttacked.isPossible(client) , true);
		
		/* verifica il corretto funzionamento dei metodi della classe renderAttacked */
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
		
		/* verifica il corretto funzionamento del metodo isPossible delle varie azioni */
		client.setPlayerClientState(PlayerClientState.PLAYING);
		assertTrue(renderTeleport.render(client) instanceof EventContinue);
		assertEquals(renderDrown.isPossible(client) , false);
		client.setPlayerClientState(PlayerClientState.ISTURN);
		client.getPlayer().getAvatar().setIsAlive(LifeState.DEAD);
		client.getPlayer().getAvatar().setIsWinner(EndState.LOOSER);	
		assertTrue(!renderHumanWin.isPossible(client));
		assertEquals(renderDrown.isPossible(client) , false);
		assertTrue(!renderRetired.isPossible(client));
		client.getPlayer().getAvatar().setIsAlive(LifeState.ALIVE);
		client.getPlayer().getAvatar().setIsWinner(EndState.WINNER);	
		assertTrue(renderRetired.isPossible(client));
		assertTrue(renderChatMessage.isPossible(client));
		assertTrue(renderHumanWin.isPossible(client));
		assertTrue(renderClosingGame.isPossible(client));
		assertEquals(renderDrown.isPossible(client) , true);		
		
		/* verifica il corretto funzionamento dei metodi della classe renderDrown */
		assertEquals(renderDrown.render(client).getGenerator() , evtNoiseMySect.getGenerator());
		card = new SectorCard(SectorCardType.SILENCE , false);
		evtDrown = new EventDrown(player1 , null , card);
		renderDrown = new RenderDrown(evtDrown);
		assertEquals(renderDrown.render(client) , null);
		assertEquals(client.getIsInterfaceBlocked() , false);
		renderDrown = new RenderDrown(evtDrown2);
		card2 = new HatchCard(HatchCardType.RED);
		evtDrown2 = new EventDrown(player1 , null , card2);
		renderDrown = new RenderDrown(evtDrown2);
		renderDrown.render(client);
		card2 = new HatchCard(HatchCardType.GREEN);
		evtDrown2 = new EventDrown(player1 , null , card2);
		renderDrown = new RenderDrown(evtDrown2);
		client.getPlayer().getAvatar().setCurrentSector(sector3);
		((Hatch)client.getPlayer().getAvatar().getCurrentSector()).setIsOpen(true);
		assertTrue(renderDrown.render(client) instanceof EventHumanWin);
		assertEquals(client.getPlayerClientState() , PlayerClientState.WINNER);
		assertTrue(client.getIsInterfaceBlocked());
		assertTrue(client.getIsInterfaceBlocked());
		card3 = new ObjectCard(ObjectCardType.ADRENALINE);
		evtDrown2 = new EventDrown(player1 , null , card3);
		client.getPlayer().getAvatar().setIsWinner(EndState.WINNER);
		client.getPlayer().getAvatar().setIsAlive(LifeState.DEAD);
		assertEquals(renderDrown.check(client) , true);
		
		/* controllo generale sui metodi delle azioni di notifica */
		assertTrue(addedToGame.getEvt() instanceof EventAddedToGame);
		assertEquals(client.getPlayer() , player1);
		assertEquals(renderHatchBlocked.render(client) , null);
		client.setIsMyTurn(true);
		assertTrue(client.getIsMyTurn());
		killed2.add(player1);
		sufferAttack = new EventSufferAttack(player1 , killed2);
		renderAttackDamage = new RenderAttackDamage(sufferAttack);
		assertTrue(client.getIsInterfaceBlocked());
		evtDrown = new EventDrown(player1 , null , null);
		renderDrown = new RenderDrown(evtDrown);
		assertEquals(renderDrown.render(client) , null);
		player2.getAvatar().getMyCards().add(card3);
		assertTrue(renderCardPerformed.render(client2) instanceof EventContinue);
		assertTrue(renderRejectCard.render(client) instanceof EventContinue);
		assertEquals(renderRejectHuman.render(client) , null);
		assertEquals(renderRejectHuman.render(client2) , null);
		assertTrue(renderHumanWin.render(client) instanceof EventFinishTurn);
		assertTrue(client.getIsInterfaceBlocked());
		assertEquals(renderHumanWin.render(client2) , null);
		assertEquals(!client2.getIsInterfaceBlocked(), false);
		assertEquals(renderChatMessage.render(client) , null);
		assertTrue(renderRetired.render(client) instanceof EventFinishTurn);
		
	}

}
