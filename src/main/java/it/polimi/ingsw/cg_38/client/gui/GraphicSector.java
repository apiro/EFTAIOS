package it.polimi.ingsw.cg_38.client.gui;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class GraphicSector extends JLabel {

	private static final long serialVersionUID = 1L;
	private int row;
	private int col;
	private int x;
	private int y;
	
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
