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
    
    private Boolean finishGame = false;

	public static void main(String[] args) throws ParserConfigurationException, Exception {
    	
		Scanner in = new Scanner(System.in);
    	
    	GameController generalController = new GameController("Galilei", "myArena");
    	
    	generalController.setState(GameState.STARTING);
    	
    	generalController.getGameModel().getGameMap().printMap();
    
    	generalController.listenForIncomingConnections(in);
    	
    	for(Player pl:generalController.getGameModel().getGamePlayers()) {
    		generalController.getPcs().add(new PlayerController(pl));
    	}
    	
    	generalController.assignAvatars();
    	
    	generalController.setFirstTurn();
    	
    	generalController.setState(GameState.RUNNING);

    	
    	while(!generalController.getFinishGame()) {
    		System.out.println("Turno player:" + generalController.getCurrentPlayerController().getPlayer().getName() + " :\n");
    		System.out.println(generalController.getGameModel().getActualTurn().getCurrentPlayer().getAvatar().toString());
    		System.out.println("Inserisci il tipo di azione da compiere: \n");
    		System.out.println("\t 1) MOVE - M\n");
    		System.out.println("\t 2) DRAW - D\n");
    		System.out.println("\t 3) ATTACK - A\n");
    		System.out.println("\t 4) USE CARD - U\n");
    		System.out.println("\t 5) FINISH TURN - F\n");
    		
    		GameAction action = null;
    		System.out.println("Devi muovere obbligatoriamente !\n");
    		String command = in.nextLine();
    		
    		if(command.equals("M")) {
    			Sector toMove = generalController.askForMoveCoordinates(in);
    			action = ActionCreator.createAction("Move", toMove, null, generalController.getGameModel());
    			generalController.handleMoveAction(action, in);
    		} else if (command.equals("D")) {
    			action = ActionCreator.createAction("Draw", null, null, generalController.getGameModel());
    			generalController.handleDrawAction(action, in);
    		} else if (command.equals("A")) {
    			
    		} else if (command.equals("U")) {
    			
    			/*generalController.handleUseCardAction(action, in, card, sector);*/
    		} else if (command.equals("F") && generalController.getGameModel().getActualTurn().getHasMoved()) {
    			generalController.getGameModel().getActualTurn().getCurrentPlayer().finishTurn();
        		generalController.changeTurn();/******/
        		System.out.println("Turno terminato !\n");
    		} else {
    			System.out.println("ERROR\n");
    		}
    		/*
    		if(action.isPossible().equals(false)) {
    			System.out.println("Azione non permessa !\n");
    		} else {
    			System.out.println("Azione effettuata !\n");
    		}
    		*/
    	} 	
    }
    
    private void handleUseCardAction(GameAction action, Scanner in, Card card, Sector sector) {
		// TODO Auto-generated method stub
    	System.out.println("Inserire il tipo di carta che si vuole utilizzare: \n");
		System.out.println("\t 1) ATTACK-CARD | AC\n");
		System.out.println("\t 2) LIGHTS-CARD | LC\n");
		System.out.println("\t 3) SEDATIVES-CARD | SC\n");
		System.out.println("\t 4) TELEPORT-CARD | TC\n");
		System.out.println("\t 5) ADRENALINE-CARD | DC\n");
    	String type = in.nextLine();
    	if(type.equals("AC")) {
    		action = ActionCreator.createAction("UseAttackCard",
    				this.getCurrentPlayerController().getPlayer().getAvatar().getCurrentSector(),
    				card, this.getGameModel());
    		this.getCurrentPlayerController().performUserCommands(action);
    	} else if(type.equals("LC")) {
    		action = ActionCreator.createAction("UseLightsCard",
    				sector,
    				card, this.getGameModel());
    		this.getCurrentPlayerController().performUserCommands(action);
    		System.out.println("Player: " + this.getCurrentPlayerController().getPlayer().getName() + 
    				",Sector: " + this.getCurrentPlayerController().getPlayer().getAvatar().getCurrentSector().toString());
    			
    	} else if(type.equals("SC")) {
    		action = ActionCreator.createAction("UseSedativesCard",
    				null,
    				card, this.getGameModel());
    		this.getCurrentPlayerController().performUserCommands(action);
    	} else if(type.equals("TC")) {
    		action = ActionCreator.createAction("UseTeleportCard",
    				null,
    				card, this.getGameModel());
    		this.getCurrentPlayerController().performUserCommands(action);
    	} else if(type.equals("DC")) {
    		action = ActionCreator.createAction("UseAdrenalineCard",
    				null,
    				card, this.getGameModel());
    		this.getCurrentPlayerController().performUserCommands(action);
    	}
	}

	public Boolean getFinishGame() {
		return finishGame;
	}

	public void setFinishGame(Boolean finishGame) {
		this.finishGame = finishGame;
	}

	public void handleDrawAction(GameAction action, Scanner in) {
		// TODO Auto-generated method stub
    	
    	Object returned1 = this.getCurrentPlayerController().performUserCommands(action);
    	Object returned2; 
    	if(returned1 instanceof Card) {
    		returned2 = (Card)returned1;
    	} else {
    		returned2 = returned1;
    		return;
    	}
    	
    	if(returned2 instanceof SectorCard) {
    		if(((SectorCard) returned2).getType().equals(SectorCardType.Silence)) {
    			//genero azione corrispondente e la invio al playercontroller
    			GameAction action1 = ActionCreator.createAction("UseSilenceCard", null, null, this.getGameModel());
    			//playercontroller performa l'azione
    			this.getCurrentPlayerController().performUserCommands(action1);
    			//adesso il playercontroller riceve i settori in c
    		} else if(((SectorCard) returned2).getType().equals(SectorCardType.MySectorNoise)) {
    			//genero azione corrispondente e la invio al playercontroller
    			GameAction action2 = ActionCreator.createAction("UseMySectorNoise", null, null, this.getGameModel());
    			//playercontroller performa l'azione
    			this.getCurrentPlayerController().performUserCommands(action2);
    			//passo al gameController il settore dichiarato e il giocatore
    		} else if(((SectorCard) returned2).getType().equals(SectorCardType.RandomSectorNoise)) {
    			//genero azione corrispondente e la invio al playercontroller
    			Sector toNoise = askForMoveCoordinates(in);
    			GameAction action3 = ActionCreator.createAction("UseRandomSectorNoise", toNoise, null, this.getGameModel());
    			//playercontroller performa l'azione
    			this.getCurrentPlayerController().performUserCommands(action3);
    			//passo al gameController il settore dichiarato e il giocatore
    		}
    	} else {
    		if(((HatchCard) returned2).getColor().equals(HatchCardType.Red)) {
    			((Hatch)this.getCurrentPlayerController().getPlayer().getAvatar().getCurrentSector()).setIsOpen(false);
    		} else if (((HatchCard) returned2).getColor().equals(HatchCardType.Green)) {
    			this.getCurrentPlayerController().getPlayer().getAvatar().setIsWinner(EndState.WINNER);
    			((Hatch)this.getCurrentPlayerController().getPlayer().getAvatar().getCurrentSector()).setIsOpen(false);
    		}
    	}
		
	}

	public void askForAttackAction(Scanner in) {
		// TODO Auto-generated method stub
		
	}

	private void startTurn() {
		// TODO Auto-generated method stub
	}
	
	public Sector askForMoveCoordinates(Scanner in) {
		System.out.println("Inserire la riga :");
    	int userRow = Integer.parseInt(in.nextLine());
    	System.out.println("Inserire la colonna :");
    	int userCol = Integer.parseInt(in.nextLine());
    	Sector toMove = this.getGameModel().getGameMap().searchSectorByCoordinates(userRow, userCol);
    	return toMove;
	}
	
	public Object handleMoveAction(GameAction action, Scanner in) {
		// TODO Auto-generated method stub
    	return this.getCurrentPlayerController().performUserCommands(action);
    	
	}

	private ArrayList<PlayerController> pcs = new ArrayList<PlayerController>();
    
    public ArrayList<PlayerController> getPcs() {
		return pcs;
	}

    public PlayerController getCurrentPlayerController() {
    	for(PlayerController myPc: this.getPcs()) {
    		if(myPc.getPlayer().equals(this.getGameModel().getActualTurn().getCurrentPlayer())) {
    			return myPc;
    		}
    	}
    	return null;
    }
    
	public void setPcs(ArrayList<PlayerController> pcs) {
		this.pcs = pcs;
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
/*
	public ArrayList<Player> getMyPlayers() {
		return myPlayers;
	}

	public void setMyPlayers(ArrayList<Player> myPlayers) {
		this.myPlayers = myPlayers;
	}
*/
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
    /*public ArrayList<Player> myPlayers = new ArrayList<Player>();*/

    /**
     * @return
     */
    public void assignAvatars() {
        // TODO implement here
    	Collections.shuffle(getGameModel().getGamePlayers());
   		for(int i =0; i<this.getGameModel().getGamePlayers().size(); i++) {
   			int floor = this.getGameModel().getGamePlayers().size()/2;
   			if(i<floor) {
   				this.getGameModel().getGamePlayers().get(i).setAvatar(new Human(Name.valueOf("Human"+(i+1)), this.getGameModel().getGameMap().searchSectorByName("HumanStartingPoint")));
    		} else {
    			this.getGameModel().getGamePlayers().get(i).setAvatar(new Alien(Name.valueOf("Alien"+(i-floor+1)), this.getGameModel().getGameMap().searchSectorByName("AlienStartingPoint")));
    		}
   			System.out.println(this.getGameModel().getGamePlayers().get(i).getAvatar().getName());
    	}
   		Collections.shuffle(getGameModel().getGamePlayers());
    }
    
    /**
     * @param Player player 
     * @return
     */
    public void pushPlayerToGame(Player player) {
        // TODO implement here
    	/*this.getMyPlayers().add(player);*/
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
    	this.setFinishGame(true);
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
    		this.getGameModel().getGamePlayers().add(new Player(name));
    		if(this.getGameModel().getGamePlayers().size()==8) {
    			this.getTimer().cancel();
    			this.getTimer().purge();
    			controllMyLoop[0] = false;
    		}
    	}
    	System.out.println("THE GAME IS PASSING FROM STARTING TO RUNNING");
    }

    public void setFirstTurn() {
    	Turn actualTurn = new Turn(this.getGameModel().getGamePlayers().get(0));
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