package it.polimi.ingsw.cg_38.model;
import java.util.*;

/**
 * 
 * codifica: 
 * 	-1-> vuota 
 * 	0->safe 
 *	1-> dangerous 
 *	2->hsp 
 *	3->asp 
 *	4->hatch 
 *
 * elemento [i,j] della matrice n*n con "i" riga e "j" colonna: dove j lettera vicini: 
 *  [i-1,j] [i+1,j] [i, j+1] [i, j-1] [i+1,j+1] [i+1,j-1]
 * 
 */
public abstract class Map {

    /**
     * 
     */
    public Map() {
    }

    /**
     * 
     */
    public ArrayList<ArrayList<Sector>> table;
    
    
	public void printMap() {
		System.out.println("\n");
		for(int i = 0; i < this.height ; i++) {
			for(int j = 0; j < this.width ; j++) {
				if(j == this.width){
					System.out.println("\n");
				}
				 System.out.println("["+i+"]["+j+"] "+this.getTable().get(i).get(j).toString());
			}
		}
		
		
		
	}

	public final int height = 14;
    public final int width = 23;

    public void fillTable(int[] configuration) {
    	//riempio gli arraylist con settori in base alla codifica
    	for(int i = 0; i<this.height; i++) {
    		for(int j = 0; j<this.width; j++) {
    			if(configuration[i*this.width+j]==-1) {
    				Sector mySector = Sector.factoryCreator("Empty");
    				((ArrayList <Sector>)(this.getTable().get(i))).add(j, mySector);
    			}if(configuration[i*this.width+j]==0) {
    				Sector mySector = Sector.factoryCreator("Safe");
    				((ArrayList <Sector>)(this.getTable().get(i))).add(j, mySector);
    			}if(configuration[i*this.width+j]==1) {
    				Sector mySector = Sector.factoryCreator("Dangerous");
    				((ArrayList <Sector>)(this.getTable().get(i))).add(j, mySector);
    			}if(configuration[i*this.width+j]==2) {
    				Sector mySector = Sector.factoryCreator("HumanStartingPoint");
    				((ArrayList <Sector>)(this.getTable().get(i))).add(j, mySector);
    			}if(configuration[i*this.width+j]==3) {
    				Sector mySector = Sector.factoryCreator("AlienStartingPoint");
    				((ArrayList <Sector>)(this.getTable().get(i))).add(j, mySector);
    			}if(configuration[i*this.width+j]==4) {
    				Sector mySector = Sector.factoryCreator("Hatch");
    				((ArrayList <Sector>)(this.getTable().get(i))).add(j, mySector);
    			}
    		}
    	}
    }
    
    public void init() {
    	
    	//inizializzo la mappa con arraylist vuoti
    	table = new ArrayList<ArrayList<Sector>>();
    	for(int i = 0; i<this.height; i++) {
    		this.table.add(new ArrayList<Sector>());
    	}
    }
    
    /**
     * @param Sector sector
     */
    public void searchTargetSector(Sector sector) {
        // TODO implement here
    }

	public ArrayList<ArrayList<Sector>> getTable() {
		return table;
	}

}