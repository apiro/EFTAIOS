package it.polimi.ingsw.cg_38.model;

import static org.junit.Assert.*;
import it.polimi.ingsw.cg_38.model.deck.ObjectCard;
import it.polimi.ingsw.cg_38.model.deck.ObjectDeck;
import it.polimi.ingsw.cg_38.model.map.Sector;

import org.junit.Before;
import org.junit.Test;

public class ObjectDeckTest {
	
	ObjectDeck deck;
	ObjectCard objectCard;
	int i;

	@Before
	public void init(){
		deck = new ObjectDeck();		
		
	}
	@Test
	public void test() {

		i = deck.getObjectDeck().size();
		objectCard = (ObjectCard)deck.draw();
		/*
		assertEquals(deck.getObjectDeck().size() ,  i-1);
		*/
		assertEquals(objectCard.equals(null) , false);
		assertEquals(objectCard.equals(new Sector()) , false);
		
	}

}
