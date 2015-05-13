package it.polimi.ingsw.cg_38.model;
import java.util.*;

/**
 * 
 */
public class Player {

    /**
     * 
     */
    public Player(String name) {
    	this.setName(name);
    }
    
	/**
     * 
     */
    private int numTurniGiocati = 0;
    
    /**
     * 
     */
    private Avatar avatar;

    /**
     * 
     */
    private String name;

    /**
     * getter e setter
     * **/

    public int getNumTurniGiocati() {
		return numTurniGiocati;
	}

	public void setNumTurniGiocati(int numTurniGiocati) {
		this.numTurniGiocati = numTurniGiocati;
	}

	public Avatar getAvatar() {
		return avatar;
	}

	public void setAvatar(Avatar avatar) {
		this.avatar = avatar;
	}

	public String getName() {
		return name;
	}


    /**
     * @param String name 
     * @return
     */
    public void setName(String name) {
        // TODO implement here
    	this.name = name;
    }

    /**
     * @param String name
     */
    public void finishTurn(){
    	this.setNumTurniGiocati(this.getNumTurniGiocati()+1);
    }

}