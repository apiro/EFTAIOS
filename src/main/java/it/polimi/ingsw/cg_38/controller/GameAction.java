package it.polimi.ingsw.cg_38.controller;
import  it.polimi.ingsw.cg_38.model.*;
import java.util.*;

/**
 * 
 * il playercontroller riceve azioni e le performa. anche se il playercontroller non ha riferimento al modello su cui
 * performare puo performare azioni lo stesso perche le azioni hanno un riferimento al gamemodel
 * 
 * le action sono sempre riferite al currentTurn
 * 
 */
public abstract class GameAction {

    /**
     * 
     */
    public GameAction(GameModel gameModel) {
    	this.setGameModel(gameModel);
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

	public String currentAvatarType(){
		if(this.getGameModel().getActualTurn().getCurrentPlayer().getAvatar() instanceof Alien) {
			return "Alien";
		} else {
			return "Human";
		}
	}
	
	public void setGameModel(GameModel gameModel) {
		this.gameModel = gameModel;
	}
}