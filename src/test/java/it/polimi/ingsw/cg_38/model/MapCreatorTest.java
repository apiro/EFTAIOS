
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
		assertTrue(MapCreator.createMap("Galilei") instanceof Galilei);
		assertTrue(MapCreator.createMap("Galvani") instanceof Galvani);
		assertTrue(MapCreator.createMap("Fermi") instanceof Fermi);
		assertTrue(MapCreator.createMap("myMap") instanceof Map);
	}

}
