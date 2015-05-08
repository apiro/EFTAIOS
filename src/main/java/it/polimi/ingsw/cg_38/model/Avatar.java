package it.polimi.ingsw.cg_38.model;
import java.util.*;

/**
 * 
 */
public abstract class Avatar {

    /**
     * 
     */
    private ObjectCard[] myCards;

    /**
     * 
     */
    private Sector currentSector;

    /**
     * 
     */
    private int numTurniGiocati;

    /**
     * 
     */
    private Name name;

    /**
     * 
     */
    private Boolean isPowered;

    /**
     * 
     */
    private LifeState isAlive;

    /**
     * 
     */
    private EndState isWinner;





    /**
     * @param Name name
     */
    public void Avatar(Name name) {
        // TODO implement here
    }

    /**
     * @param Deck deck 
     * @return
     */
    public Card draw(Deck deck) {
        // TODO implement here
        return null;
    }

    /**
     * @param Sector sector 
     * @return
     */
    public String move(Sector sector) {
        // TODO implement here
        return "";
    }

    /**
     * @param Card card 
     * @return
     */
    public Card eliminateFromMyCards(Card card) {
        // TODO implement here
        return null;
    }

    /**
     * @param Card card 
     * @return
     */
    public void addCard(Card card) {
        // TODO implement here
    }

    /**
     * @param Sector sector 
     * @return
     */
    public void setCurrentSector(Sector sector) {
        // TODO implement here
    }

    /**
     * @param Sector sector 
     * @return
     */
    public abstract Boolean canMove(Sector sector);

    /**
     * @return
     */
    public void setPowered() {
        // TODO implement here
    }

    /**
     * @param Sector sector 
     * @return
     */
    public void attack(Sector sector) {
        // TODO implement here
    }

}