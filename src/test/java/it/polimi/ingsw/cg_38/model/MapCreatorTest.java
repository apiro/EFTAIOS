
package it.polimi.ingsw.cg_38.model;

import static org.junit.Assert.*;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.Before;
import org.junit.Test;

public class MapCreatorTest {

	MapCreator mapCreator;
	DeckCreator deckCreator;
	Map map;
	Map map1;
	
	@Before
	public void init() {
		mapCreator = new MapCreator();
		deckCreator = new DeckCreator();
		
		map = new Galilei();
		map1 = new Fermi();
	}
	
	@Test
	public void test() throws ParserConfigurationException, Exception {
		
		assertTrue(mapCreator instanceof MapCreator);
		assertTrue(deckCreator instanceof DeckCreator);
		assertTrue(MapCreator.createMap("Galilei") instanceof Galilei);
		MapCreator.createMap("Galilei");
		assertTrue(MapCreator.createMap("Galvani") instanceof Galvani);
		MapCreator.createMap("Galvani");
		assertTrue(MapCreator.createMap("Fermi") instanceof Fermi);
		MapCreator.createMap("Fermi");
		assertTrue(MapCreator.createMap("myMap") instanceof Map);
		MapCreator.createMap("mymap");
		
		assertEquals(map.searchSectorByName("nothing") , null);
		assertEquals(map.searchSectorByCoordinates(56, 12) , null);
		map.readMap("Nothing");
		map1.getConfiguration();
	}

}
