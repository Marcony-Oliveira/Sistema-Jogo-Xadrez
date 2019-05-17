package xadrez;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import tabuleiro.Tabuleiro;
import tabuleiro.Peca;
import tabuleiro.Posicao;
import xadrez.pecas.Rei;
import xadrez.pecas.Torre;

public class Partida_de_Xadrez {

	private int turn;
	private Color currentPlayer;
	private Tabuleiro board;
	private boolean check;
	private boolean checkMate;
	
	private List<Peca> piecesOnTheBoard = new ArrayList<>();
	private List<Peca> capturedPieces = new ArrayList<>();
	
	public Partida_de_Xadrez() {
		board = new Tabuleiro(8, 8);
		turn = 1;
		currentPlayer = Color.WHITE;
		config_Inicial();
	}
	
	public int getTurn() {
		return turn;
	}
	
	public Color getCurrentPlayer() {
		return currentPlayer;
	}
	
	public boolean getCheck() {
		return check;
	}
	
	public boolean getCheckMate() {
		return checkMate;
	}
	
	public Peca_de_Xadrez[][] getPieces() {
		Peca_de_Xadrez[][] mat = new Peca_de_Xadrez[board.getLinhas()][board.getColunas()];
		for (int i=0; i<board.getLinhas(); i++) {
			for (int j=0; j<board.getColunas(); j++) {
				mat[i][j] = (Peca_de_Xadrez) board.pecas(i, j);
			}
		}
		return mat;
	}
	
	public boolean[][] possibleMoves(XadrezPosicao sourcePosition) {
		Posicao position = sourcePosition.toPosicao();
		validateSourcePosition(position);
		return board.peca(position).movimentosPossiveis();
	}
	
	public Peca_de_Xadrez performChessMove(XadrezPosicao sourcePosition, XadrezPosicao targetPosition) {
		Posicao source = sourcePosition.toPosicao();
		Posicao target = targetPosition.toPosicao();
		validateSourcePosition(source);
		validateTargetPosition(source, target);
		Peca capturedPiece = makeMove(source, target);
		
		if (testCheck(currentPlayer)) {
			undoMove(source, target, capturedPiece);
			throw new XadrezExceptions("You can't put yourself in check");
		}
		
		check = (testCheck(opponent(currentPlayer))) ? true : false;

		if (testCheckMate(opponent(currentPlayer))) {
			checkMate = true;
		}
		else {
			nextTurn();
		}
		
		return (Peca_de_Xadrez)capturedPiece;
	}
	
	private Peca makeMove(Posicao source, Posicao target) {
		Peca p = board.removePeca(source);
		Peca capturedPiece = board.removePeca(target);
		board.lugar_da_Peca(p, target);
		
		if (capturedPiece != null) {
			piecesOnTheBoard.remove(capturedPiece);
			capturedPieces.add(capturedPiece);
		}
		
		return capturedPiece;
	}
	
	private void undoMove(Posicao source, Posicao target, Peca capturedPiece) {
		Peca p = board.removePeca(target);
		board.lugar_da_Peca(p, source);
		
		if (capturedPiece != null) {
			board.lugar_da_Peca(capturedPiece, target);
			capturedPieces.remove(capturedPiece);
			piecesOnTheBoard.add(capturedPiece);
		}
	}
	
	private void validateSourcePosition(Posicao position) {
		if (!board.temUmaPecaNaPosicao(position)) {
			throw new XadrezExceptions("There is no piece on source position");
		}
		if (currentPlayer != ((Peca_de_Xadrez)board.peca(position)).getColor()) {
			throw new XadrezExceptions("The chosen piece is not yours");
		}
		if (!board.peca(position).existeAlgumMovimentoPossivel()) {
			throw new XadrezExceptions("There is no possible moves for the chosen piece");
		}
	}
	
	private void validateTargetPosition(Posicao source, Posicao target) {
		if (!board.peca(source).movimentoPossivel(target)) {
			throw new XadrezExceptions("The chosen piece can't move to target position");
		}
	}
	
	private void nextTurn() {
		turn++;
		currentPlayer = (currentPlayer == Color.WHITE) ? Color.BLACK : Color.WHITE;
	}
	
	private Color opponent(Color color) {
		return (color == Color.WHITE) ? Color.BLACK : Color.WHITE;
	}
	
	private Peca_de_Xadrez king(Color color) {
		List<Peca> list = piecesOnTheBoard.stream().filter(x -> ((Peca_de_Xadrez)x).getColor() == color).collect(Collectors.toList());
		for (Peca p : list) {
			if (p instanceof Rei) {
				return (Peca_de_Xadrez)p;
			}
		}
		throw new IllegalStateException("There is no " + color + " king on the board");
	}
	
	private boolean testCheck(Color color) {
		Posicao kingPosition = king(color).getXadrezPosicao().toPosicao();
		List<Peca> opponentPieces = piecesOnTheBoard.stream().filter(x -> ((Peca_de_Xadrez)x).getColor() == opponent(color)).collect(Collectors.toList());
		for (Peca p : opponentPieces) {
			boolean[][] mat = p.movimentosPossiveis();
			if (mat[kingPosition.getLinha()][kingPosition.getColuna()]) {
				return true;
			}
		}
		return false;
	}
	
	private boolean testCheckMate(Color color) {
		if (!testCheck(color)) {
			return false;
		}
		List<Peca> list = piecesOnTheBoard.stream().filter(x -> ((Peca_de_Xadrez)x).getColor() == color).collect(Collectors.toList());
		for (Peca p : list) {
			boolean[][] mat = p.movimentosPossiveis();
			for (int i=0; i<board.getLinhas(); i++) {
				for (int j=0; j<board.getColunas(); j++) {
					if (mat[i][j]) {
						Posicao source = ((Peca_de_Xadrez)p).getXadrezPosicao().toPosicao();
						Posicao target = new Posicao(i, j);
						Peca capturedPiece = makeMove(source, target);
						boolean testCheck = testCheck(color);
						undoMove(source, target, capturedPiece);
						if (!testCheck) {
							return false;
						}
					}
				}
			}
		}
		return true;
	}	
	
	private void placeNewPiece(char column, int row, Peca_de_Xadrez piece) {
		board.lugar_da_Peca(piece, new XadrezPosicao(column, row).toPosicao());
		piecesOnTheBoard.add(piece);
	}
	
	private void config_Inicial() {
		
	        placeNewPiece('h', 7, new Torre(board, Color.WHITE));
	        placeNewPiece('d', 1, new Torre(board, Color.WHITE));
	        placeNewPiece('e', 1, new Rei(board, Color.WHITE));

	        placeNewPiece('b', 8, new Torre(board, Color.BLACK));
	        placeNewPiece('a', 8, new Rei(board, Color.BLACK));
		}
	
	}


/*package xadrez;

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
		} else {
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
		List<Peca> pecasDoOponente = pecasNoTabuleiro.stream()
				.filter(x -> ((Peca_de_Xadrez) x).getColor() == oponente(color)).collect(Collectors.toList());
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

		coloqueUmaNovaPeca('h', 6, new Torre(tabuleiro, Color.WHITE));
		coloqueUmaNovaPeca('b', 1, new Torre(tabuleiro, Color.WHITE));
		coloqueUmaNovaPeca('e', 1, new Rei(tabuleiro, Color.WHITE));

		coloqueUmaNovaPeca('b', 8, new Torre(tabuleiro, Color.BLACK));
		coloqueUmaNovaPeca('a', 8, new Rei(tabuleiro, Color.BLACK));
	}

} */
