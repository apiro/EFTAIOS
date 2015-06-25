package it.polimi.ingsw.cg_38.model.map;
import java.io.Serializable;

/** identifica un settore di tipo Safe */
public class Safe extends Sector implements Serializable{
	
	private static final long serialVersionUID = 1L;

    public Safe() {
    	super();
    	super.setName("Safe");
    }

}