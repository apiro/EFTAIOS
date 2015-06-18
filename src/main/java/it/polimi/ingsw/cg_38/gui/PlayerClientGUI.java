package it.polimi.ingsw.cg_38.gui;

import it.polimi.ingsw.cg_38.controller.Client;
import it.polimi.ingsw.cg_38.controller.Logger;
import it.polimi.ingsw.cg_38.controller.LoggerCLI;
import it.polimi.ingsw.cg_38.controller.PlayerClient;
import it.polimi.ingsw.cg_38.controller.PlayerClientState;
import it.polimi.ingsw.cg_38.controller.event.Event;
import it.polimi.ingsw.cg_38.controller.event.GameEvent;
import it.polimi.ingsw.cg_38.gameEvent.EventAttack;
import it.polimi.ingsw.cg_38.gameEvent.EventContinue;
import it.polimi.ingsw.cg_38.gameEvent.EventDraw;
import it.polimi.ingsw.cg_38.gameEvent.EventFinishTurn;
import it.polimi.ingsw.cg_38.gameEvent.EventSubscribe;
import it.polimi.ingsw.cg_38.model.Galvani;
import it.polimi.ingsw.cg_38.model.Map;
import it.polimi.ingsw.cg_38.model.MapCreator;
import it.polimi.ingsw.cg_38.model.Player;
import it.polimi.ingsw.cg_38.model.Safe;
import it.polimi.ingsw.cg_38.model.Sector;
import it.polimi.ingsw.cg_38.notifyAction.NotifyAction;
import it.polimi.ingsw.cg_38.notifyAction.NotifyActionCreator;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.swing.*;

public class PlayerClientGUI implements PlayerClient {
	
   private JFrame mainFrame;
   private JPanel controlPanel;
   private JPanel panelCentr;
   private JPanel panelDx;
   private JPanel panelSx;
   private JTextArea text1;
   private JTextArea text2;
   private Map map; 
   private EventSubscribe evt;
   private Client client;
   private PlayerClientState playerClientState;
   private it.polimi.ingsw.cg_38.model.Player player;
   private Boolean isMyTurn = false;
   private Boolean clientAlive = true;
   private Boolean isInterfaceBlocked = true;
   private Thread gameEventSender;
   private Logger logger;

   final Boolean[] alive = new Boolean[1];
   final static int BSIZEW = 23;
   final static int BSIZEH = 14;
   final static int HEXSIZE = 45;
   final static int BORDERS = 15;
   
   private int[][] board = new int[BSIZEH][BSIZEW];
   private ArrayList<JButton> buttons = new ArrayList<JButton>();
   private ConcurrentLinkedQueue<Event> toSend;
   private ConcurrentLinkedQueue<Event> toProcess;
   private String connection;
   private String room;
   private String nameMap;
   
   public PlayerClientGUI(){
	  alive[0] = true;
      prepareGUI();
      init();
      show();   
   }
   
   public static void main(String[] args){
	  
      PlayerClientGUI swingLayoutDemo = new PlayerClientGUI();     
      swingLayoutDemo.run();
   }
      
    public void run() {
	  while(alive[0]) {
		  Event msg = toProcess.poll();
			if(msg != null) {
				this.process(msg);
				text1.append(msg.toString() + "\n");
			}
	  }
   }

   public void process(Event msg) {
		System.out.println("----------------------------------------------------------------------\n");
		System.err.println("Recieving " + msg.toString() + " ...\n");
		NotifyAction action = (NotifyAction)NotifyActionCreator.createNotifyAction(msg);
		GameEvent gamEvt = null;
		if(action.isPossible(this)) {
			gamEvt = action.render(this);
			if(gamEvt != null) {
				if(gamEvt instanceof EventContinue) return;
				this.toSend.add(gamEvt);
			} else { 
				if(!isInterfaceBlocked) {
					/*this.loadInterface();*/
				} else return;
			}
		} else {
			System.out.println("Error in parsing the notifyEvent ...");
		}
	}
   
   public void init() {
	   
	   playerClientState = PlayerClientState.init;
	   this.toProcess = new ConcurrentLinkedQueue<Event>();
	   this.toSend = new ConcurrentLinkedQueue<Event>();
	   this.logger = new LoggerGUI(text1);
	   this.startSender();
	   
	   this.buttons.get(0).setEnabled(false);
	   this.buttons.get(1).setEnabled(false);
	   this.buttons.get(2).setEnabled(true);
	   this.buttons.get(3).setEnabled(false);
	   this.buttons.get(4).setEnabled(false);
	   this.buttons.get(5).setEnabled(false);
	   
	   HexagonHandler.setXYasVertex(false);
	   
	   HexagonHandler.setHeight(HEXSIZE);
	   HexagonHandler.setBorders(BORDERS);
	   
	   
	   
	   for (int i=0;i<board.length;i++) {
			for (int j=0;j<board[i].length;j++) {
				board[i][j]=map.getConfiguration()[j + 23*i];
			}
		}
   }

   public void startSender() {
	   client = Client.clientCreator(connection, toSend, toProcess, evt);
	   gameEventSender = new Thread(client, "GameEventSender");
	   gameEventSender.start();
   }

  private void prepareGUI(){
	 
      mainFrame = new JFrame("Game");
      mainFrame.setSize(1250, 680);
      mainFrame.setResizable(false);
      
      mainFrame.addWindowListener(new WindowAdapter() {
		public void windowClosing(WindowEvent windowEvent){
        	 alive[0] = false;
	        System.exit(0);
         }        
      });  
      
      controlPanel = new JPanel();
      mainFrame.add(controlPanel);
      mainFrame.setVisible(true); 
      
      this.connection = JOptionPane.showInputDialog(
	           mainFrame,
	            "WELCOME TO THE GAME: \nChoose a type of connection:",
	            "Connection",
	            JOptionPane.INFORMATION_MESSAGE);
      
      this.player = this.getIdentity(); 
      
      evt = new EventSubscribe(player, room, nameMap);
   }

   private Player getIdentity() {
	   String name;
	   
	   name = JOptionPane.showInputDialog(
	           mainFrame,
	            "Choose a name in the game:",
	            "Name",
	            JOptionPane.INFORMATION_MESSAGE);
      
      this.room = JOptionPane.showInputDialog(
	           mainFrame,
	            "Choose a Room in the game:",
	            "Room",
	            JOptionPane.INFORMATION_MESSAGE);
      
      try {
    	this.nameMap = JOptionPane.showInputDialog(
		           mainFrame,
		            "Choose a Map:",
		            "Map",
		            JOptionPane.INFORMATION_MESSAGE);
    	
	} catch (Exception e) {
		System.out.println("Problems in creating the requestes map");
	}
      
      return new Player(name);
  }

  private void show(){
      controlPanel.setBackground(Color.DARK_GRAY);
      BorderLayout layout = new BorderLayout();
      
      panelSx = new JPanel();
      
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
      
      panelDx = new JPanel();
      
      panelDx.setBackground(Color.DARK_GRAY);
      panelDx.setLayout(new GridLayout(2,1)); 
      
      text1 = new JTextArea("Informazioni di gioco\n");
      text1.setLineWrap(true);
      text2 = new JTextArea("Movements\n");
      text2.setLineWrap(true);
      text1.setBounds(0, 0, 270, 300);
      panelDx.add(text1);
      panelDx.add(text2);
           
      panelCentr = new HexGrid(this.board);
      panelCentr.setLayout(null);
      
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

	public Boolean getIsInterfaceBlocked() {
		return isInterfaceBlocked;
	}

	public void setIsInterfaceBlocked(Boolean isInterfaceBlocked) {
		this.isInterfaceBlocked = isInterfaceBlocked;
	}

	public Logger getLogger() {
		return logger;
	}

	public it.polimi.ingsw.cg_38.model.Player getPlayer() {
		return player;
	}

	public void setPlayerClientState(PlayerClientState playerClientState) {
		this.playerClientState = playerClientState;
	}

	@Override
	public void setPlayer(Player player) {
		this.player = player;
	}

	@Override
	public PlayerClientState getPlayerClientState() {
		return playerClientState;
	}

	@Override
	public void closeClient() {
		
	}

	@Override
	public void setMap(Map map) {
		this.map = map;
	}

	@Override
	public Sector askForMoveCoordinates() {
		int x  = Integer.parseInt(JOptionPane.showInputDialog(
		           mainFrame,
		            "Choose a Room in the game:",
		            "Room",
		            JOptionPane.INFORMATION_MESSAGE));
		
		int y = Integer.parseInt(JOptionPane.showInputDialog(
		           mainFrame,
		            "Choose a Room in the game:",
		            "Room",
		            JOptionPane.INFORMATION_MESSAGE));
		Sector toMove = map.searchSectorByCoordinates(x, y);
		return toMove;
	}

	@Override
	public Map getMap() {
		return this.map;
	}


}