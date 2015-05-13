package it.polimi.ingsw.cg_38.controller;
import  it.polimi.ingsw.cg_38.model.*;
import java.util.*;

/**
 * 
 */
public class GameController {

    /**
     * 
     */
    public GameController() {
    }
    /**
     * 
     */
    public String room;
    
    public String getRoom() {
		return room;
	}

	public void setRoom(String room) {
		this.room = room;
	}

	public ArrayList<Avatar> getAssignedAvatars() {
		return assignedAvatars;
	}

	public void setAssignedAvatars(ArrayList<Avatar> assignedAvatars) {
		this.assignedAvatars = assignedAvatars;
	}

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
     */
    public ArrayList<Avatar> assignedAvatars;

    /**
     * 
     */
    public GameState state;

    /**
     * 
     */
    public Timer timer;

    /**
     * 
     */
    public ArrayList<Player> myPlayers;








    /**
     * @return
     */
    public void assignAvatar() {
        // TODO implement here
    }

    /**
     * @param String map
     */
    public void GameController(String map) {
        // TODO implement here
    }

    /**
     * @param Player player 
     * @return
     */
    public void pushPlayerToGame(Player player) {
        // TODO implement here
    }

    /**
     * @return
     */
    public void startGame() {
        // TODO implement here
    }

    /**
     * @return
     */
    public void closeGame() {
        // TODO implement here
    }

    /**
     * @return
     */
    public void startTimer() {
        // TODO implement here
    }

    /**
     * @return
     */
    public Boolean checkInitState() {
        // TODO implement here
        return null;
    }

    /**
     * @return
     */
    public void changeTurn() {
        // TODO implement here
    }

    /**
     * @return
     */
    public void initGame() {
        // TODO implement here
    }

    /**
     * @return
     */
    public GameModel getGameModel() {
        // TODO implement here
        return null;
    }

}