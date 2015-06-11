package it.polimi.ingsw.cg_38.controller.action;

import static org.junit.Assert.*;

import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;

import it.polimi.ingsw.cg_38.controller.GameState;
import it.polimi.ingsw.cg_38.gameEvent.EventAttack;
import it.polimi.ingsw.cg_38.gameEvent.EventDraw;
import it.polimi.ingsw.cg_38.gameEvent.EventFinishTurn;
import it.polimi.ingsw.cg_38.gameEvent.EventMove;
import it.polimi.ingsw.cg_38.gameEvent.EventPlayerLooser;
import it.polimi.ingsw.cg_38.gameEvent.EventPlayerWinner;
import it.polimi.ingsw.cg_38.model.Alien;
import it.polimi.ingsw.cg_38.model.Avatar;
import it.polimi.ingsw.cg_38.model.Card;
import it.polimi.ingsw.cg_38.model.Dangerous;
import it.polimi.ingsw.cg_38.model.Deck;
import it.polimi.ingsw.cg_38.model.EndState;
import it.polimi.ingsw.cg_38.model.GameModel;
import it.polimi.ingsw.cg_38.model.Hatch;
import it.polimi.ingsw.cg_38.model.HatchCard;
import it.polimi.ingsw.cg_38.model.HatchCardType;
import it.polimi.ingsw.cg_38.model.HatchDeck;
import it.polimi.ingsw.cg_38.model.Human;
import it.polimi.ingsw.cg_38.model.HumanStartingPoint;
import it.polimi.ingsw.cg_38.model.LifeState;
import it.polimi.ingsw.cg_38.model.Name;
import it.polimi.ingsw.cg_38.model.ObjectCard;
import it.polimi.ingsw.cg_38.model.ObjectCardType;
import it.polimi.ingsw.cg_38.model.ObjectDeck;
import it.polimi.ingsw.cg_38.model.Player;
import it.polimi.ingsw.cg_38.model.Safe;
import it.polimi.ingsw.cg_38.model.Sector;
import it.polimi.ingsw.cg_38.model.SectorCard;
import it.polimi.ingsw.cg_38.model.SectorCardType;
import it.polimi.ingsw.cg_38.model.SectorDeck;
import it.polimi.ingsw.cg_38.model.Turn;
import it.polimi.ingsw.cg_38.notifyEvent.EventAttacked;
import it.polimi.ingsw.cg_38.notifyEvent.EventDrown;
import it.polimi.ingsw.cg_38.notifyEvent.EventFinishedTurn;
import it.polimi.ingsw.cg_38.notifyEvent.EventMoved;
import it.polimi.ingsw.cg_38.notifyEvent.EventNotifyLoose;
import it.polimi.ingsw.cg_38.notifyEvent.EventNotifyTurn;
import it.polimi.ingsw.cg_38.notifyEvent.EventNotifyWin;

import org.junit.Before;
import org.junit.Test;

public class GameActionTest {
	
	Draw draw1;
	Draw draw2;
	Draw draw3;
	Draw draw4;
	Attack attack1;
	Attack attack2;
	Attack attack3;
	FinishTurn finishTurn;
	FinishTurn finishTurn2;
	FinishTurn finishTurn3;
	Move move;
	Move move2;
	Move move3;
	Looser looser;
	Winner winner;
	

	EventDraw evtDraw1;
	EventDraw evtDraw2;
	EventDraw evtDraw3;
	EventDraw evtDraw4;
	EventFinishTurn evtFinishTurn;
	EventFinishTurn evtFinishTurn2;
	EventFinishTurn evtFinishTurn3;
	EventAttack evtAttack1;
	EventAttack evtAttack2;
	EventAttack evtAttack3;
	EventMove evtMove;
	EventMove evtMove2;
	EventMove evtMove3;
	EventPlayerWinner evtPlayerWinner;
	EventPlayerLooser evtPlayerLooser;
	
	EventAttacked evtAttacked1;
	EventAttacked evtAttacked2;
	EventDrown evtDrown1;
	EventDrown evtDrown2;
	EventDrown evtDrown3;
	EventDrown evtDrown4;
	EventMoved evtMoved;
	EventNotifyTurn evtNotifyTurn;
	EventNotifyTurn evtNotifyTurn2;
	
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
	
	ArrayList<SectorCard> sectorList1;
	ArrayList<ObjectCard> objectList1;
	ArrayList<HatchCard> hatchList1;
	ArrayList<Player> killedPlayer;
	ArrayList<Player> addPlayers;
	
	SectorDeck sectorDeck1;
	ObjectDeck objectDeck1;
	HatchDeck hatchDeck1;
	
	Card sectorCard1;
	Card sectorCard2;
	Card objectCard1;
	Card hatchCard1;
	
	Sector sector1;
	Sector sector2;
	Sector sector3;
	Sector sector4;
	Sector sector5;
	Sector sector6;
	
	Avatar avatar1;
	Avatar avatar2;
	Avatar avatar3;
	Avatar avatar4;
	Avatar avatar5;
	Avatar avatar6;
	
	GameModel model1;
	
	SectorCard drown1;
	
	ObjectCard drown2;
	

	@Before
	public void init() throws ParserConfigurationException, Exception {
		
		player1 = new Player("albi");
		player2 = new Player("scimmiu");
		player2.getAvatar();
		player3 = new Player("shane");
		player4 = new Player("diffi");
		player5 = new Player("reda");
		player6 = new Player("piccio");
		
		model1 = new GameModel("Galvani");
		
		turn1 = new Turn(player1);
		turn1.setHasMoved(true);
		turn2 = new Turn(player2);
		turn2.setHasMoved(true);
		turn3 = new Turn(player3);
		turn4 = new Turn(player4);
		turn5 = new Turn(player5);
		turn6 = new Turn(player6);
		
		sector1 = new Dangerous();
		sector1.setCol(5);
		sector1.setRow(11);
		sector2 = model1.getGameMap().getTable().get(3).get(6);
		sector3 = model1.getGameMap().getTable().get(3).get(5);
		sector4 = new Hatch();
		sector5 = new HumanStartingPoint();
		sector6 = new Safe();
		
		avatar1 = new Alien(Name.Alien1 , sector2);
		avatar2 = new Alien(Name.Alien2 , sector2);
		avatar3 = new Human(Name.Human1 , sector2);
		avatar4 = new Human(Name.Human2 , sector4);
		avatar5 = new Human(Name.Human3 , sector5);
		avatar6 = new Alien(Name.Alien3 , sector2);
		
		sectorList1 = new ArrayList<SectorCard>();
		objectList1 = new ArrayList<ObjectCard>();
		hatchList1 = new ArrayList<HatchCard>();
		killedPlayer = new ArrayList<Player>();
		addPlayers = new ArrayList<Player>();
		
		sectorDeck1 = new SectorDeck();
		objectDeck1 = new ObjectDeck();
		hatchDeck1 = new HatchDeck();
		
		sectorCard1 = new SectorCard(SectorCardType.Silence , false);
		sectorCard2 = new SectorCard(SectorCardType.MySectorNoise , true);
		objectCard1 = new ObjectCard(ObjectCardType.Attack );
		hatchCard1 = new HatchCard(HatchCardType.Green);
		
		evtDraw1 = new EventDraw(player1);
		evtDraw2 = new EventDraw(player1);
		evtDraw3 = new EventDraw(player4);
		evtDraw4 = new EventDraw(player5);
		evtAttack1 = new EventAttack(player2 , sector2);
		evtAttack2 = new EventAttack(player1 , sector1);
		evtAttack3 = new EventAttack(player3 , sector3);
		evtFinishTurn = new EventFinishTurn(player2);
		evtFinishTurn2 = new EventFinishTurn(player4);
		evtFinishTurn3 = new EventFinishTurn(player3);
		evtMove = new EventMove(player3 , sector3);
		evtMove2 = new EventMove(player3 , sector2);
		evtMove3 = new EventMove(player2 , sector4);
		evtPlayerWinner = new EventPlayerWinner(player5);
		evtPlayerLooser = new EventPlayerLooser(player6);
		
		draw1 = new Draw(evtDraw1);
		draw2 = new Draw(evtDraw2);
		draw3 = new Draw(evtDraw3);
		draw4 = new Draw(evtDraw4);
		attack1 = new Attack(evtAttack1);
		attack2 = new Attack(evtAttack2);
		attack3 = new Attack(evtAttack3);
		finishTurn = new FinishTurn(evtFinishTurn);
		finishTurn2 = new FinishTurn(evtFinishTurn2);
		finishTurn3 = new FinishTurn(evtFinishTurn3);
		move = new Move(evtMove);
		move2 = new Move(evtMove2);
		move3 = new Move(evtMove3);
		winner = new Winner(evtPlayerWinner);
		looser = new Looser(evtPlayerLooser);
		
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
		
		sectorList1.add((SectorCard)sectorCard1);
		sectorList1.add((SectorCard)sectorCard2);
		objectList1.add((ObjectCard)objectCard1);
		hatchList1.add((HatchCard)hatchCard1);
		
		sectorDeck1.setSectorDeck(sectorList1);
		objectDeck1.setObjectDeck(objectList1);
		hatchDeck1.setHatchDeck(hatchList1);
		
		model1.setActualTurn(turn1);
		model1.setDeckSector(sectorDeck1);
		model1.setDeckObject(objectDeck1);
		model1.setDeckHatch(hatchDeck1);

			
	}
	
	
	
	@Test
	public void test() {
		
			assertEquals(draw1.isPossible(model1) , false);
			model1.setGameState(GameState.RUNNING);
			assertEquals(draw1.isPossible(model1) , true);
			evtDrown1 = (EventDrown)draw1.perform(model1);	
			evtDrown2 = (EventDrown)draw2.perform(model1);
		
			assertEquals(sectorCard1 , evtDrown1.getDrown());
			
			assertEquals(sectorCard2 , evtDrown2.getDrown());
			assertEquals(objectCard1 , evtDrown2.getAdded());
			model1.getActualTurn().setHasAttacked(true);
			assertEquals(attack2.isPossible(model1) , false);
			
			model1.setActualTurn(turn2);
			model1.getActualTurn().setHasMoved(false);
			assertEquals(move3.isPossible(model1) , false);
			model1.getActualTurn().setHasMoved(true);
			assertEquals(draw1.isPossible(model1) , true);
			assertEquals(attack1.isPossible(model1) , true);
			evtAttacked1 = (EventAttacked)attack1.perform(model1);


			killedPlayer.add(player1);
			killedPlayer.add(player3);
			killedPlayer.add(player6);
			
			
			assertEquals(killedPlayer , evtAttacked1.getKilled());	
			
			model1.setGameState(GameState.ACCEPTING);
			assertEquals(finishTurn.isPossible(model1) , false);
			model1.setGameState(GameState.RUNNING);
			assertEquals(finishTurn.isPossible(model1) , true);
			evtNotifyTurn = (EventNotifyTurn)finishTurn.perform(model1);
			assertEquals(evtNotifyTurn.getPlayerOfTurn() , model1.getNextPlayer());
			
			model1.setActualTurn(turn3);
			model1.setGameState(GameState.ACCEPTING);
			assertEquals(move.isPossible(model1) , false);
			model1.setGameState(GameState.RUNNING);
			assertEquals(move.isPossible(model1) , true);
			evtMoved = (EventMoved)move.perform(model1);
			assertEquals(evtMoved.getMoved() , sector3.getName());
			assertEquals(move2.isPossible(model1) , false);
			((Human)model1.getActualTurn().getCurrentPlayer().getAvatar()).setCanAttack(false);
			assertEquals(attack3.isPossible(model1) , false);
			((Human)model1.getActualTurn().getCurrentPlayer().getAvatar()).setCanAttack(true);
			model1.getActualTurn().setHasAttacked(true);
			assertEquals(attack3.isPossible(model1) , false);
			model1.getActualTurn().setHasAttacked(false);
			model1.getActualTurn().setHasDraw(true);
			assertEquals(attack3.isPossible(model1) , false);
			model1.getActualTurn().setHasMoved(false);
			evtNotifyTurn2 = (EventNotifyTurn)finishTurn3.perform(model1);
			assertEquals(evtNotifyTurn2.getPlayerOfTurn() , player3);
			
			
			model1.setActualTurn(turn4);
			evtDrown4 = (EventDrown)draw3.perform(model1);
			assertEquals(evtDrown4.getDrown() , hatchCard1);
			assertEquals(finishTurn2.isPossible(model1) , false);
			
			model1.setActualTurn(turn5);
			assertEquals(draw4.perform(model1) , null);
			model1.getActualTurn().getCurrentPlayer().getAvatar().setCurrentSector(sector3);
			assertEquals(draw4.isPossible(model1) , false);
			model1.getActualTurn().setHasMoved(true);
			model1.getActualTurn().setHasAttacked(true);
			assertEquals(draw4.isPossible(model1) , false);
			model1.getActualTurn().setHasAttacked(false);
			model1.getActualTurn().setHasDraw(true);
			assertEquals(draw4.isPossible(model1) , false);
			model1.getActualTurn().setHasDraw(false);
			model1.setGameState(GameState.ACCEPTING);
			assertEquals(draw4.isPossible(model1) , false);
			model1.setGameState(GameState.RUNNING);
			model1.getActualTurn().getCurrentPlayer().getAvatar().setCurrentSector(sector6);
			assertEquals(draw4.isPossible(model1) , false);
			winner.perform(model1);
			assertEquals(model1.getActualTurn().getCurrentPlayer().getAvatar().getIsAlive() , LifeState.ALIVE);
			assertEquals(model1.getActualTurn().getCurrentPlayer().getAvatar().getIsWinner() , EndState.WINNER);
			
			model1.setActualTurn(turn6);
			looser.perform(model1);
			assertEquals(model1.getActualTurn().getCurrentPlayer().getAvatar().getIsAlive() , LifeState.DEAD);
			assertEquals(model1.getActualTurn().getCurrentPlayer().getAvatar().getIsWinner() , EndState.LOOSER);

			
	}
} 
