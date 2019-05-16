package xadrez;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import tabuleiro.Peca;
import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.pecas.Rei;
import xadrez.pecas.Torre;

public class Partida_de_Xadrez {

	private Tabuleiro tabuleiro;
	private int turno;
	private Color corJogadorAtual;
	private boolean check;
	private boolean checkMate;

	private List<Peca> pecasNoTabuleiro = new ArrayList<>();
	private List<Peca> pecasCapturadas = new ArrayList<>();

	public Partida_de_Xadrez() {
		tabuleiro = new Tabuleiro(8, 8);
		turno = 1;
		corJogadorAtual = Color.WHITE;
		config_Inicial();
	}

	public int getTurno() {
		return turno;
	}

	public Color getCorJogadorAtual() {
		return corJogadorAtual;
	}

	public boolean getCheck() {
		return check;
	}

	public boolean getCheckMate() {
		return checkMate;
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

	public boolean[][] possiveisMovimentos(XadrezPosicao posicaoDeOrigem) {
		Posicao posicao = posicaoDeOrigem.toPosicao();
		validacaoPosicaoDeOrigem(posicao);
		return tabuleiro.peca(posicao).movimentosPossiveis();

	}

	public Peca_de_Xadrez realizarMovimentoDeXadrez(XadrezPosicao posicaoOrigem, XadrezPosicao posicaoDestino) {
		Posicao origem = posicaoOrigem.toPosicao();
		Posicao destino = posicaoDestino.toPosicao();
		validacaoPosicaoDeOrigem(origem);
		validacaoPosicaoDeDestino(origem, destino);
		Peca pecaCapturada = fazerMover(origem, destino);

		if (testeCheck(corJogadorAtual)) {
			desfazerMovimento(origem, destino, pecaCapturada);
			throw new XadrezExceptions("Você não pode se colocar em Check!");
		}

		check = (testeCheck(oponente(corJogadorAtual))) ? true : false;

		if (testeCheckMate(oponente(corJogadorAtual))) {
			checkMate = true;
		} 
		else {
			nextTurno();
		}

		return (Peca_de_Xadrez) pecaCapturada;
	}

	private void validacaoPosicaoDeOrigem(Posicao posicao) {
		if (!tabuleiro.temUmaPecaNaPosicao(posicao)) {
			throw new XadrezExceptions("Não existe peça na posição de origem!");
		}
		if (corJogadorAtual != ((Peca_de_Xadrez) tabuleiro.peca(posicao)).getColor()) {
			throw new XadrezExceptions("A peça escolhida não é sua!");
		}
		if (!tabuleiro.peca(posicao).existeAlgumMovimentoPossivel()) {
			throw new XadrezExceptions("Não existe movimentos possíveis para a peça escolhida.");
		}
	}

	private void validacaoPosicaoDeDestino(Posicao origem, Posicao destino) {
		if (!tabuleiro.peca(origem).movimentoPossivel(destino)) {
			throw new XadrezExceptions("A peça escolhida não pode se mover para a posição de destino.");
		}

	}

	private void nextTurno() {
		turno++;
		corJogadorAtual = (corJogadorAtual == Color.WHITE) ? Color.BLACK : Color.WHITE;
	}

	private Peca fazerMover(Posicao origem, Posicao destino) {
		Peca p = tabuleiro.removePeca(origem);
		Peca pecaCapturada = tabuleiro.removePeca(destino);
		tabuleiro.lugar_da_Peca(p, destino);

		if (pecaCapturada != null) {
			pecasNoTabuleiro.remove(pecaCapturada);
			pecasCapturadas.add(pecaCapturada);
		}

		return pecaCapturada;

	}

	private void desfazerMovimento(Posicao origem, Posicao destino, Peca pecaCapturada) {
		Peca p = tabuleiro.removePeca(destino);
		tabuleiro.lugar_da_Peca(p, origem);

		if (pecaCapturada != null) {
			tabuleiro.lugar_da_Peca(pecaCapturada, destino);
			pecasCapturadas.remove(pecaCapturada);
			pecasNoTabuleiro.add(pecaCapturada);
		}
	}

	private Color oponente(Color color) {
		return (color == Color.WHITE) ? Color.BLACK : Color.WHITE;

	}

	private Peca_de_Xadrez rei(Color color) {
		List<Peca> list = pecasNoTabuleiro.stream().filter(x -> ((Peca_de_Xadrez) x).getColor() == color)
				.collect(Collectors.toList());
		for (Peca p : list) {
			if (p instanceof Rei) {
				return (Peca_de_Xadrez) p;
			}
		}
		throw new IllegalStateException("Não existe o Rei da cor " + color + " no tabuleiro.");
	}

	private boolean testeCheck(Color color) {
		Posicao posicaoDoRei = rei(color).getXadrezPosicao().toPosicao();
		List<Peca> pecasDoOponente = pecasNoTabuleiro.stream().filter(x -> ((Peca_de_Xadrez) x).getColor() == oponente(color)).collect(Collectors.toList());
		for (Peca p : pecasDoOponente) {
			boolean[][] mat = p.movimentosPossiveis();
			if (mat[posicaoDoRei.getLinha()][posicaoDoRei.getColuna()]) {
				return true;
			}
		}
		return false;
	}

	private boolean testeCheckMate(Color color) {
		if (!testeCheck(color)) {
			return false;
		}
		List<Peca> list = pecasNoTabuleiro.stream().filter(x -> ((Peca_de_Xadrez) x).getColor() == oponente(color))
				.collect(Collectors.toList());
		for (Peca p : list) {
			boolean[][] mat = p.movimentosPossiveis();
			for (int i = 0; i < tabuleiro.getLinhas(); i++) {
				for (int j = 0; j < tabuleiro.getColunas(); j++) {
					if (mat[i][j]) {
						Posicao origem = ((Peca_de_Xadrez) p).getXadrezPosicao().toPosicao();
						Posicao destino = new Posicao(i, j);
						Peca pecaCapturada = fazerMover(origem, destino);
						boolean testeCheck = testeCheck(color);
						desfazerMovimento(origem, destino, pecaCapturada);
						if (!testeCheck) {
							return false;
						}
					}
				}

			}
		}
		return true;
	}

	private void coloqueUmaNovaPeca(char coluna, int linha, Peca_de_Xadrez peca) {
		tabuleiro.lugar_da_Peca(peca, new XadrezPosicao(coluna, linha).toPosicao());
		pecasNoTabuleiro.add(peca);

	}

	private void config_Inicial() {
		
		coloqueUmaNovaPeca('h', 7, new Torre(tabuleiro, Color.WHITE));
		coloqueUmaNovaPeca('d', 1, new Torre(tabuleiro, Color.WHITE));
        coloqueUmaNovaPeca('e', 1, new Rei(tabuleiro, Color.WHITE));

        coloqueUmaNovaPeca('b', 8, new Torre(tabuleiro, Color.BLACK));
        coloqueUmaNovaPeca('a', 8, new Rei(tabuleiro, Color.BLACK));
	}

}
