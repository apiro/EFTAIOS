
package it.polimi.ingsw.cg_38.model;

import static org.junit.Assert.*;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.Before;
import org.junit.Test;

public class MapCreatorTest {

	MapCreator mapCreator;
	DeckCreator deckCreator;
	
	@Before
	public void init() {
		mapCreator = new MapCreator();
		deckCreator = new DeckCreator();
	}
	
	@Test
	public void test() throws ParserConfigurationException, Exception {
		
		assertTrue(mapCreator instanceof MapCreator);
		assertTrue(deckCreator instanceof DeckCreator);
		System.out.println("Galilei:");
		assertTrue(MapCreator.createMap("Galilei") instanceof Galilei);
		MapCreator.createMap("Galilei").printMap();
		System.out.println("Galvani:");
		assertTrue(MapCreator.createMap("Galvani") instanceof Galvani);
		MapCreator.createMap("Galvani").printMap();
		System.out.println("Fermi:");
		assertTrue(MapCreator.createMap("Fermi") instanceof Fermi);
		MapCreator.createMap("Fermi").printMap();
		System.out.println("myMap:");
		assertTrue(MapCreator.createMap("myMap") instanceof Map);
		MapCreator.createMap("mymap").printMap();
	}

}
