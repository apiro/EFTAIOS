package it.polimi.ingsw.cg_38.model;
import java.util.*;

/**
 * 
 */
public class GameModel {

    /**
     * 
     */
    public GameModel(String map) {
    	this.init(map);
    }

    public static void main( String[] args ) throws InterruptedException
    {
    	GameModel model = new GameModel("Galilei");
    	Sector alienStartingPoint = model.getGameMap().searchSectorByName("AlienStartingPoint");
    	//asp 5,11
    	model.getGamePlayers().add(new Player("Alberto"));
    	Player player = model.getGamePlayers().get(0);
    	Thread.sleep(3000);
    	model.getGamePlayers().get(0).setAvatar(new Alien(Name.Alien1));
    	Turn actualTurn = new Turn(player);
    	model.setActualTurn(actualTurn);
    	player.setNumTurniGiocati(player.getNumTurniGiocati());
    	Avatar avatar = model.getGamePlayers().get(0).getAvatar();
    	avatar.setCurrentSector(alienStartingPoint);
    	System.out.println(avatar.getCurrentSector().toString());
    	Card drown = avatar.draw(model.getDeckSector());
    	
    	Scanner in = new Scanner(System.in);
    	Boolean controller = true;
    	while(controller){
    		System.out.println("vuoi muovere ancora?");
    		String s = in.nextLine();
    		if(s.equals("no")) {
    			controller = false;
    		}    		
			System.out.println("X OF MOVE");
			int row = Integer.parseInt(in.nextLine());
			System.out.println("Y OF MOVE");
			int col = Integer.parseInt(in.nextLine());
			
			Sector sector = model.getGameMap().searchSectorByCoordinates(row, col);
	    	if(avatar.canMove(sector)) {
	    		avatar.move(sector, player.getNumTurniGiocati()+1);
	    		player.setNumTurniGiocati(player.getNumTurniGiocati()+1);
	    	}
	    	System.out.println(avatar.getMyMovements());
    	}
    }
    
    /**
     * 
     */
    public void init(String map){
    	this.setGameMap(MapCreator.createMap(map));
    	this.setDeckObject(DeckCreator.createDeck("ObjectDeck"));
    	this.setDeckHatch(DeckCreator.createDeck("HatchDeck"));
    	this.setDeckSector(DeckCreator.createDeck("SectorDeck"));
    	this.setGamePlayers(new ArrayList<Player>());
    }
    
    public ArrayList<Player> getGamePlayers() {
		return gamePlayers;
	}

	public void setGamePlayers(ArrayList<Player> gamePlayers) {
		this.gamePlayers = gamePlayers;
	}

	public Deck getDeckSector() {
		return deckSector;
	}

	public void setDeckSector(Deck deckSector) {
		this.deckSector = deckSector;
	}

	public Deck getDeckHatch() {
		return deckHatch;
	}

	public void setDeckHatch(Deck deckHatch) {
		this.deckHatch = deckHatch;
	}

	public Deck getDeckObject() {
		return deckObject;
	}

	public void setDeckObject(Deck deckObject) {
		this.deckObject = deckObject;
	}

	public Map getGameMap() {
		return gameMap;
	}

	public void setGameMap(Map gameMap) {
		this.gameMap = gameMap;
	}

	/**
     * 
     */
    private ArrayList<Player> gamePlayers;

    /**
     * 
     */
    private Map gameMap;

    /**
     * 
     */
    private Deck deckSector;

    /**
     * 
     */
    private Deck deckHatch;

    /**
     * 
     */
    private Deck deckObject;

    /**
     * 
     */
    private Turn actualTurn = null;

    /**
     * @return
     */
    public Player getNextPlayer() {
        // TODO implement here
    	for(int i = 0; i < this.getGamePlayers().size(); i++){
	    	 if(this.getGamePlayers().get(i).equals(actualTurn.getCurrentPlayer())) {
	    		  if(i+1<=7) {
	    			  return this.getGamePlayers().get(i+1);
	    		  } else return this.getGamePlayers().get(0);
	    	 }
    	} 
    	return null;
    }

	public Turn getActualTurn() {
		return actualTurn;
	}

	public void setActualTurn(Turn actualTurn) {
		this.actualTurn = actualTurn;
	}

    
}