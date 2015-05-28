package it.polimi.ingsw.cg_38.model;
import java.io.IOException;
import java.util.*;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

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
public class Map implements Serializable {

	private static final long serialVersionUID = 1L;
	private int[] configuration;
    public Map() {
  
    }
    	
    public int[] getConfiguration() {
		return configuration;
	}
	public void setConfiguration(int[] configuration) {
		this.configuration = configuration;
	}
	
    public Map(String type) throws ParserConfigurationException, Exception {
    	
    	this.init();
    	this.readMap(type);
    	this.fillTable(this.getConfiguration());
    
    }

    public void readMap(String type) throws ParserConfigurationException, SAXException{
    	
    	int [] conf = new int[323];
    	
    	try {
    		
    		File fXmlFile = new File("./res/"+ type +".xml");
    		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
    		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
    		Document doc = dBuilder.parse(fXmlFile);
    		
    		doc.getDocumentElement().normalize();
    		
    		String rootNode = doc.getDocumentElement().getNodeName();
    		
    		NodeList mapNodeList = doc.getElementsByTagName(rootNode);
    		
    		Node mapNode = mapNodeList.item(0);
    		String[] mapNumbers = mapNode.getTextContent().split("\\s+");
    		System.out.println(mapNumbers.length);
    		
    		for(int i = 1; i<mapNumbers.length; i++){
    			conf[i] = Integer.parseInt(mapNumbers[i]);
    		}
    	}  catch(Exception e) {
			e.printStackTrace();
		}
    	this.setConfiguration(conf);
    }
    
    /**
     * 
     */
    public ArrayList<ArrayList<Sector>> table;
    
	public void printMap() {
		System.out.println("MAPPA DEL GIOCO:\n");
		System.out.println("\n|_|A|B|C|D|E|F|G|H|I|J|K|L|M|N|O|P|Q|R|S|T|U|V|W|");
		for(int i = 0; i < this.height ; i++) {
			System.out.print("|" + i + "|");
			for(int j = 0; j < this.width ; j++) {
				if(this.getTable().get(i).get(j).getName().substring(0, 1).equals("E")) {
					System.out.print(this.getTable().get(i).get(j).getName().substring(0, 1) + "|");
				} else {
				System.err.print(this.getTable().get(i).get(j).getName().substring(0, 1) + "|");
				 /*System.out.println("["+i+"]["+j+"] "+this.getTable().get(i).get(j).toString());*/
				}
			}
			System.out.println("\n|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|");
		}
	}
	

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