package it.polimi.ingsw.cg_38.gui;
import java.awt.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import java.awt.event.*; 
import java.util.ArrayList;
 
/**********************************
  This is the main class of a Java program to play a game based on hexagonal tiles.
  The mechanism of handling hexes is in the file hexmech.java.

  Written by: M.H.
  Date: December 2012

 ***********************************/
 
public class GUI
{
	private String name;

	public GUI() {
		
		initGame();
		createAndShowGUI();
	}
 
	//constants and global variables
	final static Color COLOUREMPTY = Color.WHITE;
	final static Color COLOURSAFE = Color.LIGHT_GRAY;
	final static Color COLOURDANGEROUS = Color.GRAY;
	final static Color COLOURHSP = Color.ORANGE;
	final static Color COLOURASP = Color.RED;
	final static Color COLOURHATCH = Color.GREEN;
	
	final static Color COLOURBACK =  Color.DARK_GRAY;
	final static Color COLOURCELL =  Color.GRAY;	 
	final static Color COLOURGRID =  Color.BLUE;
	final static Color COLOURONETXT = Color.DARK_GRAY;
	final static int EMPTY = 0;
	final static int BSIZE = 10; //board size.
	final static int HEXSIZE = 45;	//hex size in pixels
	final static int BORDERS = 15;  
	final static int SCRSIZE = HEXSIZE * (BSIZE + 1) + BORDERS*3; //screen size (vertical dimension).
 
	int[][] board = new int[BSIZE][BSIZE];
	private ArrayList<JButton> buttons = new ArrayList<JButton>();
 
	void initGame(){
 
		HexagonHandler.setXYasVertex(false); //RECOMMENDED: leave this as FALSE.
 
		HexagonHandler.setHeight(HEXSIZE); //Either setHeight or setSize must be run to initialize the hex
		HexagonHandler.setBorders(BORDERS);
 
		for (int i=0;i<BSIZE;i++) {
			for (int j=0;j<BSIZE;j++) {
				board[i][j]=EMPTY;
			}
		}
 
		//set up board here
		board[3][3] = (int)'A';
		board[4][3] = (int)'Q';
		board[4][4] = -(int)'B';
		board[4][2] = -(int)'B';
		board[5][4] = -(int)'B';
		board[2][4] = -(int)'B';
		board[4][6] = -(int)'B';
	}
	
    private void initializeToolBar(JToolBar tools){
		
		ArrayList<String> commands = new ArrayList<String>();
		commands.add("FINISH TURN");
		commands.add("USE CARD");
		
		for(int i = 0; i < buttons.size(); i++) {
			JButton b = new JButton(commands.get(i));
			buttons.add(b);
			tools.add(b);
		}
        
	}
 
    public void addButtonActionListener(ActionListener listener) {
		for (int i = 0; i < buttons.size() ; i++ ){
			buttons.get(i).addActionListener(listener);
		}
	}
    
	private void createAndShowGUI()
	{
		JFrame.setDefaultLookAndFeelDecorated(true);
		JFrame frame = new JFrame("ESCAPE ALIENS FROM THE OUTER SPACE");
		frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		JPanel gui = new JPanel(); 
		JToolBar tools = new JToolBar();
		tools.setFloatable(false);
	    gui.add(tools, BorderLayout.PAGE_START);
	    this.initializeToolBar(tools);
	    
	    name = JOptionPane.showInputDialog(
	           frame,
	            "Choose a type of connection:",
	            "Connection",
	            JOptionPane.PLAIN_MESSAGE);
		
		DrawingPanel board = new DrawingPanel();
		
		board.setBorder(new LineBorder(Color.GREEN));
		
		gui.add(board);
		
		/*Container content = frame.getContentPane();
		LayoutManager mgr = new 
		content.setLayout(mgr);*/
		frame.add(gui);
		//this.add(panel);  -- cannot be done in a static context
		//for hexes in the FLAT orientation, the height of a 10x10 grid is 1.1764 * the width. (from h / (s+t))
		frame.setSize( (int)(SCRSIZE/1.23), SCRSIZE);
		frame.setResizable(false);
		frame.setLocationRelativeTo( null );
		frame.setVisible(true);
	}
	
	class DrawingPanel extends JPanel
	{		
		//mouse variables here
		//Point mPt = new Point(0,0);
 
		public DrawingPanel()
		{	
			/*super();
			this.setBorder(new LineBorder(Color.BLACK));*/
			setBackground(COLOURBACK);
 
			MyMouseListener ml = new MyMouseListener();            
			addMouseListener(ml);
		}
 
		public void paintComponent(Graphics g)
		{
			Graphics2D g2 = (Graphics2D)g;
			g2.setRenderingHint(RenderingHints .KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
			super.paintComponent(g2);
			//draw grid
			for (int i=0;i<BSIZE;i++) {
				for (int j=0;j<BSIZE;j++) {
					HexagonHandler.drawHex(i,j,g2);
				}
			}
			//fill in hexes
			for (int i=0;i<BSIZE;i++) {
				for (int j=0;j<BSIZE;j++) {					
					//if (board[i][j] < 0) hexmech.fillHex(i,j,COLOURONE,-board[i][j],g2);
					//if (board[i][j] > 0) hexmech.fillHex(i,j,COLOURTWO, board[i][j],g2);
					HexagonHandler.fillHex(i,j,board[i][j],g2);
				}
			}
 
			//g.setColor(Color.RED);
			//g.drawLine(mPt.x,mPt.y, mPt.x,mPt.y);
		}
 
		class MyMouseListener extends MouseAdapter	{	//inner class inside DrawingPanel 
			public void mouseClicked(MouseEvent e) { 
				int x = e.getX(); 
				int y = e.getY(); 
				//mPt.x = x;
				//mPt.y = y;
				Point p = new Point( HexagonHandler.pxtoHex(e.getX(),e.getY()) );
				if (p.x < 0 || p.y < 0 || p.x >= BSIZE || p.y >= BSIZE) return;
 
				//DEBUG: colour in the hex which is supposedly the one clicked on
				//clear the whole screen first.
				/* for (int i=0;i<BSIZE;i++) {
					for (int j=0;j<BSIZE;j++) {
						board[i][j]=EMPTY;
					}
				} */
 
				//What do you want to do when a hexagon is clicked?
				board[p.x][p.y] = (int)'X';
				repaint();
			}		 
		} //end of MyMouseListener class 
	} // end of DrawingPanel class
}
