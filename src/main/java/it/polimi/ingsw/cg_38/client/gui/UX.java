package it.polimi.ingsw.cg_38.client.gui;

import it.polimi.ingsw.cg_38.controller.event.Event;
import it.polimi.ingsw.cg_38.controller.gameEvent.EventADRENALINE;
import it.polimi.ingsw.cg_38.controller.gameEvent.EventATTACKCARD;
import it.polimi.ingsw.cg_38.controller.gameEvent.EventFinishTurn;
import it.polimi.ingsw.cg_38.controller.gameEvent.EventRetired;
import it.polimi.ingsw.cg_38.controller.gameEvent.EventSEDATIVES;
import it.polimi.ingsw.cg_38.controller.gameEvent.EventSPOTLIGHT;
import it.polimi.ingsw.cg_38.controller.gameEvent.EventSubscribe;
import it.polimi.ingsw.cg_38.controller.gameEvent.EventTELEPORT;
import it.polimi.ingsw.cg_38.controller.logger.Logger;
import it.polimi.ingsw.cg_38.controller.logger.LoggerGUI;
import it.polimi.ingsw.cg_38.model.Movement;
import it.polimi.ingsw.cg_38.model.Player;
import it.polimi.ingsw.cg_38.model.deck.ObjectCard;
import it.polimi.ingsw.cg_38.model.deck.ObjectCardType;
import it.polimi.ingsw.cg_38.model.map.Map;
import it.polimi.ingsw.cg_38.model.map.Sector;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class UX extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel controlPanel;
	private JPanel panelSx;
	private List<JButton> buttons = new ArrayList<JButton>();
	private JPanel panelDx;
	private JTextArea text1;
	private JScrollPane scroll1;
	private JTextArea text2;
	private JScrollPane scroll2;
	private Logger logger;
	private HexGrid panelCentr;
	private JTextArea text3;
	private JScrollPane scroll3;
	private JPanel panelChat;
	private JTextField input;
	private String name;

	public JTextField getInput() {
		return input;
	}

	public UX (Logger logger) {
		this.logger = new LoggerGUI(null, this);
	}
	
    public void setLogger(Logger logger) {
		this.logger = logger;
	}

	public JTextArea getText1() {
		return text1;
	}

	public JTextArea getText3() {
		return text3;
	}
	
    public Player prepareGUI(final Queue<?>[] toSend){
		 
	    this.setTitle("ESCAPE FROM THE ALIENS IN OUTER SPACE");
	    this.setSize(1285, 700);
	    this.setResizable(false);
	      
	    this.addWindowListener(new WindowAdapter() {
	    	public void windowClosing(WindowEvent windowEvent){
	    		synchronized(toSend) {
	    			((Queue<Event>)toSend[0]).add(new EventRetired(new Player(name)));
	    			((Queue<Event>)toSend[0]).add(new EventFinishTurn(new Player(name)));
	    		}
	    		Thread.currentThread().interrupt();
	        }        
	    });  
	      
	    controlPanel = new JPanel();
	    this.add(controlPanel);
	    this.setVisible(true); 
	      
	    return this.getIdentity();     
	}
	
	public EventSubscribe generateEventSub(Player player) {
		   
		String room;
		  room = logger.showAndRead("Choose a room in the game:", "REGISTRATION:");
	      while(room == "") {
	    	  room = logger.showAndRead("Choose a room in the game:", "REGISTRATION:");
	      }
		
	      String nameMap;
	      nameMap = logger.showAndRead("Choose a map for the starting game: \nType smth random if you are accessing to an existing room ...", "REGISTRATION:");
	      while(nameMap == "") {
	    	  nameMap = logger.showAndRead("Choose a map for the starting game:", "REGISTRATION:");
	      }
		
		return new EventSubscribe(player, room, nameMap);
	}
	
	public String askForUseCardOrRejectCard() {
		String choice = logger.showAndRead("Do you wanna Use [U] or Reject [R] the selected Card ?", "USE OR REJECT:");
		while(!("U").equals(choice) && !("R").equals(choice)) {
			choice = logger.showAndRead("Do you wanna Use [U] or Reject [R] the selected Card ?", "USE OR REJECT:");
		}
		return choice;
	}
	
	public Player getIdentity() {
		  String name;
		  name = logger.showAndRead("Choose a name in the game:", "REGISTRATION:");
	      while(name == "") {
	    	  name = logger.showAndRead("Choose a name in the game:", "REGISTRATION:");
	      }
	      this.setTitle("EFTAIOS - " + name);
	      this.name = name;
	      return new Player(name);
	}
	
	public void updateCards(Player player, Queue<Event> toSend, Map map) {
		   int i = 2;
		   if(player.getAvatar().getMyCards().size() == 4) {
			   int cardSelected = Integer.parseInt(logger.showAndRead("DROWN 4TH CARD: type-> " + player.getAvatar().getMyCards().get(3).getType().toString(),
			            "You have 4 cards: you have to use one of them ! choose..."));
			   try {
					
					if(player.getAvatar().getMyCards().get(cardSelected).getType().equals(ObjectCardType.ADRENALINE)) {
						
						toSend.add(new EventADRENALINE(player, player.getAvatar().getMyCards().get(cardSelected)));
						
					} else if(player.getAvatar().getMyCards().get(cardSelected).getType().equals(ObjectCardType.ATTACKCARD)) {
						
						toSend.add(new EventATTACKCARD(player, player.getAvatar().getMyCards().get(cardSelected)));
						
					} else if(player.getAvatar().getMyCards().get(cardSelected).getType().equals(ObjectCardType.DEFENSE)) {
						
						logger.print("---> You can't use defense card !");
						player.getAvatar().getMyCards().remove(cardSelected);
						
					} else if(player.getAvatar().getMyCards().get(cardSelected).getType().equals(ObjectCardType.SEDATIVES)) {
						
						toSend.add(new EventSEDATIVES(player, player.getAvatar().getMyCards().get(cardSelected)));
						
					} else if(player.getAvatar().getMyCards().get(cardSelected).getType().equals(ObjectCardType.SPOTLIGHT)) {
						
						Sector toMove = this.askForMoveCoordinates(map);
						toSend.add(new EventSPOTLIGHT(player, toMove, player.getAvatar().getMyCards().get(cardSelected)));
						
					} else if (player.getAvatar().getMyCards().get(cardSelected).getType().equals(ObjectCardType.TELEPORT)) {
						
						toSend.add(new EventTELEPORT(player, player.getAvatar().getMyCards().get(cardSelected)));
						
					}
					this.resetButton(cardSelected, player);
					
				} catch(IndexOutOfBoundsException e) {
					logger.print("The requested card doesn't exist !");
					this.updateCards(player, toSend, map);
				}  
		   }
		   
		   for(ObjectCard c:player.getAvatar().getMyCards()) {
			   buttons.get(i).setText(c.getType().toString());
			   i++;
		   }
	}

	public void updateMovements(Player player) {
		this.text2.setText(null);
		int i = 0;
		for(Movement m:player.getAvatar().getMyMovements()) {
			this.text2.append(i + ")" + m.toString() + "\n");
			i++;
		}
		
		panelCentr.slidePlayerPosition(player.getAvatar().getCurrentSector().getCol(), player.getAvatar().getCurrentSector().getRow(),
				player.getAvatar().getCurrentSector().getClass().toString());
	}
	
	public void resetButton(int cardSelected, Player player) {
		 buttons.get(cardSelected + buttons.size()).setText("Use Card");
		 player.getAvatar().getMyCards().remove(cardSelected);
	}
	
	public void showUX(int[][] board, Queue<Event> toSend, it.polimi.ingsw.cg_38.model.Player player, Map map){
	      controlPanel.setBackground(Color.DARK_GRAY);
	      BorderLayout layout = new BorderLayout();
	      
	      panelSx = new JPanel();
	      
	      panelSx.setBackground(Color.DARK_GRAY);
	      panelSx.setLayout(new GridLayout(5,1));     
	      buttons.add(new JButton("Close\nClient"));
	      buttons.add(new JButton("FinishTurn"));
	      buttons.add(new JButton("Use card"));
	      buttons.add(new JButton("Use card"));
	      buttons.add(new JButton("Use card"));
	      
	      for(int i = 0; i<buttons.size(); i++) {
	    	  panelSx.add(buttons.get(i));
	      }
	      
	      panelDx = new JPanel();
	      
	      panelDx.setBackground(Color.DARK_GRAY);
	      panelDx.setLayout(new GridLayout(3,1)); 
	      
	      text1 = new JTextArea("Informazioni di gioco\n");
	      text1.setLineWrap(true);
	      scroll1 = new JScrollPane(text1);
	      text2 = new JTextArea("Movements\n");
	      text2.setLineWrap(true);
	      scroll2 = new JScrollPane(text2);
	      panelChat = new JPanel();
	      text3 = new JTextArea("Chat\n");
	      text3.setLineWrap(true);
	      scroll3 = new JScrollPane(text3);
	      input = new JTextField();
	      
	      panelChat.setLayout(new BorderLayout());
	      panelChat.add(scroll3, BorderLayout.CENTER);
	      panelChat.add(input, BorderLayout.PAGE_END);
	      text1.setBounds(0, 0, 270, 300);
	      panelDx.add(scroll1);
	      panelDx.add(scroll2);
	      panelDx.add(panelChat);  
	      
	      this.logger = new LoggerGUI(this.text1, this);
	      panelCentr = new HexGrid(board, toSend, player, map);
	      panelCentr.setLayout(null);
	      
	      controlPanel.setLayout(layout);        
		  
	      controlPanel.add(panelCentr, BorderLayout.CENTER);
	      
	      controlPanel.add(panelSx,BorderLayout.LINE_START); 
	      controlPanel.add(panelDx,BorderLayout.LINE_END);
	      
	      this.setVisible(true);  
	}
	
	public Sector askForMoveCoordinates(Map map) {
		String x = logger.showAndRead("x?", "ENTER COORDINATES:");
		
		while( x == null) {
			x = logger.showAndRead("x?", "RETRY:");
		}
		String y = logger.showAndRead("y?", "ENTER COORDINATES:");
		while(y == null) {
			y = logger.showAndRead("y?", "RETRY:");
		}
		
		Sector toMove = map.searchSectorByCoordinates(Integer.parseInt(x), Integer.parseInt(y));
		return toMove;
	}
	
	public void blockInterf(Boolean b) {
		for(JButton butt:this.buttons) {
			butt.setEnabled(!b);
		}
		for(GraphicSector sec:this.panelCentr.getSects()) {
			sec.setEnabled(!b);
		}
	}
	   
	public void addButtonActionListener(int index, ActionListener listener) {
		for (int i = 0; i < 5 ; i++ ){
			if(i == index) buttons.get(i).addActionListener(listener);
		}
	}
	
	public void paintHatch(Boolean bool, Sector sec) {
		panelCentr.hatchPainter(sec.getCol(), sec.getRow(), bool);
	}
	
	public String getConnection() {
		String connection = logger.showAndRead("Choose a type of connection:\n" + "Are available: [Socket] | [RMI] choose one of them !", "WELCOME TO EFTAIOS:");
		while(connection == null) {
			connection = logger.showAndRead("Choose a type of connection:\n" + "Are available: [Socket] | [RMI] choose one of them !", "WELCOME TO EFTAIOS:");
	    }
		return connection;
	}
}
