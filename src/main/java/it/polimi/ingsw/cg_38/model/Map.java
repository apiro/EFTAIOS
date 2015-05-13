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
    
    /*
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
	*/

	public final int height = 14;
    public final int width = 23;

    public void fillTable(int[] configuration) {
    	//riempio gli arraylist con settori in base alla codifica
    	for(int i = 0; i<this.getHeight(); i++) {
    		for(int j = 0; j<this.getWidth(); j++) {
    			Sector mySector = null;
    			if(configuration[i*this.width+j]==-1) {
    				mySector = Sector.factoryCreator("Empty");
    				((ArrayList <Sector>)(this.getTable().get(i))).add(j, mySector);
    			}if(configuration[i*this.width+j]==0) {
    				mySector = Sector.factoryCreator("Safe");
    				((ArrayList <Sector>)(this.getTable().get(i))).add(j, mySector);
    			}if(configuration[i*this.width+j]==1) {
    				mySector = Sector.factoryCreator("Dangerous");
    				((ArrayList <Sector>)(this.getTable().get(i))).add(j, mySector);
    			}if(configuration[i*this.width+j]==2) {
    				mySector = Sector.factoryCreator("HumanStartingPoint");
    				((ArrayList <Sector>)(this.getTable().get(i))).add(j, mySector);
    			}if(configuration[i*this.width+j]==3) {
    				mySector = Sector.factoryCreator("AlienStartingPoint");
    				((ArrayList <Sector>)(this.getTable().get(i))).add(j, mySector);
    			}if(configuration[i*this.width+j]==4) {
    				mySector = Sector.factoryCreator("Hatch");
    				((ArrayList <Sector>)(this.getTable().get(i))).add(j, mySector);
    			}
    			mySector.setRow(i);
				mySector.setCol(j);
				Map.addNeighboringSectors(mySector, this.getTable());
    		}
    	}
    }
    
    public int getHeight() {
		return height;
	}

	private static void addNeighboringSectors(Sector mySector, ArrayList<ArrayList<Sector>> table){
    	ArrayList<Sector> toAdd = new ArrayList<Sector>();
    	if(mySector.getName() != "Empty") {
    		if(mySector.getRow()-1 !=-1) {
    			//limite superiore
    			if(table.get(mySector.getRow()-1).get(mySector.getCol()).getName() != "Empty"){
    				toAdd.add(table.get(mySector.getRow()-1).get(mySector.getCol()));
    			}
    		}
    		if(mySector.getCol()-1 !=-1) {
    			//limite sx
    			if(table.get(mySector.getRow()).get(mySector.getCol()-1).getName() != "Empty"){
    				toAdd.add(table.get(mySector.getRow()).get(mySector.getCol()-1));
    			}
    		}
    		if(mySector.getCol()%2 == 0 ) {
        		//colonna dispari
    			if(mySector.getRow()-1 != -1){
    				if(mySector.getCol()+1 != 23) {
    					if((table.get(mySector.getRow()-1)).get(mySector.getCol()+1).getName() != "Empty"){
    						toAdd.add((table.get(mySector.getRow()-1)).get(mySector.getCol()+1));
    					}
    				}
    				if (mySector.getCol()-1 != -1){
    					if((table.get(mySector.getRow()-1)).get(mySector.getCol()-1).getName() != "Empty"){
    						toAdd.add((table.get(mySector.getRow()-1)).get(mySector.getCol()-1));
    					}
    				}
    			}
    		}
    	}
    	mySector.setNeighboringSectors(toAdd);
    	Map.addMySectorToNeighboringSectors(toAdd, mySector);
    }
    
    private static void addMySectorToNeighboringSectors(ArrayList<Sector> toAdd, Sector mySector){
    	for(Sector selected: toAdd){
    		selected.getNeighboringSectors().add(mySector);
    	}
    }
    
    public int getWidth() {
		return width;
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
    public Sector searchSectorByCoordinates(int row, int col) {
    	//row e col passati con numerazione che parte da 0 !
    	for(int i = 0; i< this.getTable().size(); i++){
    		for(int j = 0; j< this.getTable().get(i).size(); j++){
    			if((this.getTable().get(i).get(j).getRow() == row) && (this.getTable().get(i).get(j).getCol() == col)) {
    				return this.getTable().get(i).get(j);
    			}
    		}
    	}
    	return null;
    }

    public Sector searchSectorByName(String type){
    	for(int i = 0; i< this.getTable().size(); i++){
    		for(int j = 0; j< this.getTable().get(i).size(); j++){
    			if(this.getTable().get(i).get(j).getName() == type) {
    				return this.getTable().get(i).get(j);
    			}
    		}
    	}
    	return null;
    }
    
	public ArrayList<ArrayList<Sector>> getTable() {
		return table;
	}

}