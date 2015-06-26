package it.polimi.ingsw.cg_38.controller.action;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import it.polimi.ingsw.cg_38.controller.GameState;
import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.controller.gameEvent.EventNoiseMySect;
import it.polimi.ingsw.cg_38.controller.gameEvent.EventNoiseRandSect;
import it.polimi.ingsw.cg_38.controller.gameEvent.EventSEDATIVES;
import it.polimi.ingsw.cg_38.controller.gameEvent.EventTELEPORT;
import it.polimi.ingsw.cg_38.controller.notifyEvent.EventDeclareNoise;
import it.polimi.ingsw.cg_38.controller.notifyEvent.EventMoved;
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
import it.polimi.ingsw.cg_38.model.deck.ObjectDeck;
import it.polimi.ingsw.cg_38.model.deck.SectorCard;
import it.polimi.ingsw.cg_38.model.deck.SectorDeck;
import it.polimi.ingsw.cg_38.model.map.Sector;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.Before;
import org.junit.Test;

/** contiene i test delle azioni relativo l'utilizzo delle carte settore rumore nel mio settore,
 *  rumore in un settore qualsiasi, silenzio e delle carte oggetto sedativo e teletraporto */
public class UseCard2Test {
	
	UseMySectorNoise useMySectorNoise;
	UseRandomSectorNoise useRandomSectorNoise;
	UseSedativesCard useSedativesCard;
	UseSedativesCard useSedativesCard2;
	UseSedativesCard useSedativesCard3;
	UseSilenceCard useSilenceCard;
	UseTeleportCard useTeleportCard;
	UseTeleportCard useTeleportCard2;
	UseTeleportCard useTeleportCard3;
	
	EventNoiseMySect evtNoiseMySect;
	EventNoiseRandSect evtNoiseRandSect;
	EventSEDATIVES evtSedat;
	EventSEDATIVES evtSedat2;
	EventSEDATIVES evtSedat3;
	EventTELEPORT evtTeleport;
	EventTELEPORT evtTeleport2;
	EventTELEPORT evtTeleport3;
	
	List<NotifyEvent> evtAttacked;
	List<NotifyEvent> evtDeclarePosition1;
	List<NotifyEvent> evtDeclareNoise1;
	List<NotifyEvent> evtDeclareNoise2;
	List<NotifyEvent> evtDeclareNoise3;
	List<NotifyEvent> evtMoved;
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
	
	List<SectorCard> sectorList1;
	List<ObjectCard> objectList1;
	List<Player> killedPlayer;
	List<Player> addPlayers;
	List<Player> toDeclare;
	
	SectorDeck sectorDeck1;
	ObjectDeck objectDeck1;
	
	Card adrenalineCard1;
	Card adrenalineCard2;
	Card attackCard1;
	Card attackCard2;
	Card lightsCard1;
	Card lightsCard2;
	Card sedatCard;
	Card teleportCard;
	Card defenseCard;
	
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
	
	
	GameModel model1;

	@Before
	public void init() throws ParserConfigurationException, Exception {
		
		player1 = new Player("albi");
		player2 = new Player("scimmiu");
		player3 = new Player("shane");
		player4 = new Player("diffi");
		player5 = new Player("piccio");
		player6 = new Player("raf");
		
		sedatCard = new ObjectCard(ObjectCardType.SEDATIVES);
		teleportCard = new ObjectCard(ObjectCardType.TELEPORT);
		defenseCard = new ObjectCard(ObjectCardType.DEFENSE);
		
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
		avatar4.addCard(sedatCard);
		avatar5.addCard(attackCard1);
		avatar5.addCard(attackCard2);
		avatar5.addCard(teleportCard);
		avatar6.addCard(lightsCard2);
		avatar6.addCard(attackCard2);
		avatar6.addCard(teleportCard);
		avatar6.addCard(sedatCard);
		
		
		killedPlayer = new ArrayList<Player>();
		addPlayers = new ArrayList<Player>();
		toDeclare = new ArrayList<Player>();
		
		sectorDeck1 = new SectorDeck();
		objectDeck1 = new ObjectDeck();
		
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
		
		model1.setActualTurn(turn2);
		
		evtNoiseMySect = new EventNoiseMySect(player2);
		evtNoiseRandSect = new EventNoiseRandSect(player3 , sector4);
		evtSedat = new EventSEDATIVES(player4 , sedatCard);
		evtSedat2 = new EventSEDATIVES(player6 , sedatCard);
		evtSedat3 = new EventSEDATIVES(player5 , sedatCard);
		evtTeleport = new EventTELEPORT(player5 , teleportCard);
		evtTeleport2 = new EventTELEPORT(player6 , teleportCard);
		evtTeleport3 = new EventTELEPORT(player4 , teleportCard);
		
		useMySectorNoise = new UseMySectorNoise(evtNoiseMySect);
		useRandomSectorNoise = new UseRandomSectorNoise(evtNoiseRandSect);
		useSedativesCard = new UseSedativesCard(evtSedat);
		useSedativesCard2 = new UseSedativesCard(evtSedat2);
		useSedativesCard3 = new UseSedativesCard(evtSedat3);
		useSilenceCard = new UseSilenceCard(evtNoiseMySect);
		useTeleportCard = new UseTeleportCard(evtTeleport);
		useTeleportCard2 = new UseTeleportCard(evtTeleport2);
		useTeleportCard3 = new UseTeleportCard(evtTeleport3);
		
		
		toDeclare.add(player1);
		toDeclare.add(player3);
		toDeclare.add(player6);
	}
	
	@Test
	public void test() {
		
		/* verifica il corretto funzionamento del metodo isPossible dell'utilizzo 
		 * delle carte settore rumore nel mio settore, rumore in un settore qualsia
		 * e silenzio */
		model1.setGameState(GameState.RUNNING);
		assertEquals(useMySectorNoise.isPossible(model1) , true);
		assertEquals(useSilenceCard.isPossible(model1) , true);
		assertEquals(useRandomSectorNoise.isPossible(model1) , true);
		
		/* verifica il corretto funzionamento della perform dell'utilizzo 
		 * delle carte settore rumore nel mio settore, rumore in un settore qualsiasi
		 * e silenzio*/
		evtDeclareNoise1 = useMySectorNoise.perform(model1);
		assertEquals(((EventDeclareNoise)evtDeclareNoise1.get(0)).getSectorToNoise() , sector3);
		evtDeclareNoise3 = useSilenceCard.perform(model1);
		assertEquals(((EventDeclareNoise)evtDeclareNoise3.get(0)).getSectorToNoise() , null);
		evtDeclareNoise2 = useRandomSectorNoise.perform(model1);
		assertEquals(((EventDeclareNoise)evtDeclareNoise2.get(0)).getSectorToNoise() , sector4);
		
		model1.setActualTurn(turn4);
		
		/* verifica il corretto funzionamento del metodo isPossible dell'utilizzo 
		 * delle carte teletrasporto e sedativo*/
		assertEquals(useTeleportCard3.isPossible(model1) , false);
		model1.setGameState(GameState.ACCEPTING);
		assertEquals(useSedativesCard.isPossible(model1) , false);
		model1.setGameState(GameState.RUNNING);
		assertEquals(useSedativesCard.isPossible(model1) , true);
		model1.getActualTurn().setHasUsedObjectCard(true);
		assertEquals(useSedativesCard.isPossible(model1) , false);
		
		/* verifica il corretto funzionamento della perform dell'utilizzo 
		 * della carta sedativo */
		useSedativesCard.perform(model1);
		assertEquals(model1.getActualTurn().getHasDraw() , true);
		
		model1.setActualTurn(turn5);
		
		/* verifica il corretto funzionamento del metodo isPossible dell'utilizzo 
		 * delle carte teletrasporto e sedativo*/
		assertEquals(useSedativesCard3.isPossible(model1) , false);
		model1.setGameState(GameState.ACCEPTING);
		model1.getActualTurn().setHasUsedObjectCard(false);
		assertEquals(useTeleportCard.isPossible(model1) , false);
		model1.setGameState(GameState.RUNNING);
		assertEquals(useTeleportCard.isPossible(model1) , true);
		model1.getActualTurn().setHasUsedObjectCard(true);
		assertEquals(useTeleportCard.isPossible(model1) , false);
		assertEquals(useTeleportCard2.isPossible(model1) , false);
		assertEquals(useSedativesCard2.isPossible(model1) , false);
		
		/* verifica il corretto funzionamento della perform dell'utilizzo 
		 * della carta teletrasporto */
		evtMoved = useTeleportCard.perform(model1);
		assertEquals(((EventMoved)evtMoved.get(0)).getMoved() , "HumanStartingPoint");
	}

}
