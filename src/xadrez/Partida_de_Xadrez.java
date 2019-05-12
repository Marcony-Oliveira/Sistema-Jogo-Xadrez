package xadrez;

import tabuleiro.Peca;
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
	
	public Peca_de_Xadrez realizarMovimentoDeXadrez(XadrezPosicao posicaoOrigem,XadrezPosicao posicaoDestino) {
		Posicao origem = posicaoOrigem.toPosicao();
		Posicao destino = posicaoDestino.toPosicao();
		validacaoPosicaoDeOrigem(origem);
		Peca pecaCapturada = fazerMover(origem,destino);
		return (Peca_de_Xadrez)pecaCapturada;
	}
	
	private void validacaoPosicaoDeOrigem(Posicao posicao) {
		if(!tabuleiro.temUmaPecaNaPosicao(posicao)) {
			throw new XadrezExceptions("Não existe peça na posição de origem!");
		}
	}
	
	private Peca fazerMover(Posicao origem, Posicao destino) {
		Peca p = tabuleiro.removePeca(origem);
		Peca pecaCapturada = tabuleiro.removePeca(destino);
		tabuleiro.lugar_da_Peca(p, destino);
		return pecaCapturada;
		
	}
	
	private void coloqueUmaNovaPeca(char coluna, int linha,Peca_de_Xadrez peca) {
		tabuleiro.lugar_da_Peca(peca, new XadrezPosicao(coluna,linha).toPosicao());
		
	}
	
	private void config_Inicial() {
		coloqueUmaNovaPeca('c', 1, new Torre(tabuleiro, Color.WHITE));
		coloqueUmaNovaPeca('c', 2, new Torre(tabuleiro, Color.WHITE));
		coloqueUmaNovaPeca('d', 2, new Torre(tabuleiro, Color.WHITE));
		coloqueUmaNovaPeca('e', 2, new Torre(tabuleiro, Color.WHITE));
		coloqueUmaNovaPeca('e', 1, new Torre(tabuleiro, Color.WHITE));
		coloqueUmaNovaPeca('d', 1, new Rei(tabuleiro, Color.WHITE));

		coloqueUmaNovaPeca('c', 7, new Torre(tabuleiro, Color.BLACK));
		coloqueUmaNovaPeca('c', 8, new Torre(tabuleiro, Color.BLACK));
		coloqueUmaNovaPeca('d', 7, new Torre(tabuleiro, Color.BLACK));
		coloqueUmaNovaPeca('e', 7, new Torre(tabuleiro, Color.BLACK));
		coloqueUmaNovaPeca('e', 8, new Torre(tabuleiro, Color.BLACK));
		coloqueUmaNovaPeca('d', 8, new Rei(tabuleiro, Color.BLACK));
	}
	
	

}
