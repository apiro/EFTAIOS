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
    	this.setNeighboringSectors(new ArrayList<Sector>());
    }

    public void setNeighboringSectors(ArrayList<Sector> neighboringSectors) {
		this.neighboringSectors = neighboringSectors;
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
    private int col;

    /**
     * @param String typeSector 
     * @return
     */
    public static Sector factoryCreator(String typeSector) {
        // TODO implement here
    	Sector creatingSector;
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
    	} else if(typeSector == "Empty") {
    		creatingSector = new Empty();
    	} else {
    		creatingSector = null;
    	}
        return creatingSector;
    }
    
    public static void main( String[] args )
    {
    	Sector sec = Sector.factoryCreator("Safe");
    	System.out.println(sec.toString());
    	
    }

	@Override
	public String toString() {
		return "Sector [name=" + name + "row:" + this.getRow() + "col" + this.getCol() + "]";
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getCol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;
	}

	public ArrayList<Sector> getNeighboringSectors() {
		return neighboringSectors;
	}

}