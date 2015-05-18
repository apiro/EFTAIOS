package it.polimi.ingsw.cg_38.controller;
import  it.polimi.ingsw.cg_38.model.*;

import java.util.*;

import javax.xml.parsers.ParserConfigurationException;

/**
 * 
 */
public class GameController {

    /**
     * @throws Exception 
     * @throws ParserConfigurationException 
     * 
     */
    public GameController(String type, String room) throws ParserConfigurationException, Exception {
    	
    	this.initGame(type, room);
    }

	public static void main(String[] args) throws ParserConfigurationException, Exception {
    	
		Scanner in = new Scanner(System.in);
    	
    	GameController generalController = new GameController("Galilei", "myArena");
    	generalController.getGameModel().getGameMap().printMap();
    
    	generalController.listenForIncomingConnections(in);
    	
    	for(Player pl:generalController.getMyPlayers()) {
    		generalController.getPc().add(new PlayerController(pl));
    	}
    	
    	generalController.assignAvatars();
    	
    	generalController.setFirstTurn();
    	
    	generalController.setState(GameState.RUNNING);

    	while(true) {
    		System.out.println("Inizia il turno del: \n -->");
    		System.out.println(generalController.getGameModel().getActualTurn().getCurrentPlayer().getAvatar().toString());
    		generalController.askForMoveAction(in);
    		System.out.println(generalController.getGameModel().getActualTurn().getCurrentPlayer().getAvatar().toString());
    		if(generalController.getGameModel().getActualTurn().getCurrentPlayer().getAvatar().getCurrentSector().getName().equals("Safe")) {
    			System.out.println("Ti sei mosso in un settore Safe, dunque passi il turno, vuoi attaccare? Per attaccare digita ATTACCARE");
    			if(in.nextLine().equals("ATTACCARE")) {
       			 generalController.askForAttackAction(in);
       			 break;
       		} else { break; }
    		} else {
    			System.out.println("Vuoi pescare una carta o attaccare? Per attaccare digita ATTACCARE ");
        		if(in.nextLine().equals("ATTACCARE")) {
        			 generalController.askForAttackAction(in);
        			 System.out.println(generalController.getGameModel().getActualTurn().getCurrentPlayer().getAvatar().toString());
        		} else {
        			generalController.askForDrawAction(in);
        			System.out.println(generalController.getGameModel().getActualTurn().getCurrentPlayer().getAvatar().toString());
        		}
    		}
    		generalController.changeTurn();
    	}    	
    }
    
    private void askForDrawAction(Scanner in) {
		// TODO Auto-generated method stub
    	GameAction action2 = ActionCreator.createAction("Draw", null, null, this.getGameModel());
    	
    	Card returned2 = (Card)this.getCurrentPlayerController().performUserCommands(action2);
    	if(returned2 instanceof SectorCard) {
    		if(((SectorCard) returned2).getType().equals(SectorCardType.Silence)) {
    			//genero azione corrispondente e la invio al playercontroller
    			GameAction action21 = ActionCreator.createAction("UseSilenceCard", null, null, this.getGameModel());
    			//playercontroller performa l'azione
    			this.getCurrentPlayerController().performUserCommands(action21);
    			//adesso il playercontroller riceve i settori in c
    		} else if(((SectorCard) returned2).getType().equals(SectorCardType.MySectorNoise)) {
    			//genero azione corrispondente e la invio al playercontroller
    			GameAction action22 = ActionCreator.createAction("UseMySectorNoise", null, null, this.getGameModel());
    			//playercontroller performa l'azione
    			this.getCurrentPlayerController().performUserCommands(action22);
    			//passo al gameController il settore dichiarato e il giocatore
    		} else if(((SectorCard) returned2).getType().equals(SectorCardType.RandomSectorNoise)) {
    			//genero azione corrispondente e la invio al playercontroller
    			Sector toNoise = this.getGameModel().getGameMap().searchSectorByCoordinates(3, 10);
    			toNoise.setCol(3);
    			toNoise.setRow(10);
    			GameAction action23 = ActionCreator.createAction("UseRandomSectorNoise", toNoise, null, this.getGameModel());
    			//playercontroller performa l'azione
    			this.getCurrentPlayerController().performUserCommands(action23);
    			//passo al gameController il settore dichiarato e il giocatore
    		}
    	}
		
	}

	private void askForAttackAction(Scanner in) {
		// TODO Auto-generated method stub
		
	}

	private void startTurn() {
		// TODO Auto-generated method stub
	}

	private void askForMoveAction(Scanner in) {
		// TODO Auto-generated method stub
		System.out.println("Devi muovere obbligatoriamente !");
    	System.out.println("Inserire la riga della mossa che si vuole effettuare:");
    	int userRow = Integer.parseInt(in.nextLine());
    	System.out.println("Inserire la colonna della mossa che si vuole effettuare:");
    	int userCol = Integer.parseInt(in.nextLine());
    	Sector toMove = this.getGameModel().getGameMap().searchSectorByCoordinates(userRow, userCol);
    	GameAction action1 = ActionCreator.createAction("Move", toMove, null, this.getGameModel());
    	
    	Boolean returned1 = (Boolean)this.getCurrentPlayerController().performUserCommands(action1);
    	System.out.println(this.getGameModel().getActualTurn().getCurrentPlayer().getAvatar().getCurrentSector());
	}

	private ArrayList<PlayerController> pc = new ArrayList<PlayerController>();
    
    public ArrayList<PlayerController> getPc() {
		return pc;
	}

    public PlayerController getCurrentPlayerController() {
    	for(PlayerController myPc: this.getPc()) {
    		if(myPc.getPlayer().equals(this.getGameModel().getActualTurn().getCurrentPlayer())) {
    			return myPc;
    		}
    	}
    	return null;
    }
    
	public void setPc(ArrayList<PlayerController> pc) {
		this.pc = pc;
	}

	/**
     * 
     */
    private String room;
    
    public String getRoom() {
		return room;
	}

	public void setRoom(String room) {
		this.room = room;
	}

	/*public ArrayList<Avatar> getAssignedAvatars() {
		return assignedAvatars;
	}

	public void setAssignedAvatars(ArrayList<Avatar> assignedAvatars) {
		this.assignedAvatars = assignedAvatars;
	}*/

	public GameState getState() {
		return state;
	}

	public void setState(GameState state) {
		this.state = state;
	}

	public Timer getTimer() {
		return timer;
	}

	public void setTimer(Timer timer) {
		this.timer = timer;
	}

	public ArrayList<Player> getMyPlayers() {
		return myPlayers;
	}

	public void setMyPlayers(ArrayList<Player> myPlayers) {
		this.myPlayers = myPlayers;
	}

	public void setGameModel(GameModel gameModel) {
		this.gameModel = gameModel;
	}
	/**
     * 
     */
    public GameModel gameModel;

    /**
     * 
     *
     * public ArrayList<Avatar> assignedAvatars;
     */

    /**
     * 
     */
    public GameState state = GameState.STARTING;

    /**
     * 
     */
    public Timer timer;

    /**
     * 
     */
    public ArrayList<Player> myPlayers = new ArrayList<Player>();

    /**
     * @return
     */
    public void assignAvatars() {
        // TODO implement here
    	Collections.shuffle(this.getMyPlayers());
   		for(int i =0; i<this.getMyPlayers().size(); i++) {
   			if(i<((int)this.getMyPlayers().size())/2) {
   				this.getMyPlayers().get(i).setAvatar(new Human(Name.valueOf("Human"+(i+1)), this.getGameModel().getGameMap().searchSectorByName("HumanStartingPoint")));
    		} else {
    			this.getMyPlayers().get(i).setAvatar(new Human(Name.valueOf("Alien"+(i+1)), this.getGameModel().getGameMap().searchSectorByName("AlienStartingPoint")));
    		}
    	}
    }
    
    /**
     * @param Player player 
     * @return
     */
    public void pushPlayerToGame(Player player) {
        // TODO implement here
    	this.getMyPlayers().add(player);
    	this.getGameModel().getGamePlayers().add(player);
    }

    /**
     * @return
     */
    public void startGame() {
        // TODO implement here
    	this.assignAvatars();
    }

    /**
     * @return
     */
    public void closeGame() {
        // TODO implement here
    	this.setState(GameState.FINISHED);
    }

    /**
     * @return
     */
    public void listenForIncomingConnections(Scanner in) {
        // TODO implement here
    	final Boolean[] controllMyLoop = {true};
    	timer.schedule(new TimerTask() {
    		@Override
    	    public void run() {
    			controllMyLoop[0] = false;
    	    }
    	} , 10000);
    	
    	while(controllMyLoop[0]) {
    		System.out.println("Inserire nome nuovo Player:");
    		String name = in.nextLine();
    		this.getMyPlayers().add(new Player(name));
    		if(this.getMyPlayers().size()==8) {
    			this.getTimer().cancel();
    			this.getTimer().purge();
    			controllMyLoop[0] = false;
    		}
    	}
    	System.out.println("THE GAME IS PASSING FROM STARTING TO RUNNING");
    }

    public void setFirstTurn() {
    	Turn actualTurn = new Turn(this.getMyPlayers().get(0));
    	this.getGameModel().setActualTurn(actualTurn);
    }
    
    /**
     * @return
     */
    public void changeTurn() {
        // TODO implement here
    	Turn newTurn = new Turn(this.getGameModel().getNextPlayer());
    	this.getGameModel().setActualTurn(newTurn);
    }

    /**
     * @return
     * @throws Exception 
     * @throws ParserConfigurationException 
     */
    public void initGame(String type, String room) throws ParserConfigurationException, Exception {
        // TODO implement here
    	this.setRoom(room);
    	this.setGameModel(new GameModel(type));
    	this.setTimer(new Timer());
    }

    /**
     * @return
     */
    public GameModel getGameModel() {
        // TODO implement here
        return gameModel;
    }

}