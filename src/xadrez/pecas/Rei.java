package xadrez.pecas;

import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.Color;
import xadrez.Peca_de_Xadrez;

public class Rei extends Peca_de_Xadrez {

	public Rei(Tabuleiro tabuleiro, Color color) {
		super(tabuleiro, color);
	}

	@Override
	public String toString() {
		return "R";
	}

	private boolean podeMover(Posicao posicao) {
		Peca_de_Xadrez p = (Peca_de_Xadrez) getTabuleiro().peca(posicao);
		return p == null || p.getColor() != getColor();

	}

	@Override
	public boolean[][] movimentosPossiveis() {
		boolean[][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];
		Posicao p = new Posicao(0, 0);

		// acima da pe�a
		p.setValues(posicao.getLinha() - 1, posicao.getColuna());
		if (getTabuleiro().posicao_Existente(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		// abaixo da pe�a
		p.setValues(posicao.getLinha() + 1, posicao.getColuna());
		if (getTabuleiro().posicao_Existente(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		// a esquerda da pe�a
		p.setValues(posicao.getLinha(), posicao.getColuna() - 1);
		if (getTabuleiro().posicao_Existente(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		// a direita da pe�a
		p.setValues(posicao.getLinha(), posicao.getColuna() + 1);
		if (getTabuleiro().posicao_Existente(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		// a noroeste da pe�a
		p.setValues(posicao.getLinha() - 1, posicao.getColuna() - 1);
		if (getTabuleiro().posicao_Existente(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		// a nordeste da pe�a
		p.setValues(posicao.getLinha() - 1, posicao.getColuna() + 1);
		if (getTabuleiro().posicao_Existente(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		// a sudoeste da pe�a
		p.setValues(posicao.getLinha() + 1, posicao.getColuna() - 1);
		if (getTabuleiro().posicao_Existente(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		// a sudeste da pe�a
		p.setValues(posicao.getLinha() + 1, posicao.getColuna() + 1);
		if (getTabuleiro().posicao_Existente(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		return mat;
	}

}
