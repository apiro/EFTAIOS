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
        // TODO implement here
    	Map creating;
    	if(type.equals("Galilei")) {
    		creating = new Galilei();
    	} else if (type.equals("Galvani")) {
    		creating = new Galvani();
    	} else if (type.equals("Fermi")) {
    		creating = new Fermi();
    	} else {
    		creating = new Map(type);
    	}
    	return creating;
    }
}