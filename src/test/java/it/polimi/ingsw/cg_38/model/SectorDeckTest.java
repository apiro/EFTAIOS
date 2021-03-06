package it.polimi.ingsw.cg_38.model;

import static org.junit.Assert.*;

import java.util.ArrayList;

import it.polimi.ingsw.cg_38.model.deck.Card;
import it.polimi.ingsw.cg_38.model.deck.SectorCard;
import it.polimi.ingsw.cg_38.model.deck.SectorCardType;
import it.polimi.ingsw.cg_38.model.deck.SectorDeck;

import org.junit.Before;
import org.junit.Test;

/** contiene i test del mazzo di carte settore */
public class SectorDeckTest {

	SectorDeck deck;
	
	ArrayList<SectorCard> rejected;
	
	int a;
	
	SectorCard card1;
	SectorCard card2;
	boolean contain;

	@Before
	public void init(){
		
		deck = new SectorDeck();
		contain = false;
		
		rejected = new ArrayList<SectorCard>();
		card1 = new SectorCard(SectorCardType.MYSECTORNOISE , true);
		card2 = new SectorCard(SectorCardType.RANDOMSECTORNOISE , false);
		rejected.add(card1);
		rejected.add(card2);
		
		
		
	}
	@Test
	public void test() {
		
		Card sectorCardDrown = deck.draw();
		if(deck.getSectorDeck().contains(sectorCardDrown)){
			contain = true;
		}
		
		deck.setRejectedSectorDeck(rejected);
		assertEquals(deck.getRejectedSectorDeck() , rejected);
		a = deck.getSectorDeck().size();
		deck.shuffle();
		assertEquals(deck.getSectorDeck().size() , a);
		deck.setSectorDeck(rejected);
		assertEquals(deck.draw() , card1);
		deck.eliminateCard(card2);
		
	}
	
}
