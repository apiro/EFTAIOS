package it.polimi.ingsw.cg_38.model.map;

import javax.xml.parsers.ParserConfigurationException;

/** permette la creazione di una mappa specifica in base al tipo desiderato */
public class MapCreator {

    /** crea una mappa in base al tipo passato come parametro 
     * @param type indica il nome della mappa da creare
     * @return mappa creata*/
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