package it.polimi.ingsw.cg_38.client.gui;

import it.polimi.ingsw.cg_38.client.Client;
import it.polimi.ingsw.cg_38.client.PlayerClient;
import it.polimi.ingsw.cg_38.client.PlayerClientCLI;
import it.polimi.ingsw.cg_38.client.PlayerClientState;
import it.polimi.ingsw.cg_38.client.notifyAction.NotifyAction;
import it.polimi.ingsw.cg_38.client.notifyAction.NotifyActionCreator;
import it.polimi.ingsw.cg_38.controller.event.Event;
import it.polimi.ingsw.cg_38.controller.event.GameEvent;
import it.polimi.ingsw.cg_38.controller.gameEvent.EventAdrenaline;
import it.polimi.ingsw.cg_38.controller.gameEvent.EventAttack;
import it.polimi.ingsw.cg_38.controller.gameEvent.EventAttackCard;
import it.polimi.ingsw.cg_38.controller.gameEvent.EventContinue;
import it.polimi.ingsw.cg_38.controller.gameEvent.EventDraw;
import it.polimi.ingsw.cg_38.controller.gameEvent.EventFinishTurn;
import it.polimi.ingsw.cg_38.controller.gameEvent.EventSedatives;
import it.polimi.ingsw.cg_38.controller.gameEvent.EventSpotLight;
import it.polimi.ingsw.cg_38.controller.gameEvent.EventSubscribe;
import it.polimi.ingsw.cg_38.controller.gameEvent.EventTeleport;
import it.polimi.ingsw.cg_38.controller.logger.Logger;
import it.polimi.ingsw.cg_38.controller.logger.LoggerCLI;
import it.polimi.ingsw.cg_38.controller.logger.LoggerGUI;
import it.polimi.ingsw.cg_38.model.Movement;
import it.polimi.ingsw.cg_38.model.Player;
import it.polimi.ingsw.cg_38.model.deck.Card;
import it.polimi.ingsw.cg_38.model.deck.ObjectCard;
import it.polimi.ingsw.cg_38.model.deck.ObjectCardType;
import it.polimi.ingsw.cg_38.model.map.Map;
import it.polimi.ingsw.cg_38.model.map.Safe;
import it.polimi.ingsw.cg_38.model.map.Sector;

import java.awt.*;
import java.awt.event.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.swing.*;

public class PlayerClientGUI implements PlayerClient {
	
   private JFrame mainFrame;
   private JPanel controlPanel;
   private HexGrid panelCentr;
   private JPanel panelDx;
   private JPanel panelSx;
   private JTextArea text1;
   private JTextArea text2;
   private JScrollPane scroll1;
   private JScrollPane scroll2;
   private Map map; 
   private EventSubscribe evt;
   private Client client;
   private PlayerClientState playerClientState;
   private it.polimi.ingsw.cg_38.model.Player player;
   private Boolean isMyTurn = false;
   private Boolean clientAlive = false;
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
   
   public Boolean getClientAlive() {
	return clientAlive;
   }

   public PlayerClientGUI(){
	  alive[0] = true;
      prepareGUI();
      logger = new LoggerCLI();
      while(!clientAlive) {
    	  try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
    	  Event msg = toProcess.poll();
    	  if(msg != null) {
    		  this.process(msg);
    	  }
      }
      init();
      show();   
   }
   
   public void setClientAlive(Boolean clientAlive) {
	   this.clientAlive = clientAlive;
   }

  public void setAlive(Boolean b) {
	  this.alive[0] = b;
   }

   public static void main(String[] args){
	  
      PlayerClientGUI gui = new PlayerClientGUI();     
      gui.run();
   }
      
    public void run() {
	  while(alive[0]) {
		  Event msg = toProcess.poll();
			if(msg != null) {
				this.process(msg);
				/*text1.append(msg.toString() + "\n");*/
				this.updateCards();
			}
			this.blockInterf(isInterfaceBlocked);
	  }
	  logger.print("GoodBye !");
	  Thread.currentThread().interrupt();
   }
    
   private void blockInterf(boolean b) {
		for(JButton butt:this.buttons) {
			butt.setEnabled(!b);
		}
		for(GraphicSector sec:this.panelCentr.getSects()) {
			sec.setEnabled(!b);
		}
	}

  public void updateCards() {
	   int i = 3;
	   if(player.getAvatar().getMyCards().size() == 4) {
		   int cardSelected = Integer.parseInt(JOptionPane.showInputDialog(
		           mainFrame,
		            "DROWN 4TH CARD: type-> " + player.getAvatar().getMyCards().get(3).getType().toString(),
		            "You have 4 cards: you have to use one of them ! choose...",
		            JOptionPane.INFORMATION_MESSAGE));
		   try {
				
				if(player.getAvatar().getMyCards().get(cardSelected).getType().equals(ObjectCardType.Adrenaline)) {
					
					this.toSend.add(new EventAdrenaline(player, player.getAvatar().getMyCards().get(cardSelected)));
					
				} else if(player.getAvatar().getMyCards().get(cardSelected).getType().equals(ObjectCardType.AttackCard)) {
					
					this.toSend.add(new EventAttackCard(player, player.getAvatar().getMyCards().get(cardSelected)));
					
				} else if(player.getAvatar().getMyCards().get(cardSelected).getType().equals(ObjectCardType.Defense)) {
					
					logger.print("---> You can't use defense card !");
					this.getPlayer().getAvatar().getMyCards().remove(cardSelected);
					
				} else if(player.getAvatar().getMyCards().get(cardSelected).getType().equals(ObjectCardType.Sedatives)) {
					
					this.toSend.add(new EventSedatives(player, player.getAvatar().getMyCards().get(cardSelected)));
					
				} else if(player.getAvatar().getMyCards().get(cardSelected).getType().equals(ObjectCardType.SpotLight)) {
					
					Sector toMove = this.askForMoveCoordinates();
					this.toSend.add(new EventSpotLight(player, toMove, player.getAvatar().getMyCards().get(cardSelected)));
					
				} else if (player.getAvatar().getMyCards().get(cardSelected).getType().equals(ObjectCardType.Teleport)) {
					
					this.toSend.add(new EventTeleport(player, player.getAvatar().getMyCards().get(cardSelected)));
					
				}
				this.resetButton(cardSelected);
				
			} catch(IndexOutOfBoundsException e) {
				logger.print("The requested card doesn't exist !");
				this.updateCards();
			}  
	   }
	   
	   for(ObjectCard c:player.getAvatar().getMyCards()) {
		   buttons.get(i).setText(c.getType().toString());
		   i++;
	   }
   }

   private void resetButton(int cardSelected) {
	   buttons.get(cardSelected).setText("Use Card");
		player.getAvatar().getMyCards().remove(cardSelected);
  }

  public HexGrid getPanelCentr() {
	return panelCentr;
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
	            "WELCOME TO THE GAME: \nChoose a type of connection:\n"
	            + "Are available: [Socket] | [RMI] choose one of them !",
	            "Connection",
	            JOptionPane.INFORMATION_MESSAGE);
      
      this.player = this.getIdentity(); 
      
      evt = new EventSubscribe(player, room, nameMap);
      
      playerClientState = PlayerClientState.init;
	  this.toProcess = new ConcurrentLinkedQueue<Event>();
	  this.toSend = new ConcurrentLinkedQueue<Event>();
	  this.startSender();
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
		            "Choose a Map:\n (if you are joining an existing room you can type smth random)",
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
      buttons.add(new JButton("Use card"));
      buttons.add(new JButton("Use card"));
      buttons.add(new JButton("Use card"));
      
      this.handlActionListeners();
      
      for(int i = 0; i<buttons.size(); i++) {
    	  panelSx.add(buttons.get(i));
      }
      
      panelDx = new JPanel();
      
      panelDx.setBackground(Color.DARK_GRAY);
      panelDx.setLayout(new GridLayout(2,1)); 
      
      text1 = new JTextArea("Informazioni di gioco\n");
      text1.setLineWrap(true);
      scroll1 = new JScrollPane(text1);
      text2 = new JTextArea("Movements\n");
      text2.setLineWrap(true);
      scroll2 = new JScrollPane(text2);
      text1.setBounds(0, 0, 270, 300);
      panelDx.add(scroll1);
      panelDx.add(scroll2);
        
      this.logger = new LoggerGUI(this.text1, mainFrame);
      panelCentr = new HexGrid(this.board, this.toSend, this.player, this.map);
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
  			EventAttack evt = new EventAttack(player, player.getAvatar().getCurrentSector());
  			synchronized(toSend) {
  				toSend.add(evt);
  			}
  		}
      	  
        });
        
        buttons.get(1).addActionListener(new ActionListener() {

    		@Override
    		public void actionPerformed(ActionEvent e) {
    			synchronized(toSend) {
    				toSend.add(new EventDraw(player));
    			}
    		}
        	  
          });
        
        buttons.get(2).addActionListener(new ActionListener() {

    		@Override
    		public void actionPerformed(ActionEvent e) {
    			synchronized(toSend) {
    				Event evt = new EventFinishTurn(player);
    				toSend.add(evt);
    			}
    		}
        });
        
        for(int i = 3; i<buttons.size();i++) {
	        buttons.get(i).addActionListener(new ActionListener() {
	
	    		@Override
	    		public void actionPerformed(ActionEvent e) {
	    			int x;
	    			int y;
	    			Sector sec = null;
	    			ObjectCard card = null;
	    			String s = "it.polimi.ingsw.cg_38.controller.gameEvent.Event" + ((JButton)e.getSource()).getText();
	    			System.out.println(s);
	    			Class<?> myClass = null;
					try {
						myClass = Class.forName(s);
					} catch (ClassNotFoundException e1) {
						logger.print("The requested Class is not available !");
						e1.printStackTrace();
					}
	    			Constructor<?> constructor = null;
					try {
						for(ObjectCard c:player.getAvatar().getMyCards()) {
							if(c.getType().toString().equals(((JButton)e.getSource()).getText())) {
								card = c;
							}
						}
						if(s.equals("it.polimi.ingsw.cg_38.controller.gameEvent.EventSpotLight")) {
							sec = askForMoveCoordinates();
							constructor = myClass.getConstructor(it.polimi.ingsw.cg_38.model.Player.class, 
									it.polimi.ingsw.cg_38.model.map.Sector.class, 
									it.polimi.ingsw.cg_38.model.deck.Card.class );
						} else {
							constructor = myClass.getConstructor(it.polimi.ingsw.cg_38.model.Player.class, 
									it.polimi.ingsw.cg_38.model.deck.Card.class);
						}
					} catch (NoSuchMethodException e1) {
						logger.print("The constructor is not available for the requested class !");
						e1.printStackTrace();
					} catch (SecurityException e1) {
						e1.printStackTrace();
					}
	    			Object instance = null;
					try {
						if(s.equals("it.polimi.ingsw.cg_38.controller.gameEvent.EventSpotLight")) {
							instance = constructor.newInstance(player, sec, card);
						} else {
							instance = constructor.newInstance(player, card);
						}
					} catch (InstantiationException e1) {
						logger.print("Problems with the instantiation of the object ...");
						e1.printStackTrace();
					} catch (IllegalAccessException e1) {
						e1.printStackTrace();
					} catch (IllegalArgumentException e1) {
						e1.printStackTrace();
					} catch (InvocationTargetException e1) {
						e1.printStackTrace();
					}
					player.getAvatar().getMyCards().remove(card);
					updateCards();
					((JButton)e.getSource()).setText("Use Card");
	    			GameEvent evt = (GameEvent)instance;
	    			synchronized(toSend) {
	    				toSend.add(evt);
	    			}
	    		}
	        });
        }
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
		String x = JOptionPane.showInputDialog(
		           mainFrame,
		            "ASKING FOR MOVE COORDINATES:",
		            "x?",
		            JOptionPane.INFORMATION_MESSAGE);
		while(x == null) {
			x = JOptionPane.showInputDialog(
			           mainFrame,
			            "ASKING FOR MOVE COORDINATES:",
			            "x?",
			            JOptionPane.INFORMATION_MESSAGE);
		}
		String y = JOptionPane.showInputDialog(
		           mainFrame,
		            "ASKING FOR MOVE COORDINATES:",
		            "y?",
		            JOptionPane.INFORMATION_MESSAGE);
		while(y == null) {
			y = JOptionPane.showInputDialog(
			           mainFrame,
			            "ASKING FOR MOVE COORDINATES:",
			            "y?",
			            JOptionPane.INFORMATION_MESSAGE);
		}
		
		Sector toMove = map.searchSectorByCoordinates(Integer.parseInt(x), Integer.parseInt(y));
		return toMove;
	}

	@Override
	public Map getMap() {
		return this.map;
	}

	@Override
	public void setIsMyTurn(Boolean b) {
		this.isMyTurn = b;
	}

	@Override
	public void updateMovements() {
		this.text2.setText(null);
		int i = 0;
		for(Movement m:player.getAvatar().getMyMovements()) {
			this.text2.append(i + ")" + m.toString() + "\n");
			i++;
		}
		
		panelCentr.slidePlayerPosition(player.getAvatar().getCurrentSector().getCol(), player.getAvatar().getCurrentSector().getRow(),
				player.getAvatar().getCurrentSector().getClass().toString());
	}

	@Override
	public void paintHatch(Boolean bool, Sector sec) {
		panelCentr.hatchPainter(sec.getCol(), sec.getRow(), bool);
	}
}