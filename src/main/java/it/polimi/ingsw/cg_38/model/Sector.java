package it.polimi.ingsw.cg_38.model;
import java.util.*;

/**
 * 
 */
public class Sector {

    /**
     * 
     */
    public Sector() {
    }

    public String name;
    
    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
     * 
     */
    private ArrayList<Sector> neighboringSectors;

    /**
     * 
     */
    private int row;

    /**
     * 
     */
    private String col;






    /**
     * @param String typeSector 
     * @return
     */
    public Sector factoryCreator(String typeSector) {
        // TODO implement here
        return null;
    }

}