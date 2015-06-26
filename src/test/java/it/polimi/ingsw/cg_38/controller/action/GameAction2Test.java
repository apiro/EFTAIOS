package it.polimi.ingsw.cg_38.controller.action;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import it.polimi.ingsw.cg_38.controller.GameState;
import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.controller.gameEvent.EventAliensWinner;
import it.polimi.ingsw.cg_38.controller.gameEvent.EventAttack;
import it.polimi.ingsw.cg_38.controller.gameEvent.EventDEFENSE;
import it.polimi.ingsw.cg_38.controller.gameEvent.EventHumanWin;
import it.polimi.ingsw.cg_38.controller.notifyEvent.EventAttacked;
import it.polimi.ingsw.cg_38.controller.notifyEvent.EventCardUsed;
import it.polimi.ingsw.cg_38.controller.notifyEvent.EventNotifyHumanWin;
import it.polimi.ingsw.cg_38.controller.notifyEvent.EventRejectCardAlien;
import it.polimi.ingsw.cg_38.controller.notifyEvent.EventSufferAttack;
import it.polimi.ingsw.cg_38.model.Alien;
import it.polimi.ingsw.cg_38.model.Avatar;
import it.polimi.ingsw.cg_38.model.EndState;
import it.polimi.ingsw.cg_38.model.GameModel;
import it.polimi.ingsw.cg_38.model.Human;
import it.polimi.ingsw.cg_38.model.LifeState;
import it.polimi.ingsw.cg_38.model.Name;
import it.polimi.ingsw.cg_38.model.Player;
import it.polimi.ingsw.cg_38.model.Turn;
import it.polimi.ingsw.cg_38.model.deck.Card;
import it.polimi.ingsw.cg_38.model.deck.ObjectCard;
import it.polimi.ingsw.cg_38.model.deck.ObjectCardType;
import it.polimi.ingsw.cg_38.model.map.Dangerous;
import it.polimi.ingsw.cg_38.model.map.Hatch;
import it.polimi.ingsw.cg_38.model.map.HumanStartingPoint;
import it.polimi.ingsw.cg_38.model.map.Safe;
import it.polimi.ingsw.cg_38.model.map.Sector;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.Before;
import org.junit.Test;

/** contiene i test delle aziioni di attacco, di vittoria di un umano, di vittoria degli alieni e
 * dell'utilizzo della carta difesa  */
public class GameAction2Test {

	Attack attack1;
	Attack attack2;
	Attack attack3;
	HumanWin humanWin;
	HumanWin humanWin2;
	AliensWin aliensWin1;
	AliensWin aliensWin2;
	Defense defense;
	
	EventAttack evtAttack1;
	EventAttack evtAttack2;
	EventAttack evtAttack3;
	EventAttack evtAttack4;
	EventHumanWin evtHuman;
	EventHumanWin evtHuman2;
	EventAliensWinner evtAliensWinner1;
	EventAliensWinner evtAliensWinner2;
	EventAliensWinner evtAliensWinner3;
	EventDEFENSE evtDefense;
	
	List<NotifyEvent> evtAttacked1;
	List<NotifyEvent> evtAttacked2;
	List<NotifyEvent> evtNotifyAliensWin;
	
	Player player1;
	Player player2;
	Player player3;
	Player player4;
	Player player5;
	Player player6;
	Player player7;
	
	Turn turn1;
	Turn turn2;
	Turn turn3;
	Turn turn4;
	Turn turn5;
	Turn turn6;
	Turn turn7;
	
	Card card;
	
	List<Player> killedPlayer;
	List<Player> addPlayers;
	
	Sector sector1;
	Sector sector2;
	Sector sector3;
	Sector sector4;
	Sector sector5;
	Sector sector6;
	Hatch sector7;
	
	Avatar avatar1;
	Avatar avatar2;
	Avatar avatar3;
	Avatar avatar4;
	Avatar avatar5;
	Avatar avatar6;
	Avatar avatar7;
	
	GameModel model1;
	
	
	
	
	@Before
	public void init() throws ParserConfigurationException, Exception {
	
		player1 = new Player("albi");
		player2 = new Player("scimmiu");
		player2.getAvatar();
		player3 = new Player("shane");
		player4 = new Player("diffi");
		player5 = new Player("reda");
		player6 = new Player("piccio");
		player7 = new Player("poldo");
		
		model1 = new GameModel("Galvani");
		
		turn1 = new Turn(player1);
		turn1.setHasMoved(true);
		turn2 = new Turn(player2);
		turn2.setHasMoved(true);
		turn3 = new Turn(player3);
		turn4 = new Turn(player4);
		turn5 = new Turn(player5);
		turn6 = new Turn(player6);
		turn7 = new Turn(player7);
		
		sector1 = new Dangerous();
		sector1.setCol(5);
		sector1.setRow(11);
		sector2 = model1.getGameMap().getTable().get(3).get(6);
		sector3 = model1.getGameMap().getTable().get(3).get(5);
		sector4 = new Hatch();
		sector5 = new HumanStartingPoint();
		sector6 = new Safe();
		sector7 = new Hatch();
		
		avatar1 = new Alien(Name.ALIEN1 , sector2);
		avatar2 = new Alien(Name.ALIEN2 , sector2);
		avatar3 = new Human(Name.HUMAN1 , sector2);
		avatar4 = new Human(Name.HUMAN2 , sector4);
		avatar5 = new Human(Name.HUMAN3 , sector5);
		avatar6 = new Alien(Name.ALIEN3 , sector4);
		avatar7 = new Human(Name.HUMAN4 , sector7);
		
		killedPlayer = new ArrayList<Player>();
		addPlayers = new ArrayList<Player>();		
		
		evtAttack1 = new EventAttack(player2 , sector2);
		evtAttack2 = new EventAttack(player1 , sector1);
		evtAttack3 = new EventAttack(player3 , sector3);
		evtAttack4 = new EventAttack(player6 , sector3);
		evtAliensWinner1 = new EventAliensWinner(player3 , false);
		evtAliensWinner2 = new EventAliensWinner(player1 , true);
		evtAliensWinner3 = new EventAliensWinner(player6 , true);
		evtHuman = new EventHumanWin(player7);
		evtHuman2 = new EventHumanWin(player4);
		evtDefense = new EventDEFENSE(player7);
		
		card = new ObjectCard(ObjectCardType.DEFENSE);
		
		attack1 = new Attack(evtAttack1);
		attack2 = new Attack(evtAttack2);
		attack3 = new Attack(evtAttack3);
		aliensWin1 = new AliensWin(evtAliensWinner1);
		aliensWin2 = new AliensWin(evtAliensWinner2);
		humanWin = new HumanWin(evtHuman);
		humanWin2 = new HumanWin(evtHuman2);
		
		player1.setAvatar(avatar1);
		player2.setAvatar(avatar2);
		player3.setAvatar(avatar3);
		player4.setAvatar(avatar4);
		player5.setAvatar(avatar5);
		player6.setAvatar(avatar6);
		player7.setAvatar(avatar7);
		addPlayers.add(player1);
		addPlayers.add(player2);
		addPlayers.add(player3);
		addPlayers.add(player4);
		addPlayers.add(player5);
		addPlayers.add(player6);
		addPlayers.add(player7);
		model1.setGamePlayers(addPlayers);
		
		model1.setActualTurn(turn1);
		
		killedPlayer.add(player1);
		killedPlayer.add(player3);		
	}
	
	@Test
	public void test() {
		
		/* verifica il corrretto funzionamento del metodo isPossible dell'azione di attacco */
		model1.getActualTurn().setHasAttacked(true);
		assertEquals(attack2.isPossible(model1) , false);
		
		/* verifica che se gli alieni vincono il gioco viene chiuso */
		aliensWin2.perform(model1);
		assertEquals(model1.getGameState() , GameState.CLOSING);
		assertEquals(model1.getActualTurn().getCurrentPlayer().getAvatar().getIsWinner() , EndState.WINNER);
		
		model1.setActualTurn(turn2);
		
		/* verifica il corrretto funzionamento del metodo isPossible dell'azione di attacco */
		assertEquals(attack1.isPossible(model1) , false);
		model1.getActualTurn().setHasMoved(true);
		model1.getActualTurn().setHasDraw(true);
		assertEquals(attack1.isPossible(model1) , false);
		model1.getActualTurn().setHasDraw(false);
		model1.setGameState(GameState.ACCEPTING);
		assertEquals(attack1.isPossible(model1) , false);
		model1.setGameState(GameState.RUNNING);
		assertEquals(attack1.isPossible(model1) , true);
		model1.getGamePlayers().get(0).getAvatar().setIsAlive(LifeState.DEAD);
		model1.getGamePlayers().get(1).getAvatar().setIsAlive(LifeState.DEAD);
		model1.getGamePlayers().get(2).getAvatar().setIsAlive(LifeState.DEAD);
		model1.getGamePlayers().get(3).getAvatar().setIsAlive(LifeState.DEAD);
		model1.getGamePlayers().get(4).getAvatar().setIsAlive(LifeState.DEAD);
		model1.getGamePlayers().get(5).getAvatar().setIsAlive(LifeState.DEAD);
		model1.getGamePlayers().get(6).getAvatar().setIsAlive(LifeState.DEAD);
		evtAttacked1 = attack1.perform(model1);
		assertEquals(((EventAttacked)evtAttacked1.get(1)).getAreThereOtherHumans() , false);
		model1.getGamePlayers().get(1).getAvatar().setIsAlive(LifeState.ALIVE);
		model1.getGamePlayers().get(0).getAvatar().setIsAlive(LifeState.ALIVE);
		model1.getGamePlayers().get(2).getAvatar().setIsAlive(LifeState.ALIVE);
		model1.getGamePlayers().get(3).getAvatar().setIsAlive(LifeState.ALIVE);
		evtAttacked1 = attack1.perform(model1);	
		assertEquals(((EventSufferAttack)evtAttacked1.get(0)).getKilled() , killedPlayer);
		
		model1.setActualTurn(turn3);
		
		/* verifica il corrretto funzionamento del metodo isPossible dell'azione di attacco */
		model1.setGameState(GameState.ACCEPTING);
		((Human)model1.getActualTurn().getCurrentPlayer().getAvatar()).setCanAttack(true);
		assertEquals(attack3.isPossible(model1) , false);
		model1.setGameState(GameState.RUNNING);
		((Human)model1.getActualTurn().getCurrentPlayer().getAvatar()).setCanAttack(false);
		assertEquals(attack3.isPossible(model1) , false);
		((Human)model1.getActualTurn().getCurrentPlayer().getAvatar()).setCanAttack(true);
		model1.getActualTurn().setHasAttacked(true);
		assertEquals(attack3.isPossible(model1) , false);
		
		model1.setActualTurn(turn4);
		
		/* verifica il corrretto funzionamento della perform dell'azione di attacco */
		sector1 = model1.getGameMap().searchSectorByName("Hatch");
		model1.getActualTurn().getCurrentPlayer().getAvatar().setCurrentSector(sector1);
		evtAttacked2 = humanWin2.perform(model1);
		assertEquals(((EventNotifyHumanWin)evtAttacked2.get(0)).getGenerator() , player4);
		model1.getGamePlayers().get(3).getAvatar().setIsWinner(EndState.LOOSER);
		model1.getGamePlayers().get(2).getAvatar().setIsWinner(EndState.LOOSER);
		model1.getGamePlayers().get(0).getAvatar().setIsWinner(EndState.LOOSER);
		model1.getGamePlayers().get(1).getAvatar().setIsWinner(EndState.LOOSER);
		model1.getGamePlayers().get(4).getAvatar().setIsWinner(EndState.LOOSER);
		assertEquals(humanWin2.perform(model1).size() , 3);
		
		model1.setActualTurn(turn7);
		
		/* verifica il corrretto funzionamento dell'utilizzo della carta difesa */
		model1.getActualTurn().getCurrentPlayer().getAvatar().addCard(card);
		defense = new Defense(evtDefense);
		evtAttacked2 = defense.perform(model1);
		assertTrue(!model1.getActualTurn().getCurrentPlayer().getAvatar().getMyCards().contains(card));
		assertTrue(evtAttacked2.get(0) instanceof EventRejectCardAlien);
		assertTrue(evtAttacked2.get(1) instanceof EventCardUsed);
		
	}

}
