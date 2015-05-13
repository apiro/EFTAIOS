package it.polimi.ingsw.cg_38.model;

import static org.junit.Assert.*;

import org.junit.Test;

public class MapCreatorTest {

	@Test
	public void test() {
		
		assertTrue(MapCreator.createMap("Galilei") instanceof Galilei);
		assertTrue(MapCreator.createMap("Galvani") instanceof Galvani);
		assertTrue(MapCreator.createMap("Fermi") instanceof Fermi);
	}

}
