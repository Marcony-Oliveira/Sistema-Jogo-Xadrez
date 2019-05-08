package xadrez;

import tabuleiro.Peca;
import tabuleiro.Tabuleiro;

public class Peca_de_Xadrez extends Peca {
	
	private Color color;

	public Peca_de_Xadrez(Tabuleiro tabuleiro, Color color) {
		super(tabuleiro);
		this.color = color;
	}

	public Color getColor() {
		return color;
	}

	

}
