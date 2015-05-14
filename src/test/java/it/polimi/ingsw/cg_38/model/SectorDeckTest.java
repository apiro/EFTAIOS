package it.polimi.ingsw.cg_38.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class SectorDeckTest {

	SectorDeck deck;
	private boolean contain;

	@Before
	public void init(){
		deck = new SectorDeck();
		contain = false;
		
		
	}
	@Test
	public void test() {
		Card sectorCardDrown = deck.draw();
		if(deck.getSectorDeck().contains(sectorCardDrown)){
			contain = true;
		}
		
		assertEquals(contain, false);
		assertTrue(((SectorCard)sectorCardDrown).getType() instanceof SectorCardType);
	}
}
