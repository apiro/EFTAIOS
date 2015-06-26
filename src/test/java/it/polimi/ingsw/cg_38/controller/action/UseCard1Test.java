package it.polimi.ingsw.cg_38.controller.action;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import it.polimi.ingsw.cg_38.controller.GameState;
import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.controller.gameEvent.EventADRENALINE;
import it.polimi.ingsw.cg_38.controller.gameEvent.EventATTACKCARD;
import it.polimi.ingsw.cg_38.controller.gameEvent.EventSPOTLIGHT;
import it.polimi.ingsw.cg_38.controller.notifyEvent.EventCardUsed;
import it.polimi.ingsw.cg_38.controller.notifyEvent.EventNotifyCardPerformed;
import it.polimi.ingsw.cg_38.controller.notifyEvent.EventRejectCardAlien;
import it.polimi.ingsw.cg_38.controller.notifyEvent.EventSufferAttack;
import it.polimi.ingsw.cg_38.model.Alien;
import it.polimi.ingsw.cg_38.model.Avatar;
import it.polimi.ingsw.cg_38.model.GameModel;
import it.polimi.ingsw.cg_38.model.Human;
import it.polimi.ingsw.cg_38.model.Name;
import it.polimi.ingsw.cg_38.model.Player;
import it.polimi.ingsw.cg_38.model.Turn;
import it.polimi.ingsw.cg_38.model.deck.Card;
import it.polimi.ingsw.cg_38.model.deck.ObjectCard;
import it.polimi.ingsw.cg_38.model.deck.ObjectCardType;
import it.polimi.ingsw.cg_38.model.map.Sector;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.Before;
import org.junit.Test;

/** contiene i test delle azioni relativo l'utilizzo delle carte attacco, luci e adrenalina */
public class UseCard1Test {

	UseAdrenalineCard useAdrenalineCard1;
	UseAdrenalineCard useAdrenalineCard2;
	UseAdrenalineCard useAdrenalineCard3;
	UseAttackCard useAttackCard1;
	UseAttackCard useAttackCard2;
	UseAttackCard useAttackCard3;
	UseAttackCard useAttackCard4;
	UseLightsCard useLightsCard1;
	UseLightsCard useLightsCard2;
	UseLightsCard useLightsCard3;
	
	EventADRENALINE evtAdren1;
	EventADRENALINE evtAdren2;
	EventADRENALINE evtAdren3;
	EventATTACKCARD evtAttackCard1;
	EventATTACKCARD evtAttackCard2;
	EventATTACKCARD evtAttackCard3;
	EventATTACKCARD evtAttackCard4;
	EventSPOTLIGHT evtLights1;
	EventSPOTLIGHT evtLights2;
	EventSPOTLIGHT evtLights3;
	
	List<NotifyEvent> evtAttacked;
	List<NotifyEvent> evtDeclarePosition1;
	List<NotifyEvent> evtCardUsed;
	
	Player player1;
	Player player2;
	Player player3;
	Player player4;
	Player player5;
	Player player6;
	
	Turn turn1;
	Turn turn2;
	Turn turn3;
	Turn turn4;
	Turn turn5;
	Turn turn6;
	
	List<Player> killedPlayer;
	List<Player> addPlayers;
	List<Player> toDeclare;
	
	Card adrenalineCard1;
	Card adrenalineCard2;
	Card attackCard1;
	Card attackCard2;
	Card lightsCard1;
	Card lightsCard2;
	
	Avatar avatar1;
	Avatar avatar2;
	Avatar avatar3;
	Avatar avatar4;
	Avatar avatar5;
	Avatar avatar6;
	
	Sector sector1;
	Sector sector2;
	Sector sector3;	
	Sector sector4;
	Sector sector5;
	
	Card defenseCard;
	
	GameModel model1;
	
	@Before
	public void init() throws ParserConfigurationException, Exception {
		
		player1 = new Player("albi");
		player2 = new Player("scimmiu");
		player3 = new Player("shane");
		player4 = new Player("diffi");
		player5 = new Player("piccio");
		player6 = new Player("raf");
		
		adrenalineCard1 = new ObjectCard(ObjectCardType.ADRENALINE);
		adrenalineCard2 = new ObjectCard(ObjectCardType.ADRENALINE);
		attackCard1 = new ObjectCard(ObjectCardType.ATTACKCARD);
		attackCard2 = new ObjectCard(ObjectCardType.ATTACKCARD);
		lightsCard1 = new ObjectCard(ObjectCardType.SPOTLIGHT);
		lightsCard2 = new ObjectCard(ObjectCardType.SPOTLIGHT);
		
		model1 = new GameModel("Galvani");
		
		turn1 = new Turn(player1);
		turn2 = new Turn(player2);
		turn3 = new Turn(player3);
		turn4 = new Turn(player4);
		turn5 = new Turn(player5);
		turn6 = new Turn(player6);
		
		sector1 = model1.getGameMap().getTable().get(2).get(4);
		sector2 = model1.getGameMap().getTable().get(3).get(6);
		sector3 = model1.getGameMap().getTable().get(5).get(11);
		sector4 = model1.getGameMap().getTable().get(8).get(13);
		sector5 = model1.getGameMap().getTable().get(3).get(5);
		
		avatar1 = new Alien(Name.ALIEN1 , sector2);
		avatar2 = new Human(Name.HUMAN1 , sector3);
		avatar3 = new Human(Name.HUMAN2 , sector2);
		avatar4 = new Human(Name.HUMAN4 , sector4);
		avatar5 = new Human(Name.HUMAN3 , sector4);
		avatar6 = new Alien(Name.ALIEN2 , sector5);
		
		avatar1.addCard(adrenalineCard1);
		avatar2.addCard(adrenalineCard2);
		avatar3.addCard(lightsCard1);
		avatar5.addCard(attackCard1);
		avatar5.addCard(attackCard2);
		avatar6.addCard(lightsCard2);
		avatar6.addCard(attackCard2);
		
		defenseCard = new ObjectCard(ObjectCardType.DEFENSE);
		
		killedPlayer = new ArrayList<Player>();
		addPlayers = new ArrayList<Player>();
		toDeclare = new ArrayList<Player>();
		
		player1.setAvatar(avatar1);
		player2.setAvatar(avatar2);
		player3.setAvatar(avatar3);
		player4.setAvatar(avatar4);
		player5.setAvatar(avatar5);
		player6.setAvatar(avatar6);
		addPlayers.add(player1);
		addPlayers.add(player2);
		addPlayers.add(player3);
		addPlayers.add(player4);
		addPlayers.add(player5);
		addPlayers.add(player6);
		model1.setGamePlayers(addPlayers);
		killedPlayer.add(player4);
		
		model1.setActualTurn(turn1);
		
		evtAdren1 = new EventADRENALINE(player1 , adrenalineCard1);
		evtAdren2 = new EventADRENALINE(player2 , adrenalineCard2);
		evtAdren3 = new EventADRENALINE(player3 , adrenalineCard2);
		evtAttackCard1 = new EventATTACKCARD(player5 , attackCard1);
		evtAttackCard2 = new EventATTACKCARD(player6 , attackCard2);
		evtAttackCard3 = new EventATTACKCARD(player4 , attackCard2);
		evtAttackCard4 = new EventATTACKCARD(player1 , attackCard1);
		evtLights1 = new EventSPOTLIGHT(player3 , sector2 , lightsCard1);
		evtLights2 = new EventSPOTLIGHT(player6 , sector5 , lightsCard2);
		evtLights3 = new EventSPOTLIGHT(player2 , sector3 , lightsCard2);
		
		useAdrenalineCard1 = new UseAdrenalineCard(evtAdren1);
		useAdrenalineCard2 = new UseAdrenalineCard(evtAdren2);
		useAdrenalineCard3 = new UseAdrenalineCard(evtAdren3);
		useAttackCard1 = new UseAttackCard(evtAttackCard1);
		useAttackCard2 = new UseAttackCard(evtAttackCard2);
		useAttackCard3 = new UseAttackCard(evtAttackCard3);
		useAttackCard4 = new UseAttackCard(evtAttackCard4);
		useLightsCard1 = new UseLightsCard(evtLights1);
		useLightsCard2 = new UseLightsCard(evtLights2);
		useLightsCard3 = new UseLightsCard(evtLights3);
		
		toDeclare.add(player1);
		toDeclare.add(player3);
		toDeclare.add(player6);		
	}
	
	@Test
	public void test() {

		/* verifica il corretto funzionamento del metodo isPossible dell'utilizzo 
		 * della carta adrenalina */
		assertEquals(useAdrenalineCard1.isPossible(model1) , false);
		
		/* verifica il corretto funzionamento della perform dell'utilizzo 
		 * della carta adrenalina */
		model1.setGameState(GameState.RUNNING);
		evtCardUsed = useAdrenalineCard1.perform(model1);
		assertTrue(evtCardUsed.get(0) instanceof EventRejectCardAlien);
		
		/* verifica il corretto funzionamento del metodo isPossible dell'utilizzo 
		 * della carta attacco */
		assertEquals(useAttackCard4.isPossible(model1) , false);
		
		model1.setActualTurn(turn2);
		
		/* verifica il corretto funzionamento del metodo isPossible dell'utilizzo 
		 * della carta luci e della carta adrenaline */
		assertEquals(useLightsCard3.isPossible(model1) , false);
		model1.setGameState(GameState.ACCEPTING);
		assertEquals(useAdrenalineCard2.isPossible(model1) , false);
		model1.setGameState(GameState.RUNNING);
		assertEquals(useAdrenalineCard2.isPossible(model1) , true); 
		model1.getActualTurn().setHasUsedObjectCard(true);
		assertEquals(useAdrenalineCard2.isPossible(model1) , false);
		
		/* verifica il corretto funzionamento della perform dell'utilizzo 
		 * della carta adrenalina */
		useAdrenalineCard2.perform(model1);
		assertEquals(model1.getActualTurn().getCurrentPlayer().getAvatar().getIsPowered() , true);
		assertEquals(model1.getActualTurn().getCurrentPlayer().getAvatar().getMyCards() , new ArrayList<Card>());
		
		model1.setActualTurn(turn3);
		
		/* verifica il corretto funzionamento del metodo isPossible dell'utilizzo 
		 * della carta luci e della carta adrenalina */
		assertEquals(useAdrenalineCard3.isPossible(model1) , false);
		model1.setGameState(GameState.ACCEPTING);
		assertEquals(useLightsCard1.isPossible(model1) , false);
		model1.setGameState(GameState.RUNNING);
		assertEquals(useLightsCard1.isPossible(model1) , true);
		model1.getActualTurn().setHasUsedObjectCard(true);
		assertEquals(useLightsCard1.isPossible(model1) , false);
		
		/* verifica il corretto funzionamento della perform dell'utilizzo 
		 * della carta luci */
		evtDeclarePosition1 = useLightsCard1.perform(model1);
		assertEquals(model1.getActualTurn().getCurrentPlayer().getAvatar().getMyCards() , new ArrayList<Card>());
		assertTrue(evtDeclarePosition1.get(0) instanceof EventNotifyCardPerformed);
		
		model1.setActualTurn(turn5);
		
		/* verifica il corretto funzionamento del metodo isPossible dell'utilizzo 
		 * della carta attacco  */
		model1.setGameState(GameState.ACCEPTING);
		assertEquals(useAttackCard1.isPossible(model1) , false);
		model1.setGameState(GameState.RUNNING);
		model1.getActualTurn().getCurrentPlayer().getAvatar().addCard(attackCard1);
		model1.getActualTurn().setHasUsedObjectCard(true);
		assertEquals(useAttackCard1.isPossible(model1) , false);
		model1.getActualTurn().setHasUsedObjectCard(false);
		assertEquals(useAttackCard1.isPossible(model1) , true);
		assertEquals(useAttackCard1.getCard() , attackCard1);
		
		/* verifica il corretto funzionamento della perform dell'utilizzo 
		 * della carta attacco */
		model1.getActualTurn().setHasAttacked(true);
		assertEquals(useAttackCard1.perform(model1).size() , 1);
		model1.getActualTurn().setHasAttacked(false);
		model1.getGamePlayers().get(3).getAvatar().addCard(defenseCard);
		assertTrue(useAttackCard1.perform(model1).get(0) instanceof EventCardUsed);
		model1.getActualTurn().setHasAttacked(false);
		assertEquals(model1.getActualTurn().getCurrentPlayer().getAvatar().getMyCards().size() , 1);
		evtAttacked = useAttackCard1.perform(model1);
		assertEquals(((EventSufferAttack)evtAttacked.get(0)).getKilled() , killedPlayer);
		
		model1.setActualTurn(turn6);
		
		/* verifica il corretto funzionamento della perform dell'utilizzo 
		 * della carta luci e della carta attacco */
		useLightsCard2.perform(model1);
		assertTrue(!(model1.getActualTurn().getCurrentPlayer().getAvatar().getMyCards().contains(lightsCard2)));
		assertEquals(useLightsCard2.isPossible(model1) , false);
		useAttackCard2.perform(model1);
		assertTrue(!(model1.getActualTurn().getCurrentPlayer().getAvatar().getMyCards().contains(attackCard2)));
	
		/* verifica il corretto funzionamento del metodo isPossible dell'utilizzo 
		 * della carta attacco  */
		assertEquals(useAttackCard2.isPossible(model1) , false);
	}

}
