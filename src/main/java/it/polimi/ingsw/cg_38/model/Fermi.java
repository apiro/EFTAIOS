package it.polimi.ingsw.cg_38.model;
import java.util.*;

/**
 * 
 */
public class Fermi extends Map {

    /**
     * 
     */
    public Fermi() {
    	
    }

    public int[][] getConfiguration() {
		return configuration;
	}

	/**
     * 
     */
    private final String name = "Fermi";

    /**
     * 
     */
    private final int[][] configuration = null;

    /**
     * @return
     */
    public Map createFermi() {
        // TODO implement here
    	Map fermi = new Fermi();
    	for(int i = 1; i<fermi.height+1; i++) {
    		for(int j = 1; j<fermi.width+1; j++) {
    			if(configuration[i][j]==-1) {
    				fermi.table[i][j] = null;
    			}if(configuration[i][j]==0) {
    				Sector mySector = new Sector();
    				mySector.factoryCreator("Safe");
    				fermi.table[i][j] = mySector;
    			}if(configuration[i][j]==1) {
    				Sector mySector = new Sector();
    				mySector.factoryCreator("Dangerous");
    				fermi.table[i][j] = mySector;
    			}if(configuration[i][j]==2) {
    				Sector mySector = new Sector();
    				mySector.factoryCreator("HumanStartingPoint");
    				fermi.table[i][j] = mySector;
    			}if(configuration[i][j]==3) {
    				Sector mySector = new Sector();
    				mySector.factoryCreator("AlienStartingPoint");
    				fermi.table[i][j] = mySector;
    			}if(configuration[i][j]==4) {
    				Sector mySector = new Sector();
    				mySector.factoryCreator("Hatch");
    				fermi.table[i][j] = mySector;
    			}
    		}
    	}
    	
        return fermi;
    }

}