package it.polimi.ingsw.cg_38.client.gui;

import it.polimi.ingsw.cg_38.controller.event.Event;
import it.polimi.ingsw.cg_38.controller.gameEvent.EventMove;
import it.polimi.ingsw.cg_38.model.Player;
import it.polimi.ingsw.cg_38.model.map.Map;

import java.awt.Color;
import java.awt.Polygon;
import java.awt.Shape;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Queue;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 * Questa classe permette di instanziare un oggetto che rappresenta il tavolo di gioco. Estende JPanel per 
 * creare un particolare tipo di pannello che isntazi al suo interno la desiderata scacchiera.
 * */
public class HexGrid extends JPanel {

	private static final long serialVersionUID = 1L;
	private int[][] board;
	private final static Color COLOURBACK =  Color.DARK_GRAY;
	
	/**
	 * HashMap che mappa l'icona caricata dal FS con un nome che la identifica tra le altre
	 * */
	private HashMap<String, ImageIcon> imgUrls = new HashMap<String, ImageIcon>();
	
	/**
	 * immagine del settore safe con all'interno il marker della posizione del giocatore
	 * */
	private ImageIcon playerPointerS = null;
	
	/**
	 * immagine del settore dangerous con all'interno il marker della posizione del giocatore
	 * */
	private ImageIcon playerPointerD = null;
	
	/**
	 * immagine del settore hatch quando si attiva
	 * */
	private ImageIcon hatchGreen = null;
	
	/**
	 * immagine del settore hatch quando è guasto
	 * */
	private ImageIcon hatchRed = null;
	
	/**
	 * lista dei settori che compongono la scacchiera
	 * */
	private List<GraphicSector> sects = new ArrayList<GraphicSector>();
	
	/**
	 * è la coda che il client passa in fase di costruzione di questo oggetto, ed alla quale viene aggiunto 
	 * l'evento di RETIRED quando viene premuto il tasto di chiusura della finestra.
	 * */
	private Queue<Event> toSend;
	private Player player;
	private Map map;
	private int oldx = 30;
	private int oldy = 30;
	private ImageIcon oldIcon = null;
	
	public List<GraphicSector> getSects() {
		return sects;
	}
	
	/**
	 * Ogni volta che un giocatore entra in una scialuppa questa viene ricolorata in base alla carta che il giocatore 
	 * ha pescato.
	 * @param x coordinata del settore hatch
	 * @param y coordinata del settore hatch
	 * @param available boolean relativo al colore da applicare alla scialuppa
	 * */
	public void hatchPainter(int x, int y, Boolean available) {
		for(GraphicSector sec:sects) {
			if((sec.getCol() == x) && (sec.getRow() == y)) {
				/*oldx = sec.getCol();
				oldy = sec.getRow();
				oldIcon = (ImageIcon) sec.getIcon();*/
				if(available) {
					sec.setIcon(hatchGreen);
				}
				else {
					sec.setIcon(hatchRed);
				}
			}
		}
	}

	/**
	 * Ogni volta che un giocatore si muove viene cambiata l'icona del settore in cui era prima di effettuare la mossa
	 * (infatti l'icona del settore aveva il marker per segnalare la presenza del giocatore), e viene aggiornata l'icona
	 * del settore dove si è mosso cambiandola con una con il marker.
	 * @param x riga del settore in cui il giocatore si è mosso
	 * @param y colonna del settore in cui il giocatore si è mosso
	 * @param type nome del settore dove si è mosso( Safe o Dangerous) 
	 * */
	public void slidePlayerPosition(int x, int y, String type) {
		for(GraphicSector sec:sects) {
			if((sec.getCol() == oldx) && (sec.getRow() == oldy)) {
				sec.setIcon(oldIcon);
			} 
		}
		for(GraphicSector sec:sects) {
			if((sec.getCol() == x) && (sec.getRow() == y)) {
				oldx = sec.getCol();
				oldy = sec.getRow();
				oldIcon = (ImageIcon) sec.getIcon();
				System.out.println(type);
				if(("Dangerous").equals(type.substring(38))){
					sec.setIcon(playerPointerD);
					this.repaint();
				}
				if(("Safe").equals(type.substring(38))){
					sec.setIcon(playerPointerS);
					this.repaint();
				}
			}
		}
	}
	
	/**
	 * Instanzia una scacchiera, carica le risorse e popola la GUI
	 * @param board multiarray che definisce come disegnarla
	 * @param toSend coda del server dove aggiungere l'evento di ritiro
	 * @param player riferimento al player 
	 * @param map riferimento alla mappa
	 * */
	public HexGrid(int[][] board, Queue<Event> toSend, Player player, Map map) {
	
		this.board = board;
		this.toSend = toSend;
		this.player = player;
		this.map = map;
		
		this.loadResurces();
		
		this.popolateGUI();
		
		
		setBackground(COLOURBACK);
	
	}

	/**
	 * Metodo per disegnare la scacchiera.
	 * */
	private void popolateGUI() {
		
		for(int j = 0; j<board.length; j++) {
		for(int i=0; i<board[0].length; i++) {
				final GraphicSector gs;
				if(i%2==0) {
					gs = new GraphicSector(j, i, 37*i, 45*j, this.chooseImage(board[j][i]));
					this.sects.add(gs);
				} else {
					gs = new GraphicSector(j, i, 37*i, 22+45*j, this.chooseImage(board[j][i]));
					this.sects.add(gs);
				}
				this.add(gs);
				gs.position();
				gs.addMouseListener(new MouseAdapter() {	
		        	public void mouseClicked(MouseEvent e) {
		        		System.out.println("clicked: row: "+ gs.getRow() + "col: " + gs.getCol());
		        		Event evt = new EventMove(player, map.searchSectorByCoordinates(gs.getRow(), gs.getCol()));
		        		synchronized(toSend) {
		        			toSend.add(evt);
		        		}
		        	}
				});
			}
		}
	}

	/**
	 * Dato un intero i ritorna una certa immagine. Parsa la codifica con la quale sono memorizzate le mappe
	 * @param i codifica
	 * */
	public ImageIcon chooseImage(int i) {
		if(i==-1){
			return imgUrls.get("Empty");
		} else if (i==0) {
			return imgUrls.get("Safe");
		} else if (i==1) {
			return imgUrls.get("Dangerous");
		} else if (i==2) {
			return imgUrls.get("HSP");
		} else if (i==3) {
			return imgUrls.get("ASP");
		} else if (i==4) {
			return imgUrls.get("H");
		} else {
			return null;
		}
		
	}

	/**
	 * Carica le immagini dal FS.
	 * */
	public void loadResurces() {
		
		hatchGreen = this.generateImageIconFromFS("./res/hatchG.png");
		hatchRed = this.generateImageIconFromFS("./res/hatchR.png");
		playerPointerS = this.generateImageIconFromFS("./res/safeY.png");
		playerPointerD = this.generateImageIconFromFS("./res/dangerousY.png");
		imgUrls.put("Empty",this.generateImageIconFromFS("./res/empty.png"));
		imgUrls.put("Safe", this.generateImageIconFromFS("./res/safe.png"));
		imgUrls.put("Dangerous",this.generateImageIconFromFS("./res/dangerous.png"));
		imgUrls.put("HSP",this.generateImageIconFromFS("./res/hsp.png"));
		imgUrls.put("ASP",this.generateImageIconFromFS("./res/asp.png"));
		imgUrls.put("H",this.generateImageIconFromFS("./res/hatch.png"));
	}
	
	/**
	 * Genera una imageicon a partire da un path del FS, sagomando i bordi della JLabel utilizzando il poligono
	 * creato con il metodo statico dell'HexagonHandler
	 * @param path path del FS dove si trova l'immagine
	 * */
	public ImageIcon generateImageIconFromFS(String path) {
		BufferedImage bi = null;
		try {
			bi = ImageIO.read(new File(path));
		} catch (IOException e) {
			System.out.println("Impossible to read the resource ! " + path);
		}
		Polygon poly = HexagonHandler.hex(0,0);
        Shape myShape = TexturedShape.fromPolygonToShape(poly);
        return new ImageIcon(TexturedShape.getTexturedImage(bi, myShape));
	}
	
	/**
	 * Genera una imageicon a partire da un path web, sagomando i bordi della JLabel utilizzando il poligono
	 * creato con il metodo statico dell'HexagonHandler
	 * @param path path web dove si trova l'immagine
	 * */
	public ImageIcon generateImageIcon(URL url) {
		BufferedImage bi = null;
		try {
			bi = ImageIO.read(url);
		} catch (IOException e) {
			System.out.println("Impossible to read the resource !");
		}
		Polygon poly = HexagonHandler.hex(0,0);
        Shape myShape = TexturedShape.fromPolygonToShape(poly);
        return new ImageIcon(TexturedShape.getTexturedImage(bi, myShape));
	}
}
