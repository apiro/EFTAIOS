package it.polimi.ingsw.cg_38.model;

import static org.junit.Assert.*;

import java.util.ArrayList;

import it.polimi.ingsw.cg_38.model.deck.SectorCard;
import it.polimi.ingsw.cg_38.model.deck.SectorCardType;
import it.polimi.ingsw.cg_38.model.deck.SectorDeck;

import org.junit.Before;
import org.junit.Test;

public class SectorDeckTest {

	SectorDeck deck;
	
	ArrayList<SectorCard> rejected;
	
	int a;
	
	SectorCard card1;
	SectorCard card2;
	private boolean contain;

	@Before
	public void init(){
		
		deck = new SectorDeck();
		contain = false;
		
		rejected = new ArrayList<SectorCard>();
		card1 = new SectorCard(SectorCardType.MySectorNoise , true);
		card2 = new SectorCard(SectorCardType.RandomSectorNoise , false);
		rejected.add(card1);
		rejected.add(card2);
		
		
		
	}
	@Test
	public void test() {
		/*
		Card sectorCardDrown = deck.draw();
		if(deck.getSectorDeck().contains(sectorCardDrown)){
			contain = true;
		}*/
		
		deck.setRejectedSectorDeck(rejected);
		assertEquals(deck.getRejectedSectorDeck() , rejected);
		a = deck.getSectorDeck().size();
		deck.shuffle();
		assertEquals(deck.getSectorDeck().size() , a);
		deck.setSectorDeck(rejected);
		assertEquals(deck.draw() , card1);
		deck.eliminateCard(card2);/*
		assertEquals(deck.getRejectedSectorDeck().size() , 3);
		deck.draw();
		assertEquals(deck.draw() , card1);
	*/
	}
}
