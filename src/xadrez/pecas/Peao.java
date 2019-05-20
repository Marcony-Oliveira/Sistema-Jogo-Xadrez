package xadrez.pecas;

import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.Color;
import xadrez.Peca_de_Xadrez;

public class Peao extends Peca_de_Xadrez {

	public Peao(Tabuleiro tabuleiro, Color color) {
		super(tabuleiro, color);

	}
	
	@Override
	public String toString() {
		return "P";
	}

	@Override
	public boolean[][] movimentosPossiveis() {
		boolean[][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];

		Posicao p = new Posicao(0, 0);

		if (getColor() == Color.WHITE) {
			p.setValues(posicao.getLinha() - 1, posicao.getColuna());
			if (getTabuleiro().posicao_Existente(p) && !getTabuleiro().temUmaPecaNaPosicao(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
			p.setValues(posicao.getLinha() - 2, posicao.getColuna());
			Posicao p2 = new Posicao(posicao.getLinha() - 1, posicao.getColuna());
			if (getTabuleiro().posicao_Existente(p) && !getTabuleiro().temUmaPecaNaPosicao(p)
					&& getTabuleiro().posicao_Existente(p2) && !getTabuleiro().temUmaPecaNaPosicao(p2)
					&& getContagemMovimentos() == 0) {
				mat[p.getLinha()][p.getColuna()] = true;
			}

			p.setValues(posicao.getLinha() - 1, posicao.getColuna() - 1);
			if (getTabuleiro().posicao_Existente(p) && existePecaAdversaria(p)) {
				mat[p.getLinha()][p.getColuna()] = true;

			}
			p.setValues(posicao.getLinha() - 1, posicao.getColuna() + 1);
			if (getTabuleiro().posicao_Existente(p) && existePecaAdversaria(p)) {
				mat[p.getLinha()][p.getColuna()] = true;

			}
		} else {
			p.setValues(posicao.getLinha() + 1, posicao.getColuna());
			if (getTabuleiro().posicao_Existente(p) && !getTabuleiro().temUmaPecaNaPosicao(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
			p.setValues(posicao.getLinha() + 2, posicao.getColuna());
			Posicao p2 = new Posicao(posicao.getLinha() - 1, posicao.getColuna());
			if (getTabuleiro().posicao_Existente(p) && !getTabuleiro().temUmaPecaNaPosicao(p)
					&& getTabuleiro().posicao_Existente(p2) && !getTabuleiro().temUmaPecaNaPosicao(p2)
					&& getContagemMovimentos() == 0) {
				mat[p.getLinha()][p.getColuna()] = true;
			}

			p.setValues(posicao.getLinha() + 1, posicao.getColuna() - 1);
			if (getTabuleiro().posicao_Existente(p) && existePecaAdversaria(p)) {
				mat[p.getLinha()][p.getColuna()] = true;

			}
			p.setValues(posicao.getLinha() + 1, posicao.getColuna() + 1);
			if (getTabuleiro().posicao_Existente(p) && existePecaAdversaria(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
			
		}
		return mat;
		
	}
}
