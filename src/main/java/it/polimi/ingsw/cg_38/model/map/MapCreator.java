package it.polimi.ingsw.cg_38.model.map;

import javax.xml.parsers.ParserConfigurationException;

/**
 * 
 */
public class MapCreator {

    /**
     * @param String type
     * @throws Exception 
     * @throws ParserConfigurationException 
     */
    public static Map createMap(String type) throws ParserConfigurationException, Exception {
    	Map creating;
    	if(("Galilei").equals(type)) {
    		creating = new Galilei();
    	} else if (("Galvani").equals(type)) {
    		creating = new Galvani();
    	} else if (("Fermi").equals(type)) {
    		creating = new Fermi();
    	} else {
    		creating = new Map(type);
    	}
    	return creating;
    }
}