package xadrez.pecas;

import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.Color;
import xadrez.Partida_de_Xadrez;
import xadrez.Peca_de_Xadrez;

public class Rei extends Peca_de_Xadrez {
	
	private Partida_de_Xadrez partida_de_xadrez;

	public Rei(Tabuleiro tabuleiro, Color color,Partida_de_Xadrez partida_de_xadrez) {
		super(tabuleiro, color);
		this.partida_de_xadrez = partida_de_xadrez;
	}

	@Override
	public String toString() {
		return "R";
	}

	private boolean podeMover(Posicao posicao) {
		Peca_de_Xadrez p = (Peca_de_Xadrez) getTabuleiro().peca(posicao);
		return p == null || p.getColor() != getColor();

	}
	
	private boolean testeRookCastling (Posicao posicao) {
		Peca_de_Xadrez p = (Peca_de_Xadrez)getTabuleiro().peca(posicao);
		return p != null && p instanceof Torre && p.getColor() == getColor() && p.getContagemMovimentos() == 0;
	}

	@Override
	public boolean[][] movimentosPossiveis() {
		boolean[][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];
		Posicao p = new Posicao(0, 0);

		// acima da peça
		p.setValues(posicao.getLinha() - 1, posicao.getColuna());
		if (getTabuleiro().posicao_Existente(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		// abaixo da peça
		p.setValues(posicao.getLinha() + 1, posicao.getColuna());
		if (getTabuleiro().posicao_Existente(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		// a esquerda da peça
		p.setValues(posicao.getLinha(), posicao.getColuna() - 1);
		if (getTabuleiro().posicao_Existente(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		// a direita da peça
		p.setValues(posicao.getLinha(), posicao.getColuna() + 1);
		if (getTabuleiro().posicao_Existente(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		// a noroeste da peça
		p.setValues(posicao.getLinha() - 1, posicao.getColuna() - 1);
		if (getTabuleiro().posicao_Existente(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		// a nordeste da peça
		p.setValues(posicao.getLinha() - 1, posicao.getColuna() + 1);
		if (getTabuleiro().posicao_Existente(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		// a sudoeste da peça
		p.setValues(posicao.getLinha() + 1, posicao.getColuna() - 1);
		if (getTabuleiro().posicao_Existente(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		// a sudeste da peça
		p.setValues(posicao.getLinha() + 1, posicao.getColuna() + 1);
		if (getTabuleiro().posicao_Existente(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		//Jogada especial Castling
		if (getContagemMovimentos() == 0 && !partida_de_xadrez.getCheck()) {
			//Jogada especial Castling mais próximo do Rei
			Posicao posicaoT1 = new Posicao (posicao.getLinha(), posicao.getColuna() +3);
			if(testeRookCastling(posicaoT1));
			Posicao p1 = new Posicao (posicao.getLinha(), posicao.getColuna() + 1);
			Posicao p2 = new Posicao (posicao.getLinha(), posicao.getColuna() + 2);
			if(getTabuleiro().peca(p1) == null && getTabuleiro().peca(p2) == null) {
				mat[posicao.getLinha()][posicao.getColuna() + 2] = true;
			}
		}
		if (getContagemMovimentos() == 0 && !partida_de_xadrez.getCheck()) {
			//Jogada especial Castling mais distante do Rei
			Posicao posicaoT2 = new Posicao (posicao.getLinha(), posicao.getColuna() - 4);
			if(testeRookCastling(posicaoT2));
			Posicao p1 = new Posicao (posicao.getLinha(), posicao.getColuna() - 1);
			Posicao p2 = new Posicao (posicao.getLinha(), posicao.getColuna() - 2);
			Posicao p3 = new Posicao (posicao.getLinha(), posicao.getColuna() - 3);
			if(getTabuleiro().peca(p1) == null && getTabuleiro().peca(p2) == null  && getTabuleiro().peca(p3) == null) {
				mat[posicao.getLinha()][posicao.getColuna() - 2] = true;
			}
		}

		return mat;
	}

}
