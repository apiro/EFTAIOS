package it.polimi.ingsw.cg_38.controller.action;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import it.polimi.ingsw.cg_38.controller.GameState;
import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.controller.gameEvent.EventChat;
import it.polimi.ingsw.cg_38.controller.gameEvent.EventDEFENSE;
import it.polimi.ingsw.cg_38.controller.gameEvent.EventHatchBlocked;
import it.polimi.ingsw.cg_38.controller.gameEvent.EventRejectCard;
import it.polimi.ingsw.cg_38.controller.gameEvent.EventRetired;
import it.polimi.ingsw.cg_38.controller.notifyEvent.EventNotifyChatMessage;
import it.polimi.ingsw.cg_38.controller.notifyEvent.EventNotifyRetired;
import it.polimi.ingsw.cg_38.controller.notifyEvent.EventRejectCardHuman;
import it.polimi.ingsw.cg_38.model.Alien;
import it.polimi.ingsw.cg_38.model.Avatar;
import it.polimi.ingsw.cg_38.model.GameModel;
import it.polimi.ingsw.cg_38.model.Name;
import it.polimi.ingsw.cg_38.model.Player;
import it.polimi.ingsw.cg_38.model.Turn;
import it.polimi.ingsw.cg_38.model.deck.ObjectCard;
import it.polimi.ingsw.cg_38.model.deck.ObjectCardType;
import it.polimi.ingsw.cg_38.model.map.Dangerous;
import it.polimi.ingsw.cg_38.model.map.Sector;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.Before;
import org.junit.Test;

public class GameAction3Test {

	HatchBlocked hatchBlocked;
	Defense defense;
	Reject reject;
	Retire retire;
	Chat chat;	
	
	EventHatchBlocked evtHatch;
	EventDEFENSE evtDefense;
	EventRejectCard evtRejectCard;
	EventRetired evtRetired;
	EventChat evtChat;
	
	Player player1;
	Player player2;
	Player player3;
	Player player4;
	Player player5;
	Player player6;
	Player player7;
	
	String message;
	
	Turn turn1;
	Turn turn2;
	Turn turn3;
	Turn turn4;
	Turn turn5;
	Turn turn6;
	Turn turn7;
	
	List<Player> addPlayers;
	
	ObjectCard card2;
	
	Sector sector1;
	
	Avatar avatar1;
	Avatar avatar2;
	Avatar avatar3;
	Avatar avatar4;
	Avatar avatar5;
	Avatar avatar6;
	Avatar avatar7;
	
	GameModel model1;
	
	NotifyEvent evtNotify;
	
	@Before
	public void init() throws ParserConfigurationException, Exception {
		
		message = "Welcome";
		player1 = new Player("albi");
		
		model1 = new GameModel("Galvani");
		
		turn1 = new Turn(player1);
		turn1.setHasMoved(true);
		
		sector1 = new Dangerous();
		
		card2 = new ObjectCard(ObjectCardType.ADRENALINE);
		
		avatar1 = new Alien(Name.ALIEN1 , sector1);
		
		addPlayers = new ArrayList<Player>();
		
		evtHatch = new EventHatchBlocked(player1);
		evtDefense = new EventDEFENSE(player1);
		evtRejectCard = new EventRejectCard(player1 , card2);
		evtRetired = new EventRetired(player1);
		evtChat = new EventChat(player1 , message);
		
		hatchBlocked = new HatchBlocked(evtHatch);
		reject = new Reject(evtRejectCard);
		retire = new Retire(evtRetired);
		chat = new Chat(evtChat);
		
		player1.setAvatar(avatar1);
		addPlayers.add(player1);
		model1.setGamePlayers(addPlayers);
		
		model1.setActualTurn(turn1);
		
	}
	
	@Test
	public void test() {
		
		model1.setActualTurn(turn1);
		/* verifica il corrretto funzionamento del metodo isPossible dell'azione di scarto della 
		 * carta oggetto */
		assertTrue(!reject.isPossible(model1));
		model1.getActualTurn().getCurrentPlayer().getAvatar().addCard(card2);
		model1.setGameState(GameState.CLOSING);
		assertTrue(!reject.isPossible(model1));
		model1.setGameState(GameState.RUNNING);
		assertTrue(reject.isPossible(model1));
		
		assertEquals(reject.getCard() , card2);
		
		/* verifica il corrretto funzionamento della perform dell'azione di rifiuto della
		 * carta oggetto */
		assertTrue(reject.perform(model1).get(0)instanceof EventRejectCardHuman);
		assertTrue(!model1.getActualTurn().getCurrentPlayer().getAvatar().getMyCards().contains(card2));
		
		sector1 = model1.getGameMap().searchSectorByName("Hatch");
		model1.getActualTurn().getCurrentPlayer().getAvatar().setCurrentSector(sector1);
		evtNotify = hatchBlocked.perform(model1).get(0);
		
		/* verifica il corrretto funzionamento della perform dell'azione di ritiro di
		 * un giocatore */
		assertTrue(retire.perform(model1).get(0) instanceof EventNotifyRetired);
		
		/* verifica il corrretto funzionamento della perform dell'azione invio di
		 * un messaggio di chat */
		assertTrue(chat.perform(model1).get(0) instanceof EventNotifyChatMessage);
	}

}
