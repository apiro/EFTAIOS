package it.polimi.ingsw.cg_38.model;
import java.util.*;

public class GamesModel {

    public GamesModel() {
    }

    public List<GameModel> getGames() {
		return games;
	}

	public void setGames(ArrayList<GameModel> games) {
		this.games = games;
	}

    private List<GameModel> games;

}