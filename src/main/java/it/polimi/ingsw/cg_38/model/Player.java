package it.polimi.ingsw.cg_38.model;
import java.io.Serializable;

/**
 * 
 */
public class Player implements Serializable {

	private static final long serialVersionUID = 1L;
	
    @Override
	public String toString() {
		return "Player [ Name=" + name + "numTurniGiocati=" + numTurniGiocati + ", avatar="
				+ avatar + "]";
	}

    public Player(String name) {
    	this.setName(name);
    }

    private int numTurniGiocati = 0;

    private Avatar avatar;

    private String name;

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

    public void setName(String name) {
    	this.name = name;
    }

    /** increase number of turn played */
    public void finishTurn(){
    	this.setNumTurniGiocati(this.getNumTurniGiocati()+1);
    }

}