package it.polimi.ingsw.cg_38.model;

import static org.junit.Assert.*;
import it.polimi.ingsw.cg_38.model.deck.DeckCreator;
import it.polimi.ingsw.cg_38.model.map.Dangerous;
import it.polimi.ingsw.cg_38.model.map.Hatch;
import it.polimi.ingsw.cg_38.model.map.Sector;

import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.Before;
import org.junit.Test;

public class GameModelTest {

	GameModel model1;
	GameModel model2;
	GameModel model3;
	GameModel model4;
	Player player11;
	Player player21;
	Player player31;
	Player player12;
	Player player22;
	Player player32;
	Player player18;
	Avatar avatar11;
	Avatar avatar12;
	Avatar avatar22;
	Avatar avatar32;
	Turn turn1;
	Turn turn2;
	Turn turn3;
	Turn turn8;
	Sector sector;
	Sector sector2;
	Sector sector3;
	Hatch hatch;
	ArrayList<Sector> neighboringSectors;
	int i;
	
	/*Deck deckSector11;
	Deck deckObject12;
	Deck deckHatch13;
	Deck deckSector21;
	Deck deckObject22;
	Deck deckHatch23;
	Deck deckSector31;
	Deck deckObject32;
	Deck deckHatch33;*/
	
	
	@Before
	public void init() throws ParserConfigurationException, Exception {
		
		avatar11 = new Alien(Name.Alien2 , sector);
		avatar12 = new Human(Name.Human1 , sector);
		avatar22 = new Alien(Name.Alien1 , sector);
		avatar32 = new Human(Name.Human2 , sector);
		model1 = new GameModel("Galilei");
		model2 = new GameModel("Galvani");
		model3 = new GameModel("Fermi");
		model4 = new GameModel("myMap");
		model1.getGamePlayers().add(new Player("Anna"));
		model1.getGamePlayers().add(new Player("Alberto"));
		model2.getGamePlayers().add(new Player("Anna"));
		model2.getGamePlayers().add(new Player("Alberto"));
		model3.getGamePlayers().add(new Player("Anna"));
		model3.getGamePlayers().add(new Player("Alberto"));
		model1.getGamePlayers().add(new Player("Marco"));
		model1.getGamePlayers().add(new Player("Frank"));
		model1.getGamePlayers().add(new Player("Mario"));
		model1.getGamePlayers().add(new Player("Andrea"));
		model1.getGamePlayers().add(new Player("Ermenegilda"));
		model1.getGamePlayers().add(new Player("Candeloro"));
		model1.getGamePlayers().get(0).setAvatar(avatar11);
		player11 = model1.getGamePlayers().get(0);
		player21 = model2.getGamePlayers().get(0);
		player31 = model3.getGamePlayers().get(0);
		model1.getGamePlayers().get(1).setAvatar(avatar12);
		player12 = model1.getGamePlayers().get(1);
		model2.getGamePlayers().get(1).setAvatar(avatar22);
		player22 = model2.getGamePlayers().get(1);
		model3.getGamePlayers().get(1).setAvatar(avatar32);
		player32 = model3.getGamePlayers().get(1);
		player18 = model1.getGamePlayers().get(7);
		turn1 = new Turn(player11);
		turn2 = new Turn(player21);
		turn3 = new Turn(player31);
		turn8 = new Turn(player18);
		model1.setActualTurn(turn1);
		model2.setActualTurn(turn2);
		model3.setActualTurn(turn3);
		sector2 = new Dangerous();
		sector2.setName(null);
		sector3 = new Dangerous();
		hatch = new Hatch();
		neighboringSectors = new ArrayList<Sector>();
		neighboringSectors.add(sector);
	}
	
	
	
	@Test
	public void test() {
		
		assertEquals(model4.getNextPlayer(), null);
		
		assertEquals(model1.getActualTurn(), turn1);
		assertEquals(model2.getActualTurn(), turn2);
		assertEquals(model3.getActualTurn(), turn3);
		
		assertEquals(model1.getNextPlayer(), player12);
		assertEquals(model2.getNextPlayer(), player22);
		assertEquals(model3.getNextPlayer(), player32);
		
		model1.setActualTurn(turn8);
		
		assertEquals(model1.getNextPlayer(), player11);
		
		assertEquals(player11.getName(), "Anna");
		
		i = player11.getNumTurniGiocati();
		player11.finishTurn();
		assertEquals(player11.getNumTurniGiocati(), i+1);
		assertEquals(DeckCreator.createDeck("nothing") , null);

		assertEquals(sector2.equals(null) , false);
		assertEquals(sector2.equals(sector3) , false);
		sector2.setCol(7);
		sector3.setCol(7);
		assertEquals(sector2.equals(sector3) , false);
		sector2.setName(null);
		assertEquals(sector2.equals(sector3) , false);
		sector2.setName("Hatch");
		sector3.setName("Hatch");
		sector2.setNeighboringSectors(null);
		sector3.setNeighboringSectors(neighboringSectors);
		assertEquals(sector2.equals(sector3) , false);
		sector2.setNeighboringSectors(neighboringSectors);
		sector2.setRow(5);
		sector3.setRow(5);
		assertEquals(sector2.equals(sector3) , true);
		assertEquals(sector3.toString() , "Sector [name=" + sector3.getName() + "row:" + sector3.getRow() + "col" + sector3.getCol() + "]");
		hatch.setIsOpen(true);
		assertEquals(hatch.getIsOpen() , true);
		}

}
