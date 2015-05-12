package it.polimi.ingsw.cg_38.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class ObjectDeckTest {
	
	ObjectDeck deck;
	private boolean contain;

	@Before
	public void init(){
		deck = new ObjectDeck();
		contain = false;
		
		
	}
	@Test
	public void test() {
		Card ObjectCardDraw = deck.draw();
		if(deck.getObjectDeck().contains(ObjectCardDraw)){
			contain = true;
		}
		
		assertEquals(contain, false);
		
	}

}
