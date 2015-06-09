package it.polimi.ingsw.cg_38.controller.action;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.ParserConfigurationException;

import it.polimi.ingsw.cg_38.controller.GameState;
import it.polimi.ingsw.cg_38.gameEvent.EventAdren;
import it.polimi.ingsw.cg_38.gameEvent.EventLights;
import it.polimi.ingsw.cg_38.gameEvent.EventNoiseMySect;
import it.polimi.ingsw.cg_38.gameEvent.EventNoiseRandSect;
import it.polimi.ingsw.cg_38.model.Alien;
import it.polimi.ingsw.cg_38.model.Avatar;
import it.polimi.ingsw.cg_38.model.Card;
import it.polimi.ingsw.cg_38.model.Dangerous;
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
import it.polimi.ingsw.cg_38.notifyEvent.EventCardUsed;
import it.polimi.ingsw.cg_38.notifyEvent.EventDeclareNoise;
import it.polimi.ingsw.cg_38.notifyEvent.EventDeclarePosition;

import org.junit.Before;
import org.junit.Test;

public class UseCardTest {
	
	UseAdrenalineCard useAdrenalineCard1;
	UseAdrenalineCard useAdrenalineCard2;
	UseLightsCard useLightsCard;
	UseMySectorNoise useMySectorNoise;
	UseRandomSectorNoise useRandomSectorNoise;
	
	EventAdren evtAdren1;
	EventAdren evtAdren2;
	EventLights evtLights;
	EventNoiseMySect evtNoiseMySect;
	EventNoiseRandSect evtNoiseRandSect;
	
	EventDeclarePosition evtDeclarePosition1;
	EventDeclareNoise evtDeclareNoise1;
	EventDeclareNoise evtDeclareNoise2;
	
	
	Player player1;
	Player player2;
	Player player3;
	
	Turn turn1;
	Turn turn2;
	Turn turn3;
	
	ArrayList<SectorCard> sectorList1;
	ArrayList<ObjectCard> objectList1;
	ArrayList<Player> killedPlayer;
	ArrayList<Player> addPlayers;
	HashMap<String , Sector> toDeclare;
	
	SectorDeck sectorDeck1;
	ObjectDeck objectDeck1;
	
	Card adrenalineCard1;
	Card adrenalineCard2;
	Card lightsCard;
	
	Avatar avatar1;
	Avatar avatar2;
	Avatar avatar3;
	
	Sector sector1;
	Sector sector2;
	Sector sector3;	
	Sector sector4;
	
	GameModel model1;
	
	@Before
	public void init() throws ParserConfigurationException, Exception {
		
		player1 = new Player("albi");
		player2 = new Player("scimmiu");
		player3 = new Player("shane");
		
		adrenalineCard1 = new ObjectCard(ObjectCardType.Adrenaline);
		adrenalineCard2 = new ObjectCard(ObjectCardType.Adrenaline);
		lightsCard = new ObjectCard(ObjectCardType.SpotLight);
		
		model1 = new GameModel("Galvani");
		model1.setGameState(GameState.RUNNING);
		
		turn1 = new Turn(player1);
		turn2 = new Turn(player2);
		turn3 = new Turn(player3);
		
		sector1 = model1.getGameMap().getTable().get(2).get(4);
		sector2 = model1.getGameMap().getTable().get(3).get(6);
		sector3 = model1.getGameMap().getTable().get(5).get(11);
		sector4 = model1.getGameMap().getTable().get(8).get(13);
		
		avatar1 = new Alien(Name.Alien1 , sector2);
		avatar2 = new Human(Name.Human1 , sector3);
		avatar3 = new Human(Name.Human2 , sector2);
		
		avatar1.addCard(adrenalineCard1);
		avatar2.addCard(adrenalineCard2);
		avatar3.addCard(lightsCard);
		
		killedPlayer = new ArrayList<Player>();
		addPlayers = new ArrayList<Player>();
		toDeclare = new HashMap<String , Sector>();
		
		sectorDeck1 = new SectorDeck();
		objectDeck1 = new ObjectDeck();
		
		player1.setAvatar(avatar1);
		player2.setAvatar(avatar2);
		player3.setAvatar(avatar3);
		addPlayers.add(player1);
		addPlayers.add(player2);
		addPlayers.add(player3);
		model1.setGamePlayers(addPlayers);
		
		model1.setActualTurn(turn1);
		
		evtAdren1 = new EventAdren(player1 , adrenalineCard1);
		evtAdren2 = new EventAdren(player2 , adrenalineCard2);
		evtLights = new EventLights(player3 , sector2 , lightsCard);
		evtNoiseMySect = new EventNoiseMySect(player2);
		evtNoiseRandSect = new EventNoiseRandSect(player3 , sector4);
		
		useAdrenalineCard1 = new UseAdrenalineCard(evtAdren1);
		useAdrenalineCard2 = new UseAdrenalineCard(evtAdren2);
		useLightsCard = new UseLightsCard(evtLights);
		useMySectorNoise = new UseMySectorNoise(evtNoiseMySect);
		useRandomSectorNoise = new UseRandomSectorNoise(evtNoiseRandSect);
		
		toDeclare.put(player1.getName(), player1.getAvatar().getCurrentSector());
		toDeclare.put(player3.getName() , player3.getAvatar().getCurrentSector());
		
	}
	
	

	@Test
	public void test() {

		
		assertEquals(useAdrenalineCard1.isPossible(model1) , false);
		
		model1.setActualTurn(turn2);
		
		assertEquals(useAdrenalineCard2.isPossible(model1) , true); 
		useAdrenalineCard2.perform(model1);
		assertEquals(model1.getActualTurn().getCurrentPlayer().getAvatar().getIsPowered() , true);
		assertEquals(model1.getActualTurn().getCurrentPlayer().getAvatar().getMyCards() , new ArrayList<Card>());
		
		assertEquals(useMySectorNoise.isPossible(model1) , true);
		evtDeclareNoise1 = (EventDeclareNoise) useMySectorNoise.perform(model1);
		assertEquals(evtDeclareNoise1.getSectorToNoise() , sector3);
		
		model1.setActualTurn(turn3);
		assertEquals(useLightsCard.isPossible(model1) , true);
		evtDeclarePosition1 = (EventDeclarePosition) useLightsCard.perform(model1);
		assertEquals(model1.getActualTurn().getCurrentPlayer().getAvatar().getMyCards() , new ArrayList<Card>());
		assertEquals(evtDeclarePosition1.getToDeclare() , toDeclare);
		
		evtDeclareNoise2 = (EventDeclareNoise) useRandomSectorNoise.perform(model1);
		assertEquals(evtDeclareNoise2.getSectorToNoise() , sector4);
		
		
	}

}
