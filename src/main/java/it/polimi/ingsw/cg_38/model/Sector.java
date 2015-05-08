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
    	Sector creatingSector = new Sector();
    	if(typeSector == "Safe") {
    		creatingSector = new Safe();
    	} else if(typeSector == "Hatch") {
    		creatingSector = new Hatch();
    	} else if(typeSector == "HumanStartingPoint") {
    		creatingSector = new HumanStartingPoint();
    	} else if(typeSector == "AlienStartingPoint") {
    		creatingSector = new AlienStartingPoint();
    	} else if(typeSector == "Dangerous") {
    		creatingSector = new Dangerous();
    	}
        return creatingSector;
    }

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public String getCol() {
		return col;
	}

	public void setCol(String col) {
		this.col = col;
	}

	public ArrayList<Sector> getNeighboringSectors() {
		return neighboringSectors;
	}

}