package it.polimi.ingsw.cg_38.client.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JPanel;

public class MapPanel extends JPanel {		
	//mouse variables here
	//Point mPt = new Point(0,0);

	private static final long serialVersionUID = 1L;
	private int[][] board;
	private int BSIZEW;
	private int BSIZEH;
	final static Color COLOURBACK =  Color.DARK_GRAY;
	
	public MapPanel(int sizeW, int sizeH, int[][] board)
	{	
		this.BSIZEW = sizeW;
		this.BSIZEH = sizeH;
		this.board = board;
		/*super();
		this.setBorder(new LineBorder(Color.BLACK));*/
		setBackground(COLOURBACK);

		/*MyMouseListener ml = new MyMouseListener();            
		addMouseListener(ml);*/
	}

	public void paintComponent(Graphics g)
	{
		Graphics2D g2 = (Graphics2D)g;
		g2.setRenderingHint(RenderingHints .KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
		super.paintComponent(g2);
		//draw grid
		for (int i=0;i<BSIZEH;i++) {
			for (int j=0;j<BSIZEW;j++) {
				HexagonHandler.drawHex(i,j,g2);
			}
		}
		//fill in hexes
		for (int i=0;i<BSIZEH;i++) {
			for (int j=0;j<BSIZEW;j++) {					
				//if (board[i][j] < 0) hexmech.fillHex(i,j,COLOURONE,-board[i][j],g2);
				//if (board[i][j] > 0) hexmech.fillHex(i,j,COLOURTWO, board[i][j],g2);
				HexagonHandler.fillHex(i,j,board[i][j],g2);
				repaint();
			}
		}
		//g.setColor(Color.RED);
		//g.drawLine(mPt.x,mPt.y, mPt.x,mPt.y);
	}
} // end of DrawingPanel class