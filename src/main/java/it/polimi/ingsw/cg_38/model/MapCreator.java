package it.polimi.ingsw.cg_38.model;
import java.util.*;

/**
 * 
 */
public class MapCreator {

    /**
     * @param String type
     */
    public static Map createMap(String type) {
        // TODO implement here
    	Map creating;
    	if(type == "Galilei") {
    		creating = new Galilei();
    	} else if (type == "Galvani") {
    		creating = new Galvani();
    	} else if (type == "Fermi") {
    		creating = new Fermi();
    	} else {
    		creating = null;
    	}
    	return creating;
    }
}