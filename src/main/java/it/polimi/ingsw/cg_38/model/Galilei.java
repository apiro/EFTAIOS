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
    private final String name = "Galilei";

    /**
     * 
     */
    private final int[][] configuration = null;

    /**
     * @return
     */
    public Map createGalilei() {
        // TODO implement here
    	Map galilei = new Galilei();
    	for(int i = 1; i<galilei.height+1; i++) {
    		for(int j = 1; j<galilei.width+1; j++) {
    			if(configuration[i][j]==-1) {
    				galilei.table[i][j] = null;
    			}if(configuration[i][j]==0) {
    				Sector mySector = new Sector();
    				mySector.factoryCreator("Safe");
    				galilei.table[i][j] = mySector;
    			}if(configuration[i][j]==1) {
    				Sector mySector = new Sector();
    				mySector.factoryCreator("Dangerous");
    				galilei.table[i][j] = mySector;
    			}if(configuration[i][j]==2) {
    				Sector mySector = new Sector();
    				mySector.factoryCreator("HumanStartingPoint");
    				galilei.table[i][j] = mySector;
    			}if(configuration[i][j]==3) {
    				Sector mySector = new Sector();
    				mySector.factoryCreator("AlienStartingPoint");
    				galilei.table[i][j] = mySector;
    			}if(configuration[i][j]==4) {
    				Sector mySector = new Sector();
    				mySector.factoryCreator("Hatch");
    				galilei.table[i][j] = mySector;
    			}
    		}
    	}
    	
        return galilei;
    }

}