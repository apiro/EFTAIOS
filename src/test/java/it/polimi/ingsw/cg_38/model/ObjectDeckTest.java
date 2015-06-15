package it.polimi.ingsw.cg_38.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class ObjectDeckTest {
	
	ObjectDeck deck;
	int i;

	@Before
	public void init(){
		deck = new ObjectDeck();		
		
	}
	@Test
	public void test() {

		i = deck.getObjectDeck().size();
		deck.draw();
		assertEquals(deck.getObjectDeck().size() ,  i-1);
		
	}

}
