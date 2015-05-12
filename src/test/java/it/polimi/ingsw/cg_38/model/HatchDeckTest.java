package it.polimi.ingsw.cg_38.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class HatchDeckTest {

	HatchDeck deck;
	Boolean contains;
	
	@Before 
	public void init(){
		deck = new HatchDeck();
	}
	
	@Test
	public void test() {
		Card hatchCard = deck.draw();
		if(!deck.getHatchDeck().contains(hatchCard)) {
			contains = false;
		} else {
			contains = true;
		}
		assertEquals(contains, false);
	}
}
