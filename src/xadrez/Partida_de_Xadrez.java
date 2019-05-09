package xadrez;

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
	
	private void coloqueUmaNovaPeca(char coluna, int linha,Peca_de_Xadrez peca) {
		tabuleiro.lugar_da_Peca(peca, new XadrezPosicao(coluna,linha).toPosicao());
		
	}
	
	private void config_Inicial() {
		coloqueUmaNovaPeca('b',6,new Torre(tabuleiro,Color.WHITE));
		coloqueUmaNovaPeca('e',8,new Rei(tabuleiro,Color.BLACK));
		coloqueUmaNovaPeca('e',1,new Rei(tabuleiro,Color.WHITE));
	}

}
