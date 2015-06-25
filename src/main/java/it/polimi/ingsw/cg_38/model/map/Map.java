package it.polimi.ingsw.cg_38.model.map;

import java.util.*;
import java.io.File;
import java.io.Serializable;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**classe astratta che identifica una generica mappa formata da un insieme di settori */
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

/** il costruttore della mappa inizializza la lista di settori con una lista vuota, successivamente viene
 * letta la configurazione da file XML e infine viene riempita la lista di settori in base alla configurazione
 * @param type indica il tipo della mappa da creare
 * @throws ParserConfigurationException
 * @throws Exception
 */
	public Map(String type) throws ParserConfigurationException, Exception {

		this.init();
		this.readMap(type);
		this.fillTable(this.getConfiguration());

	}

	/**
	 * legge la mappa da file XML . Codifica settori: -1 --> Empty sector 0 -->
	 * Safe sector 1 --> Dangerous sector 2 --> Human starting sector 3 -->
	 * Alien starting sector 4 --> Hatch sector
	 * 
	 * @param type indica il tipo della mappa della quale leggere la configurazione 
	 * @throws ParserConfigurationException
	 * @throws Exception
	 */
	public void readMap(String type) throws ParserConfigurationException,
			SAXException {
		
		int[] conf = new int[323];

		try {

			File fXmlFile = new File("./res/" + type + ".xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);

			doc.getDocumentElement().normalize();

			String rootNode = doc.getDocumentElement().getNodeName();

			NodeList mapNodeList = doc.getElementsByTagName(rootNode);

			Node mapNode = mapNodeList.item(0);
			String[] mapNumbers = mapNode.getTextContent().split("\\s+");

			for (int i = 1; i < mapNumbers.length; i++) {
				conf[i] = Integer.parseInt(mapNumbers[i]);
			}
		} catch (Exception e) {
			System.out.println("Someone asked for a not existing map !");
		}
		this.setConfiguration(conf);
	}

	/** lista di tutti i settori che formano la mappa */
	public List<ArrayList<Sector>> table;

	public final int height = 14;

	public final int width = 23;

	/**
	 * popola la mappa con i relativi settori in base alla sua configurazione
	 * @param configuration array di interi che indica la configurazione della mappa
	 */
	public void fillTable(int[] configuration) {
		for (int i = 0; i < this.getHeight(); i++) {
			for (int j = 0; j < this.getWidth(); j++) {
				Sector mySector = null;
				if (configuration[i * this.width + j] == -1) {
					mySector = Sector.factoryCreator("Empty");
					((List<Sector>) (this.getTable().get(i))).add(j, mySector);
				}
				if (configuration[i * this.width + j] == 0) {
					mySector = Sector.factoryCreator("Safe");
					((List<Sector>) (this.getTable().get(i))).add(j, mySector);
				}
				if (configuration[i * this.width + j] == 1) {
					mySector = Sector.factoryCreator("Dangerous");
					((List<Sector>) (this.getTable().get(i))).add(j, mySector);
				}
				if (configuration[i * this.width + j] == 2) {
					mySector = Sector.factoryCreator("HumanStartingPoint");
					((List<Sector>) (this.getTable().get(i))).add(j, mySector);
				}
				if (configuration[i * this.width + j] == 3) {
					mySector = Sector.factoryCreator("AlienStartingPoint");
					((List<Sector>) (this.getTable().get(i))).add(j, mySector);
				}
				if (configuration[i * this.width + j] == 4) {
					mySector = Sector.factoryCreator("Hatch");
					((List<Sector>) (this.getTable().get(i))).add(j, mySector);
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

	/**
	 * Questo algoritmo aggiunge al settore scelto i propri settori adiacenti.
	 * @param mySector indica il settore al quale si vuole aggiungere la lista dei settori adiacenti
	 * @param table rappresenta la lista dei settori attualmente già aggiunti alla mappa
	 */
	private static void addNeighboringSectors(Sector mySector,
			List<ArrayList<Sector>> table) {
		List<Sector> toAdd = new ArrayList<Sector>();
		if (mySector.getName() != "Empty") {
			if (mySector.getRow() - 1 != -1) {
				// limite superiore
				if (table.get(mySector.getRow() - 1).get(mySector.getCol())
						.getName() != "Empty") {
					toAdd.add(table.get(mySector.getRow() - 1).get(
							mySector.getCol()));
				}
			}
			if (mySector.getCol() - 1 != -1) {
				// limite sx
				if (table.get(mySector.getRow()).get(mySector.getCol() - 1)
						.getName() != "Empty") {
					toAdd.add(table.get(mySector.getRow()).get(
							mySector.getCol() - 1));
				}
			}
			if (mySector.getCol() % 2 == 0) {
				// colonna dispari
				if (mySector.getRow() - 1 != -1) {
					if (mySector.getCol() + 1 != 23) {
						if ((table.get(mySector.getRow() - 1)).get(
								mySector.getCol() + 1).getName() != "Empty") {
							toAdd.add((table.get(mySector.getRow() - 1))
									.get(mySector.getCol() + 1));
						}
					}
					if (mySector.getCol() - 1 != -1) {
						if ((table.get(mySector.getRow() - 1)).get(
								mySector.getCol() - 1).getName() != "Empty") {
							toAdd.add((table.get(mySector.getRow() - 1))
									.get(mySector.getCol() - 1));
						}
					}
				}
			}
		}
		mySector.setNeighboringSectors(toAdd);
		Map.addMySectorToNeighboringSectors(toAdd, mySector);
	}

	/** per ogni settore della lista passata come parametro aggiunge un settore nella lista
	 * dei settori adiacenti
	 * @param toAdd lista dei settori
	 * @param mySector settore da aggiungere come settore adiacente */
	private static void addMySectorToNeighboringSectors(List<Sector> toAdd,
			Sector mySector) {
		for (Sector selected : toAdd) {
			selected.getNeighboringSectors().add(mySector);
		}
	}

	public int getWidth() {
		return width;
	}

	/** viene inizializzata la mappa con una lista di settori vuoti */
	public void init() {

		table = new ArrayList<ArrayList<Sector>>();
		for (int i = 0; i < this.height; i++) {
			this.table.add(new ArrayList<Sector>());
		}
	}

	/** cerca il settore corrispondete alle cordinate passate come parametro 
	 * @param row indica il numero della riga del settore da cercare
	 * @param col indica il numero della colonna del settore da cercare
	 * @return settore trovato in base alle cordinate*/
	public Sector searchSectorByCoordinates(int row, int col) {
		// row e col passati con numerazione che parte da 0 !
		for (int i = 0; i < this.getTable().size(); i++) {
			for (int j = 0; j < this.getTable().get(i).size(); j++) {
				if ((this.getTable().get(i).get(j).getRow() == row)
						&& (this.getTable().get(i).get(j).getCol() == col)) {
					return this.getTable().get(i).get(j);
				}
			}
		}
		return null;
	}

	/** cerca il settore corrispondete al tipo passato come parametro 
	 * @param type indicia il nome del settore da cercare
	 * @return primo settore trovato in base al nome*/
	public Sector searchSectorByName(String type) {
		for (int i = 0; i < this.getTable().size(); i++) {
			for (int j = 0; j < this.getTable().get(i).size(); j++) {
				if (this.getTable().get(i).get(j).getName() == type) {
					return this.getTable().get(i).get(j);
				}
			}
		}
		return null;
	}

	public List<ArrayList<Sector>> getTable() {
		return table;
	}

}