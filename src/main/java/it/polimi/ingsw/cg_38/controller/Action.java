package it.polimi.ingsw.cg_38.controller;
import  it.polimi.ingsw.cg_38.model.*;
import java.util.*;

/**
 * 
 */
public abstract class Action {

    /**
     * 
     */
    public Action() {
    }

    /**
     * 
     */
    public GameModel gameModel;

    /**
     * @return
     */
    public abstract Object perform();

    /**
     * @return
     */
    public abstract Boolean isPossible();

	public GameModel getGameModel() {
		return gameModel;
	}

	public void setGameModel(GameModel gameModel) {
		this.gameModel = gameModel;
	}
}