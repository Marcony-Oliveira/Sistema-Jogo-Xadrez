package xadrez;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import tabuleiro.Peca;
import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.pecas.Bispo;
import xadrez.pecas.Cavalo;
import xadrez.pecas.Peao;
import xadrez.pecas.Rainha;
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
		Peca_de_Xadrez p = (Peca_de_Xadrez)board.removePeca(source);
		p.incrementaContagemMovimentos();
		Peca capturedPiece = board.removePeca(target);
		board.lugar_da_Peca(p, target);
		
		if (capturedPiece != null) {
			piecesOnTheBoard.remove(capturedPiece);
			capturedPieces.add(capturedPiece);
		}
		
		return capturedPiece;
	}
	
	private void undoMove(Posicao source, Posicao target, Peca capturedPiece) {
		Peca_de_Xadrez p = (Peca_de_Xadrez)board.removePeca(target);
		p.decrementaContagemMovimentos();
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
		
		placeNewPiece('a', 1, new Torre(board, Color.WHITE));
		placeNewPiece('b', 1, new Cavalo(board, Color.WHITE));
        placeNewPiece('c', 1, new Bispo(board, Color.WHITE));
        placeNewPiece('d', 1, new Rainha(board, Color.WHITE));
        placeNewPiece('e', 1, new Rei(board, Color.WHITE));
        placeNewPiece('f', 1, new Bispo(board, Color.WHITE));
        placeNewPiece('g', 1, new Cavalo(board, Color.WHITE));
        placeNewPiece('h', 1, new Torre(board, Color.WHITE));
        placeNewPiece('a', 2, new Peao(board, Color.WHITE));
        placeNewPiece('b', 2, new Peao(board, Color.WHITE));
        placeNewPiece('c', 2, new Peao(board, Color.WHITE));
        placeNewPiece('d', 2, new Peao(board, Color.WHITE));
        placeNewPiece('e', 2, new Peao(board, Color.WHITE));
        placeNewPiece('f', 2, new Peao(board, Color.WHITE));
        placeNewPiece('g', 2, new Peao(board, Color.WHITE));
        placeNewPiece('h', 2, new Peao(board, Color.WHITE));

        placeNewPiece('a', 8, new Torre(board, Color.BLACK));
        placeNewPiece('b', 8, new Cavalo(board, Color.BLACK));
        placeNewPiece('c', 8, new Bispo(board, Color.BLACK));
        placeNewPiece('d', 8, new Rainha(board, Color.BLACK));
        placeNewPiece('e', 8, new Rei(board, Color.BLACK));
        placeNewPiece('f', 8, new Bispo(board, Color.BLACK));
        placeNewPiece('g', 8, new Cavalo(board, Color.BLACK));
        placeNewPiece('h', 8, new Torre(board, Color.BLACK));
        placeNewPiece('a', 7, new Peao(board, Color.BLACK));
        placeNewPiece('b', 7, new Peao(board, Color.BLACK));
        placeNewPiece('c', 7, new Peao(board, Color.BLACK));
        placeNewPiece('d', 7, new Peao(board, Color.BLACK));
        placeNewPiece('e', 7, new Peao(board, Color.BLACK));
        placeNewPiece('f', 7, new Peao(board, Color.BLACK));
        placeNewPiece('g', 7, new Peao(board, Color.BLACK));
        placeNewPiece('h', 7, new Peao(board, Color.BLACK));
		}
	
	}



