package it.polimi.ingsw.cg_38.model.map;

/** identifica una mappa di tipo Galvani */
public class Galvani extends Map {

	private static final long serialVersionUID = 1L;

	/** il costruttore inizializza la lista di settori con una lista vuota e successivamente
	 * popola la tabella con i settori in base alla configurazione
	 */
    public Galvani() {
    	
    	/*
    	 * rigurardare la configurazione della mappa che da 325 numeri quando dovrebbero essere 322
    	 * */
    	
    	super.init();
    	super.fillTable(configuration);
    }
 
    private final String name = "Galvani";

	/** contiene la configurazione della mappa Galvani.
	 * 
	 *Codifica settori:
	 * -1 --> Empty sector 
	 *  0 --> Safe sector
	 *  1 --> Dangerous sector 
	 *  2 --> Human starting sector
	 *  3 --> Alien starting sector
	 *  4 --> Hatch sector */
    private final int[] configuration = {-1,-1,-1,1,1,4,-1,-1,-1,1,1,1,1,1,-1,4,1,1,1,1,1,0,-1
    									,-1,1,1,-1,-1,1,0,1,1,-1,-1,-1,1,0,1,1,0,-1,1,1,-1,1,-1
    									,1,-1,-1,1,1,-1,-1,1,1,1,0,0,-1,-1,0,1,1,1,-1,-1,1,1,1
    									,1,0,1,-1,-1,1,1,-1,-1,1,1,-1,1,1,-1,-1,1,1,1,-1,1,1,1
    									,1,-1,1,-1,1,-1,-1,0,1,-1,-1,1,0,-1,0,1,-1,0,1,-1,0,1,-1,-1,-1,1
    									,1,-1,1,-1,1,-1,1,-1,-1,1,1,3,1,1,-1,-1,1,-1,1,-1,-1,-1,1
    									,1,-1,1,0,1,-1,1,0,1,-1,-1,-1,-1,-1,1,-1,1,-1,1,-1,-1,0,1
    									,0,-1,1,-1,1,-1,1,-1,1,1,-1,2,-1,1,1,-1,1,-1,1,-1,0,1,1
    									,1,-1,1,-1,1,-1,1,1,-1,-1,0,1,0,-1,0,1,1,-1,1,0,-1,-1,1
    									,1,4,1,-1,1,1,0,-1,1,1,0,-1,-1,1,1,-1,-1,1,1,-1,1,-1,1
    									,1,0,1,-1,-1,1,1,1,-1,-1,1,1,1,0,-1,1,1,-1,-1,1,1,4,1
    									,1,1,-1,1,1,-1,-1,0,1,-1,-1,0,-1,1,1,-1,-1,-1,-1,1,1,-1,1
    									,-1,-1,1,-1,-1,-1,1,1,-1,-1,1,1,-1,-1,-1,-1,-1,1,1,-1,-1,1,1
    									,-1,-1,-1,-1,-1,-1,-1,-1,1,1,0,-1,0,1,0,1,0,1,1,1,1,-1,-1};

    @Override
	public int[] getConfiguration() {
		return configuration;
	}

	public String getName() {
		return name;
	}

}