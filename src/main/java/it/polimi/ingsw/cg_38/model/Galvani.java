package it.polimi.ingsw.cg_38.model;
import java.util.*;

/**
 * 
 */
public class Galvani extends Map {

    /**
     * 
     */
    public Galvani() {
    }

    /**
     * 
     */
    private final String name = "Galvani";

    /**
     * 
     */
    private final int[][] configuration = null;

    /**
     * @return
     */
    public Map createGalvani() {
        // TODO implement here
    	Map galvani = new Galvani();
    	for(int i = 1; i<galvani.height+1; i++) {
    		for(int j = 1; j<galvani.width+1; j++) {
    			if(configuration[i][j]==-1) {
    				galvani.table[i][j] = null;
    			}if(configuration[i][j]==0) {
    				Sector mySector = new Sector();
    				mySector.factoryCreator("Safe");
    				galvani.table[i][j] = mySector;
    			}if(configuration[i][j]==1) {
    				Sector mySector = new Sector();
    				mySector.factoryCreator("Dangerous");
    				galvani.table[i][j] = mySector;
    			}if(configuration[i][j]==2) {
    				Sector mySector = new Sector();
    				mySector.factoryCreator("HumanStartingPoint");
    				galvani.table[i][j] = mySector;
    			}if(configuration[i][j]==3) {
    				Sector mySector = new Sector();
    				mySector.factoryCreator("AlienStartingPoint");
    				galvani.table[i][j] = mySector;
    			}if(configuration[i][j]==4) {
    				Sector mySector = new Sector();
    				mySector.factoryCreator("Hatch");
    				galvani.table[i][j] = mySector;
    			}
    		}
    	}
    	
        return galvani;
    }

}