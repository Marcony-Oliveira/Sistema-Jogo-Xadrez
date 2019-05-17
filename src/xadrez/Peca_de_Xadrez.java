package xadrez;

import tabuleiro.Peca;
import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;

public abstract class Peca_de_Xadrez extends Peca {
	
	private Color color;
	private int contagemMovimentos;
	

	public Peca_de_Xadrez(Tabuleiro tabuleiro, Color color) {
		super(tabuleiro);
		this.color = color;
	}

	public Color getColor() {
		return color;
	}
	
	public int getContagemMovimentos() {
		return contagemMovimentos;
	}
	
	public void incrementaContagemMovimentos() {
		contagemMovimentos++;
	}
	
	public void decrementaContagemMovimentos() {
		contagemMovimentos--;
	}
	
	protected boolean existePecaAdversaria(Posicao posicao) {
		Peca_de_Xadrez p = (Peca_de_Xadrez)getTabuleiro().peca(posicao);
		return p != null && p.getColor() != color;
	}

	
	public XadrezPosicao getXadrezPosicao() {
		return XadrezPosicao.dePosicao(posicao);
		
	}
	

}
