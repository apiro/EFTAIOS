package it.polimi.ingsw.cg_38.model;

/**
 * 
 */
public class Galvani extends Map {

	private static final long serialVersionUID = 1L;

    public Galvani() {
    	
    	/*
    	 * rigurardare la configurazione della mappa che da 325 numeri quando dovrebbero essere 322
    	 * */
    	
    	super.init();
    	super.fillTable(configuration);
    }
 
    private final String name = "Galvani";

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

	public int[] getConfiguration() {
		return configuration;
	}

	public String getName() {
		return name;
	}

}