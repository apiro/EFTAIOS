package it.polimi.ingsw.cg_38.model;
import java.util.*;

/**
 * 
 */
public class Galilei extends Map {

    /**
     * 
     */
    public Galilei() {
    }

    /**
     * 
     */
    private String name;

    /**
     * 
     */
    private final int[] configuration = {3,4,5,6};

    /**
     * @return
     */
    public Map createGalilei() {
        // TODO implement here
    	this.table[1][2] = new Sector();
    	
        return null;
    }

}