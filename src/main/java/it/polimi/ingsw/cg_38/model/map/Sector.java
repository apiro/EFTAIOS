package it.polimi.ingsw.cg_38.model.map;
import java.io.Serializable;
import java.util.*;

/** identifica un settore generico in base alle cordinate e ai suoi settori vicini */
public class Sector implements Serializable{

	private static final long serialVersionUID = 1L;
	
	/** lista dei settori adiacenti */
    private List<Sector> neighboringSectors;

    private int row;

    private int col;
	
    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + col;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime
				* result
				+ ((neighboringSectors == null) ? 0 : neighboringSectors
						.hashCode());
		result = prime * result + row;
		return result;
	}

    /** definisce l'uguaglianza tra settori 
     * @param obj oggetto rispetto al quale verificare l'uguaglianza
     * @return true se gli oggetti sono uguali,  false altrimenti */
    @Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Sector other = (Sector) obj;
		if (col != other.col)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (neighboringSectors == null) {
			if (other.neighboringSectors != null)
				return false;
		} 
		if (row != other.row)
			return false;
		return true;
	}

	public Sector() {
    	this.setNeighboringSectors(new ArrayList<Sector>());
    }

    public void setNeighboringSectors(List<Sector> neighboringSectors) {
		this.neighboringSectors = neighboringSectors;
	}

	public String name;
    
    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

    /** crea uno specifico settore in base al tipo passato come parametro
     * @param typeSector indica il tipo del settore da creare
     * @return settore creato*/
    public static Sector factoryCreator(String typeSector) {
    	Sector creatingSector = null;
    	if(typeSector == "Safe") {
    		creatingSector = new Safe();
    	} else if(typeSector == "Hatch") {
    		creatingSector = new Hatch();
    	} else if(typeSector == "HumanStartingPoint") {
    		creatingSector = new HumanStartingPoint();
    	} else if(typeSector == "AlienStartingPoint") {
    		creatingSector = new AlienStartingPoint();
    	} else if(typeSector == "Dangerous") {
    		creatingSector = new Dangerous();
    	} else if(typeSector == "Empty") {
    		creatingSector = new Empty();
    	}
        return creatingSector;
    }
   
	@Override
	public String toString() {
		return "Sector [name=" + name + "row:" + this.getRow() + "col" + this.getCol() + "]";
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getCol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;
	}

	public List<Sector> getNeighboringSectors() {
		return neighboringSectors;
	}

}
