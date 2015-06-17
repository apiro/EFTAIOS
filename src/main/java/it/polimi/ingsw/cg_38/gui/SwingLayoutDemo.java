package it.polimi.ingsw.cg_38.gui;

import it.polimi.ingsw.cg_38.controller.event.Event;
import it.polimi.ingsw.cg_38.gameEvent.EventAttack;
import it.polimi.ingsw.cg_38.gameEvent.EventDraw;
import it.polimi.ingsw.cg_38.gameEvent.EventFinishTurn;
import it.polimi.ingsw.cg_38.gameEvent.EventMove;
import it.polimi.ingsw.cg_38.model.Galilei;
import it.polimi.ingsw.cg_38.model.Map;
import it.polimi.ingsw.cg_38.model.Player;
import it.polimi.ingsw.cg_38.model.Safe;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.swing.*;

public class SwingLayoutDemo {
	
   private JFrame mainFrame;
   private JLabel headerLabel;
   private JLabel statusLabel;
   private JPanel controlPanel;
   private JLabel msglabel;
   private Map map;
   private MapPanel panelCentr; 

   final static int EMPTY = 0;
   final static int BSIZEW = 23;
   final static int BSIZEH = 14; //board size.
   final static int HEXSIZE = 45;	//hex size in pixels
   final static int BORDERS = 15;
   final static int SCRSIZE = HEXSIZE * (BSIZEH + 1) + BORDERS*3;
   
   private int[][] board = new int[BSIZEH][BSIZEW];
   private ArrayList<JButton> buttons = new ArrayList<JButton>();
   private ConcurrentLinkedQueue<Event> toSend;
   private Player player;
   
   public SwingLayoutDemo(ConcurrentLinkedQueue<Event> toSend){
	   this.toSend = toSend;
      prepareGUI();
   }

   public static void main(String[] args){
	  
      SwingLayoutDemo swingLayoutDemo = new SwingLayoutDemo(new ConcurrentLinkedQueue<Event>()); 
      swingLayoutDemo.init(new Galilei());
      swingLayoutDemo.showBorderLayoutDemo();       
   }
      
   public void init(Map map) {
	   
	   this.map = map;
	   HexagonHandler.setXYasVertex(false); //RECOMMENDED: leave this as FALSE.
	   
	   HexagonHandler.setHeight(HEXSIZE); //Either setHeight or setSize must be run to initialize the hex
	   HexagonHandler.setBorders(BORDERS);
	   
	   for (int i=0;i<board.length;i++) {
			for (int j=0;j<board[i].length;j++) {
				board[i][j]=map.getConfiguration()[j + 14*i];
			}
		}
	   
   }

   private void prepareGUI(){
	 
      mainFrame = new JFrame("Game");
      mainFrame.setSize(1250, 700);
      /*mainFrame.setSize( (int)(SCRSIZE/1.23), SCRSIZE);*/
      mainFrame.setResizable(false);
      
      mainFrame.addWindowListener(new WindowAdapter() {
         public void windowClosing(WindowEvent windowEvent){
	        System.exit(0);
         }        
      });  
     
      this.player =new Player( JOptionPane.showInputDialog(
	           mainFrame,
	            "Choose a type of connection:",
	            "Connection",
	            JOptionPane.INFORMATION_MESSAGE)); 
	   
      
      controlPanel = new JPanel();
      mainFrame.add(controlPanel);
      mainFrame.setVisible(true);  
   }

   private void showBorderLayoutDemo(){
      controlPanel.setBackground(Color.DARK_GRAY);
      BorderLayout layout = new BorderLayout();
      
      JPanel panelSx = new JPanel();
      
      panelSx.setBackground(Color.DARK_GRAY);
      panelSx.setLayout(new GridLayout(6,1));     
      buttons.add(new JButton("Attack"));
      buttons.add(new JButton("Draw"));
      buttons.add(new JButton("FinishTurn"));
      buttons.add(new JButton("Use card 1"));
      buttons.add(new JButton("Use card 2"));
      buttons.add(new JButton("Use card 3"));
      
      this.handlActionListeners();
      
      for(int i = 0; i<buttons.size(); i++) {
    	  panelSx.add(buttons.get(i));
      }
      
      JPanel panelDx = new JPanel();
      
      panelDx.setBackground(Color.DARK_GRAY);
      panelDx.setLayout(new GridLayout(2,1)); 
      
      JTextArea text1 = new JTextArea("Informazioni di gioco");
      text1.setLineWrap(true);
      JTextArea text2 = new JTextArea("Chat");
      text2.setLineWrap(true);
      
      panelDx.add(text1);
      panelDx.add(text2);
      
      
      panelCentr = new MapPanel(this.BSIZEW, this.BSIZEH, this.board);
      /*panelCentr.setSize( (int)(SCRSIZE/1.23), SCRSIZE);*/
      
      panelCentr.addMouseListener(new MouseAdapter() {	//inner class inside DrawingPanel 
		public void mouseClicked(MouseEvent e) { 
			int x = e.getX(); 
			int y = e.getY(); 
			//mPt.x = x;
			//mPt.y = y;
			Point p = new Point( HexagonHandler.pxtoHex(e.getX(),e.getY()) );
			if (p.x < 0 || p.y < 0 || p.x >= BSIZEW || p.y >= BSIZEH) return;

			//DEBUG: colour in the hex which is supposedly the one clicked on
			//clear the whole screen first.
			/* for (int i=0;i<BSIZE;i++) {
				for (int j=0;j<BSIZE;j++) {
					board[i][j]=EMPTY;
				}
			} */

			//What do you want to do when a hexagon is clicked?
			board[p.x][p.y] = (int)'X';
			EventMove evt = new EventMove(player, map.searchSectorByCoordinates(p.x, p.y));
			synchronized(toSend) {
				toSend.add(evt);
			}
			System.out.println(evt.toString());
			panelCentr.repaint();
		  }
      });
      
      controlPanel.setLayout(layout);        
	  
      controlPanel.add(panelCentr, BorderLayout.CENTER);
      
      controlPanel.add(panelSx,BorderLayout.LINE_START); 
      controlPanel.add(panelDx,BorderLayout.LINE_END);
      
      mainFrame.setVisible(true);  
   }

    private void handlActionListeners() {

        buttons.get(0).addActionListener(new ActionListener() {

  		@Override
  		public void actionPerformed(ActionEvent e) {
  			EventAttack evt = new EventAttack(player, new Safe());
  			synchronized(toSend) {
  				toSend.add(evt);
  			}
  			System.out.println(evt.toString());
  		}
      	  
        });
        
        buttons.get(1).addActionListener(new ActionListener() {

    		@Override
    		public void actionPerformed(ActionEvent e) {
    			EventDraw evt = new EventDraw(player);
    			synchronized(toSend) {
    				toSend.add(evt);
    			}
    			System.out.println(evt.toString());
    		}
        	  
          });
        
        buttons.get(2).addActionListener(new ActionListener() {

    		@Override
    		public void actionPerformed(ActionEvent e) {
    			EventFinishTurn evt = new EventFinishTurn(player);
    			synchronized(toSend) {
    				toSend.add(evt);
    			}
    			System.out.println(evt.toString());
    		}
        });
   }
}