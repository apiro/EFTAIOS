package it.polimi.ingsw.cg_38.model;
import java.util.*;

/**
 * 
 * codifica: 
 * 	-1-> vuota 
 * 	0->safe 
 *	1-> dangerous 
 *	2->hsp 
 *	3->asp 
 *	4->hatch 
 *
 * elemento [i,j] della matrice n*n con "i" riga e "j" colonna: dove j lettera vicini: 
 *  [i-1,j] [i+1,j] [i, j+1] [i, j-1] [i+1,j+1] [i+1,j-1]
 * 
 */
public abstract class Map {

    /**
     * 
     */
    public Map() {
    }

    /**
     * 
     */
    public Sector[][] table;
    public final int height = 14;
    public final int width = 23;

    /**
     * @param Sector sector
     */
    public void searchTargetSector(Sector sector) {
        // TODO implement here
    }

}