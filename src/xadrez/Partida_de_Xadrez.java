package xadrez;

import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.pecas.Rei;
import xadrez.pecas.Torre;

public class Partida_de_Xadrez {

	private Tabuleiro tabuleiro;

	public Partida_de_Xadrez() {
		tabuleiro = new Tabuleiro(8, 8);
		config_Inicial();
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
	
	private void config_Inicial() {
		tabuleiro.lugar_da_Peca(new Torre(tabuleiro,Color.WHITE),new Posicao(2,1));
		tabuleiro.lugar_da_Peca(new Rei(tabuleiro,Color.BLACK),new Posicao(0,4));
		tabuleiro.lugar_da_Peca(new Rei(tabuleiro,Color.WHITE),new Posicao(7,4));
	}

}
