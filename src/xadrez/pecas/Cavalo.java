package xadrez.pecas;

import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.Color;
import xadrez.Peca_de_Xadrez;

public class Cavalo extends Peca_de_Xadrez {
	
	public Cavalo(Tabuleiro tabuleiro, Color color) {
		super(tabuleiro, color);
		
	}

	@Override
	public String toString() {
		return "C";
	}

	private boolean podeMover(Posicao posicao) {
		Peca_de_Xadrez p = (Peca_de_Xadrez) getTabuleiro().peca(posicao);
		return p == null || p.getColor() != getColor();

	}

	@Override
	public boolean[][] movimentosPossiveis() {
		boolean[][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];
		Posicao p = new Posicao(0, 0);

		
		p.setValues(posicao.getLinha() - 1, posicao.getColuna() - 2);
		if (getTabuleiro().posicao_Existente(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		
		p.setValues(posicao.getLinha() - 2, posicao.getColuna() - 1);
		if (getTabuleiro().posicao_Existente(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		
		p.setValues(posicao.getLinha() - 2, posicao.getColuna() + 1);
		if (getTabuleiro().posicao_Existente(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		
		p.setValues(posicao.getLinha() - 1, posicao.getColuna() + 2);
		if (getTabuleiro().posicao_Existente(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		
		p.setValues(posicao.getLinha() + 1, posicao.getColuna() + 2);
		if (getTabuleiro().posicao_Existente(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		
		p.setValues(posicao.getLinha() + 2, posicao.getColuna() + 1);
		if (getTabuleiro().posicao_Existente(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		
		p.setValues(posicao.getLinha() + 2, posicao.getColuna() - 1);
		if (getTabuleiro().posicao_Existente(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		
		p.setValues(posicao.getLinha() + 1, posicao.getColuna() - 2);
		if (getTabuleiro().posicao_Existente(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		return mat;
	}

}
