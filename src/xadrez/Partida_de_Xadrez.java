package xadrez;

import tabuleiro.Tabuleiro;

public class Partida_de_Xadrez {

	private Tabuleiro tabuleiro;

	public Partida_de_Xadrez() {
		tabuleiro = new Tabuleiro(8, 8);
	}

	public Peca_de_Xadrez[][] getPecas() {
		Peca_de_Xadrez[][] matriz = new Peca_de_Xadrez[tabuleiro.getLinhas()][tabuleiro.getColunas()];
		for (int i = 0; i < tabuleiro.getLinhas(); i++) {
			for (int j = 0; j < tabuleiro.getColunas(); j++) {
				matriz[i][j] = (Peca_de_Xadrez) tabuleiro.pecas(i, j);
			}
		}
		return matriz;

	}

}
