package it.polimi.ingsw.cg_38.controller.action;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.ParserConfigurationException;

import it.polimi.ingsw.cg_38.controller.GameState;
import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.gameEvent.EventAdren;
import it.polimi.ingsw.cg_38.gameEvent.EventAttackCard;
import it.polimi.ingsw.cg_38.gameEvent.EventLights;
import it.polimi.ingsw.cg_38.gameEvent.EventNoiseMySect;
import it.polimi.ingsw.cg_38.gameEvent.EventNoiseRandSect;
import it.polimi.ingsw.cg_38.gameEvent.EventSedat;
import it.polimi.ingsw.cg_38.gameEvent.EventTeleport;
import it.polimi.ingsw.cg_38.model.Alien;
import it.polimi.ingsw.cg_38.model.Avatar;
import it.polimi.ingsw.cg_38.model.Card;
import it.polimi.ingsw.cg_38.model.GameModel;
import it.polimi.ingsw.cg_38.model.Human;
import it.polimi.ingsw.cg_38.model.Name;
import it.polimi.ingsw.cg_38.model.ObjectCard;
import it.polimi.ingsw.cg_38.model.ObjectCardType;
import it.polimi.ingsw.cg_38.model.ObjectDeck;
import it.polimi.ingsw.cg_38.model.Player;
import it.polimi.ingsw.cg_38.model.Sector;
import it.polimi.ingsw.cg_38.model.SectorCard;
import it.polimi.ingsw.cg_38.model.SectorDeck;
import it.polimi.ingsw.cg_38.model.Turn;
import it.polimi.ingsw.cg_38.notifyEvent.EventAttacked;
import it.polimi.ingsw.cg_38.notifyEvent.EventCardUsed;
import it.polimi.ingsw.cg_38.notifyEvent.EventSufferAttack;
import it.polimi.ingsw.cg_38.notifyEvent.EventDeclareNoise;
import it.polimi.ingsw.cg_38.notifyEvent.EventDeclarePosition;
import it.polimi.ingsw.cg_38.notifyEvent.EventMoved;

import org.junit.Before;
import org.junit.Test;

public class UseCardTest {
	
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
	UseMySectorNoise useMySectorNoise;
	UseRandomSectorNoise useRandomSectorNoise;
	UseSedativesCard useSedativesCard;
	UseSedativesCard useSedativesCard2;
	UseSedativesCard useSedativesCard3;
	UseSilenceCard useSilenceCard;
	UseTeleportCard useTeleportCard;
	UseTeleportCard useTeleportCard2;
	UseTeleportCard useTeleportCard3;
	
	EventAdren evtAdren1;
	EventAdren evtAdren2;
	EventAdren evtAdren3;
	EventAttackCard evtAttackCard1;
	EventAttackCard evtAttackCard2;
	EventAttackCard evtAttackCard3;
	EventAttackCard evtAttackCard4;
	EventLights evtLights1;
	EventLights evtLights2;
	EventLights evtLights3;
	EventNoiseMySect evtNoiseMySect;
	EventNoiseRandSect evtNoiseRandSect;
	EventSedat evtSedat;
	EventSedat evtSedat2;
	EventSedat evtSedat3;
	EventTeleport evtTeleport;
	EventTeleport evtTeleport2;
	EventTeleport evtTeleport3;
	
	
	ArrayList<NotifyEvent> evtAttacked;
	ArrayList<NotifyEvent> evtDeclarePosition1;
	ArrayList<NotifyEvent> evtDeclareNoise1;
	ArrayList<NotifyEvent> evtDeclareNoise2;
	ArrayList<NotifyEvent> evtDeclareNoise3;
	ArrayList<NotifyEvent> evtMoved;
	ArrayList<NotifyEvent> evtCardUsed;
	
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
	ArrayList<Player> killedPlayer;
	ArrayList<Player> addPlayers;
	ArrayList<Player> toDeclare;
	
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
		
		adrenalineCard1 = new ObjectCard(ObjectCardType.Adrenaline);
		adrenalineCard2 = new ObjectCard(ObjectCardType.Adrenaline);
		attackCard1 = new ObjectCard(ObjectCardType.Attack);
		lightsCard1 = new ObjectCard(ObjectCardType.SpotLight);
		lightsCard2 = new ObjectCard(ObjectCardType.SpotLight);
		sedatCard = new ObjectCard(ObjectCardType.Sedatives);
		teleportCard = new ObjectCard(ObjectCardType.Teleport);
		
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
		
		avatar1 = new Alien(Name.Alien1 , sector2);
		avatar2 = new Human(Name.Human1 , sector3);
		avatar3 = new Human(Name.Human2 , sector2);
		avatar4 = new Human(Name.Human4 , sector4);
		avatar5 = new Human(Name.Human3 , sector4);
		avatar6 = new Alien(Name.Alien2 , sector5);
		
		avatar1.addCard(adrenalineCard1);
		avatar2.addCard(adrenalineCard2);
		avatar3.addCard(lightsCard1);
		avatar4.addCard(sedatCard);
		avatar5.addCard(attackCard1);
		avatar5.addCard(teleportCard);
		avatar6.addCard(lightsCard2);
		avatar6.addCard(attackCard2);
		
		
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
		killedPlayer.add(player4);
		model1.setGamePlayers(addPlayers);
		
		model1.setActualTurn(turn1);
		
		evtAdren1 = new EventAdren(player1 , adrenalineCard1);
		evtAdren2 = new EventAdren(player2 , adrenalineCard2);
		evtAdren3 = new EventAdren(player3 , adrenalineCard2);
		evtAttackCard1 = new EventAttackCard(player5 , attackCard1);
		evtAttackCard2 = new EventAttackCard(player6 , attackCard2);
		evtAttackCard3 = new EventAttackCard(player4 , attackCard2);
		evtAttackCard4 = new EventAttackCard(player1 , attackCard1);
		evtLights1 = new EventLights(player3 , sector2 , lightsCard1);
		evtLights2 = new EventLights(player6 , sector5 , lightsCard2);
		evtLights3 = new EventLights(player2 , sector3 , lightsCard2);
		evtNoiseMySect = new EventNoiseMySect(player2);
		evtNoiseRandSect = new EventNoiseRandSect(player3 , sector4);
		evtSedat = new EventSedat(player4 , sedatCard);
		evtSedat2 = new EventSedat(player6 , sedatCard);
		evtSedat3 = new EventSedat(player5 , sedatCard);
		evtTeleport = new EventTeleport(player5 , teleportCard);
		evtTeleport2 = new EventTeleport(player6 , teleportCard);
		evtTeleport3 = new EventTeleport(player4 , teleportCard);
		
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
		/*toDeclare.put(player6.getName(), player6.getAvatar().getCurrentSector());*/
		
	}
	
	

	@Test
	public void test() {

		
		assertEquals(useAdrenalineCard1.isPossible(model1) , false);
		model1.setGameState(GameState.RUNNING);
		evtCardUsed = useAdrenalineCard1.perform(model1);
		assertEquals(((EventCardUsed)evtCardUsed.get(0)).getPerformed() , false);
		assertEquals(useAttackCard4.isPossible(model1) , false);
		
		model1.setActualTurn(turn2);
		
		assertEquals(useLightsCard3.isPossible(model1) , false);
		model1.setGameState(GameState.ACCEPTING);
		assertEquals(useAdrenalineCard2.isPossible(model1) , false);
		model1.setGameState(GameState.RUNNING);
		assertEquals(useAdrenalineCard2.isPossible(model1) , true); 
		useAdrenalineCard2.perform(model1);
		assertEquals(model1.getActualTurn().getCurrentPlayer().getAvatar().getIsPowered() , true);
		assertEquals(model1.getActualTurn().getCurrentPlayer().getAvatar().getMyCards() , new ArrayList<Card>());
		
		assertEquals(useMySectorNoise.isPossible(model1) , true);
		evtDeclareNoise1 = useMySectorNoise.perform(model1);
		assertEquals(((EventDeclareNoise)evtDeclareNoise1.get(0)).getSectorToNoise() , sector3);
		
		model1.setActualTurn(turn3);
		assertEquals(useAdrenalineCard3.isPossible(model1) , false);
		model1.setGameState(GameState.ACCEPTING);
		assertEquals(useLightsCard1.isPossible(model1) , false);
		model1.setGameState(GameState.RUNNING);
		assertEquals(useLightsCard1.isPossible(model1) , true);
		evtDeclarePosition1 = useLightsCard1.perform(model1);
		assertEquals(model1.getActualTurn().getCurrentPlayer().getAvatar().getMyCards() , new ArrayList<Card>());
		assertEquals(((EventDeclarePosition)evtDeclarePosition1.get(0)).getToDeclare() , toDeclare);
		assertEquals(useSilenceCard.isPossible(model1) , true);
		evtDeclareNoise3 = useSilenceCard.perform(model1);
		assertEquals(((EventDeclareNoise)evtDeclareNoise3.get(0)).getSectorToNoise() , null);
		
		assertEquals(useRandomSectorNoise.isPossible(model1) , true);
		evtDeclareNoise2 = useRandomSectorNoise.perform(model1);
		assertEquals(((EventDeclareNoise)evtDeclareNoise2.get(0)).getSectorToNoise() , sector4);
		
		model1.setActualTurn(turn4);
		assertEquals(useTeleportCard3.isPossible(model1) , false);
		model1.setGameState(GameState.ACCEPTING);
		assertEquals(useSedativesCard.isPossible(model1) , false);
		model1.setGameState(GameState.RUNNING);
		assertEquals(useSedativesCard.isPossible(model1) , true);
		useSedativesCard.perform(model1);
		assertEquals(model1.getActualTurn().getHasDraw() , true);
		assertEquals(useAttackCard3.isPossible(model1) , false);
		
		model1.setActualTurn(turn5);
		
		assertEquals(useSedativesCard3.isPossible(model1) , false);
		model1.setGameState(GameState.ACCEPTING);
		assertEquals(useAttackCard1.isPossible(model1) , false);
		model1.setGameState(GameState.RUNNING);
		model1.getActualTurn().getCurrentPlayer().getAvatar().addCard(attackCard1);
		assertEquals(useAttackCard1.isPossible(model1) , true);
		assertEquals(useAttackCard1.getCard() , attackCard1);
		evtAttacked = useAttackCard1.perform(model1);
		assertEquals(((EventSufferAttack)evtAttacked.get(0)).getKilled() , killedPlayer);
		model1.setGameState(GameState.ACCEPTING);
		assertEquals(useTeleportCard.isPossible(model1) , false);
		model1.setGameState(GameState.RUNNING);
		assertEquals(useTeleportCard.isPossible(model1) , true);
		evtMoved = useTeleportCard.perform(model1);
		assertEquals(((EventMoved)evtMoved.get(0)).getMoved() , "HumanStartingPoint");
		
		model1.setActualTurn(turn6);
		assertEquals(useLightsCard2.isPossible(model1) , false);
		assertEquals(useAttackCard2.isPossible(model1) , false);
		assertEquals(useTeleportCard2.isPossible(model1) , false);
		assertEquals(useSedativesCard2.isPossible(model1) , false);
			
	}

}
