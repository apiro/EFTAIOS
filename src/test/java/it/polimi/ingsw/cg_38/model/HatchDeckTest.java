package it.polimi.ingsw.cg_38.model;

import static org.junit.Assert.*;
import it.polimi.ingsw.cg_38.model.deck.HatchCard;
import it.polimi.ingsw.cg_38.model.deck.HatchDeck;

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
		HatchCard hatchCard = (HatchCard)deck.draw();
		if(!deck.getHatchDeck().contains(hatchCard)) {
			contains = false;
		} else {
			contains = true;
		}
		assertEquals(contains, false);
		assertEquals(hatchCard.toString() , "HatchCard [color=" + hatchCard.getColor() + "]");
	}
}
