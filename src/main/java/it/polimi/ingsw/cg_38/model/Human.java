package it.polimi.ingsw.cg_38.model;
import it.polimi.ingsw.cg_38.model.map.Hatch;
import it.polimi.ingsw.cg_38.model.map.Sector;

import java.io.Serializable;

/** contiene i dati del gioco di un avatar di tipo umano */
public class Human extends Avatar implements Serializable {

	private static final long serialVersionUID = 1L;

	/** indica se l'avatar può attaccare */
    private Boolean canAttack = false;
    
    public Human(Name name, Sector sector) {
    	this.setName(name);
    	this.setCurrentSector(sector);
    }

    
    public Boolean getCanAttack() {
		return canAttack;
	}

	public void setCanAttack(Boolean canAttack) {
		this.canAttack = canAttack;
	}

	/** controlla se l'umano può muoversi in un dato settore 
	 * @param sector indica il settore nel quale il giocatore vuole effettuare il movimento 
	 * @return true se è l'avatar può spostarsi nel settore indicato */
	@Override
    public Boolean canMove(Sector sector) {
    	if(("HumanStartingPoint").equals(sector.getName())) {
    		return false;
    	}
    	if(this.getCurrentSector().getNeighboringSectors().contains(sector)) {

        	if(("Hatch").equals(sector.getName())) {
        		return ((Hatch)sector).getIsOpen();
        	}
    		return true;
    	} else if(this.getIsPowered()) {
        	for(Sector sec: this.getCurrentSector().getNeighboringSectors()){
        		for(Sector sec2: sec.getNeighboringSectors()){
	        		if(sec2.equals(sector) && !(sec2.equals(this.getCurrentSector()))) {

	        	    	if(("Hatch").equals(sector.getName())) {
	        	    		return ((Hatch)sector).getIsOpen();
	        	    	}
	        			return true;
	        		}
        		}
        	}
    	}
        return false;
    }
}