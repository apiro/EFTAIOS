package it.polimi.ingsw.cg_38.model;

import static org.junit.Assert.*;
import it.polimi.ingsw.cg_38.model.deck.Deck;
import it.polimi.ingsw.cg_38.model.deck.DeckCreator;
import it.polimi.ingsw.cg_38.model.deck.HatchCard;
import it.polimi.ingsw.cg_38.model.deck.HatchCardType;
import it.polimi.ingsw.cg_38.model.deck.ObjectCard;
import it.polimi.ingsw.cg_38.model.deck.ObjectCardType;
import it.polimi.ingsw.cg_38.model.deck.SectorCard;
import it.polimi.ingsw.cg_38.model.deck.SectorCardType;
import it.polimi.ingsw.cg_38.model.deck.SectorDeck;
import it.polimi.ingsw.cg_38.model.deck.HatchDeck;
import it.polimi.ingsw.cg_38.model.deck.ObjectDeck;
import it.polimi.ingsw.cg_38.model.map.Dangerous;
import it.polimi.ingsw.cg_38.model.map.Hatch;
import it.polimi.ingsw.cg_38.model.map.Sector;

import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.Before;
import org.junit.Test;

/** contiene i test di alcune classi del modello */
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
	SectorCard card1;
	ObjectCard card2;
	HatchCard card3;
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
	
	Deck deckSector;
	Deck deckObject;
	Deck deckHatch;
	
	@Before
	public void init() throws ParserConfigurationException, Exception {
		
		avatar11 = new Alien(Name.ALIEN2 , sector);
		avatar12 = new Human(Name.HUMAN1 , sector);
		avatar22 = new Alien(Name.ALIEN1 , sector);
		avatar32 = new Human(Name.HUMAN2 , sector);
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
		card1 = new SectorCard(SectorCardType.MYSECTORNOISE , false);
		card2 = new ObjectCard(ObjectCardType.ATTACKCARD);
		card3 = new HatchCard(HatchCardType.GREEN);
		player11 = model1.getGamePlayers().get(0);
		player21 = model2.getGamePlayers().get(0);
		player21.setAvatar(avatar22);
		player31 = model3.getGamePlayers().get(0);
		player31.setAvatar(avatar32);
		player31.getAvatar().setIsAlive(LifeState.ALIVE);
		model1.getGamePlayers().get(1).setAvatar(avatar12);
		player12 = model1.getGamePlayers().get(1);
		model2.getGamePlayers().get(1).setAvatar(avatar22);
		player22 = model2.getGamePlayers().get(1);
		player22.getAvatar().setIsAlive(LifeState.ALIVE);
		player22.getAvatar().setIsWinner(EndState.PLAYING);
		player21.getAvatar().setIsAlive(LifeState.ALIVE);
		player21.getAvatar().setIsWinner(EndState.PLAYING);
		model3.getGamePlayers().get(1).setAvatar(avatar32);
		player32 = model3.getGamePlayers().get(1);
		player18 = model1.getGamePlayers().get(7);
		player18.setAvatar(avatar32);
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
		/* verifico il corretto funzionamento dei vari getter and setter */
		assertEquals(model4.getNextPlayer(), null);
		
		assertEquals(model1.getActualTurn(), turn1);
		assertEquals(model2.getActualTurn(), turn2);
		assertEquals(model3.getActualTurn(), turn3);
		assertEquals(model1.getNextPlayer(), player12);
		assertEquals(model2.getNextPlayer(), player22);
		assertEquals(model3.getNextPlayer(), player32);
		
		model1.setActualTurn(turn8);
		/* verifico il corretto funzionamento del metodo getNextPlayer del model */
		assertEquals(model1.getNextPlayer(), player11);
		
		assertEquals(player11.getName(), "Anna");
		
		i = player11.getNumTurniGiocati();
		player11.finishTurn();
		assertEquals(player11.getNumTurniGiocati(), i+1);
		
		assertEquals(DeckCreator.createDeck("nothing") , null);
		
		/* verifica il corretto funzionamento del metodo handelRejectedCard del model */
		model1.handleRejectedCard(card1);
		assertTrue(((SectorDeck)model1.getDeckSector()).getRejectedSectorDeck().contains(card1));
		model1.handleRejectedCard(card2);
		assertTrue(((ObjectDeck)model1.getDeckObject()).getRejectedObjectDeck().contains(card2));
		model1.handleRejectedCard(card3);
		assertTrue(((HatchDeck)model1.getDeckHatch()).getRejectedHatchDeck().contains(card3));
		
		/* verifia il corretto funzionamento del metodo equals del settore */
		assertEquals(sector2.equals(null) , false);
		assertEquals(sector2.equals(sector3) , false);
		sector2.setCol(7);
		sector3.setCol(7);
		assertEquals(sector2.equals(sector3) , false);
		sector2.setName(null);
		sector3.setName("Hatch");
		assertEquals(sector2.equals(sector3) , false);
		sector2.setName("Hatch");
		sector3.setName("Hatch");
		sector2.setNeighboringSectors(null);
		sector3.setNeighboringSectors(null);
		assertEquals(sector2.equals(sector3) , true);
		sector3.setNeighboringSectors(neighboringSectors);
		assertEquals(sector2.equals(sector3) , false);
		sector2.setNeighboringSectors(neighboringSectors);
		sector2.setRow(5);
		sector3.setRow(5);
		assertEquals(sector2.equals(sector3) , true);
		
		assertEquals(sector3.toString() , "Sector [name=" + sector3.getName() + "row:" + sector3.getRow() + "col" + sector3.getCol() + "]");
		
		hatch.setIsOpen(true);
		assertEquals(hatch.getIsOpen() , true);
		
		assertTrue(model3.areThereOtherHumans());
		
		}

}
