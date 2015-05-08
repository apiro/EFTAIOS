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
    public abstract void perform();

    /**
     * @return
     */
    public abstract Boolean isPossible();

    /**
     * @param GameModel gameModel 
     * @return
     */
    public void setGameModel(GameModel gameModel) {
        // TODO implement here
    }

}