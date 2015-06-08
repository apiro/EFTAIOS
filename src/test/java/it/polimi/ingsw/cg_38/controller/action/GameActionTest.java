package it.polimi.ingsw.cg_38.controller.action;

import static org.junit.Assert.*;

import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;

import it.polimi.ingsw.cg_38.gameEvent.EventAttack;
import it.polimi.ingsw.cg_38.gameEvent.EventDraw;
import it.polimi.ingsw.cg_38.model.Alien;
import it.polimi.ingsw.cg_38.model.Avatar;
import it.polimi.ingsw.cg_38.model.Card;
import it.polimi.ingsw.cg_38.model.Dangerous;
import it.polimi.ingsw.cg_38.model.Deck;
import it.polimi.ingsw.cg_38.model.GameModel;
import it.polimi.ingsw.cg_38.model.Human;
import it.polimi.ingsw.cg_38.model.Name;
import it.polimi.ingsw.cg_38.model.ObjectCard;
import it.polimi.ingsw.cg_38.model.ObjectCardType;
import it.polimi.ingsw.cg_38.model.ObjectDeck;
import it.polimi.ingsw.cg_38.model.Player;
import it.polimi.ingsw.cg_38.model.Sector;
import it.polimi.ingsw.cg_38.model.SectorCard;
import it.polimi.ingsw.cg_38.model.SectorCardType;
import it.polimi.ingsw.cg_38.model.SectorDeck;
import it.polimi.ingsw.cg_38.model.Turn;
import it.polimi.ingsw.cg_38.notifyEvent.EventAttacked;
import it.polimi.ingsw.cg_38.notifyEvent.EventDrown;

import org.junit.Before;
import org.junit.Test;

public class GameActionTest {
	
	Draw draw1;
	Draw draw2;
	Attack attack1;
	
	EventAttacked evtAttacked1;
	EventDraw evtDraw1;
	EventDraw evtDraw2;
	EventAttack evtAttack1;
	
	EventDrown evtDrown1;
	EventDrown evtDrown2;
	EventDrown evtDrown3;
	
	Player player1;
	Player player2;
	Player player3;
	
	Turn turn1;
	Turn turn2;
	
	ArrayList<SectorCard> sectorList1;
	ArrayList<ObjectCard> objectList1;
	ArrayList<Player> killedPlayer;
	
	SectorDeck sectorDeck1;
	ObjectDeck objectDeck1;
	
	Card sectorCard1;
	Card sectorCard2;
	Card objectCard1;
	
	Sector sector1;
	
	Avatar avatar1;
	Avatar avatar2;
	Avatar avatar3;
	
	GameModel model1;
	
	SectorCard drown1;
	
	ObjectCard drown2;
	

	@Before
	public void init() throws ParserConfigurationException, Exception {
		
		player1 = new Player("albi");
		player2 = new Player("scimmiu");
		player2.getAvatar();
		player3 = new Player("shane");
		
		model1 = new GameModel("Galvani");
		
		turn1 = new Turn(player1);
		turn2 = new Turn(player2);
		turn2.setHasMoved(true);
		
		sector1 = new Dangerous();
		
		avatar1 = new Alien(Name.Alien1 , sector1);
		avatar2 = new Alien(Name.Alien2 , sector1);
		avatar3 = new Human(Name.Human1 , sector1);
		
		sectorList1 = new ArrayList<SectorCard>();
		objectList1 = new ArrayList<ObjectCard>();
		killedPlayer = new ArrayList<Player>();
		
		sectorDeck1 = new SectorDeck();
		objectDeck1 = new ObjectDeck();
		
		sectorCard1 = new SectorCard(SectorCardType.Silence , false);
		sectorCard2 = new SectorCard(SectorCardType.MySectorNoise , true);
		objectCard1 = new ObjectCard(ObjectCardType.Attack );
		
		evtDraw1 = new EventDraw(player1);
		evtDraw2 = new EventDraw(player1);
		evtAttack1 = new EventAttack(player2 , sector1);
		
		draw1 = new Draw(evtDraw1);
		draw2 = new Draw(evtDraw2);
		attack1 = new Attack(evtAttack1);
		
		player1.setAvatar(avatar1);
		player2.setAvatar(avatar2);
		player3.setAvatar(avatar3);
		
		sectorList1.add((SectorCard)sectorCard1);
		sectorList1.add((SectorCard)sectorCard2);
		objectList1.add((ObjectCard)objectCard1);
		killedPlayer.add(player1);
		killedPlayer.add(player3);
		
		sectorDeck1.setSectorDeck(sectorList1);
		objectDeck1.setObjectDeck(objectList1);
		
		model1.setActualTurn(turn1);
		model1.setDeckSector(sectorDeck1);
		model1.setDeckObject(objectDeck1);
		
		evtDrown1 = (EventDrown)draw1.perform(model1);	
		evtDrown2 = (EventDrown)draw2.perform(model1);
		
		
	}
	
	
	
	@Test
	public void test() {
		
			assertEquals(sectorCard1 , evtDrown1.getDrown());
			
			assertEquals(sectorCard2 , evtDrown2.getDrown());
			assertEquals(objectCard1 , evtDrown2.getAdded());
			
			model1.setActualTurn(turn2);
			evtAttacked1 = (EventAttacked)attack1.perform(model1);
			
			assertEquals(killedPlayer , evtAttacked1.getKilled());	
			
	}
}
