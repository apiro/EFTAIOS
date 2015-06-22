
package it.polimi.ingsw.cg_38.model;

import static org.junit.Assert.*;

import java.util.ArrayList;

import it.polimi.ingsw.cg_38.model.deck.DeckCreator;
import it.polimi.ingsw.cg_38.model.map.AlienStartingPoint;
import it.polimi.ingsw.cg_38.model.map.Dangerous;
import it.polimi.ingsw.cg_38.model.map.Empty;
import it.polimi.ingsw.cg_38.model.map.Fermi;
import it.polimi.ingsw.cg_38.model.map.Galilei;
import it.polimi.ingsw.cg_38.model.map.Galvani;
import it.polimi.ingsw.cg_38.model.map.Hatch;
import it.polimi.ingsw.cg_38.model.map.Map;
import it.polimi.ingsw.cg_38.model.map.MapCreator;
import it.polimi.ingsw.cg_38.model.map.Safe;
import it.polimi.ingsw.cg_38.model.map.Sector;
import it.polimi.ingsw.cg_38.model.map.HumanStartingPoint;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.Before;
import org.junit.Test;

public class MapCreatorTest {

	MapCreator mapCreator;
	DeckCreator deckCreator;
	Map map;
	Map map1;
	Map map2;
	Sector sector1;
	Sector sector2;
	ArrayList<Sector> sectors;
	
	@Before
	public void init() {
		mapCreator = new MapCreator();
		deckCreator = new DeckCreator();
		
		sectors = new ArrayList<Sector>();
		
		map = new Galilei();
		map1 = new Fermi();
		map2 = new Galvani();
		
		sector1 = new Dangerous();
		sector2 = sector1;
	
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
		assertEquals(map.getConfiguration().length , 322);
		map2.getConfiguration();
		assertEquals(((Galilei)map).getName() , "Galilei");
		assertEquals(((Fermi)map1).getName() , "Fermi");
		assertEquals(((Galvani)map2).getName() , "Galvani");
		
		sector1.hashCode();
		assertTrue(sector1.equals(sector2));
		sector2 = null;
		assertTrue(!sector1.equals(sector2));
		sector2 = new Hatch();
		assertTrue(!sector1.equals(sector2));
		sector2 = new Dangerous();
		sector2.setCol(7);
		sector1.setCol(6);
		assertEquals(sector1.getCol() , 6);
		assertTrue(!sector1.equals(sector2));
		sector2.setCol(6);
		sector1.setName(null);
		assertTrue(!sector1.equals(sector2));
		sector1.setName("HumanStartingPoint");
		assertEquals(sector1.getName() , "HumanStartingPoint");
		assertTrue(!sector1.equals(sector2));
		sector2.getNeighboringSectors().add(sector1);
		sector1.setNeighboringSectors(null);
		sector1.setName("Dangerous");
		assertTrue(!sector1.equals(sector2));
		sectors.add(sector1);
		sector1.setNeighboringSectors(sectors);
		sector1.setRow(5);
		assertEquals(sector1.getRow() , 5);
		sector2.setRow(4);
		assertTrue(!sector1.equals(sector2));
		sector2.setRow(5);
		assertTrue(sector1.equals(sector2));
		
		assertTrue(Sector.factoryCreator("Empty") instanceof Empty);
		assertTrue(Sector.factoryCreator("Dangerous") instanceof Dangerous);
		assertTrue(Sector.factoryCreator("Hatch")instanceof Hatch);
		assertTrue(Sector.factoryCreator("HumanStartingPoint") instanceof HumanStartingPoint);
		assertTrue(Sector.factoryCreator("AlienStartingPoint") instanceof AlienStartingPoint);
		assertTrue(Sector.factoryCreator("Safe") instanceof Safe);
		assertEquals(Sector.factoryCreator("nothing") , null);
				
	
	}

}
