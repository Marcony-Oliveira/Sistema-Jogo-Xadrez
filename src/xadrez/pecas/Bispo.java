package xadrez.pecas;

import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.Color;
import xadrez.Peca_de_Xadrez;

public class Bispo extends Peca_de_Xadrez {
	
	public Bispo(Tabuleiro tabuleiro, Color color) {
		super(tabuleiro, color);
		
	}

	@Override
	public String toString() {
		return "B";
	}

	@Override
	public boolean[][] movimentosPossiveis() {
		boolean[][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];

		Posicao p = new Posicao(0, 0);

		//noroeste
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
