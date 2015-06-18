package it.polimi.ingsw.cg_38.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class HexGrid extends JPanel {

	private BufferedImage fromImageToShape;
	private int[][] board;
	private int BSIZEW;
	private int BSIZEH;
	private final static Color COLOURBACK =  Color.DARK_GRAY;
	private HashMap<String, ImageIcon> imgUrls = new HashMap<String, ImageIcon>();
	private ArrayList<GraphicSector> sects = new ArrayList<GraphicSector>();
	
	
	public HexGrid(int[][] board) {
	
		this.board = board;
		
		try {
			this.loadResurces();
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		this.popolateGUI();
		
		
		setBackground(COLOURBACK);
	
	}

	private void popolateGUI() {
		
		System.out.println(board.length);
		System.out.println(board[0].length);
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
		        	}
				});
			}
		}
		
		/*
		for(int i = 0; i<board.length; i++) {
			for(int j = 0;j<board[i].length; j++) {
				this.sects.add(new GraphicSector(i, j, , , this.chooseImage(board[i][j])));
			}
		}
		*/
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

	public void loadResurces() throws MalformedURLException{
		
		imgUrls.put("Empty",this.generateImageIcon(new URL("http://www.blueskysports.com/image/cache/data/Colors/white-200x200.png")));
		imgUrls.put("Safe",this.generateImageIcon(new URL("http://a1.mzstatic.com/eu/r30/Purple7/v4/10/1a/ac/101aac56-d71c-0108-426b-912aae214c4d/icon175x175.png")));
		imgUrls.put("Dangerous",this.generateImageIcon(new URL("http://adoptusanimalrescue.org/wp-content/uploads/2013/04/2191109-184523-green-grass-texture-that-tiles-seamlessly-as-a-pattern-300x300.jpg")));
		imgUrls.put("HSP",this.generateImageIcon(new URL("http://royalcovering.com/media/catalog/product/cache/1/image/991bf44e479402b25df0908fda8f5972/v/i/vinyl-carbone-bleu-ciel-royal-covering_1.jpg")));
		imgUrls.put("ASP",this.generateImageIcon(new URL("http://akonder.org/wp-content/themes/allegro-theme/images/background-texture-4.jpg")));
		imgUrls.put("H",this.generateImageIcon(new URL("http://fc08.deviantart.net/fs31/i/2008/193/a/7/Sun_comes_up_texture_by_iNeedChemicalX.jpg")));
	
	}
	
	public ImageIcon generateImageIcon(URL url) {
		BufferedImage bi = null;
		try {
			bi = ImageIO.read(url);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Polygon poly = HexagonHandler.hex(0,0);
        Shape myShape = TexturedShape.fromPolygonToShape(poly);
        return new ImageIcon(TexturedShape.getTexturedImage(bi, myShape));
	}

	@Override
	public void paintComponent(Graphics g){
		
		Graphics2D g2 = (Graphics2D)g;
		g2.setRenderingHint(RenderingHints .KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
		super.paintComponent(g2);
		g2.drawImage(fromImageToShape, null, 0, 0);
		g2.drawImage(fromImageToShape, null, 0, 0+45);
		g2.drawImage(fromImageToShape, null, 37, 23);
		g2.drawImage(fromImageToShape, null, 37, 23+45);
		
	}
}
