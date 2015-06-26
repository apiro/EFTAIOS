package it.polimi.ingsw.cg_38.client.gui;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 * Questo oggetto è l'implementazione grafica del settore, ha una riga ,una colonna, e due valori che indicano il
 * posizionamento assoluto sullo schermo
 * */
public class GraphicSector extends JLabel {

	private static final long serialVersionUID = 1L;
	private int row;
	private int col;
	private int x;
	private int y;
	
	/**
	 * @param row riga dell'HEXGRID dove si trova il settore 
	 * @param col colonna dell'HEXGRID dove si trova il settore 
	 * @param x prima coordinata assoluta dei posizionamento della jlabel sullo schermo
	 * @param y seconda coordinata assoluta dei posizionamento della jlabel sullo schermo
	 * @param i icona di sfondo della jlabel
	 * */
	public GraphicSector(int row, int col, int x, int y, ImageIcon i) {
		super(i);
		this.x = x;
		this.y = y;
		this.row = row;
		this.col = col;
	}

	public void position(){
		super.setBounds(this.x, this.y, 50, 50);
	}

	public int getRow() {
		return row;
	}

	public int getCol() {
		return col;
	}
}
