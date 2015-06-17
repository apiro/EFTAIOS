package it.polimi.ingsw.cg_38.model;

public class Fermi extends Map {

	private static final long serialVersionUID = 1L;

    public Fermi() {

    	this.init();
    	this.fillTable(configuration);
    }

    private final String name = "Fermi";

    private final int[] configuration = {-1,-1,-1,-1,-1,-1,-1,-1,-1,4,-1,-1,-1,4,-1,-1,-1,-1,-1,-1,-1,-1,-1
    									,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,0,-1,1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1
    									,-1,-1,-1,-1,-1,-1,-1,-1,-1,1,0,1,0,1,-1,-1,-1,-1,-1,-1,-1,-1,-1
    									,-1,-1,-1,-1,-1,-1,-1,-1,-1,0,1,1,1,0,-1,-1,-1,-1,-1,-1,-1,-1,-1
    									,-1,-1,-1,-1,-1,-1,-1,-1,-1,4,-1,0,-1,4,-1,-1,-1,-1,-1,-1,-1,-1,-1
    									,-1,-1,-1,-1,-1,-1,-1,-1,0,-1,-1,0,-1,-1,0,-1,-1,-1,-1,-1,-1,-1,-1
    									,-1,-1,-1,-1,-1,-1,-1,-1,1,0,-1,0,-1,1,0,-1,-1,-1,-1,-1,-1,-1,-1
    									,-1,-1,-1,-1,-1,-1,-1,-1,-1,0,0,0,0,0,-1,-1,-1,-1,-1,-1,-1,-1,-1
    									,-1,-1,-1,-1,-1,-1,-1,-1,1,-1,-1,3,-1,-1,0,-1,-1,-1,-1,-1,-1,-1,-1
    									,-1,-1,-1,-1,-1,-1,-1,0,0,0,-1,2,-1,0,1,0,-1,-1,-1,-1,-1,-1,-1
    									,-1,-1,-1,-1,-1,-1,-1,0,-1,0,0,0,0,0,-1,1,-1,-1,-1,-1,-1,-1,-1
    									,-1,-1,-1,-1,-1,-1,-1,-1,1,0,-1,0,-1,1,0,-1,-1,-1,-1,-1,-1,-1,-1
    									,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,1,-1,0,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1
    									,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1};

    @Override 
    public int[] getConfiguration() {
    	return configuration;
    }

}