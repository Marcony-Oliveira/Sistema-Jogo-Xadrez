package xadrez.pecas;

import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.Color;
import xadrez.Partida_de_Xadrez;
import xadrez.Peca_de_Xadrez;

public class Peao extends Peca_de_Xadrez {

	private Partida_de_Xadrez partidaDeXadrez;

	public Peao(Tabuleiro tabuleiro, Color color, Partida_de_Xadrez partidaDeXadrez) {
		super(tabuleiro, color);
		this.partidaDeXadrez = partidaDeXadrez;

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

			// Jogada especial ENPASSANT white
			if (posicao.getLinha() == 3) {
				Posicao esquerda = new Posicao(posicao.getLinha(), posicao.getColuna() - 1);
				if (getTabuleiro().posicao_Existente(esquerda) && existePecaAdversaria(esquerda)
						&& getTabuleiro().peca(esquerda) == partidaDeXadrez.enPassantVuneravel()) {
					mat[esquerda.getLinha() - 1][esquerda.getColuna()] = true;
				}
				Posicao direita = new Posicao(posicao.getLinha(), posicao.getColuna() + 1);
				if (getTabuleiro().posicao_Existente(direita) && existePecaAdversaria(direita)
						&& getTabuleiro().peca(direita) == partidaDeXadrez.enPassantVuneravel()) {
					mat[direita.getLinha() - 1][direita.getColuna()] = true;

				}

			}

		} else {
			p.setValues(posicao.getLinha() + 1, posicao.getColuna());
			if (getTabuleiro().posicao_Existente(p) && !getTabuleiro().temUmaPecaNaPosicao(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
			p.setValues(posicao.getLinha() + 2, posicao.getColuna());
			Posicao p2 = new Posicao(posicao.getLinha() + 1, posicao.getColuna());
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
			
			// Jogada especial ENPASSANT black
			if (posicao.getLinha() == 4) {
				Posicao esquerda = new Posicao(posicao.getLinha(), posicao.getColuna() - 1);
				if (getTabuleiro().posicao_Existente(esquerda) && existePecaAdversaria(esquerda)
						&& getTabuleiro().peca(esquerda) == partidaDeXadrez.enPassantVuneravel()) {
					mat[esquerda.getLinha() + 1][esquerda.getColuna()] = true;
				}
				Posicao direita = new Posicao(posicao.getLinha(), posicao.getColuna() + 1);
				if (getTabuleiro().posicao_Existente(direita) && existePecaAdversaria(direita)
						&& getTabuleiro().peca(direita) == partidaDeXadrez.enPassantVuneravel()) {
					mat[direita.getLinha() + 1][direita.getColuna()] = true;

				}

			}

		}
		return mat;

	}
}
