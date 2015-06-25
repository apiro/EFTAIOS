package it.polimi.ingsw.cg_38.controller.action;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import it.polimi.ingsw.cg_38.controller.GameController;
import it.polimi.ingsw.cg_38.controller.GameState;
import it.polimi.ingsw.cg_38.controller.ServerController;
import it.polimi.ingsw.cg_38.controller.connection.Communicator;
import it.polimi.ingsw.cg_38.controller.connection.SocketCommunicator;
import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.controller.gameEvent.EventAliensWinner;
import it.polimi.ingsw.cg_38.controller.gameEvent.EventAttack;
import it.polimi.ingsw.cg_38.controller.gameEvent.EventChat;
import it.polimi.ingsw.cg_38.controller.gameEvent.EventDEFENSE;
import it.polimi.ingsw.cg_38.controller.gameEvent.EventDraw;
import it.polimi.ingsw.cg_38.controller.gameEvent.EventFinishTurn;
import it.polimi.ingsw.cg_38.controller.gameEvent.EventHatchBlocked;
import it.polimi.ingsw.cg_38.controller.gameEvent.EventHumanWin;
import it.polimi.ingsw.cg_38.controller.gameEvent.EventMove;
import it.polimi.ingsw.cg_38.controller.gameEvent.EventRejectCard;
import it.polimi.ingsw.cg_38.controller.gameEvent.EventRetired;
import it.polimi.ingsw.cg_38.controller.gameEvent.EventSubscribe;
import it.polimi.ingsw.cg_38.controller.notifyEvent.EventAddedToGame;
import it.polimi.ingsw.cg_38.controller.notifyEvent.EventAttacked;
import it.polimi.ingsw.cg_38.controller.notifyEvent.EventCardUsed;
import it.polimi.ingsw.cg_38.controller.notifyEvent.EventClosingGame;
import it.polimi.ingsw.cg_38.controller.notifyEvent.EventDrown;
import it.polimi.ingsw.cg_38.controller.notifyEvent.EventMoved;
import it.polimi.ingsw.cg_38.controller.notifyEvent.EventNotifyAliensWin;
import it.polimi.ingsw.cg_38.controller.notifyEvent.EventNotifyChatMessage;
import it.polimi.ingsw.cg_38.controller.notifyEvent.EventNotifyRetired;
import it.polimi.ingsw.cg_38.controller.notifyEvent.EventNotifyTopics;
import it.polimi.ingsw.cg_38.controller.notifyEvent.EventNotifyTurn;
import it.polimi.ingsw.cg_38.controller.notifyEvent.EventNotifyHumanWin;
import it.polimi.ingsw.cg_38.controller.notifyEvent.EventRejectCardAlien;
import it.polimi.ingsw.cg_38.controller.notifyEvent.EventRejectCardHuman;
import it.polimi.ingsw.cg_38.controller.notifyEvent.EventSufferAttack;
import it.polimi.ingsw.cg_38.controller.notifyEvent.EventNotifyClosingTopic;
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
import it.polimi.ingsw.cg_38.model.deck.HatchCard;
import it.polimi.ingsw.cg_38.model.deck.HatchCardType;
import it.polimi.ingsw.cg_38.model.deck.HatchDeck;
import it.polimi.ingsw.cg_38.model.deck.ObjectCard;
import it.polimi.ingsw.cg_38.model.deck.ObjectCardType;
import it.polimi.ingsw.cg_38.model.deck.ObjectDeck;
import it.polimi.ingsw.cg_38.model.deck.SectorCard;
import it.polimi.ingsw.cg_38.model.deck.SectorCardType;
import it.polimi.ingsw.cg_38.model.deck.SectorDeck;
import it.polimi.ingsw.cg_38.model.map.Dangerous;
import it.polimi.ingsw.cg_38.model.map.Hatch;
import it.polimi.ingsw.cg_38.model.map.HumanStartingPoint;
import it.polimi.ingsw.cg_38.model.map.Safe;
import it.polimi.ingsw.cg_38.model.map.Sector;

import org.junit.Before;
import org.junit.Test;

public class GameActionTest {
	
	Draw draw1;
	Draw draw2;
	Draw draw3;
	Draw draw4;
	Draw draw5;
	Attack attack1;
	Attack attack2;
	Attack attack3;
	FinishTurn finishTurn;
	FinishTurn finishTurn2;
	FinishTurn finishTurn3;
	HumanWin humanWin;
	HumanWin humanWin2;
	Move move;
	Move move2;
	Move move3;
	AliensWin aliensWin1;
	AliensWin aliensWin2;	
	Subscribe subscribe;
	Subscribe subscribe2;
	HatchBlocked hatchBlocked;
	Winner winner;
	Looser looser;
	Defense defense;
	Reject reject;
	Retire retire;
	Chat chat;	

	EventDraw evtDraw1;
	EventDraw evtDraw2;
	EventDraw evtDraw3;
	EventDraw evtDraw4;
	EventDraw evtDraw5;
	EventFinishTurn evtFinishTurn;
	EventFinishTurn evtFinishTurn2;
	EventFinishTurn evtFinishTurn3;
	EventAttack evtAttack1;
	EventAttack evtAttack2;
	EventAttack evtAttack3;
	EventAttack evtAttack4;
	EventMove evtMove;
	EventMove evtMove2;
	EventMove evtMove3;
	EventHumanWin evtHuman;
	EventHumanWin evtHuman2;
	EventAliensWinner evtAliensWinner1;
	EventAliensWinner evtAliensWinner2;
	EventAliensWinner evtAliensWinner3;
	EventSubscribe evtSubscribe;
	EventSubscribe evtSubscribe2;
	EventHatchBlocked evtHatch;
	EventDEFENSE evtDefense;
	EventRejectCard evtRejectCard;
	EventRetired evtRetired;
	EventChat evtChat;
	
	List<NotifyEvent> evtAttacked1;
	List<NotifyEvent> evtAttacked2;
	List<NotifyEvent> evtDrown1;
	List<NotifyEvent> evtDrown2;
	List<NotifyEvent> evtDrown3;
	List<NotifyEvent> evtDrown4;
	List<NotifyEvent> evtMoved;
	List<NotifyEvent> evtNotifyTurn;
	List<NotifyEvent> evtNotifyTurn2;
	List<NotifyEvent> evtNotifyAliensWin;
	EventNotifyTopics evtNotifyTopics;
	EventAddedToGame evtAddedToGame;
	NotifyEvent evtNotify;
	
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
	
	List<SectorCard> sectorList1;
	List<ObjectCard> objectList1;
	List<HatchCard> hatchList1;
	List<Player> killedPlayer;
	List<Player> addPlayers;
	List<String> topics;
	
	SectorDeck sectorDeck1;
	ObjectDeck objectDeck1;
	HatchDeck hatchDeck1;
	
	Card sectorCard1;
	Card sectorCard2;
	Card objectCard1;
	Card hatchCard1;
	Card hatchCard2;
	
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
	
	ServerController server;
	GameController gc;
	Communicator c;
	
	SectorCard drown1;
	
	ObjectCard drown2;
	ObjectCard card;
	ObjectCard card2;
	

	@Before
	public void init() throws ParserConfigurationException, Exception {
		
		message = "Welcome";
		player1 = new Player("albi");
		player2 = new Player("scimmiu");
		player2.getAvatar();
		player3 = new Player("shane");
		player4 = new Player("diffi");
		player5 = new Player("reda");
		player6 = new Player("piccio");
		player7 = new Player("poldo");
		
		model1 = new GameModel("Galvani");
		
		gc = new GameController("Galilei" , "room1");
		server = new ServerController();
		server.getTopics().put("room1", gc);
		c = new SocketCommunicator(null);
		
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
		
		sectorList1 = new ArrayList<SectorCard>();
		objectList1 = new ArrayList<ObjectCard>();
		hatchList1 = new ArrayList<HatchCard>();
		killedPlayer = new ArrayList<Player>();
		addPlayers = new ArrayList<Player>();
		topics = new ArrayList<String>();
		topics.add("room1");
		topics.add("room2");
		
		sectorDeck1 = new SectorDeck();
		objectDeck1 = new ObjectDeck();
		hatchDeck1 = new HatchDeck();
		
		sectorCard1 = new SectorCard(SectorCardType.SILENCE , false);
		sectorCard2 = new SectorCard(SectorCardType.MYSECTORNOISE , true);
		objectCard1 = new ObjectCard(ObjectCardType.SPOTLIGHT);
		hatchCard1 = new HatchCard(HatchCardType.GREEN);
		hatchCard2 = new HatchCard(HatchCardType.RED);
		
		card = new ObjectCard(ObjectCardType.DEFENSE);
		card2 = new ObjectCard(ObjectCardType.ADRENALINE);
		
		evtDraw1 = new EventDraw(player1);
		evtDraw2 = new EventDraw(player1);
		evtDraw3 = new EventDraw(player4);
		evtDraw4 = new EventDraw(player5);
		evtDraw5 = new EventDraw(player7);
		evtAttack1 = new EventAttack(player2 , sector2);
		evtAttack2 = new EventAttack(player1 , sector1);
		evtAttack3 = new EventAttack(player3 , sector3);
		evtAttack4 = new EventAttack(player6 , sector3);
		evtFinishTurn = new EventFinishTurn(player2);
		evtFinishTurn2 = new EventFinishTurn(player4);
		evtFinishTurn3 = new EventFinishTurn(player3);
		evtMove = new EventMove(player3 , sector3);
		evtMove2 = new EventMove(player3 , sector2);
		evtMove3 = new EventMove(player2 , sector4);
		evtAliensWinner1 = new EventAliensWinner(player3 , false);
		evtAliensWinner2 = new EventAliensWinner(player1 , true);
		evtAliensWinner3 = new EventAliensWinner(player6 , true);
		evtSubscribe = new EventSubscribe(player1 , "room1" , "Galilei");
		evtSubscribe2 = new EventSubscribe(player1 , "room2" , "Galvani");
		evtHuman = new EventHumanWin(player7);
		evtHuman2 = new EventHumanWin(player4);
		evtHatch = new EventHatchBlocked(player7);
		evtDefense = new EventDEFENSE(player7);
		evtRejectCard = new EventRejectCard(player7 , card2);
		evtRetired = new EventRetired(player7);
		evtChat = new EventChat(player7 , message);
		
		draw1 = new Draw(evtDraw1);
		draw2 = new Draw(evtDraw2);
		draw3 = new Draw(evtDraw3);
		draw4 = new Draw(evtDraw4);
		draw5 = new Draw(evtDraw5);
		attack1 = new Attack(evtAttack1);
		attack2 = new Attack(evtAttack2);
		attack3 = new Attack(evtAttack3);
		finishTurn = new FinishTurn(evtFinishTurn);
		finishTurn2 = new FinishTurn(evtFinishTurn2);
		finishTurn3 = new FinishTurn(evtFinishTurn3);
		move = new Move(evtMove);
		move2 = new Move(evtMove2);
		move3 = new Move(evtMove3);
		aliensWin1 = new AliensWin(evtAliensWinner1);
		aliensWin2 = new AliensWin(evtAliensWinner2);
		winner = new Winner(evtAliensWinner3);
		looser = new Looser(evtAttack4);
		subscribe = new Subscribe(evtSubscribe);
		subscribe2 = new Subscribe(evtSubscribe2);
		humanWin = new HumanWin(evtHuman);
		humanWin2 = new HumanWin(evtHuman2);
		hatchBlocked = new HatchBlocked(evtHatch);
		reject = new Reject(evtRejectCard);
		retire = new Retire(evtRetired);
		chat = new Chat(evtChat);
		
		evtNotifyTopics = new EventNotifyTopics(player1 , true , topics);
		
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
		
		sectorList1.add((SectorCard)sectorCard1);
		sectorList1.add((SectorCard)sectorCard2);
		objectList1.add((ObjectCard)objectCard1);
		hatchList1.add((HatchCard)hatchCard1);
		hatchList1.add((HatchCard) hatchCard2);
		
		sectorDeck1.setSectorDeck(sectorList1);
		objectDeck1.setObjectDeck(objectList1);
		hatchDeck1.setHatchDeck(hatchList1);
		
		model1.setActualTurn(turn1);
		model1.setDeckSector(sectorDeck1);
		model1.setDeckObject(objectDeck1);
		model1.setDeckHatch(hatchDeck1);

		killedPlayer.add(player1);
		killedPlayer.add(player3);

			
	}
	
	
	
	@Test
	public void test() {
		
			assertEquals(subscribe.getTopic() , "room1");
			assertEquals(subscribe.getTypeMap() , "Galilei");
			assertTrue(subscribe.isPossible(server));
			assertTrue(!subscribe2.isPossible(server));
			assertEquals(draw1.isPossible(model1) , false);
			model1.setGameState(GameState.RUNNING);
			assertEquals(draw1.isPossible(model1) , true);
			evtDrown1 = draw1.perform(model1);	
			evtDrown2 = draw2.perform(model1);
		
			assertEquals(sectorCard1 , ((EventDrown)evtDrown1.get(0)).getDrown());
			evtDrown1 = draw1.perform(model1);
			assertEquals(sectorCard1 , ((EventDrown)evtDrown1.get(0)).getDrown());
			
			assertEquals(sectorCard2 , ((EventDrown)evtDrown2.get(0)).getDrown());
			evtDrown2 = draw2.perform(model1);
			assertEquals(objectCard1 , model1.getActualTurn().getCurrentPlayer().getAvatar().getMyCards().get(0));
			assertEquals(evtDrown2.toString() , "[EventDrown [added=" + ((EventDrown)evtDrown2.get(0)).getAdded() + ", drown=" + ((EventDrown)evtDrown2.get(0)).getDrown() + "]]");
			model1.getActualTurn().setHasAttacked(true);
			assertEquals(attack2.isPossible(model1) , false);
			aliensWin2.perform(model1);
			assertEquals(model1.getGameState() , GameState.CLOSING);
			assertEquals(model1.getActualTurn().getCurrentPlayer().getAvatar().getIsWinner() , EndState.WINNER);
			
			model1.setActualTurn(turn2);
			model1.getActualTurn().setHasMoved(false);
			assertEquals(move3.isPossible(model1) , false);
			model1.getActualTurn().setHasMoved(true);
			model1.setGameState(GameState.RUNNING);
			assertEquals(draw1.isPossible(model1) , true);
			model1.getActualTurn().setHasMoved(false);
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
			model1.setGameState(GameState.ACCEPTING);
			assertEquals(finishTurn.isPossible(model1) , false);
			model1.setGameState(GameState.RUNNING);
			model1.getActualTurn().setHasMoved(false);
			assertEquals(finishTurn.isPossible(model1) , false);
			model1.getActualTurn().setHasMoved(true);
			assertEquals(finishTurn.isPossible(model1) , true);
			player7 = model1.getNextPlayer();
			evtNotifyTurn = finishTurn.perform(model1);
			assertEquals(((EventNotifyTurn)evtNotifyTurn.get(0)).getPlayerOfTurn() , player7);
			
			model1.setActualTurn(turn3);
			model1.setGameState(GameState.ACCEPTING);
			assertEquals(move.isPossible(model1) , false);
			((Human)model1.getActualTurn().getCurrentPlayer().getAvatar()).setCanAttack(true);
			assertEquals(attack3.isPossible(model1) , false);
			model1.setGameState(GameState.RUNNING);
			assertEquals(move.isPossible(model1) , true);
			evtMoved = move.perform(model1);
			assertEquals(((EventMoved)evtMoved.get(0)).getMoved() , sector3.getName());
			assertEquals(move2.isPossible(model1) , false);
			((Human)model1.getActualTurn().getCurrentPlayer().getAvatar()).setCanAttack(false);
			assertEquals(attack3.isPossible(model1) , false);
			((Human)model1.getActualTurn().getCurrentPlayer().getAvatar()).setCanAttack(true);
			model1.getActualTurn().setHasAttacked(true);
			assertEquals(attack3.isPossible(model1) , false);
			model1.getActualTurn().setHasAttacked(false);
			model1.getActualTurn().setHasMoved(false);
			evtNotifyTurn2 = finishTurn3.perform(model1);
			assertEquals(((EventNotifyTurn)evtNotifyTurn2.get(0)).getPlayerOfTurn() , player3);
			evtNotifyAliensWin = aliensWin1.perform(model1);
			
			
			model1.setActualTurn(turn4);
			evtDrown4 = draw3.perform(model1);
			assertEquals(((EventDrown)evtDrown4.get(0)).getDrown() , hatchCard1);
			assertEquals(finishTurn2.isPossible(model1) , false);
			model1.getActualTurn().setHasMoved(true);
			model1.getActualTurn().getCurrentPlayer().getAvatar().setIsPowered(false);
			player5.getAvatar().setIsWinner(EndState.PLAYING);
			player5.getAvatar().setIsAlive(LifeState.ALIVE);
			evtNotifyTurn2 = finishTurn2.perform(model1);
			assertEquals(((EventNotifyTurn)evtNotifyTurn2.get(0)).getPlayerOfTurn() , player5);
			model1.setActualTurn(turn4);
			model1.getActualTurn().getCurrentPlayer().getAvatar().setIsPowered(true);
			finishTurn2.perform(model1);
			assertEquals(model1.getGamePlayers().get(2).getAvatar().getIsPowered() , false);
			model1.setActualTurn(turn4);
			model1.getGamePlayers().get(3).getAvatar().setIsWinner(EndState.PLAYING);
			model1.getGamePlayers().get(2).getAvatar().setIsWinner(EndState.PLAYING);
			model1.getGamePlayers().get(0).getAvatar().setIsWinner(EndState.PLAYING);
			model1.getGamePlayers().get(1).getAvatar().setIsWinner(EndState.PLAYING);
			sector1 = model1.getGameMap().searchSectorByName("Hatch");
			model1.getActualTurn().getCurrentPlayer().getAvatar().setCurrentSector(sector1);
			evtNotifyTurn2 = humanWin2.perform(model1);
			assertEquals(((EventNotifyHumanWin)evtNotifyTurn2.get(0)).getGenerator() , player4);
			model1.getGamePlayers().get(3).getAvatar().setIsWinner(EndState.LOOSER);
			model1.getGamePlayers().get(2).getAvatar().setIsWinner(EndState.LOOSER);
			model1.getGamePlayers().get(0).getAvatar().setIsWinner(EndState.LOOSER);
			model1.getGamePlayers().get(1).getAvatar().setIsWinner(EndState.LOOSER);
			model1.getGamePlayers().get(4).getAvatar().setIsWinner(EndState.LOOSER);
			assertEquals(humanWin2.perform(model1).size() , 3);
			model1.getGamePlayers().get(3).getAvatar().setIsWinner(EndState.PLAYING);
			model1.getGamePlayers().get(2).getAvatar().setIsWinner(EndState.PLAYING);
			model1.getGamePlayers().get(0).getAvatar().setIsWinner(EndState.PLAYING);
			model1.getGamePlayers().get(1).getAvatar().setIsWinner(EndState.PLAYING);
			model1.getActualTurn().getCurrentPlayer().setNumTurniGiocati(38);
			model1.getNextPlayer().setNumTurniGiocati(39);
			model1.getActualTurn().setHasMoved(true);
			evtNotifyTurn2 = finishTurn2.perform(model1);
			assertTrue(model1.getGameState().equals(GameState.CLOSING));
			assertTrue(evtNotifyTurn2.get(0) instanceof EventNotifyAliensWin);
			assertTrue(evtNotifyTurn2.get(1) instanceof EventNotifyClosingTopic);
			assertTrue(evtNotifyTurn2.get(2) instanceof EventClosingGame);
			
			model1.setActualTurn(turn5);
			assertEquals((draw4.perform(model1)).size() , 0);
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
			
			model1.setActualTurn(turn6);
			
			winner.perform(model1);
			assertEquals(model1.getActualTurn().getCurrentPlayer().getAvatar().getIsWinner() , EndState.WINNER);
			assertEquals(model1.getActualTurn().getCurrentPlayer().getAvatar().getIsAlive() , LifeState.ALIVE);
			looser.perform(model1);
			assertEquals(model1.getActualTurn().getCurrentPlayer().getAvatar().getIsWinner() , EndState.LOOSER);
			assertEquals(model1.getActualTurn().getCurrentPlayer().getAvatar().getIsAlive() , LifeState.DEAD);
			
			model1.setActualTurn(turn7);
			
			model1.getActualTurn().getCurrentPlayer().getAvatar().addCard(card);
			defense = new Defense(evtDefense);
			evtDrown2 = defense.perform(model1);
			assertTrue(!model1.getActualTurn().getCurrentPlayer().getAvatar().getMyCards().contains(card));
			assertTrue(evtDrown2.get(0) instanceof EventRejectCardAlien);
			assertTrue(evtDrown2.get(1) instanceof EventCardUsed);
			assertTrue(!reject.isPossible(model1));
			model1.getActualTurn().getCurrentPlayer().getAvatar().addCard(card2);
			model1.setGameState(GameState.CLOSING);
			assertTrue(!reject.isPossible(model1));
			model1.setGameState(GameState.RUNNING);
			assertTrue(reject.isPossible(model1));
			assertEquals(reject.getCard() , card2);
			assertTrue(reject.perform(model1).get(0)instanceof EventRejectCardHuman);
			assertTrue(!model1.getActualTurn().getCurrentPlayer().getAvatar().getMyCards().contains(card2));
			model1.getGamePlayers().get(3).getAvatar().setIsWinner(EndState.LOOSER);
			model1.getGamePlayers().get(2).getAvatar().setIsWinner(EndState.WINNER);
			model1.getGamePlayers().get(0).getAvatar().setIsWinner(EndState.LOOSER);
			model1.getGamePlayers().get(1).getAvatar().setIsWinner(EndState.WINNER);
			model1.getActualTurn().getCurrentPlayer().getAvatar().setCurrentSector(sector1);
			evtDrown1 = humanWin.perform(model1);
			assertTrue(evtDrown1.get(0) instanceof EventNotifyHumanWin);
			assertEquals(model1.getActualTurn().getCurrentPlayer().getAvatar().getIsWinner() , EndState.WINNER);
			evtNotify = hatchBlocked.perform(model1).get(0);
			model1.getActualTurn().getCurrentPlayer().getAvatar().setCurrentSector(sector7);
			draw5.perform(model1);
			assertTrue(((Hatch)model1.getActualTurn().getCurrentPlayer().getAvatar().getCurrentSector()).getIsOpen());
			assertTrue(retire.perform(model1).get(0) instanceof EventNotifyRetired);
			assertTrue(chat.perform(model1).get(0) instanceof EventNotifyChatMessage);
				
	}
} 
