package it.polimi.ingsw.cg_38.model;
import java.util.*;

/**
 * 
 */
public class MapCreator {

    /**
     * 
     */
    public MapCreator() {
    }

    /**
     * @param String type
     */
    public Map createMap(String type) {
        // TODO implement here
    	Map creating;
    	if(type == "Galilei") {
    		creating = new Galilei();
    		((Galilei) creating).createGalilei();
    	} else if (type == "Galvani") {
    		creating = new Galvani();
    		((Galvani) creating).createGalvani();
    	} else if (type == "Fermi") {
    		creating = new Fermi();
    		((Fermi) creating).createFermi();
    	}
    	return null;
    }

}