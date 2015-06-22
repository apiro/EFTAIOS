package it.polimi.ingsw.cg_38.client.gui;

import it.polimi.ingsw.cg_38.controller.event.Event;
import it.polimi.ingsw.cg_38.controller.gameEvent.EventMove;
import it.polimi.ingsw.cg_38.controller.logger.Logger;
import it.polimi.ingsw.cg_38.controller.logger.LoggerCLI;
import it.polimi.ingsw.cg_38.model.Player;
import it.polimi.ingsw.cg_38.model.map.Map;

import java.awt.Color;
import java.awt.Polygon;
import java.awt.Shape;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class HexGrid extends JPanel {

	private static final long serialVersionUID = 1L;
	private int[][] board;
	private final static Color COLOURBACK =  Color.DARK_GRAY;
	private HashMap<String, ImageIcon> imgUrls = new HashMap<String, ImageIcon>();
	private ImageIcon playerPointerS = null;
	private ImageIcon playerPointerD = null;
	private ImageIcon hatchGreen = null;
	private ImageIcon hatchRed = null;
	private ArrayList<GraphicSector> sects = new ArrayList<GraphicSector>();
	private ConcurrentLinkedQueue<Event> toSend;
	private Player player;
	private Map map;
	private GraphicSector gps = new GraphicSector( 30, 30, 30, 30, null);
	private int oldx = 30;
	private int oldy = 30;
	private ImageIcon oldIcon = null;
	private Logger logger = new LoggerCLI();
	
	
	public ArrayList<GraphicSector> getSects() {
		return sects;
	}
	
	public void hatchPainter(int x, int y, Boolean available) {
		for(GraphicSector sec:sects) {
			if((sec.getCol() == x) && (sec.getRow() == y)) {
				oldx = sec.getCol();
				oldy = sec.getRow();
				oldIcon = (ImageIcon) sec.getIcon();
				if(available) {
					sec.setIcon(hatchGreen);
				}
				else {
					sec.setIcon(hatchRed);
				}
			}
		}
	}

	public void slidePlayerPosition(int x, int y, String type) {
		for(GraphicSector sec:sects) {
			if((sec.getCol() == oldx) && (sec.getRow() == oldy)) {
				sec.setIcon(oldIcon);
			} 
			if((sec.getCol() == x) && (sec.getRow() == y)) {
				oldx = sec.getCol();
				oldy = sec.getRow();
				oldIcon = (ImageIcon) sec.getIcon();
				if(type.substring(34).equals("Dangerous")) sec.setIcon(playerPointerD);
				if(type.substring(34).equals("Safe")) sec.setIcon(playerPointerS);
			}
		}
	}
	
	public HexGrid(int[][] board, ConcurrentLinkedQueue<Event> toSend, Player player, Map map) {
	
		this.board = board;
		this.toSend = toSend;
		this.player = player;
		this.map = map;
		
		this.loadResurces();
		
		this.popolateGUI();
		
		
		setBackground(COLOURBACK);
	
	}

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
	
	public ImageIcon generateImageIcon(URL url) {
		BufferedImage bi = null;
		try {
			bi = ImageIO.read(url);
		} catch (IOException e) {
			System.out.println("Impossible to read the resource !");
			e.printStackTrace();
		}
		Polygon poly = HexagonHandler.hex(0,0);
        Shape myShape = TexturedShape.fromPolygonToShape(poly);
        return new ImageIcon(TexturedShape.getTexturedImage(bi, myShape));
	}
}
