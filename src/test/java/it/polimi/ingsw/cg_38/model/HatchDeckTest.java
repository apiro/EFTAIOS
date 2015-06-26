package it.polimi.ingsw.cg_38.model;

import static org.junit.Assert.*;
import it.polimi.ingsw.cg_38.model.deck.HatchCard;
import it.polimi.ingsw.cg_38.model.deck.HatchDeck;

import org.junit.Before;
import org.junit.Test;

/** contiene i test del mazzo di carte scialuppa */
public class HatchDeckTest {

	HatchDeck deck;
	Boolean contains;
	
	@Before 
	public void init(){
		deck = new HatchDeck();
	}
	
	@Test
	public void test() {
		/* verifica il corretto funzionamento del metodo draw del mazzo di carte scialuppa */
		HatchCard hatchCard = (HatchCard)deck.draw();

		assertEquals(hatchCard.toString() , "HatchCard [color=" + hatchCard.getColor() + "]");
		deck.draw();
		deck.draw();
		deck.draw();
		deck.draw();
		deck.draw();
		assertEquals(hatchCard , deck.draw());
	}
}
