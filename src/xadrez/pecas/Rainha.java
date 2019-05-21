package xadrez.pecas;

import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.Color;
import xadrez.Peca_de_Xadrez;

public class Rainha extends Peca_de_Xadrez {

	public Rainha(Tabuleiro tabuleiro, Color color) {
		super(tabuleiro, color);

	}

	@Override
	public String toString() {
		return "Q";
	}

	@Override
	public boolean[][] movimentosPossiveis() {
		boolean[][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];

		Posicao p = new Posicao(0, 0);

		// acima da peça
		p.setValues(posicao.getLinha() - 1, posicao.getColuna());
		while (getTabuleiro().posicao_Existente(p) && !getTabuleiro().temUmaPecaNaPosicao(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			p.setLinha(p.getLinha() - 1);
		}
		if (getTabuleiro().posicao_Existente(p) && existePecaAdversaria(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		// a esquerda da peça
		p.setValues(posicao.getLinha(), posicao.getColuna() - 1);
		while (getTabuleiro().posicao_Existente(p) && !getTabuleiro().temUmaPecaNaPosicao(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			p.setColuna(p.getColuna() - 1);
		}
		if (getTabuleiro().posicao_Existente(p) && existePecaAdversaria(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		// a direita da peça
		p.setValues(posicao.getLinha(), posicao.getColuna() + 1);
		while (getTabuleiro().posicao_Existente(p) && !getTabuleiro().temUmaPecaNaPosicao(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			p.setColuna(p.getColuna() + 1);
		}
		if (getTabuleiro().posicao_Existente(p) && existePecaAdversaria(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		// abaixo da peça
		p.setValues(posicao.getLinha() + 1, posicao.getColuna());
		while (getTabuleiro().posicao_Existente(p) && !getTabuleiro().temUmaPecaNaPosicao(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			p.setLinha(p.getLinha() + 1);
		}
		if (getTabuleiro().posicao_Existente(p) && existePecaAdversaria(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		// noroeste
		p.setValues(posicao.getLinha() - 1, posicao.getColuna() - 1);
		while (getTabuleiro().posicao_Existente(p) && !getTabuleiro().temUmaPecaNaPosicao(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			p.setValues(p.getLinha() - 1, p.getColuna() - 1);
		}
		if (getTabuleiro().posicao_Existente(p) && existePecaAdversaria(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		// nordeste
		p.setValues(posicao.getLinha() - 1, posicao.getColuna() + 1);
		while (getTabuleiro().posicao_Existente(p) && !getTabuleiro().temUmaPecaNaPosicao(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			p.setValues(p.getLinha() - 1, p.getColuna() + 1);
		}
		if (getTabuleiro().posicao_Existente(p) && existePecaAdversaria(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		// sudeste
		p.setValues(posicao.getLinha() + 1, posicao.getColuna() + 1);
		while (getTabuleiro().posicao_Existente(p) && !getTabuleiro().temUmaPecaNaPosicao(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			p.setValues(p.getLinha() + 1, p.getColuna() + 1);
		}
		if (getTabuleiro().posicao_Existente(p) && existePecaAdversaria(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		// sudoeste
		p.setValues(posicao.getLinha() + 1, posicao.getColuna() - 1);
		while (getTabuleiro().posicao_Existente(p) && !getTabuleiro().temUmaPecaNaPosicao(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			p.setValues(p.getLinha() + 1, p.getColuna() - 1);
		}
		if (getTabuleiro().posicao_Existente(p) && existePecaAdversaria(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		return mat;
	}

}
